package org.wrongwrong

import org.openjdk.jmh.annotations.Benchmark
import com.fasterxml.jackson.module.kotlin.readValue
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class `T_20P` {
    companion object {
        const val emptyContents =
            """{"p00":[],"p01":[],"p02":[],"p03":[],"p04":[],"p05":[],"p06":[],"p07":[],"p08":[],"p09":[],"p10":[],"p11":[],"p12":[],"p13":[],"p14":[],"p15":[],"p16":[],"p17":[],"p18":[],"p19":[]}"""
        const val fiveContents =
            """{"p00":[0,1,2,3,4],"p01":[0,1,2,3,4],"p02":[0,1,2,3,4],"p03":[0,1,2,3,4],"p04":[0,1,2,3,4],"p05":[0,1,2,3,4],"p06":[0,1,2,3,4],"p07":[0,1,2,3,4],"p08":[0,1,2,3,4],"p09":[0,1,2,3,4],"p10":[0,1,2,3,4],"p11":[0,1,2,3,4],"p12":[0,1,2,3,4],"p13":[0,1,2,3,4],"p14":[0,1,2,3,4],"p15":[0,1,2,3,4],"p16":[0,1,2,3,4],"p17":[0,1,2,3,4],"p18":[0,1,2,3,4],"p19":[0,1,2,3,4]}"""
    }

    data class Dst(
        val p00: List<Int>, val p01: List<Int>, val p02: List<Int>, val p03: List<Int>, val p04: List<Int>,
        val p05: List<Int>, val p06: List<Int>, val p07: List<Int>, val p08: List<Int>, val p09: List<Int>,
        val p10: List<Int>, val p11: List<Int>, val p12: List<Int>, val p13: List<Int>, val p14: List<Int>,
        val p15: List<Int>, val p16: List<Int>, val p17: List<Int>, val p18: List<Int>, val p19: List<Int>,
    )

    @Benchmark
    fun empty_withCheck() = withCheckMapper.readValue<Dst>(emptyContents)

    @Benchmark
    fun empty_withoutCheck() = withoutCheckMapper.readValue<Dst>(emptyContents)

    @Benchmark
    fun fiveContents_withCheck() = withCheckMapper.readValue<Dst>(fiveContents)

    @Benchmark
    fun fiveContents_withoutCheck() = withoutCheckMapper.readValue<Dst>(fiveContents)
}
