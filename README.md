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
| `jackson-module-kotlin-2.19.0-f8921c.jar` | Snapshot of `2.19` before changes | 
| `jackson-module-kotlin-2.19.0-fb7352.jar` | Snapshot after changes            | 

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

| Benchmark          | Deterioration rate(before) | Deterioration rate(after) | Improvement rate | 
| ------------------ | -------------------------- | ------------------------- | ---------------- | 
| E_5P.empty         | 0.721498147                | 0.9893744169              | 1.396036277      | 
| E_5P.fiveContents  | 0.7992864106               | 1.019547368               | 1.266283685      | 
| T_20P.empty        | 0.6930720275               | 0.9940202384              | 1.445030387      | 
| T_20P.fiveContents | 0.775168912                | 0.9815612061              | 1.261301297      | 

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
E_5P.empty_withCheck             thrpt    4  308705.651 ± 14512.435  ops/s
E_5P.empty_withoutCheck          thrpt    4  427867.559 ± 48701.074  ops/s
E_5P.fiveContents_withCheck      thrpt    4  201819.772 ±  4847.674  ops/s
E_5P.fiveContents_withoutCheck   thrpt    4  252499.942 ± 25464.062  ops/s
T_20P.empty_withCheck            thrpt    4   97209.229 ± 20035.987  ops/s
T_20P.empty_withoutCheck         thrpt    4  140258.480 ±  2754.680  ops/s
T_20P.fiveContents_withCheck     thrpt    4   53074.949 ±  1481.199  ops/s
T_20P.fiveContents_withoutCheck  thrpt    4   68468.882 ± 15737.393  ops/s
```

### after
```
Benchmark                         Mode  Cnt       Score       Error  Units
E_5P.empty_withCheck             thrpt    4  430964.288 ± 15921.375  ops/s
E_5P.empty_withoutCheck          thrpt    4  435592.714 ± 51719.878  ops/s
E_5P.fiveContents_withCheck      thrpt    4  255561.085 ±  2232.710  ops/s
E_5P.fiveContents_withoutCheck   thrpt    4  250661.315 ±  3471.107  ops/s
T_20P.empty_withCheck            thrpt    4  140470.290 ± 17318.712  ops/s
T_20P.empty_withoutCheck         thrpt    4  141315.322 ± 14051.599  ops/s
T_20P.fiveContents_withCheck     thrpt    4   66943.502 ±  8878.233  ops/s
T_20P.fiveContents_withoutCheck  thrpt    4   68201.047 ±  8810.806  ops/s
```
