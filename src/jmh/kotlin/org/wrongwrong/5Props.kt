package org.wrongwrong

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import com.fasterxml.jackson.module.kotlin.readValue

@State(Scope.Benchmark)
open class E_5P {
    companion object {
        const val emptyContents =
            """{"p0":[],"p1":[],"p2":[],"p3":[],"p4":[]}"""
        const val fiveContents =
            """{"p0":[0,1,2,3,4],"p1":[0,1,2,3,4],"p2":[0,1,2,3,4],"p3":[0,1,2,3,4],"p4":[0,1,2,3,4]}"""
    }

    data class Dst(val p0: List<Int>, val p1: List<Int>, val p2: List<Int>, val p3: List<Int>, val p4: List<Int>)

    @Benchmark
    fun empty_withCheck() = withCheckMapper.readValue<Dst>(emptyContents)

    @Benchmark
    fun empty_withoutCheck() = withoutCheckMapper.readValue<Dst>(emptyContents)

    @Benchmark
    fun fiveContents_withCheck() = withCheckMapper.readValue<Dst>(fiveContents)

    @Benchmark
    fun fiveContents_withoutCheck() = withoutCheckMapper.readValue<Dst>(fiveContents)
}
