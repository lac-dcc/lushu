<div align="center">
  <span><img src="docs/images/LushuFinalLogo.png" alt="Lushu Logo" width="200" height="200">
</div>

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

## Theory

Lushu includes a novel way to merge regular expressions, based on a lattice we
call the Regex Lattice. The meet of two regexes in the Regex Lattice indicates
the result of their merge. A single word may be composed of multiple lattice
nodes. It all depends on how we structure the lattice. For instance, if we say
that punctuations are "blacklisted" by "alpha" characters, then their meet will
go to the lattice top. This can be configured by the following `config.yaml`
file:

```yaml
latticeBase:
  alpha:
    interval: 1,32
    charset: "abcdefghijklmnopqrstuvwxyz"
  punct:
    interval: 1,2
    charset: "\"!#\\$%&'()*+,-./:;<>=?@\\[\\]^_`{}|~\\\\"
    blacklist:
      - alpha
```

Arbitrary text is not in the format we require, originally. So the first thing
we do with text is divide it into words separated by space. We call these words
_tokens_. Each token might be composed of multiple lattice nodes. For instance,
suppose we have two tokens, `ab:c` and `de:fg`. They are first transformed to
_primitive_ lattice nodes:

```
[a]{1,1}[b]{1,1}[:]{1,1}[c]{1,1}
[d]{1,1}[e]{1,1}[:]{1,1}[f]{1,1}[g]{1,1}
```

These are called _primitive_ because the charset for each node is a single
character, and the interval is (1,1). Then, we _reduce_ these primitive nodes
into a more compact format. We collapse as much as possible, using the lattice
meet to check if the GLB is the Top node. If it is the top node, we do not merge
the nodes. For our example:

```
reduce([a]{1,1}[b]{1,1}[:]{1,1}[c]{1,1}) ==>
  [ab]{2,2}[:]{1,1}[c]{1,1}

reduce([d]{1,1}[e]{1,1}[:]{1,1}[f]{1,1}[g]{1,1}) ==>
  [de]{2,2}[:]{1,1}[fg]{2,2}
```

Finally, two turn these two regular expressions into one, we perform a _zip_ and
then a _map_ operation (in the functional sense). The _zip_ operation checks
that the lists must have the same size and forms pairs like `([ab]{2,2},
[de]{2,2})`. For each pair, we map their elements to their lattice meet. In
a pseudo-functional syntax:

```
map(zip(nodes1, nodes2), (first, second) => {
     lattice.meet(first, second)
})
```

If the lattice goes to top, the words are not mergeable. Otherwise, we merge
them. The result for our example would be:

```
merge(ab:c, de:fg) =
  map(zip(reduce(ab:c), reduce(de:fg)), (first, second) -> {
    lattice.meet(first, second).then { it ->
        when(it) {
            is Top: not mergeable
            else: it
        }
  })

==> merge(ab:c, de:fg) = [abde]{2,2}[:]{1,1}[cfg]{1,2}
```
