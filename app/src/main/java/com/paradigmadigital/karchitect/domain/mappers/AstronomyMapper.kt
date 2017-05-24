package com.paradigmadigital.karchitect.domain.mappers

import com.paradigmadigital.karchitect.domain.Astronomy
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AstronomyMapper
@Inject
constructor() : Mapper<Astronomy, AstronomyData> {
    override fun map(input: AstronomyData): Astronomy {
        return Astronomy(
                ageOfMoon = input.moonPhase?.ageOfMoon?.toInt() ?: 0,
                sunrise = getTime(input.moonPhase?.sunrise?.hour ?: "00", input.moonPhase?.sunrise?.minute ?: "00"),
                sunset = getTime(input.moonPhase?.sunset?.hour ?: "00", input.moonPhase?.sunset?.minute ?: "00")
        )
    }

    private fun getTime(hour: String, minute: String): Date? {
        val format = SimpleDateFormat("HH mm")
        return format.parse("$hour $minute")
    }
}