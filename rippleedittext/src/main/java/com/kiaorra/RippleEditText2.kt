package com.kiaorra

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
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

    private var widthWithoutInset: () -> Float = { ->
        width.toFloat() - resources.getDimension(R.dimen.edit_text_inset_horizontal) * 2
    }

    private var underlineWidth: () -> Float = {
        widthWithoutInset.invoke() - paddingStart - paddingEnd
    }

    private var underlineX: () -> Float = {
        x + paddingStart + resources.getDimension(R.dimen.edit_text_inset_horizontal)
    }

    private var underlineY: () -> Float = {
        height - resources.getDimension(R.dimen.edit_text_inset_bottom)
    }

    private val baselinePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 2.0f.toPx
        color = Color.DKGRAY
    }

    private val accentlinePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 2.0f.toPx
        color = Color.RED
    }

    private var pivotXPos: Float = 0.0f

    private var originalRemainedLeft: Float = 0.0f
    private var remainedLeft: Float = 0.0f

    private var originalRemainedRight: Float = 0.0f
    private var remainedRight: Float = 0.0f

    private var testBoolean = false

    init {
        background = ContextCompat.getDrawable(context, R.drawable.sample_inset)
    }

    val va1 = ValueAnimator.ofFloat(0.0f, 1.0f).apply {
        duration = 1000

        addUpdateListener {
            Log.d("RippleEditText", "pivot: $pivotXPos")
            remainedLeft = originalRemainedLeft * animatedValue as Float
            Log.d("RippleEditText", "left: $remainedLeft")
            remainedRight = originalRemainedRight * animatedValue as Float
            Log.d("RippleEditText", "right: $remainedRight")
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            drawLeftUnderline(canvas, pivotXPos - remainedLeft)
            drawRightUnderline(canvas, pivotXPos + remainedRight)
            if (testBoolean == true) drawAccentLine(canvas, pivotXPos - remainedLeft, pivotXPos + remainedRight)
        }
    }

    private fun drawAccentLine(canvas: Canvas, startX: Float, endX: Float) {
        val path = Path().apply {
            moveTo(startX, underlineY.invoke())
            lineTo(endX, underlineY.invoke())
            close()
        }

        canvas.drawPath(path, accentlinePaint)
    }

    private fun drawLeftUnderline(canvas: Canvas, endX: Float) {
        val startX = paddingLeft.toFloat()

        val path = Path().apply {
            moveTo(startX, underlineY.invoke())
            lineTo(endX, underlineY.invoke())
            close()
        }

        canvas.drawPath(path, baselinePaint)
    }

    private fun drawRightUnderline(canvas: Canvas, endX: Float) {
        val startX = width.toFloat() - paddingRight.toFloat()

        val path = Path().apply {
            moveTo(startX, underlineY.invoke())
            lineTo(endX, underlineY.invoke())
            close()
        }

        canvas.drawPath(path, baselinePaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            //Log.d("RippleEditText", (event.x / width * 100).toString())

            pivotXPos = event.x

            originalRemainedLeft = pivotXPos - paddingLeft

            originalRemainedRight = width - pivotXPos - paddingRight

//            Log.d("RippleEditText", originalRemainedLeft.toString())
//            Log.d("RippleEditText", originalRemainedRight.toString())
//            Log.d("RippleEditText", width.toString())

            testBoolean = true

            va1.start()
        }

        return super.onTouchEvent(event)
    }
}