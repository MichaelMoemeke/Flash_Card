package com.example.flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var flashcardDatabase: FlashcardDatabase
    var flashcards = mutableListOf<Flashcard>()
    var currentCardDisplayedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardDatabase = FlashcardDatabase(this)
        flashcards = flashcardDatabase.getAllCards().toMutableList()

        if (flashcards.size > 0) {
            findViewById<TextView>(R.id.flashcard_question).text = flashcards[0].question
            findViewById<TextView>(R.id.flashcard_answer).text = flashcards[0].answer
        }

        val question = findViewById<TextView>(R.id.flashcard_question)
        val answer = findViewById<TextView>(R.id.flashcard_answer)

        question.setOnClickListener {
            question.visibility = View.INVISIBLE
            answer.visibility = View.VISIBLE
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val extras = data?.extras

            if (extras != null) {
                val string1 = data.getStringExtra("string1")
                val string2 = data.getStringExtra("string2")

                findViewById<TextView>(R.id.flashcard_question).text=string1
                findViewById<TextView>(R.id.flashcard_answer).text=string2

                Log.i("MainActivity", "string1: $string1")
                Log.i("MainActivity", "string2: $string2")

                if (string1 != null && string2 != null) {
                    flashcardDatabase.insertCard(Flashcard(string1, string2))
                    flashcards = flashcardDatabase.getAllCards().toMutableList()
                } else {
                    Log.e("TAG", "Missing question or answer to input into database. Question is $question and answer is $answer")
                }
            } else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }

        }

        findViewById<ImageView>(R.id.plus_button).setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

        findViewById<ImageView>(R.id.next_button).setOnClickListener {
            if (flashcards.size == 0) {
                return@setOnClickListener
            }

            currentCardDisplayedIndex++

            if(currentCardDisplayedIndex >= flashcards.size) {
                Snackbar.make(
                    findViewById<TextView>(R.id.flashcard_question),
                    "There are no more cards.",
                    Snackbar.LENGTH_SHORT)
                    .show()
                currentCardDisplayedIndex = 0
            }

            flashcards = flashcardDatabase.getAllCards().toMutableList()
            val (string1, string2) = flashcards[currentCardDisplayedIndex]

            findViewById<TextView>(R.id.flashcard_answer).text = string2
            findViewById<TextView>(R.id.flashcard_question).text = string1
        }
    }


}