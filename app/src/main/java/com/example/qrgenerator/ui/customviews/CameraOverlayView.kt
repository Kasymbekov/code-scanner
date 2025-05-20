package com.example.qrgenerator.ui.customviews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class CameraOverlayView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // frame
    private val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    // to darken around the frame
    private val maskPaint = Paint().apply {
        color = Color.parseColor("#A6000000") // Translucent Black
        style = Paint.Style.FILL
    }

    // laser animation
    private val laserPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        strokeWidth = 4f
        isAntiAlias = true
    }

    private var laserY = 0f
    private var rectTop = 0f
    private var rectBottom = 0f
    private var rectLeft = 0f
    private var rectRight = 0f

    init {
        startLaserAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val rectWidth = width * 0.7f
        val rectHeight = height * 0.3f

        val left = (width - rectWidth) / 2
        val top = (height - rectHeight) / 2
        val right = left + rectWidth
        val bottom = top + rectHeight

        drawDarkScreen(canvas, left, top, bottom, right)
        drawFrame(canvas, left, top, bottom, right)
        drawLaser(canvas, rectWidth, rectHeight)
    }

    private fun drawDarkScreen(canvas: Canvas, left: Float, top: Float, bottom: Float, right: Float){
        val path = Path().apply {
            fillType = Path.FillType.EVEN_ODD
            addRect(0f, 0f, width.toFloat(), height.toFloat(), Path.Direction.CW)
            addRect(left, top, right, bottom, Path.Direction.CCW)
        }
        canvas.drawPath(path, maskPaint)
    }

    private fun drawFrame(canvas: Canvas, left: Float, top: Float, bottom: Float, right: Float) {
        val cornerLength = 50f

        // left top corner
        canvas.drawLine(left, top, left + cornerLength, top, paint) // horizontal
        canvas.drawLine(left, top, left, top + cornerLength, paint) // vertical

        // top right corner
        canvas.drawLine(right, top, right - cornerLength, top, paint)
        canvas.drawLine(right, top, right, top + cornerLength, paint)

        // left bottom corner
        canvas.drawLine(left, bottom, left + cornerLength, bottom, paint)
        canvas.drawLine(left, bottom, left, bottom - cornerLength, paint)

        // right bottom corner
        canvas.drawLine(right, bottom, right - cornerLength, bottom, paint)
        canvas.drawLine(right, bottom, right, bottom - cornerLength, paint)
    }

    private fun drawLaser(canvas: Canvas, rectWidth: Float, rectHeight: Float) {
        rectLeft = (width - rectWidth) / 2
        rectTop = (height - rectHeight) / 2
        rectRight = rectLeft + rectWidth
        rectBottom = rectTop + rectHeight

        canvas.drawLine(rectLeft, laserY, rectRight, laserY, laserPaint)
    }

    private fun startLaserAnimation() {
        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
            addUpdateListener {
                val progress = it.animatedValue as Float
                laserY = rectTop + progress * (rectBottom - rectTop)
                invalidate()
            }
        }
        animator.start()
    }
}