package com.paradigmadigital.karchitect.platform

import java.util.*

fun Float.format(digits: Int) = java.lang.String.format(Locale.ENGLISH, "%.${digits}f", this)

