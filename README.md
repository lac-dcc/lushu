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

## Testing

To test, run `gradle test`. To see test cases or edit tests, find all source
code under `./Merger/src/test/`.
