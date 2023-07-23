package wavy

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp

fun drawAccentLine(
    contentDrawScope: ContentDrawScope,
    underLinePivotXPosition: Float,
    originalUnderLineLength: OriginalUnderlineLength,
    underLineAnimation: Float,
    accentLineColor: Color,
    accentLineStrokeWidth: Dp,
    accentLineYPositionFromBottom: Dp
) {
    contentDrawScope.apply {
        drawLine(
            color = accentLineColor,
            start = Offset(
                x = underLinePivotXPosition,
                y = size.height - accentLineYPositionFromBottom.toPx(),
            ),
            end = Offset(
                x = underLinePivotXPosition - originalUnderLineLength.left * underLineAnimation,
                y = size.height - accentLineYPositionFromBottom.toPx()
            ),
            strokeWidth = accentLineStrokeWidth.toPx(),
        )

        drawLine(
            color = accentLineColor,
            start = Offset(
                x = underLinePivotXPosition,
                y = size.height - accentLineYPositionFromBottom.toPx(),
            ),
            end = Offset(
                x = underLinePivotXPosition + originalUnderLineLength.right * underLineAnimation,
                y = size.height - accentLineYPositionFromBottom.toPx()
            ),
            strokeWidth = accentLineStrokeWidth.toPx(),
        )
    }
}

fun drawDefaultLine(
    contentDrawScope: ContentDrawScope,
    underLinePivotXPosition: Float,
    originalUnderLineLength: OriginalUnderlineLength,
    underLineAnimation: Float,
    defaultLineColor: Color,
    defaultLineStrokeWidth: Dp,
    defaultLineYPositionFromBottom: Dp
) {
    contentDrawScope.apply {
        drawLine(
            color = defaultLineColor,
            start = Offset(
                x = 0f,
                y = size.height - defaultLineYPositionFromBottom.toPx(),
            ),
            end = Offset(
                x = originalUnderLineLength.left * (1f - underLineAnimation),
                y = size.height - defaultLineYPositionFromBottom.toPx()
            ),
            strokeWidth = defaultLineStrokeWidth.toPx(),
        )

        drawLine(
            color = defaultLineColor,
            start = Offset(
                x = underLinePivotXPosition + originalUnderLineLength.right * underLineAnimation,
                y = size.height - defaultLineYPositionFromBottom.toPx(),
            ),
            end = Offset(
                x = size.width,
                y = size.height - defaultLineYPositionFromBottom.toPx()
            ),
            strokeWidth = defaultLineStrokeWidth.toPx(),
        )
    }
}