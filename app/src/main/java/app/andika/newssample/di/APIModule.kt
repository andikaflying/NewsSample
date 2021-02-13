package app.andika.newssample.di

import android.content.Context
import app.andika.newssample.core.api.APIList
import app.andika.newssample.utilities.BASE_URL
import app.andika.newssample.utilities.connectivity.ConnectivityReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object APIModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    public fun provideOkHttp(httpLoggingInterceptor : HttpLoggingInterceptor, cache: Cache) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .addInterceptor { chain ->
                    var request = chain.request()

                    request = if (ConnectivityReceiver.isConnected!!)
                            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                        else
                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

                    chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)

            .build()
    }

    @Provides
    @Singleton
    public fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    public fun provideAPIList(retrofit: Retrofit) : APIList {
        return retrofit.create(APIList::class.java)
    }

    @Provides
    @Singleton
    public fun provideCache(@ApplicationContext appContext: Context, cacheSize : Long) : Cache {
        return Cache(appContext.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    public fun provideCacheSize() : Long {
        return (5 * 1024 * 1024).toLong()
    }
}