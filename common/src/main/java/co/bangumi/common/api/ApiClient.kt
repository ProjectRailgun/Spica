package co.bangumi.common.api

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import co.bangumi.common.BuildConfig
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.TlsVersion
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLContext


/**
 * Created by roya on 2017/5/22.
 */

object ApiClient {
    private var instance: co.bangumi.common.api.ApiService? = null
    private var retrofit: Retrofit? = null
    private var cookieJar: PersistentCookieJar? = null

    fun init(context: Context, server: String) {
        co.bangumi.common.api.ApiClient.instance = co.bangumi.common.api.ApiClient.create(context, server)
    }

    fun deinit() {
        co.bangumi.common.api.ApiClient.cookieJar?.clear()
        co.bangumi.common.api.ApiClient.cookieJar = null
        co.bangumi.common.api.ApiClient.retrofit = null
        co.bangumi.common.api.ApiClient.instance = null
    }

    fun getInstance(): co.bangumi.common.api.ApiService {
        if (co.bangumi.common.api.ApiClient.instance != null) {
            return co.bangumi.common.api.ApiClient.instance as co.bangumi.common.api.ApiService
        }

        throw IllegalStateException("ApiClient Not being initialized")
    }

    fun converterErrorBody(error: ResponseBody): co.bangumi.common.api.MessageResponse? {
        if (co.bangumi.common.api.ApiClient.retrofit == null) {
            throw IllegalStateException("ApiClient Not being initialized")
        }

        val errorConverter: Converter<ResponseBody, co.bangumi.common.api.MessageResponse> = (co.bangumi.common.api.ApiClient.retrofit as Retrofit).responseBodyConverter(co.bangumi.common.api.MessageResponse::class.java, arrayOfNulls<Annotation>(0))

        try {
            return errorConverter.convert(error)
        } catch (e: Throwable) {
            return null
        }

    }

    @SuppressLint("ObsoleteSdkInt")
    fun OkHttpClient.Builder.enableTls12OnPreLollipop(): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT in 16..21) {
            try {
                val sc = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
                this.sslSocketFactory(co.bangumi.common.api.Tls12SocketFactory(sc.socketFactory))

                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build()

                val specs = ArrayList<ConnectionSpec>()
                specs.add(cs)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)

                this.connectionSpecs(specs)
            } catch (exc: Exception) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
            }

        }

        return this
    }

    private fun create(context: Context, server: String): co.bangumi.common.api.ApiService {
        co.bangumi.common.api.ApiClient.cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

        val okHttp = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .dns(HttpsDns())
                .cookieJar(co.bangumi.common.api.ApiClient.cookieJar)
                .addInterceptor {
                    val request = it.request()
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

        co.bangumi.common.api.ApiClient.retrofit = Retrofit.Builder()
                .client(okHttp)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(server)
                .build()

        return (co.bangumi.common.api.ApiClient.retrofit as Retrofit).create(co.bangumi.common.api.ApiService::class.java)
    }
}
