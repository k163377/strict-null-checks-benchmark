package org.wrongwrong

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class E_5P {
    data class Dst(
        val p0: List<String>,
        val p1: List<String>,
        val p2: List<String>,
        val p3: List<String>,
        val p4: List<String>,
    )

    @Benchmark
    fun temp() = mapper
}
