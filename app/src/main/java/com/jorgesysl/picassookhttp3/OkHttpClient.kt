package com.jorgesysl.picassookhttp3

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
//import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

object HttpClient {

    private lateinit var okHttpClient: OkHttpClient
    private var cache: Cache? = null
    var networkIsAvailable: Boolean = true

    /*class IsOnlineInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            if(!networkIsAvailable) {
                return chain.proceed(
                    chain.request()
                        .newBuilder()
                        .addHeader("Cache-Control", "no-cache")
                        .build())
                Log.w("OkHttp", "network Is Not Available")
            }else{
                Log.d("OkHttp", "network Is Available")
            }
            return chain.proceed(chain.request())
        }
    }*/
    class HeadersCacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            if (networkIsAvailable) {
                return chain.proceed(
                    chain.request()
                        .newBuilder()
                        .addHeader("Cache-Control", "no-cache")
                        .build()
                )
            }
            return chain.proceed(chain.request())
        }
    }

    /**
     * Initialize OkHttp3 client.
     */
    operator fun invoke(): HttpClient {
        if(!this::okHttpClient.isInitialized){
            Log.e("OkHttp", "invoke() okHttpClient NOT initialized")
            val loggingInterceptor = HttpLoggingInterceptor {message ->
                Log.d("OkHttp", "invoke() Basic: $message")
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            val loggingInterceptor1 = HttpLoggingInterceptor {message ->
                Log.d("OkHttp", "invoke() Headers: $message")
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            val loggingInterceptor2 = HttpLoggingInterceptor {message ->
                Log.d("OkHttp", "invoke() Body: $message")
            }
            loggingInterceptor2.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient = OkHttpClient
                .Builder()
                .cache(cache)
                .addInterceptor(HeadersCacheInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(loggingInterceptor1)
                .addInterceptor(loggingInterceptor2)
                .addInterceptor{ chain ->
                // When offline, we always want to show old photos.
                   val neverExpireCacheControl = CacheControl.Builder().maxStale(60 /*Int.MAX_VALUE*/, TimeUnit.SECONDS).build()
                   val origRequest = chain.request()
                   val neverExpireRequest = origRequest.newBuilder().cacheControl(neverExpireCacheControl).build()
                   chain.proceed(neverExpireRequest)
                }
                .build()

        }else{
            Log.i("OkHttp", "invoke() okHttpClient previamente Inicializado")
        }
            Log.d("OkHttp", if(cache == null) "Cache no habilitado." else "Cache habilitado.")
        return this
    }

    /**
     * Enables cache. This should only be called once, subsequent calls will have no effect.
     * It is important to remark that this must be called before the client is initialized,
     * otherwise the client will not have cache enabled.
     *
     * Note that cache will be bypassed if there is no Internet connection, to that local database
     * becomes the source of data and the original behavior remains.
     *
     * @param context The context. This is used to get cacheDir value, to know where to
     * store cache files.
     */
    fun setUpCache(cacheLocation: File) {
        cache = Cache(cacheLocation, 150L * 1024L * 1024L)
        if(this::okHttpClient.isInitialized) {
            okHttpClient = okHttpClient.newBuilder().cache(cache).build()
            Log.i("HttpClient", "setUpCache() okHttpClient.isInitialized! ")
        }else{
            Log.e("HttpClient", "setUpCache() okHttpClient NOT isInitialized! ")
        }
    }

    /**
     * Returns the client. Always call on the invocation of this class, otherwise
     * client could be not initialized and an exception will be thrown.
     *
     * @throws [IllegalStateException]: OkHttpClient is not initialized. Do not call getClient()
     * directly, but after invoking HttpClient()
     * @return The OkHttp3 client at the current state.
     */
    @Throws(IllegalStateException::class)
    fun getClient(): OkHttpClient {
        if(!this::okHttpClient.isInitialized) {
            //throw IllegalStateException("OkHttpClient is not initialized. Do not call getClient() directly, but after invoking HttpClient")
            Log.e("HttpClient", " getClient() OkHttpClient is not initialized. Do not call getClient() directly, but after invoking HttpClient")
        }else {
            Log.i("HttpClient", " getClient() OkHttpClient is initialized.")
        }

        return okHttpClient
    }

}

