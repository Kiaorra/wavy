package wavy.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import wavy.Wavy

class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var text by remember { mutableStateOf("") }

            val scope = rememberCoroutineScope()

            val focusManager = LocalFocusManager.current

            val focusRequester by remember { mutableStateOf(FocusRequester()) }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Wavy(
                    text = text,
                    scope = scope,
                    focusRequester = focusRequester,
                    modifier = Modifier
                        .padding(top = 200.dp)
                        .fillMaxWidth(),
                    onTextChanged = { text = it }
                )

                Button(
                    modifier = Modifier.padding(top = 50.dp),
                    onClick = { focusManager.clearFocus() })
                {
                    Text(text = "reverse")
                }
            }

        }
    }

}
