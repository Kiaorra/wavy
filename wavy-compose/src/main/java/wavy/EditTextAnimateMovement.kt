package wavy

import android.view.MotionEvent
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.focusable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.basicWavyMovement(
    scope: CoroutineScope,
    focusRequester: FocusRequester,
    animationSpec: AnimationSpec<Float>,
    accentLineColor: Color,
    accentLineStrokeWidth: Dp,
    accentLineYPositionFromBottom: Dp,
    defaultLineColor: Color,
    defaultLineStrokeWidth: Dp,
    defaultLineYPositionFromBottom: Dp
 ): Modifier = composed {

    var underLineWidth by remember { mutableStateOf(0f) }

    var underLinePivotXPosition by remember { mutableStateOf(0f) }

    var underLineState by remember {
        mutableStateOf(
            OriginalUnderlineLength(
                left = underLineWidth / 2,
                right = underLineWidth / 2
            )
        )
    }

    var underLineAnimationEnabled by remember { mutableStateOf(false) }

    val underLineAnimation by animateFloatAsState(
        animationSpec = animationSpec,
        targetValue = if(underLineAnimationEnabled) 0f else 1f
    )

    with(scope) {
        this@composed
            .focusRequester(focusRequester = focusRequester)
            .pointerInteropFilter { motionEvent ->
                when(motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {

                        underLinePivotXPosition = motionEvent.x
                        underLineState.apply {
                            left = underLinePivotXPosition
                            right = underLineWidth - underLinePivotXPosition
                        }

                        focusRequester.requestFocus()

                        false
                    }

                    else -> true
                }
            }
            .onFocusChanged {  focusState ->
                underLineAnimationEnabled = !focusState.hasFocus
            }
            .focusable()
            .drawWithContent {

                drawContent()

                underLineWidth = size.width

                drawAccentLine(
                    contentDrawScope = this,
                    underLinePivotXPosition =  underLinePivotXPosition,
                    originalUnderLineLength = underLineState,
                    underLineAnimation = underLineAnimation,
                    accentLineColor = accentLineColor,
                    accentLineStrokeWidth = accentLineStrokeWidth,
                    accentLineYPositionFromBottom = accentLineYPositionFromBottom
                )

                drawDefaultLine(
                    contentDrawScope = this,
                    underLinePivotXPosition =  underLinePivotXPosition,
                    originalUnderLineLength = underLineState,
                    underLineAnimation = underLineAnimation,
                    defaultLineColor = defaultLineColor,
                    defaultLineStrokeWidth = defaultLineStrokeWidth,
                    defaultLineYPositionFromBottom = defaultLineYPositionFromBottom
                )
            }
    }

}