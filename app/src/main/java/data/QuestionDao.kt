package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.*


@Dao
interface QuestionDao {
    @Query("SELECT * FROM question")
    fun getAllQuestions(): List<Question>

    @Insert
    suspend fun insertAll(vararg question: Question)

    @Query("DELETE FROM question")
    fun deleteAll()
    abstract fun runInBackground(function: () -> Unit)
}