package com.hey.heys.di

import com.hey.heys.api.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/*
 * 네트워크 설정 파일
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
   const val base_url = "https://api-dev.teamheys.com/"

   private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
      override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

      }

      override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

      }

      override fun getAcceptedIssuers(): Array<X509Certificate> {
         return arrayOf()
      }
   })

   val sslContext = SSLContext.getInstance("SSL").let { sslContext ->
      sslContext.init(null, trustAllCerts, SecureRandom())
      sslContext.socketFactory
   }

   @Provides
   @Singleton
   fun provideHttpClient(): OkHttpClient {
      return OkHttpClient.Builder()
         .sslSocketFactory(sslSocketFactory = sslContext, trustAllCerts[0] as X509TrustManager)
         .hostnameVerifier { hostname, session -> true }
         .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
         .readTimeout(15, TimeUnit.SECONDS)
         .connectTimeout(15, TimeUnit.SECONDS)
         .writeTimeout(15, TimeUnit.SECONDS)
         .build()
   }

   @Singleton
   @Provides
   fun provideRetrofitInstance(
      okHttpClient: OkHttpClient,
      gsonConverterFactory: GsonConverterFactory,
      scalarsConverterFactory: ScalarsConverterFactory): Retrofit {
      return Retrofit.Builder()
         .baseUrl(base_url)
         .client(okHttpClient)
         .client(provideHttpClient())
         .addConverterFactory(gsonConverterFactory)
         .addConverterFactory(scalarsConverterFactory)
         .build()
   }

   @Provides
   @Singleton
   fun provideGsonConverterFactory(): GsonConverterFactory {
      val gson: Gson = GsonBuilder().setLenient().create()
      return GsonConverterFactory.create(gson)
   }

   @Provides
   @Singleton
   fun provideScalarsConverterFactory(): ScalarsConverterFactory {
      return ScalarsConverterFactory.create()
   }

   @Provides
   @Singleton
   fun provideSettingApiService(retrofit: Retrofit): SettingApi {
      return retrofit.create(SettingApi::class.java)
   }

   @Provides
   @Singleton
   fun provideSignUpApiService(retrofit: Retrofit): SignUpApi {
      return retrofit.create(SignUpApi::class.java)
   }

   @Provides
   @Singleton
   fun provideUserApiService(retrofit: Retrofit): UserApi {
      return retrofit.create(UserApi::class.java)
   }

   @Provides
   @Singleton
   fun provideStudyApiService(retrofit: Retrofit): StudyApi {
      return retrofit.create(StudyApi::class.java)
   }

   @Provides
   @Singleton
   fun provideChannelApiService(retrofit: Retrofit): ChannelApi {
      return retrofit.create(ChannelApi::class.java)
   }

   @Provides
   @Singleton
   fun provideContentsApiService(retrofit: Retrofit): ContentApi {
      return retrofit.create(ContentApi::class.java)
   }
}