package com.example.baseproject.custom_ui

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.baseproject.R
import kotlin.math.max

class CustomDotsIndicator(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var defaultDotHeight = 8f
        set(value) {
            field = value
            requestLayout()
        }
    var selectedDotHeight = 8f
        set(value) {
            field = value
            requestLayout()
        }
    var defaultDotWidth = 8f
        set(value) {
            field = value
            requestLayout()
        }
    var selectedDotWidth = 8f
        set(value) {
            field = value
            requestLayout()
        }
    var defaultColorDot = ContextCompat.getColor(context, R.color.disable)
        set(value) {
            field = value
            invalidate()
        }
    var selectedColorDot = ContextCompat.getColor(context, R.color.main_primary)
        set(value) {
            field = value
            invalidate()
        }
    var dotSpacing = 6f
        set(value) {
            field = value
            requestLayout()
        }
    var maxVisibleDot = 5
        set(value) {
            field = value
            requestLayout()
        }
    var isShowAnimation = true
        set(value) {
            field = value
            invalidate()
        }

    private val rectF = RectF()

    private val defaultDotPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = defaultColorDot
    }

    private val selectedDotPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = selectedColorDot
    }
    private var dotCount = 6
    private var currentDot = 0
    private var previousDot = 0

    private var currentAnimateWidth = selectedDotWidth
    private var currentAnimateHeight = selectedDotHeight
    private var previousAnimateWidth = defaultDotWidth
    private var previousAnimateHeight = defaultDotHeight
    private val expandAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 300L
        interpolator = AccelerateInterpolator()

        addUpdateListener { animator ->
            val fraction = animator.animatedValue as Float
            currentAnimateWidth = defaultDotWidth + (selectedDotWidth - defaultDotWidth) * fraction
            currentAnimateHeight = defaultDotHeight + (selectedDotHeight - defaultDotHeight) * fraction
            invalidate()
        }
    }

    private val shrinkAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 300L
        interpolator = AccelerateInterpolator()

        addUpdateListener { animator ->
            val fraction = animator.animatedValue as Float
            previousAnimateWidth = selectedDotWidth - (selectedDotWidth - defaultDotWidth) * fraction
            previousAnimateHeight = selectedDotHeight - (selectedDotHeight - defaultDotHeight) * fraction
            invalidate()
        }
    }
    private var animatorSet : AnimatorSet? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomDotsIndicator, 0, 0).apply {
            try {
                defaultDotHeight = getDimension(
                    R.styleable.CustomDotsIndicator_cdi_default_dot_height,
                    defaultDotHeight
                )
                selectedDotHeight = getDimension(
                    R.styleable.CustomDotsIndicator_cdi_selected_dot_height,
                    selectedDotHeight
                )
                defaultDotWidth = getDimension(
                    R.styleable.CustomDotsIndicator_cdi_default_dot_width,
                    defaultDotWidth
                )
                selectedDotWidth = getDimension(
                    R.styleable.CustomDotsIndicator_cdi_selected_dot_witdth,
                    selectedDotWidth
                )
                defaultColorDot =
                    getColor(R.styleable.CustomDotsIndicator_cdi_default_dot_color, defaultColorDot)
                selectedColorDot = getColor(
                    R.styleable.CustomDotsIndicator_cdi_selected_dot_color,
                    selectedColorDot
                )
                dotSpacing =
                    getDimension(R.styleable.CustomDotsIndicator_cdi_dot_spacing, dotSpacing)
                maxVisibleDot =
                    getInt(R.styleable.CustomDotsIndicator_cdi_max_visible_dots, maxVisibleDot)
                isShowAnimation = getBoolean(
                    R.styleable.CustomDotsIndicator_cdi_is_show_animation,
                    isShowAnimation
                )
            } finally {
                recycle()
            }
        }

        currentAnimateWidth = selectedDotWidth
        currentAnimateHeight = selectedDotHeight
        previousAnimateWidth = defaultDotWidth
        previousAnimateHeight = defaultDotHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val countDot = if (dotCount > maxVisibleDot) maxVisibleDot else dotCount

        val minw =
            paddingLeft + paddingRight + selectedDotWidth + (countDot - 1) * (defaultDotWidth + dotSpacing)
        val w = resolveSizeAndState(minw.toInt(), widthMeasureSpec, 0)

        val minh = paddingTop + paddingBottom + max(defaultDotHeight, selectedDotHeight)
        val h = resolveSizeAndState(minh.toInt(), heightMeasureSpec, 0)

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val countDot = if (dotCount > maxVisibleDot) maxVisibleDot else dotCount

        val maxHeight = max(defaultDotHeight, selectedDotHeight)
        for (i in 0 until countDot) {
            val isCurrentDot = i == currentDot
            val isPreviousDot = i == previousDot

            val dotWidth = when {
                isCurrentDot -> currentAnimateWidth
                isPreviousDot -> previousAnimateWidth
                else -> defaultDotWidth
            }

            val dotHeight = when {
                isCurrentDot -> currentAnimateHeight
                isPreviousDot -> previousAnimateHeight
                else -> defaultDotHeight
            }

            val top = paddingTop + (maxHeight - dotHeight) / 2
            val bottom = top + dotHeight
            val extraWidth = if (currentDot < i) currentAnimateWidth - defaultDotWidth else 0f
            val left = paddingLeft + i * (defaultDotWidth + dotSpacing) + extraWidth
            val right = left + dotWidth
            rectF.set(left, top, right, bottom)
            canvas.drawRoundRect(
                rectF,
                dotHeight / 2,
                dotWidth / 2,
                if (isCurrentDot) selectedDotPaint else defaultDotPaint
            )
        }
    }

    fun attachViewpager(vp: ViewPager2) {
        dotCount = vp.adapter?.itemCount ?: 0
        vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                previousDot = currentDot
                currentDot = position % dotCount
                applyAnimations()
            }
        })
        requestLayout()
        invalidate()
    }

    private fun applyAnimations() {
        animatorSet?.cancel()
        animatorSet = AnimatorSet().apply {
            playTogether(expandAnimator, shrinkAnimator)
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet?.cancel()
    }
}