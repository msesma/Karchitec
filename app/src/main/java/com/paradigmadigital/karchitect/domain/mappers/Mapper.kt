package com.paradigmadigital.karchitect.domain.mappers

interface Mapper<OUT, IN> {
    abstract fun map(input: IN): OUT
}