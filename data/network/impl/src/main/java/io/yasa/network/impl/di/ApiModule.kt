package io.yasa.network.impl.di;

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal val apiModule = Kodein.Module("apiModule") {
    bind<Moshi>() with singleton { provideMoshi() }
    bind<OkHttpClient.Builder>() with provider { provideOkHttpClientBuilder() }
    bind<Retrofit>() with singleton {
        provideRetrofit(
            context = instance(),
            hostName = "https://api.openbrewerydb.org",
            okHttpClientBuilder = instance(),
        )
    }
}

private fun provideMoshi() = Moshi.Builder().add(KotlinJsonAdapterFactory())
    .build()

private fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
    return builder
        .hostnameVerifier { _, _ -> true }
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
}

private fun provideRetrofit(
    context: Context,
    hostName: String,
    okHttpClientBuilder: OkHttpClient.Builder,
): Retrofit {
    okHttpClientBuilder.addInterceptor(
        ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    )
    val builder = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClientBuilder.build())
        .baseUrl(hostName)
    return builder.build()
}