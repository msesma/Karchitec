package com.paradigmadigital.karchitect.repository

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.paradigmadigital.karchitect.TestExecutor
import com.paradigmadigital.karchitect.domain.db.ChannelsDao
import com.paradigmadigital.karchitect.domain.db.ItemsDao
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.domain.mappers.ChannelMapper
import com.paradigmadigital.karchitect.domain.mappers.ItemsMapper
import com.paradigmadigital.karchitect.MockWebServerTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.MockitoAnnotations


class RefreshUseCaseAndMappersShould : MockWebServerTestBase() {

    @Mock
    private lateinit var itemsDao: ItemsDao
    @Mock
    private lateinit var channelsDao: ChannelsDao

    val channelCaptor = argumentCaptor<Channel>()
    val itemsCaptor = argumentCaptor<List<Item>>()

    private var expectedError: NetworkError? = null
    private val executor = TestExecutor()
    private val channelMapper = ChannelMapper()
    private val itemsMapper = ItemsMapper()

    private lateinit var useCase: RefreshUseCase

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        useCase = RefreshUseCase(httpClient, itemsDao, channelsDao, executor, channelMapper, itemsMapper)

        doNothing().`when`(channelsDao).insert(channelCaptor.capture())
        doNothing().`when`(itemsDao).insert(itemsCaptor.capture())
    }

    @Test
    @Throws(Exception::class)
    fun getFeedHappyPath() {
        enqueueMockResponse(200, "test_feed.xml")
        expectedError = NetworkError.SUCCESS

        useCase.refreshItems(endpoint, { verifyResult(it) })

        assertGetRequestSentTo("/")
        assertThat(channelCaptor.firstValue.title).isEqualTo("Channel title")
        assertThat(itemsCaptor.firstValue[0].title).isEqualTo("Item title")
    }

    @Test
    @Throws(Exception::class)
    fun getFeedErrorPath() {
        enqueueMockResponse(400, "test_feed.xml")
        expectedError = NetworkError.UNKNOWN

        useCase.refreshItems(endpoint, { verifyResult(it) })

    }

    private fun verifyResult(error: NetworkError) {
        assertThat(error).isEqualTo(expectedError)
    }


}