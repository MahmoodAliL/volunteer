package com.teaml.iq.volunteer.utils.custom.view

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.utils.getDrawableCompat


/**
 * Created by Mahmood Ali on 08/04/2018.
 */

class  CustomTextView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        initAttrs(context, attrs)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(replaceArabicNumbers(text), type)
    }

    private fun replaceArabicNumbers(original: CharSequence?): String? {
        return original?.toString()?.replace('٠', '0')
                ?.replace('١', '1')
                ?.replace('٢', '2')
                ?.replace('٣', '3')
                ?.replace('٤', '4')
                ?.replace('٥', '5')
                ?.replace('٦', '6')
                ?.replace('٧', '7')
                ?.replace('٨', '8')
                ?.replace('٩', '9')
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        if (attrs == null)
            return

        val attributeArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)

        val drawableStart = attributeArray.getDrawableCompat(context, R.styleable.CustomTextView_drawableStartCompat)
        val drawableEnd = attributeArray.getDrawableCompat(context, R.styleable.CustomTextView_drawableEndCompat)
        val drawableTop = attributeArray.getDrawableCompat(context, R.styleable.CustomTextView_drawableTopCompat)
        val drawableBottom = attributeArray.getDrawableCompat(context, R.styleable.CustomTextView_drawableBottomCompat)


        setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart, drawableTop, drawableEnd, drawableBottom)
        attributeArray.recycle()

    }

}