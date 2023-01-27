package eu.scatelier.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class QuizGameFragment : Fragment() {
    private lateinit var question: data.Question
    private var selectedAnswerIndex = -1
    private lateinit var submitButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var optionRadioGroup: RadioGroup

    companion object {
        fun newInstance(question: data.Question): QuizGameFragment {
            val fragment = QuizGameFragment()
            fragment.question = question
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quizz_game, container, false)
        submitButton = view.findViewById(R.id.submit_button)
        questionTextView = view.findViewById(R.id.question_text_view)
        optionRadioGroup = view.findViewById(R.id.option_radio_group)

        questionTextView.text = question.question
        for ((index, answer) in question.answers.withIndex()) {
            val radioButton = RadioButton(context)
            radioButton.id = index
            radioButton.text = answer
            optionRadioGroup.addView(radioButton)
        }

        optionRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedAnswerIndex = checkedId
        }

        submitButton.setOnClickListener {
            if (selectedAnswerIndex == question.correctAnswerIndex) {
                Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}