package com.kiaorra

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.InputFilter
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.layout_ripple_edittext.view.*

@SuppressLint("ClickableViewAccessibility")
class RippleEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.Theme_AppCompat_Light
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val expandFromX = 0.0F
    private val expandToX = 1.0F
    private val expandFromY = 1.0F
    private val expandToY = 1.0F

    private val reduceFromX = 1.0F
    private val reduceToX = 0.0F
    private val reduceFromY = 1.0F
    private val reduceToY = 1.0F

    private var pivotXPos = 0.0F
    private val pivotYPos = 0.0F

    private var duration = 300L

    private lateinit var scaleAnimation: ScaleAnimation

    init {
        inflate(context, R.layout.layout_ripple_edittext, this)

        setupListeners()

        getAttrs(attrs, defStyleAttr)
    }

    private fun setupListeners() {
        tie_layoutRippleEditText.setOnTouchListener { view, event ->
            pivotXPos =
                if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
                    event.x
                } else {
                    view.width / 2.0F
                }

            false
        }

        tie_layoutRippleEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                scaleAnimation = ScaleAnimation(
                    expandFromX,
                    expandToX,
                    expandFromY,
                    expandToY,
                    if (pivotXPos == 0.0F) view.width / 2.0F else pivotXPos,
                    pivotYPos
                ).also {
                    it.duration = this.duration
                }

                view_accentUnderline.startAnimation(scaleAnimation)
                view_accentUnderline.visibility = View.VISIBLE
            } else {
                scaleAnimation =
                    ScaleAnimation(
                        reduceFromX,
                        reduceToX,
                        reduceFromY,
                        reduceToY,
                        view.width / 2.0F,
                        pivotYPos
                    ).also {
                        it.duration = this.duration
                        it.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {
                            }

                            override fun onAnimationEnd(animation: Animation?) {
                                view_accentUnderline.visibility = View.INVISIBLE
                            }

                            override fun onAnimationRepeat(animation: Animation?) {
                            }
                        })
                    }

                view_accentUnderline.startAnimation(scaleAnimation)
            }
        }
    }

    private fun getAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        setTypedArray(
            context.obtainStyledAttributes(
                attrs,
                R.styleable.RippleEditText,
                defStyleAttr,
                0
            )
        )
    }

    private fun setTypedArray(typedArray: TypedArray) {
        tie_layoutRippleEditText.isEnabled =
            typedArray.getBoolean(R.styleable.RippleEditText_android_enabled, true)

        tie_layoutRippleEditText.hint =
            typedArray.getString(R.styleable.RippleEditText_android_hint)

        tie_layoutRippleEditText.inputType =
            typedArray.getInt(
                R.styleable.RippleEditText_android_inputType,
                EditorInfo.TYPE_CLASS_TEXT
            )

        tie_layoutRippleEditText.setTextColor(
            typedArray.getColor(
                R.styleable.RippleEditText_android_textColor,
                ContextCompat.getColor(context, android.R.color.black)
            )
        )

        tie_layoutRippleEditText.setHintTextColor(
            typedArray.getColor(
                R.styleable.RippleEditText_android_textColorHint,
                ContextCompat.getColor(context, android.R.color.darker_gray)
            )
        )

        tie_layoutRippleEditText.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            typedArray.getDimension(
                R.styleable.RippleEditText_android_textSize,
                14.0F.toPx
            )
        )

        tie_layoutRippleEditText.setPadding(
            typedArray.getDimension(
                R.styleable.RippleEditText_android_paddingHorizontal,
                0.0F.toPx
            ).toInt(),
            typedArray.getDimension(
                R.styleable.RippleEditText_android_paddingVertical,
                14.0F.toPx
            ).toInt(),
            typedArray.getDimension(
                R.styleable.RippleEditText_android_paddingHorizontal,
                0.0F.toPx
            ).toInt(),
            typedArray.getDimension(
                R.styleable.RippleEditText_android_paddingVertical,
                14.0F.toPx
            ).toInt()
        )

        tie_layoutRippleEditText.maxLines =
            typedArray.getInteger(R.styleable.RippleEditText_android_maxLines, Integer.MAX_VALUE)

        tie_layoutRippleEditText.filters = listOf<InputFilter>(
            InputFilter.LengthFilter(
                typedArray.getInt(
                    R.styleable.RippleEditText_android_maxLength, Integer.MAX_VALUE
                )
            )
        ).toTypedArray()

        til_layoutRippleEditText.endIconMode =
            typedArray.getInteger(R.styleable.RippleEditText_endIconMode, 0)

        view_baseUnderline.setBackgroundColor(
            typedArray.getColor(
                R.styleable.RippleEditText_underlineBaseColor,
                ContextCompat.getColor(context, android.R.color.darker_gray)
            )
        )

        view_accentUnderline.setBackgroundColor(
            typedArray.getColor(
                R.styleable.RippleEditText_underlineAccentColor,
                ContextCompat.getColor(context, R.color.colorAccent)
            )
        )

        typedArray.recycle()
    }

    override fun setEnabled(isEnabled: Boolean) {
        tie_layoutRippleEditText.isEnabled = isEnabled
    }
}