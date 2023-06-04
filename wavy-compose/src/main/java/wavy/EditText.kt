package wavy

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditText (
    text: String,
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
) {
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChanged,
        decorationBox = { innerTextField -> innerTextField() }
    )
}