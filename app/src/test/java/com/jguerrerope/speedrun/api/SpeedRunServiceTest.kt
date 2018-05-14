package com.jguerrerope.speedrun.api

import com.google.gson.GsonBuilder
import com.nhaarman.mockito_kotlin.any
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class SpeedRunServiceTest {
    private lateinit var service: SpeedRunService
    private lateinit var mockWebServer: MockWebServer

    @Before
    @Throws(IOException::class)
    fun createService() {
        mockWebServer = MockWebServer()

        val gson = GsonBuilder()
                .create()

        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create<SpeedRunService>(SpeedRunService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun success_getGamesPageList() {
        enqueueResponse("games-page.json")
        service.getGamesPageList(offset = 5, max = 5)
                .test()
                .assertValue { it.data.size == 5 }
                .assertValue { it.pagination.offset == 0 }
    }

    @Test
    fun success_getRunPageList() {
        enqueueResponse("runs-page.json")
        service.getRunPageList(gameId = "test", offset = 5, max = 5)
                .test()
                .assertValue { it.data.size == 5 }
                .assertValue { it.pagination.offset == 0 }
    }

    @Test
    fun success_getUserById() {
        enqueueResponse("user-request.json")
        service.getUserById(userId = "v48grxpr")
                .test()
                .assertValue { it.data.id == "v48grxpr" }
    }

    @Test
    fun badRequest() {
        mockWebServer.enqueue(MockResponse().setBody("{error:\"bad request\"").setResponseCode(400))
        service.getGamesPageList(offset = any(), max = any())
                .test()
                .assertError(HttpException::class.java)
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        val inputStream =
                javaClass.classLoader.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        mockWebServer.enqueue(MockResponse().setBody(source.readString(StandardCharsets.UTF_8)))
    }
}
