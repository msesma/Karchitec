package com.paradigmadigital.karchitect.api

import android.support.v4.util.ArrayMap
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.util.regex.Pattern

class ApiResponse<T> {
    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("page=(\\d)+")
        private val NEXT_LINK = "next"
        val LINK = "link"
    }

    val code: Int
    val body: T?
    val errorMessage: String?
    val links: Map<String, String>

    val isSuccessful: Boolean
        get() = code >= 200 && code < 300

    val nextPage: Int?
        get() {
            val next = links[NEXT_LINK] ?: return null
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) return null
            try {
                return Integer.parseInt(matcher.group(1))
            } catch (ex: NumberFormatException) {
                Timber.w("cannot parse next page from %s", next)
                return null
            }
        }

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
        links = emptyMap<String, String>()
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            body = null
            errorMessage = getErrorMessage(response)
        }

        val linkHeader = response.headers().get(LINK)
        links = if (linkHeader == null) emptyMap<String, String>() else getLinks(linkHeader)
    }

    private fun getErrorMessage(response: Response<T>): String? {
        var message: String? = null
        if (response.errorBody() != null) {
            try {
                message = response.errorBody().string()
            } catch (ignored: IOException) {
                Timber.e(ignored, "error while parsing response")
            }

        }
        if (message == null || message.trim { it <= ' ' }.length == 0) {
            message = response.message()
        }
        return message
    }

    private fun getLinks(linkHeader: String): Map<String, String> {
        val links = ArrayMap<String, String>()
        val matcher = LINK_PATTERN.matcher(linkHeader)

        while (matcher.find()) {
            val count = matcher.groupCount()
            if (count == 2) {
                links.put(matcher.group(2), matcher.group(1))
            }
        }
        return links
    }
}
