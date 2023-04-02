# Lushu

_Lushu_ (short for the Chinese 记录树, 录树), is a regular grammar generator for
infinite text. _Lushu_ generates a tree of regular expressions that recognizes
given log entries, in an online fashion.

## Disclaimer

_Lushu_ is a **work in progress**. Only one part of it is ready, the `Merger`.

## Running

Run `gradle fatJar` to generate the file `./Merger/build/libs/Merger.jar`. Run
it following the example:

```sh
echo '8.8.8.8 0.0.0.0' | java -jar ./Merger/build/libs/Merger.jar Merger/example/config.yaml
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
to edit the example file in `./Merger/example/config.yaml`.

## Testing

To test, run `gradle test`. To see test cases or edit tests, find all source
code under `./Merger/src/test/`.
