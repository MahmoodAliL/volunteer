package com.teaml.iq.volunteer.utils.custom.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by Mahmood Ali on 08/04/2018.
 */
class TextViewWithArabicDigits(context: Context, attrs: AttributeSet ) : TextView(context, attrs){

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(replaceArabicNumbers(text), type)
    }

    private fun replaceArabicNumbers(original: CharSequence?): String? {
        return original?.toString()?.replace('٠', '0')
                ?.replace('١', '1' )
                ?.replace('٢', '2' )
                ?.replace('٣', '3' )
                ?.replace('٤', '4')
                ?.replace('٥', '5')
                ?.replace('٦', '6')
                ?.replace('٧', '7')
                ?.replace('٨', '8')
                ?.replace('٩', '9')
    }

}