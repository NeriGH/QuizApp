package data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.Question

@Dao
interface QuestionDao {
    @Insert
    fun insertAll(vararg question: Question)

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<Question>
}