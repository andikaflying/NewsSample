package app.andika.newssample.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import app.andika.newssample.model.Article

@Database(entities = arrayOf(Article::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDAO
}