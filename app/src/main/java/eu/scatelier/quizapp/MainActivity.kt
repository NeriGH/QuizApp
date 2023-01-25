package eu.scatelier.quizapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import com.opencsv.CSVReader
import data.Question
import data.QuestionDatabase
import java.io.InputStreamReader
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var db: QuestionDatabase
    private val READ_REQUEST_CODE = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            QuestionDatabase::class.java, "question_database"
        ).build()

        val importButton: Button = findViewById(R.id.fileimport)
        importButton.setOnClickListener {
            openFilePicker()
        }

        val retrieveButton: Button = findViewById(R.id.retrieve_button)
        retrieveButton.setOnClickListener {
            val questions = db.questionDao().getAllQuestions()
            Log.d("QUESTIONS", questions.toString())
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/csv"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    private fun insertQuestions(questions: List<Question>) {
        CoroutineScope(Dispatchers.IO).launch {
            db.questionDao().insertAll(*questions.toTypedArray())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.let { uri ->
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    // Read the CSV file and make the data persistent
                    val reader = CSVReader(InputStreamReader(inputStream))
                    val allData = reader.readAll()
                    allData.forEach {
                        Log.d("CSV_DATA", it.joinToString(";"))
                    }
                    if (allData.isEmpty() || allData[0].size < 2) {
                        Toast.makeText(this, "Invalid file structure", Toast.LENGTH_SHORT).show()
                        return
                    }
                    try {
                        val questions = allData.map { Question(0, it[0], it[1]) }
                        insertQuestions(questions)
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        Toast.makeText(this, "Invalid file structure", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}