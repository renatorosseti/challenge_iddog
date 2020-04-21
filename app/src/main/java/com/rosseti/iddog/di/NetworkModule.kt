package com.rosseti.iddog.di

import com.rosseti.iddog.api.IddogApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun providesRetrofit(): IddogApi = Retrofit.Builder()
            .baseUrl(IddogApi.URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(IddogApi::class.java)
}