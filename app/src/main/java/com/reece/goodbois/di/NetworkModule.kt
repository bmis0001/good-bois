package com.reece.goodbois.di

import com.reece.goodbois.retrofit.DoggieApi
import com.reece.goodbois.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


/*
-> Module is installed using @InstallIn annotation
-> Specifying the component type as SingletonComponent as mentioned objects should be
   accessed anywhere in the app
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    //Defining scope
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        //val interceptor = HttpLoggingInterceptor()
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    //Defining scope
    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit): DoggieApi {
        return retrofit.create(DoggieApi::class.java)
    }
}