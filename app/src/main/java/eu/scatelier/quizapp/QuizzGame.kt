package eu.scatelier.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import data.Question
import data.QuestionDao
import data.QuestionDatabase

class QuizGameActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private lateinit var quizQuestions: List<Question>
    private lateinit var questionDao: data.QuestionDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz_game)

        showQuestion(currentQuestionIndex)
    }

    private fun showQuestion(index: Int) {
        val question = quizQuestions[index]
        val fragment = QuizGameFragment.newInstance(question)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    }
