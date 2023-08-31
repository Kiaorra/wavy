package wavy.sample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import wavy.Wavy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<Wavy>(R.id.edit_text)

        findViewById<Button>(R.id.button).apply {
            setOnClickListener {
                editText.clearFocus()
            }
        }
    }
}