package eu.scatelier.quizapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.room.Room
import com.opencsv.CSVReader
import data.Question
import data.database.QuestionDatabase
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // WILL PICK UP THE USER FILE
        val importButton: Button = findViewById(R.id.fileimport)
        importButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/csv"
            }
            var READ_REQUEST_CODE = 0
            startActivityForResult(intent, READ_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        var READ_REQUEST_CODE = null
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.let { uri ->
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    // Read the CSV file and make the data persistent
                    val reader = CSVReader(InputStreamReader(inputStream))
                    val allData = reader.readAll()
                    // The rest of the code for storing data in shared preferences or Room database
                    val db = Room.databaseBuilder(
                        applicationContext,
                        QuestionDatabase::class.java, "question_database"
                    ).build()

                    val questions = allData.map { Question(0, it[0], it[1]) }
                    db.questionDao().insertAll(*questions.toTypedArray())
                }
            }
        }
    }

}