package com.example.flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        findViewById<ImageView>(R.id.cancel_button).setOnClickListener {
            finish()
        }


        findViewById<ImageView>(R.id.save_button).setOnClickListener {

            val string1 = findViewById<EditText>(R.id.textQuestion).text.toString()
            val string2 = findViewById<EditText>(R.id.textAnswer).text.toString()

            val data = Intent() // create a new Intent, this is where we will put our data

            data.putExtra("string1", string1) // puts one string into the Intent, with the key as 'string1'

            data.putExtra("string2", string2) // puts another string into the Intent, with the key as 'string2

            setResult(RESULT_OK, data) // set result code and bundle data for response

            finish()
        }
//
//        val s1 = intent.getStringExtra("stringKey1"); // this string will be 'harry potter`
//        val s2 = intent.getStringExtra("stringKey2"); // this string will be 'voldemort'
    }
}