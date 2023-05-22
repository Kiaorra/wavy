package wavy

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

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
class EditText : AppCompatEditText {

    var duration = 200

    var interpolator: Interpolator = LinearInterpolator()

    var defaultLineColor = ContextCompat.getColor(context, android.R.color.black)

    var accentLineColor = ContextCompat.getColor(context, android.R.color.holo_purple)

    var underlineWidth = resources.getDimension(R.dimen.edit_text_underline_stroke_width)

    private var hasInitialized = false

    private var underlineYPosition = 0f

    private var underlinePivotXPosition = 0f

    private var originalLength = OriginalUnderlineLength()

    private var underlineLength = UnderlineLength()

    private val defaultLinePath = Path()

    private val accentLinePath = Path()

    private lateinit var defaultLinePaint: Paint

    private lateinit var accentLinePaint: Paint

    private lateinit var underlineLengthAnimation: ValueAnimator

    constructor(context: Context) : super(context) {
        getAttrs()
        initVariables()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttrs(attrs)
        initVariables()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getAttrs(attrs, defStyleAttr)
        initVariables()
    }

    private fun getAttrs(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        setTypedArray(
            context.obtainStyledAttributes(
                attrs,
                R.styleable.EditText,
                defStyleAttr,
                0
            )
        )
    }

    private fun setTypedArray(typedArray: TypedArray) {
        for (i in 0..typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.EditText_accentLineColor -> accentLineColor =
                    typedArray.getColor(attr, accentLineColor)

                R.styleable.EditText_defaultLineColor -> defaultLineColor =
                    typedArray.getColor(attr, defaultLineColor)

                R.styleable.EditText_duration -> duration =
                    typedArray.getInteger(attr, duration)

                R.styleable.EditText_interpolator -> interpolator =
                    AnimationUtils.loadInterpolator(
                        context,
                        typedArray.getResourceId(attr, android.R.anim.linear_interpolator)
                    )

                R.styleable.EditText_underlineWidth -> underlineWidth =
                    typedArray.getDimension(attr, underlineWidth)
            }
        }

        typedArray.recycle()
    }

    private fun initVariables() {
        defaultLinePaint = getInitializedDefaultLinePaint(defaultLineColor, underlineWidth)

        accentLinePaint = getInitializedAccentLinePaint(accentLineColor, underlineWidth)

        underlineLengthAnimation = getInitializedAnimation(duration, interpolator)

        background = ContextCompat.getDrawable(context, R.drawable.inset)
    }

    private fun getInitializedDefaultLinePaint(color: Int, strokeWidth: Float): Paint =
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            this.strokeWidth = strokeWidth
            this.color = color
        }

    private fun getInitializedAccentLinePaint(color: Int, strokeWidth: Float): Paint =
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            this.strokeWidth = strokeWidth
            this.color = color
        }

    private fun getInitializedAnimation(duration: Int, interpolator: Interpolator): ValueAnimator =
        ValueAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration.toLong()
            this.interpolator = interpolator

            var animatedValue: Float

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        underlineYPosition = initUnderlineYPosition(measuredHeight)

        if (!hasInitialized) {
            hasInitialized = true

            underlinePivotXPosition = initPivotXPosition(measuredWidth)

            originalLength = initOriginalLineLength(measuredWidth)

            underlineLength = initUnderlineLength(measuredWidth)
        }
    }

    private fun initUnderlineYPosition(height: Int) =
        height - resources.getDimension(R.dimen.edit_text_inset_bottom)

    private fun initPivotXPosition(width: Int): Float = width / 2f

    private fun initOriginalLineLength(width: Int) =
        OriginalUnderlineLength(width / 2f - paddingLeft, width / 2f - paddingRight)

    private fun initUnderlineLength(width: Int) =
        UnderlineLength(width / 2f - paddingLeft, width / 2f - paddingRight)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            drawDefaultLine(
                this,
                defaultLinePaint,
                underlineLength.defaultLineLeft,
                underlineLength.defaultLineRight
            )
            drawAccentLine(
                this,
                accentLinePaint,
                underlineLength.accentLineLeft,
                underlineLength.accentLineRight
            )
        }
    }

    private fun drawDefaultLine(
        canvas: Canvas,
        paint: Paint,
        leftLength: Float,
        rightLength: Float
    ) {
        defaultLinePath.apply {
            val underlineStartX = paddingLeft.toFloat()

            moveTo(underlineStartX, underlineYPosition)
            lineTo(underlineStartX + leftLength, underlineYPosition)
            close()

            val underlineEndX = (measuredWidth - paddingRight).toFloat()

            moveTo(underlineEndX, underlineYPosition)
            lineTo(underlineEndX - rightLength, underlineYPosition)
            close()

            canvas.drawPath(this, paint)

            reset()
        }
    }

    private fun drawAccentLine(
        canvas: Canvas,
        paint: Paint,
        leftLength: Float,
        rightLength: Float
    ) {
        accentLinePath.apply {
            moveTo(underlinePivotXPosition, underlineYPosition)
            lineTo(underlinePivotXPosition - leftLength, underlineYPosition)
            close()

            moveTo(underlinePivotXPosition, underlineYPosition)
            lineTo(underlinePivotXPosition + rightLength, underlineYPosition)
            close()

            canvas.drawPath(this, paint)

            reset()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if (!isFocused) {
                underlinePivotXPosition = event.x
                requestFocus()
            }
        }

        return super.onTouchEvent(event)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        if (focused) {
            updateOriginalLength(underlinePivotXPosition)
            underlineLengthAnimation.start()
        } else {
            if (!underlineLengthAnimation.isRunning) {
                underlinePivotXPosition = initPivotXPosition(measuredWidth)
                updateOriginalLength(underlinePivotXPosition)
            }
            underlineLengthAnimation.reverse()
        }
    }

    private fun updateOriginalLength(pivotX: Float) {
        originalLength.left = pivotX - paddingLeft
        originalLength.right = measuredWidth - pivotX - paddingRight
    }
}