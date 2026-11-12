package com.example.quizz

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.app.Activity

data class Question(
    val text: String,
    val imageResId: Int,
    val choices: Array<String>,
    val correctAnswerIndex: Int,
    val imageDescription: String
)

class MainActivity : Activity() {

    // Nouvelles questions de culture générale et monuments.
    private val questions = arrayOf(
        Question(
            "Quel monument historique célèbre possède une base triangulaire (pyramide) ?",
            R.drawable.triangle,
            arrayOf("La Tour Eiffel", "La Pyramide de Gizeh", "Le Colisée"),
            1,
            "Illustration d'une pyramide triangulaire"
        ),
        Question(
            "Quelle structure architecturale antique se distingue par une forme quadrangulaire parfaite ?",
            R.drawable.carre,
            arrayOf("Le Parthénon", "La Pyramide carrée", "L'Arc de triomphe"),
            1,
            "Illustration d'un monument de forme carrée"
        ),
        Question(
            "Quel célèbre amphithéâtre romain circulaire accueillait les spectacles antiques ?",
            R.drawable.cercle,
            arrayOf("Le Colisée", "Le Taj Mahal", "La Statue de la Liberté"),
            0,
            "Illustration d'un édifice circulaire"
        ),
        Question(
            "Quel symbole géométrique céleste orne de nombreux emblèmes et drapeaux du monde ?",
            R.drawable.etoile,
            arrayOf("Le cercle", "Le carré", "L'étoile"),
            2,
            "Illustration d'une étoile"
        ),
        Question(
            "Quelle forme géométrique ont généralement les écrans de nos smartphones et tablettes ?",
            R.drawable.rectangle,
            arrayOf("Le triangle", "Le rectangle", "Le cercle"),
            1,
            "Illustration d'un format rectangulaire"
        )
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private var hasAnswered = false

    private lateinit var txtScore: TextView
    private lateinit var txtProgress: TextView
    private lateinit var txtQuestionNumber: TextView
    private lateinit var txtQuestion: TextView
    private lateinit var txtFeedback: TextView
    private lateinit var imgQuestion: ImageView
    private lateinit var progressQuiz: ProgressBar
    private lateinit var choiceButtons: List<Button>
    private lateinit var btnNext: Button
    private lateinit var layoutResult: View
    private lateinit var txtResult: TextView
    private lateinit var btnReplay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        choiceButtons.forEachIndexed { index, button ->
            button.setOnClickListener { checkAnswer(index) }
        }

        btnNext.setOnClickListener { goToNextQuestion() }
        btnReplay.setOnClickListener { restartQuiz() }

        showQuestion()
    }

    private fun bindViews() {
        txtScore = findViewById(R.id.txt_score)
        txtProgress = findViewById(R.id.txt_progress)
        txtQuestionNumber = findViewById(R.id.txt_question_number)
        txtQuestion = findViewById(R.id.txt_question)
        txtFeedback = findViewById(R.id.txt_feedback)
        imgQuestion = findViewById(R.id.img_question)
        progressQuiz = findViewById(R.id.progress_quiz)
        choiceButtons = listOf(
            findViewById(R.id.btn_choix1),
            findViewById(R.id.btn_choix2),
            findViewById(R.id.btn_choix3)
        )
        btnNext = findViewById(R.id.btn_suivant)
        layoutResult = findViewById(R.id.layout_result)
        txtResult = findViewById(R.id.txt_result)
        btnReplay = findViewById(R.id.btn_rejouer)
    }

    private fun showQuestion() {
        val question = questions[currentQuestionIndex]
        hasAnswered = false

        txtQuestionNumber.text = "Question ${currentQuestionIndex + 1} sur ${questions.size}"
        txtQuestion.text = question.text
        imgQuestion.setImageResource(question.imageResId)
        imgQuestion.contentDescription = question.imageDescription

        choiceButtons.forEachIndexed { index, button ->
            button.text = question.choices[index]
            button.isEnabled = true
            button.visibility = View.VISIBLE
        }

        txtFeedback.text = ""
        btnNext.isEnabled = false
        btnNext.text = if (currentQuestionIndex == questions.lastIndex) {
            "Voir le résultat"
        } else {
            "Question suivante"
        }

        layoutResult.visibility = View.GONE
        updateScoreAndProgress()
    }

    private fun checkAnswer(selectedIndex: Int) {
        if (hasAnswered) return
        hasAnswered = true

        val question = questions[currentQuestionIndex]
        val isCorrect = selectedIndex == question.correctAnswerIndex

        if (isCorrect) {
            score++
            txtFeedback.text = "Bonne réponse ! Bravo."
            txtFeedback.setTextColor(Color.rgb(35, 140, 75))
        } else {
            val correctAnswer = question.choices[question.correctAnswerIndex]
            txtFeedback.text = "Mauvaise réponse. La bonne réponse était : $correctAnswer"
            txtFeedback.setTextColor(Color.rgb(190, 50, 50))
        }

        updateScoreAndProgress()
        choiceButtons.forEach { it.isEnabled = false }
        btnNext.isEnabled = true
    }

    private fun updateScoreAndProgress() {
        txtScore.text = "Score : $score/${questions.size}"
        val answeredCount = if (hasAnswered) currentQuestionIndex + 1 else currentQuestionIndex
        progressQuiz.progress = answeredCount
        txtProgress.text = "Progression : $answeredCount/${questions.size}"
    }

    private fun goToNextQuestion() {
        if (!hasAnswered) return

        if (currentQuestionIndex < questions.lastIndex) {
            currentQuestionIndex++
            showQuestion()
        } else {
            showFinalResult()
        }
    }

    private fun showFinalResult() {
        txtQuestionNumber.text = "Quiz terminé"
        txtQuestion.text = "Félicitations, vous avez terminé le quiz culturel !"
        imgQuestion.visibility = View.GONE
        choiceButtons.forEach { it.visibility = View.GONE }
        txtFeedback.text = ""
        btnNext.visibility = View.GONE
        layoutResult.visibility = View.VISIBLE
        txtResult.text = "Résultat final : $score/${questions.size}"
        progressQuiz.progress = questions.size
        txtProgress.text = "Progression : ${questions.size}/${questions.size}"
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        hasAnswered = false

        imgQuestion.visibility = View.VISIBLE
        btnNext.visibility = View.VISIBLE
        layoutResult.visibility = View.GONE

        showQuestion()
    }
}
