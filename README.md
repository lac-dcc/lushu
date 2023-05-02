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
cat example/log/test/ip-is-sensitive.log | \
  java -jar ./Lushu/build/libs/Grammar.jar ./example/config.yaml
```

You should see an ouput like the following:

```
R0 :: [023]{4,4}[-]{1,1}[04]{2,2}[-]{1,1}[29]{2,2} | [CLMNPabceilmnoqrstu]{4,12} | R1
R1 :: [0]{2,2}[:]{1,1}[0]{2,2}[:]{1,1}[0]{2,2}[,]{1,1}[123456789]{3,3} | [aceilmnoprstuv]{3,9} | R2
R2 :: [RScdefilmorstv]{3,8} | [ams]{5,5}[,]{1,1} | R3
R3 :: [dehimoqrstu]{2,7} | [cior]{4,4}[,]{1,1} | R4
R4 :: [acefghilmnoprstuyz]{3,9} | [aemt]{4,4}[,]{1,1} | R5
R5 :: [0123456789]{1,3}[.]{1,1}[0123456789]{1,3}[.]{1,1}[0123456789]{1,3}[.]{1,1}[0123456789]{1,3} | [abcdegilnoprstu]{3,11} | [agilmoqu]{3,7}[!"#$%&'()*+,-./:;<=>?@\[\\]^_`{|}~]+ | [glo]{3,3}[.]{1,1}[j]{3,3}[.]{1,1}[abdlu]{5,5}[.]{1,1} | [KNOUW]{7,7}[_]{1,1}[IP]{2,2} | R6
R6 :: [HIabcdeghilnopqrstuw]{1,10} | [no]{3,3}[,]{1,1} | R7
R7 :: [abcdeilmqrstu]{2,11} | [eilt]{4,4}[.]{1,1} | R8
R8 :: [DIabcdeilnrstuvy]{1,9} | R9
R9 :: [adefioprstuvy]{2,6} | [aceilmorstu]{4,6}[.]{1,1} | R10
R10 :: [Sacdefinprsuyz]{4,11} | [anru]{4,4}[,]{1,1} | R11
R11 :: [abcdeimnruvyz]{5,8} | R12
R12 :: [delstu]{3,6} | R13
R13 :: [adeinps]{3,6} | R14
R14 :: [a]{1,1}[,]{1,1} | [eiltv]{5,5} | R15
R15 :: [cdegilmnrstu]{8,9} | R16
R16 :: [dgimns]{9,9} | [defiln]{8,8}[.]{1,1} | R17
R17 :: [jostu]{5,5}[.]{1,1} | [Nalu]{5,5} | R18
R18 :: [acfils]{8,8}[.]{1,1}
```

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
