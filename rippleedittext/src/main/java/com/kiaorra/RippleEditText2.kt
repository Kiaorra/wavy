package com.kiaorra

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.kiaorra.rippleedittext.R

class RippleEditText2 : androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var underlineYPosition = 0f

    private var underlinePivotXPosition = 0f

    private var originalLength = OriginalLength()

    private var underlineLength = UnderlineLength()

    var duration = 400L

    private val paintDefaultLine = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = resources.getDimension(R.dimen.edit_text_underline_stroke_width)
        color = Color.DKGRAY
    }

    private val paintAccentLine = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = resources.getDimension(R.dimen.edit_text_underline_stroke_width)
        color = Color.RED
    }

    private val underlineLengthAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = this@RippleEditText2.duration

        var animatedValue = 0f

        addUpdateListener {
            animatedValue = it.animatedValue as Float

            underlineLength.apply {
                defaultLineLeft = originalLength.left * (1f - animatedValue)
                defaultLineRight = originalLength.right * (1f - animatedValue)
                accentLineLeft = originalLength.left * animatedValue
                accentLineRight = originalLength.right * animatedValue
            }

            invalidate()
        }
    }

    init {
        background = ContextCompat.getDrawable(context, R.drawable.sample_inset)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        underlineYPosition = initUnderlineYPosition(measuredHeight)

        underlinePivotXPosition = initPivotXPosition(measuredWidth)

        originalLength = initOriginalLineLength(measuredWidth)

        underlineLength = initUnderlineLength(measuredWidth)
    }

    private fun initUnderlineYPosition(height: Int) =
        height - resources.getDimension(R.dimen.edit_text_inset_bottom)

    private fun initPivotXPosition(width: Int): Float = width / 2f

    private fun initOriginalLineLength(width: Int) =
        OriginalLength(width / 2f - paddingLeft, width / 2f - paddingRight)

    private fun initUnderlineLength(width: Int) =
        UnderlineLength(width / 2f - paddingLeft, width / 2f - paddingRight)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            drawBaseline(this, underlineLength.defaultLineLeft, underlineLength.defaultLineRight)
            drawAccentLine(this, underlineLength.accentLineLeft, underlineLength.accentLineRight)
        }
    }

    private fun drawBaseline(canvas: Canvas, leftLength: Float, rightLength: Float) {
        val path = Path().apply {
            moveTo(paddingLeft.toFloat(), underlineYPosition)
            lineTo(paddingLeft + leftLength, underlineYPosition)
            close()

            val width = measuredWidth.toFloat()

            moveTo(width - paddingRight, underlineYPosition)
            lineTo(width - paddingRight - rightLength, underlineYPosition)
            close()
        }

        canvas.drawPath(path, paintDefaultLine)
    }

    private fun drawAccentLine(canvas: Canvas, leftLength: Float, rightLength: Float) {
        val path = Path().apply {

        }

        canvas.drawPath(path, paintAccentLine)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            underlinePivotXPosition = event.x
            requestFocus()
        }

        return super.onTouchEvent(event)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        if (focused) {
            updateOriginalLength(underlinePivotXPosition)
            underlineLengthAnimation.start()
        }

        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }

    private fun updateOriginalLength(pivotX: Float) {
        originalLength.left = pivotX + paddingLeft
        originalLength.right = measuredWidth - pivotX - paddingRight
    }

    private data class OriginalLength(
        var left: Float = 0f,
        var right: Float = 0f
    )

    private data class UnderlineLength(
        var defaultLineLeft: Float = 0f,
        var defaultLineRight: Float = 0f,
        var accentLineLeft: Float = 0f,
        var accentLineRight: Float = 0f
    )
}