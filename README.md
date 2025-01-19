# strict-null-checks-benchmark
This is a benchmark to measure the performance improvement of `strictNullCheck`.

- [Performance improvement of \`strictNullCheck\` · Issue \#719 · FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin/issues/719)

Benchmark is `. /gradlew jmh`.  
See `build.gradle.kts` for configuration etc.

# Introduction
## Comparison targets and environment
This repository compares the following two

| Name                                      | Target                            | 
| ----------------------------------------- | --------------------------------- | 
| `jackson-module-kotlin-2.19.0-4ecdac.jar` | Snapshot of `2.19` before changes | 
| `jackson-module-kotlin-2.19.0-aa167f.jar` | Snapshot after changes            | 

The results stored in `reports` were obtained in the following environment

- `Windows 11`
- `Ryzen 7 3700X`
- `F4-3600C16D-32GTZN`

## Benchmark details
The class name indicates the number of properties.  
In this benchmark, two patterns are compared: `E_5P` (5 properties) and `T_20P` (20 properties).
Also, all properties are `List<Int>`.

The function name indicates the number of contents stored in the `List` input to the property and the enabled of `strictNullCheck`.  
The number of stored contents is empty or five.

# Comparison of results
Below is a table that summarizes the results in an easy-to-read format.

| Benchmark             | Deterioration rate (before) | Deterioration rate (after) | Improvement rate |
|-----------------------|-----------------------------|----------------------------|------------------|
| E_5P.empty            | 0.7383099866                | 0.9977718738               | 1.388225409      |
| E_5P.fiveContents     | 0.7999091212                | 0.9869712419               | 1.258111096      |
| T_20P.empty           | 0.671255694                 | 0.9969159641               | 1.46163136       |
| T_20P.fiveContents    | 0.7351224299                | 1.051081106                | 1.295783134      |

`Deterioration rate` shows the rate of degradation of throughput if the check was enabled.
The closer this is to 1, the smaller the degradation.

`Improvement rate` shows how much the throughput improved before and after the change when the check was enabled.
The larger this value is, the greater the improvement.

With the new implementation, there is virtually no degradation in throughput even when checks are enabled.
Also, throughput is at least 1.25 times better than before the change.

## Raw results
Below are the unprocessed benchmark results.
They are also stored in `reports` in `CSV` format.

### before
```
Benchmark                         Mode  Cnt       Score       Error  Units
E_5P.empty_withCheck             thrpt    4  311704.950 ±  2866.957  ops/s
E_5P.empty_withoutCheck          thrpt    4  422187.097 ± 43810.012  ops/s
E_5P.fiveContents_withCheck      thrpt    4  200041.537 ±  6315.353  ops/s
E_5P.fiveContents_withoutCheck   thrpt    4  250080.331 ± 33948.844  ops/s
T_20P.empty_withCheck            thrpt    4   95085.591 ±  3863.961  ops/s
T_20P.empty_withoutCheck         thrpt    4  141653.311 ±  3522.712  ops/s
T_20P.fiveContents_withCheck     thrpt    4   53699.081 ±  1658.765  ops/s
T_20P.fiveContents_withoutCheck  thrpt    4   73047.807 ±  6161.061  ops/s
```

### after
```
Benchmark                         Mode  Cnt       Score       Error  Units
E_5P.empty_withCheck             thrpt    4  432716.732 ± 35672.216  ops/s
E_5P.empty_withoutCheck          thrpt    4  433683.032 ± 56168.839  ops/s
E_5P.fiveContents_withCheck      thrpt    4  251674.478 ± 54132.265  ops/s
E_5P.fiveContents_withoutCheck   thrpt    4  254996.769 ±  4395.728  ops/s
T_20P.empty_withCheck            thrpt    4  138980.082 ±  3042.823  ops/s
T_20P.empty_withoutCheck         thrpt    4  139410.028 ± 26545.725  ops/s
T_20P.fiveContents_withCheck     thrpt    4   69582.364 ±   985.035  ops/s
T_20P.fiveContents_withoutCheck  thrpt    4   66200.756 ± 14159.109  ops/s
```
