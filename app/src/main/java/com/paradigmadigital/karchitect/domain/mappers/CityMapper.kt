package com.paradigmadigital.karchitect.domain.mappers

import com.paradigmadigital.karchitect.domain.City
import javax.inject.Inject

class CityMapper
@Inject
constructor() : Mapper<City, GeoLookUp> {
    override fun map(input: GeoLookUp): City {
        return City(
                city = input.location?.city ?: "",
                countryCode = input.location?.country ?: ""
        )
    }
}