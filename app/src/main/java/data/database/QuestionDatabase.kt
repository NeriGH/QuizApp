package data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import data.Question
import data.dao.QuestionDao

@Database(entities = [Question::class], version = 1)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}