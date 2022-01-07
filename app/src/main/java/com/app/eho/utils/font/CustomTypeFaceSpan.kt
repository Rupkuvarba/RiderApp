package com.app.eho.utils.font

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan
import androidx.annotation.ColorInt

class CustomTypeFaceSpan(family: String?, type: Typeface?, @ColorInt color: Int) : TypefaceSpan(family) {

    private var newType: Typeface? = null
    private var mColor = 0

    override fun updateDrawState(ds: TextPaint) {
        ds.color = mColor
        applyCustomTypeFace(ds, newType )
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    override fun getSpanTypeId(): Int {
        return super.getSpanTypeId()
    }

    @ColorInt
    fun getForegroundColor(): Int {
        return mColor
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface?) {
        val oldStyle: Int
        val old = paint.typeface
        oldStyle = old?.style ?: 0
        val fake = oldStyle and tf!!.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }
        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        paint.typeface = tf
    }
}
