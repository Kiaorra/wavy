package com.kiaorra

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.ScaleAnimation
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.layout_ripple_edittext.view.*
import kotlin.math.roundToInt

class RippleEditText : ConstraintLayout {

    private val fromY = 1.0F
    private val fromX = 0.0F
    private val toY = 1.0F
    private val toX = 1.0F
    private val pivotYPos = 0.0F
    private var pivotXPos: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
        getAttrs(attrs, defStyleAttr)
    }

    private fun init() {
        inflate(context, R.layout.layout_ripple_edittext, this)

        tie_layoutUREditText.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                pivotXPos = (event.x / (view.right - view.left) * 1000).roundToInt()
            }
            false
        }

        tie_layoutUREditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val animation =
                    ScaleAnimation(fromX, toX, fromY, toY, pivotXPos.toFloat(), pivotYPos)
                animation.duration = 250

                view_accentUnderline.startAnimation(animation)
                view_accentUnderline.visibility = View.VISIBLE
            } else {
                view_accentUnderline.visibility = View.INVISIBLE
            }
        }
    }

    private fun getAttrs(attrs: AttributeSet?) {
        setTypedArray(context.obtainStyledAttributes(attrs, R.styleable.UnderlineRippleEditText))
    }

    private fun getAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        setTypedArray(
            context.obtainStyledAttributes(
                attrs,
                R.styleable.UnderlineRippleEditText,
                defStyleAttr,
                0
            )
        )
    }

    private fun setTypedArray(typedArray: TypedArray) {
        tie_layoutUREditText.hint = typedArray.getString(R.styleable.UnderlineRippleEditText_hint)

        tie_layoutUREditText.inputType = typedArray.getInt(
            R.styleable.UnderlineRippleEditText_inputType,
            EditorInfo.TYPE_CLASS_TEXT
        )

        tie_layoutUREditText.maxLines =
            typedArray.getInteger(R.styleable.UnderlineRippleEditText_maxLines, 1)

        tie_layoutUREditText.setTextColor(
            typedArray.getColor(
                R.styleable.UnderlineRippleEditText_textColor,
                ContextCompat.getColor(context, android.R.color.black)
            )
        )

        tie_layoutUREditText.setHintTextColor(
            typedArray.getColor(
                R.styleable.UnderlineRippleEditText_textColorHint,
                ContextCompat.getColor(context, android.R.color.darker_gray)
            )
        )

        tie_layoutUREditText.textSize =
            typedArray.getDimension(R.styleable.UnderlineRippleEditText_textSize, 18.0F)

        til_layoutUREditText.isPasswordVisibilityToggleEnabled =
            typedArray.getBoolean(R.styleable.UnderlineRippleEditText_passwordToggleEnabled, false)

        view_baseUnderline.setBackgroundColor(
            typedArray.getColor(
                R.styleable.UnderlineRippleEditText_underlineBaseColor,
                ContextCompat.getColor(context, android.R.color.black)
            )
        )

        view_accentUnderline.setBackgroundColor(
            typedArray.getColor(
                R.styleable.UnderlineRippleEditText_underlineAccentColor,
                ContextCompat.getColor(context, R.color.colorAccent)
            )
        )

        typedArray.recycle()
    }
}