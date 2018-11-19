package co.bangumi.common.api

import android.util.Log
import co.bangumi.common.BuildConfig
import co.bangumi.common.Constant
import co.bangumi.common.api.ApiClient.enableTls12OnPreLollipop
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HttpsDnsService {

    private var apiClient: ApiService;

    init {
        val client = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .addInterceptor {
                    val request = it.request().newBuilder().addHeader("accept", "application/dns-json").build()
                    val response = it.proceed(request)
                    val body = response.body()
                    val bodyString = body?.string()
                    if (BuildConfig.DEBUG) Log.i("TAG", response.toString() + " Body:" + bodyString)
                    response.newBuilder()
                            .headers(response.headers())
                            .body(ResponseBody.create(body?.contentType(), bodyString))
                            .build()
                }
                .enableTls12OnPreLollipop()
                .build()

        apiClient = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.DOH_SERVER)
                .build()
                .create(ApiService::class.java)
    }

    fun lookup(hostname: String): List<String>? {
        return apiClient.getIpByHost(hostname, "A").execute().body()?.let {
            return@let it.answer.map {
                return@map it.data
            }
        }
    }
}