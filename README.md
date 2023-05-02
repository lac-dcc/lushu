# Lushu

_Lushu_ (short for the Chinese 记录树, 录树), is a system that recognizes
infinite languages and redacts sensitive strings embedded into sentences of this
language. It is particularly useful to adjust running systems to data protection
laws, because it is capable of redacting sensitive information right at their
source.

## Running

### Simulate Lushu

Run `gradle fatJar` to generate the file `./Lushu/build/libs/Lushu.jar`. Run it
following the example:

```sh
cat example/log/test/cpf-is-sensitive.log | \
  java -jar ./Lushu/build/libs/Lushu.jar \
    ./example/config.yaml ./example/log/train/cpf-is-sensitive.log
```

You should see an output like the following:

```
Training Lushu Grammar with file './example/log/train/cpf-is-sensitive.log'
----------------------------------------
Training with log: The user <s>000.000.000-01</s> logged in on 2023-04-29 21:42:04.
Training with log: A new user <s>586.431.715-65</s> was created on 2023-04-30 12:48:53.
Training with log: The user <s>000.000.000-01</s> sent a message to user <s>417.231.715-86</s> on 2023-04-30 12:52:47.
Training with log: The product with ID RZbhCMwa was added to the cart by user <s>316.819.054-49</s> on 2023-04-30 12:53:36.
----------------------------------------
Finished training grammar

A new user ***** was created on 2023-04-30 13:16:51.
A payment of $1957800,00 was processed on 2023-04-30 13:16:51.
The user ***** downloaded video.mp4 on 2023-04-30 13:16:51.
...
```

### Generate example Lushu Grammar

Run `gradle grammarJar` to generate the file
`./Lushu/build/libs/Grammar.jar`. Run it following the example:

```sh
cat example/log/test/simple-ip.log | \
  java -jar ./Lushu/build/libs/Grammar.jar ./example/config.yaml
```

You should see an ouput like the following:

```
R0 :: [023]{4,4}[-]{1,1}[04]{2,2}[-]{1,1}[29]{2,2} | R1
R1 :: [0]{2,2}[:]{1,1}[0]{2,2}[:]{1,1}[0]{2,2}[,]{1,1}[123456789]{3,3} | R2
R2 :: [RScdeimov]{4,8} | R3
R3 :: [ehoqrstu]{5,7} | R4
R4 :: [acfmoryz]{4,5} | R5
R5 :: [0123456789]{1,3}[.]{1,1}[0123456789]{1,3}[.]{1,1}[0123456789]{1,3}[.]{1,1}[0123456789]{1,3} | [glo]{3,3} | R6
R6 :: [ehr]{4,4} | R7
R7 :: [abl]{3,3} | R8
R8 :: [abl]{3,3}
```

Note that the first production of the grammar in rule `R5` has the format of an
IP address. This is because the file `example/log/test/simple-ip.log` we gave as
an input contains examples of IP addresses at that position.

### Run the Merger

Run `gradle mergerJar` to generate the file `./Lushu/build/libs/Merger.jar`. Run
it following the example:

```sh
echo '8.8.8.8 0.0.0.0' | java -jar ./Lushu/build/libs/Merger.jar ./example/config.yaml
```

You should get the result:

```
[08]{1,1}[.]{1,1}[08]{1,1}[.]{1,1}[08]{1,1}[.]{1,1}[08]{1,1}
```

Notice that both IP addresses `8.8.8.8` and `0.0.0.0` were merged into a single
regular expression. Try different combinations, and different number of words!
Here are some more examples of words you can input:

- Date: `2023/03/26 2023/02/26 2023/12/11 1999/09/09`
- Timestamp: `00:00:00 12:34:56 12:34:57`
- Key in KV database: `key1#secondary key2#secondary`

Also, try specifying different YAML configuration files. You may find it easier
to edit the example file in `./example/config.yaml`.

## Testing

To test, run `gradle test`. Find all source code for the tests under
`./Lushu/src/test/`.
