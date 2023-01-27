package data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val question: String,
    val answer: String
)