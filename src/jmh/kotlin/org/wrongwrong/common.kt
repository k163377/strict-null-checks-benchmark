package org.wrongwrong

import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val withoutCheckMapper = jacksonObjectMapper()
val withCheckMapper = jacksonObjectMapper { enable(KotlinFeature.StrictNullChecks) }
