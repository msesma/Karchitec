package com.paradigmadigital.paraguas

import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.apache.commons.io.FileUtils
import org.junit.After
import org.junit.Before
import java.io.File
import java.io.IOException

open class MockWebServerTestBase {

    companion object {
        private val FILE_ENCODING = "UTF-8"
    }

    lateinit private var server: MockWebServer

    protected val endpoint: String
        get() {
            return server.url("/").toString()
        }

    protected val httpClient: OkHttpClient
        get() = OkHttpClient.Builder().build()

    @Before
    @Throws(Exception::class)
    open fun setUp() {
        this.server = MockWebServer()
        this.server.start()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        server.shutdown()
    }

    @Throws(IOException::class)
    @JvmOverloads protected fun enqueueMockResponse(code: Int = 200, fileName: String? = null) {
        val mockResponse = MockResponse()
        val fileContent = getContentFromFile(fileName)
        mockResponse.setResponseCode(code)
        mockResponse.setBody(fileContent)
        server.enqueue(mockResponse)
    }

    @Throws(InterruptedException::class)
    protected fun assertGetRequestSentTo(url: String) {
        val request = server.takeRequest()
        assertEquals(url, request.path)
        assertEquals("GET", request.method)
    }

    @Throws(IOException::class)
    protected fun getContentFromFile(name: String?): String {
        var fileName: String? = name ?: return ""

        fileName = javaClass.getResource("/" + fileName).file
        val file = File(fileName!!)
        val lines = FileUtils.readLines(file, FILE_ENCODING)
        val stringBuilder = StringBuilder()
        for (line in lines) {
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }
}