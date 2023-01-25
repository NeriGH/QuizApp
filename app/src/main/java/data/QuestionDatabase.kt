package data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.*

@Database(entities = [Question::class], version = 1)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}