package wavy

import androidx.compose.animation.core.tween
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.CoroutineScope

/*
 *  Copyright 2021 Kiaorra.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

@Composable
fun Wavy(
    text: String,
    scope: CoroutineScope,
    focusRequester: FocusRequester,
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
) {
    BasicTextField(
        modifier = modifier
            .basicWavyMovement(
                focusRequester = focusRequester,
                scope = scope,
                animationSpec = tween(1000)
            ),
        value = text,
        onValueChange = onTextChanged,
        decorationBox = { innerTextField -> innerTextField() }
    )
}