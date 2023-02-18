package com.example.themovieapptrainee.data

import android.content.Context
import com.example.themovieapptrainee.readFileFromAssets
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockRequestInterceptor(private val context: Context) : Interceptor {

    companion object {
        private val JSON_MEDIA_TYPE = "application/json".toMediaTypeOrNull()
        private const val MOCK = "mocks"
        private const val HTTP_200 = 200
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val header = request.header(MOCK)

        if (header != null) {
            val filename = request.url.pathSegments.last()
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .code(HTTP_200)
                .body(ResponseBody.create(JSON_MEDIA_TYPE, context.readFileFromAssets("mocks/$filename")))
                .build()
        }

        return chain.proceed(request.newBuilder().removeHeader(MOCK).build())
    }
}
