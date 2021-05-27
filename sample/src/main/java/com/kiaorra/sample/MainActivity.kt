package com.kiaorra.sample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kiaorra.RippleEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rippleEditText = findViewById<RippleEditText>(R.id.editText)

        findViewById<Button>(R.id.button).apply {
            setOnClickListener {
                rippleEditText.clearFocus()
            }
        }
    }
}