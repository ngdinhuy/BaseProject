package com.example.baseproject.custom_ui
//
//import com.example.baseproject.R
//
//package com.eway.icallme.component
//
//import android.animation.AnimatorSet
//import android.animation.ValueAnimator
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.graphics.RectF
//import android.util.AttributeSet
//import android.view.View
//import android.view.animation.AccelerateDecelerateInterpolator
//import androidx.core.content.ContextCompat
//import androidx.viewpager2.widget.ViewPager2
//
//class CustomDotsIndicator1 @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : View(context, attrs, defStyleAttr) {
//
//    // Configurable properties
//    var dotSize = 8f.dp
//        set(value) {
//            field = value
//            requestLayout()
//        }
//
//    var dotSpacing = 6f.dp
//        set(value) {
//            field = value
//            requestLayout()
//        }
//
//    var selectedDotWidth = 24f.dp
//        set(value) {
//            field = value
//            requestLayout()
//        }
//
//    var dotColor = ContextCompat.getColor(context, R.color.disable)
//        set(value) {
//            field = value
//            unselectedPaint.color = value
//            invalidate()
//        }
//
//    var selectedDotColor = ContextCompat.getColor(context, R.color.main_primary)
//        set(value) {
//            field = value
//            selectedPaint.color = value
//            invalidate()
//        }
//
//    var animationDuration = 300L
//
//    private var dotCount = 0
//    private var currentPosition = 0
//    private var currentAnimatedWidth = selectedDotWidth
//    private var previousAnimatedWidth = dotSize
//
//    private val selectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        color = selectedDotColor
//        style = Paint.Style.FILL
//    }
//
//    private val unselectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        color = dotColor
//        style = Paint.Style.FILL
//    }
//
//    private val rectF = RectF()
//    private var viewPager: ViewPager2? = null
//    private var previousPosition = 0
//    private var animatorSet: AnimatorSet? = null
//
//    private val Float.dp: Float
//        get() = this * context.resources.displayMetrics.density
//
//    init {
//        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomDotsIndicator, 0, 0).apply {
//            try {
//                dotSize = getDimension(R.styleable.CustomDotsIndicator_cdi_dotSize, 8f.dp)
//                dotSpacing = getDimension(R.styleable.CustomDotsIndicator_cdi_dotSpacing, 6f.dp)
//                selectedDotWidth = getDimension(R.styleable.CustomDotsIndicator_cdi_selectedDotWidth, 24f.dp)
//                dotColor = getColor(R.styleable.CustomDotsIndicator_cdi_dotColor, ContextCompat.getColor(context, R.color.disable))
//                selectedDotColor = getColor(R.styleable.CustomDotsIndicator_cdi_selectedDotColor, ContextCompat.getColor(context, R.color.main_primary))
//                animationDuration = getInt(R.styleable.CustomDotsIndicator_cdi_animationDuration, 300).toLong()
//            } finally {
//                recycle()
//            }
//        }
//
//        currentAnimatedWidth = selectedDotWidth
//        previousAnimatedWidth = dotSize
//    }
//
//    fun attachTo(viewPager2: ViewPager2) {
//        this.viewPager = viewPager2
//        dotCount = viewPager2.adapter?.itemCount ?: 0
//        currentPosition = viewPager2.currentItem
//        previousPosition = currentPosition
//
//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                if (position != currentPosition) {
//                    previousPosition = currentPosition
//                    currentPosition = position
//                    animateToPosition()
//                }
//            }
//        })
//
//        requestLayout()
//        invalidate()
//    }
//
//    fun setDotCount(count: Int) {
//        dotCount = count
//        requestLayout()
//        invalidate()
//    }
//
//    private fun animateToPosition() {
//        animatorSet?.cancel()
//
//        val expandAnimator = ValueAnimator.ofFloat(dotSize, selectedDotWidth).apply {
//            duration = animationDuration
//            interpolator = AccelerateDecelerateInterpolator()
//            addUpdateListener {
//                currentAnimatedWidth = it.animatedValue as Float
//                invalidate()
//            }
//        }
//
//        val shrinkAnimator = ValueAnimator.ofFloat(selectedDotWidth, dotSize).apply {
//            duration = animationDuration
//            interpolator = AccelerateDecelerateInterpolator()
//            addUpdateListener {
//                previousAnimatedWidth = it.animatedValue as Float
//                invalidate()
//            }
//        }
//
//        animatorSet = AnimatorSet().apply {
//            playTogether(expandAnimator, shrinkAnimator)
//            start()
//        }
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        if (dotCount == 0) {
//            setMeasuredDimension(0, 0)
//            return
//        }
//
//        // Calculate total width: (dotCount - 1) * dotSize + selectedDotWidth + (dotCount - 1) * dotSpacing
//        val totalWidth = (dotCount - 1) * dotSize + selectedDotWidth + (dotCount - 1) * dotSpacing
//        val totalHeight = dotSize
//
//        setMeasuredDimension(
//            resolveSize(totalWidth.toInt() + paddingLeft + paddingRight, widthMeasureSpec),
//            resolveSize(totalHeight.toInt() + paddingTop + paddingBottom, heightMeasureSpec)
//        )
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//
//        if (dotCount == 0) return
//
//        val cornerRadius = dotSize / 2
//        var currentX = paddingLeft.toFloat()
//        val centerY = (height - paddingTop - paddingBottom) / 2f + paddingTop
//
//        for (i in 0 until dotCount) {
//            val dotWidth = when (i) {
//                currentPosition -> currentAnimatedWidth
//                previousPosition -> previousAnimatedWidth
//                else -> dotSize
//            }
//
//            val paint = if (i == currentPosition) selectedPaint else unselectedPaint
//
//            rectF.set(
//                currentX,
//                centerY - dotSize / 2,
//                currentX + dotWidth,
//                centerY + dotSize / 2
//            )
//
//            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
//
//            currentX += dotWidth + dotSpacing
//        }
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        animatorSet?.cancel()
//    }
//}