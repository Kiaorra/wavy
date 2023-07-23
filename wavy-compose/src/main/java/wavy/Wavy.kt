package wavy

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    animationSpec: AnimationSpec<Float> = tween(1000),
    accentLineColor: Color = Color(0xFFEF00FF),
    accentLineStrokeWidth: Dp = 1.dp,
    accentLineYPositionFromBottom: Dp = 1.dp,
    defaultLineColor: Color = Color(0xFF000000),
    defaultLineStrokeWidth: Dp = 1.dp,
    defaultLineYPositionFromBottom: Dp = 1.dp
) {
    BasicTextField(
        modifier = modifier
            .basicWavyMovement(
                focusRequester = focusRequester,
                scope = scope,
                animationSpec = animationSpec,
                accentLineColor = accentLineColor,
                accentLineStrokeWidth = accentLineStrokeWidth,
                accentLineYPositionFromBottom = accentLineYPositionFromBottom,
                defaultLineColor = defaultLineColor,
                defaultLineStrokeWidth = defaultLineStrokeWidth,
                defaultLineYPositionFromBottom = defaultLineYPositionFromBottom
            ),
        value = text,
        onValueChange = onTextChanged,
        decorationBox = { innerTextField -> innerTextField() }
    )
}