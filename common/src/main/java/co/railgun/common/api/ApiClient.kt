package co.railgun.common.api

import android.util.Log
import co.railgun.api.bgmrip.internal.cookieJar
import co.railgun.common.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by roya on 2017/5/22.
 */
object ApiClient {

    private val _instance: ApiService by lazy { create() }
    private lateinit var retrofit: Retrofit

    fun getInstance(): ApiService = _instance

    fun converterErrorBody(error: ResponseBody): MessageResponse? {
        if (!::retrofit.isInitialized) {
            throw IllegalStateException("ApiClient Not being initialized")
        }

        val errorConverter: Converter<ResponseBody, MessageResponse> =
            retrofit.responseBodyConverter(
                MessageResponse::class.java,
                arrayOfNulls<Annotation>(0)
            )

        return try {
            errorConverter.convert(error)
        } catch (e: Throwable) {
            null
        }

    }

    private fun create(): ApiService {
        val okHttp = OkHttpClient.Builder()
            .dns(HttpsDns())
            .cookieJar(cookieJar)
            .addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                val body = response.body
                val bodyString = body?.string()
                if (BuildConfig.DEBUG) Log.i("TAG", "$response Body:$bodyString")
                response.newBuilder()
                    .headers(response.headers)
                    .body(bodyString?.toResponseBody(body.contentType()))
                    .build()
            }
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttp)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://bgm.rip/")
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
