package com.cleganeBowl2k18.trebuchet.presentation.internal.di.module

import android.app.Application
import android.content.Context
import com.cleganeBowl2k18.trebuchet.BuildConfig
import com.cleganeBowl2k18.trebuchet.data.network.*
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val mBaseApiUrl: String) {

    @Provides
    @Singleton
    internal fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 Mb
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Singleton
    internal fun providesPersistentCookieJar(context: Context) : PersistentCookieJar {
        return PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(context))
    }

    @Provides
    @Singleton
    internal fun provideHttpClient(cache: Cache, interceptor: HttpLoggingInterceptor, cookieJar: PersistentCookieJar): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(cookieJar)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mBaseApiUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    internal fun providePetStoreService(retrofit: Retrofit): PetStoreService {
        return retrofit.create<PetStoreService>(PetStoreService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideUserService(retrofit: Retrofit) : UserService {
        return retrofit.create<UserService>(UserService::class.java)
    }

    @Provides
    @Singleton
    internal fun providesGroupService(retrofit: Retrofit) : GroupService {
        return retrofit.create<GroupService>(GroupService::class.java)
    }

    @Provides
    @Singleton
    internal fun providesTransactionService(retrofit: Retrofit): TransactionService {
        return retrofit.create<TransactionService>(TransactionService::class.java)
    }

    @Provides
    @Singleton
    internal fun providesAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create<AuthService>(AuthService::class.java)
    }
}