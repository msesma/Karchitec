package com.paradigmadigital.karchitect.usecases

import com.paradigmadigital.karchitect.MockWebServerTestBase
import com.paradigmadigital.karchitect.usecases.GeoLookUpApiUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class GeoLookUpApiUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: GeoLookUpApiUseCase

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        useCase = GeoLookUpApiUseCase(httpClient, baseEndpoint)
    }

    @Test
    @Throws(Exception::class)
    fun getCityForCoordinatesHappyPath() {
        enqueueMockResponse(200, "geolookup_mock_response.json")

        useCase.execute("37.776289", "-122.395234")

                .subscribe({
            assertThat(it?.location?.city).isEqualTo("San Francisco")
            assertThat(it?.location?.country).isEqualTo("US")
        })
    }

    @Test
    @Throws(Exception::class)
    fun getCityForCoordinatesUsesCorrectUrl() {
        enqueueMockResponse(200)

        useCase.execute("37.776289", "-122.395234").subscribe()

        assertGetRequestSentTo("/geolookup/q/37.776289,-122.395234.json")
    }
}
