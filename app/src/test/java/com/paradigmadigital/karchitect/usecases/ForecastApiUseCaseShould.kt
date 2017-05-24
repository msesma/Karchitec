package com.paradigmadigital.karchitect.usecases

import com.paradigmadigital.karchitect.MockWebServerTestBase
import com.paradigmadigital.karchitect.domain.ForecastItem
import com.paradigmadigital.karchitect.domain.mappers.ForecastMapper
import com.paradigmadigital.karchitect.usecases.ForecastApiUseCase
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import java.util.*

class ForecastApiUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: ForecastApiUseCase

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        useCase = ForecastApiUseCase(httpClient, baseEndpoint, ForecastMapper())
    }

    @Test
    @Throws(Exception::class)
    fun getForecastHappyPath() {
        enqueueMockResponse(200, "hourly_mock_response.json")
        val observer = TestObserver<List<ForecastItem>>()
        val forecastItem = ForecastItem(
                time = Date(1341338400000),
                condition = "Clear",
                feelslike = 19f,
                humidity = 65f,
                rainProbability = 0f,
                rainQuantity = 0f,
                snow = 0f,
                temp = 19f,
                windSpeed = 8f,
                iconUrl = "http://icons-ak.wxug.com/i/c/k/clear.gif"
        )

        useCase.execute("CA", "San Francisco").subscribe(observer)
        observer.await()

        observer.assertNoErrors()
                .assertValue { it -> it.get(0).equals(forecastItem) }
    }

    @Test
    @Throws(Exception::class)
    fun getCityForCoordinatesUsesCorrectUrl() {
        enqueueMockResponse(200, "hourly_mock_response.json")

        useCase.execute("CA", "San Francisco").subscribe()

        assertGetRequestSentTo("/hourly/q/CA/San%20Francisco.json")
    }

    @Test
    @Throws(Exception::class)
    fun getCityForCoordinatesManagerHttpError() {
        enqueueMockResponse(500, "hourly_mock_response.json")
        val observer = TestObserver<List<ForecastItem>>()

        useCase.execute("CA", "San Francisco").subscribe(observer)
        observer.await()

        observer.assertError { it -> (it as HttpException).code() == 500 }
    }
}
