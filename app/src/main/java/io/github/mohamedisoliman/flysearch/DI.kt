package io.github.mohamedisoliman.flysearch

import io.github.mohamedisoliman.flysearch.data.FlyRepository
import io.github.mohamedisoliman.flysearch.data.Repository
import io.github.mohamedisoliman.flysearch.data.remote.FlyApis
import io.github.mohamedisoliman.flysearch.domain.GetSuggestionsUseCase
import io.github.mohamedisoliman.flysearch.domain.SearchFlightsUseCase
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
val dataModule = module {
    single { okHttpClient() }
    single { createFlyRemote(get()) }
    single<Repository>(name = "repository") { FlyRepository(get()) }

}

val domainModule = module {
  single { SearchFlightsUseCase(get()) }
  single { GetSuggestionsUseCase(get()) }
}

val presentationModule = module {
  viewModel { SearchViewModel(get(), get()) }
}


private fun createFlyRemote(okHttpClient: OkHttpClient): FlyApis {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    return retrofit.create(FlyApis::class.java)
}

private fun okHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor {
            it.proceed(
                it.request().newBuilder().addHeader("Authorization", apiKey).build()
            )
        }
    return clientBuilder.build()
}

const val apiKey = "guMRjevTJNNgv49LRTNCTzfp9cWnW6Sj"
