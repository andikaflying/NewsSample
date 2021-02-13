package app.andika.newssample.di

import android.content.Context
import androidx.room.Room
import app.andika.newssample.databases.AppDatabase
import app.andika.newssample.databases.NewsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "news.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideScanDao(database: AppDatabase) : NewsDAO {
        return database.newsDao()
    }
}