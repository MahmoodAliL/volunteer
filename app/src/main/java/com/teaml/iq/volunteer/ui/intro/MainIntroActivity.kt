package com.teaml.iq.volunteer.ui.intro

import android.os.Bundle
import android.widget.Toast
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.teaml.iq.volunteer.R


/**
 * Created by ali on 1/31/2018.
 */

class MainIntroActivity : IntroActivity() {

    override protected fun onCreate(savedInstanceState: Bundle?) {
        isFullscreen = true
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        pageScrollDuration = 500

        buttonCtaTintMode = BUTTON_CTA_TINT_MODE_BACKGROUND
        buttonCtaTintMode = BUTTON_CTA_TINT_MODE_TEXT




        addSlide(SimpleSlide.Builder()
                .title("title here")
                .description("the volunteers application is best for join to Campaigns")
                .background(R.color.intor1)
                .image(R.drawable.intro1)
                .buttonCtaLabel("sign in")
                .buttonCtaClickListener {
                    Toast.makeText(this,"sign in ",Toast.LENGTH_LONG).show()
                }
                .build())

        addSlide(SimpleSlide.Builder()
                .title("title here")
                .description("add")
                .image(R.drawable.intro2)
                .background(R.color.intor2)
                .buttonCtaLabel("sign in")
                .buttonCtaClickListener {
                    Toast.makeText(this,"sign in ",Toast.LENGTH_LONG).show()
                }
                .build())

        addSlide(SimpleSlide.Builder()
                .title("title here")
                .description("add")
                .image(R.drawable.intro3)
                .background(R.color.intor3)
                .buttonCtaLabel("sign in")
                .buttonCtaClickListener {
                    Toast.makeText(this,"sign in ",Toast.LENGTH_LONG).show()
                }
                .build())

        addSlide(SimpleSlide.Builder()
                .title("title here")
                .description("add")
                .image(R.drawable.intro4)
                .background(R.color.intor4)
                .buttonCtaLabel("sign in")
                .buttonCtaClickListener {
                    Toast.makeText(this,"sign in ",Toast.LENGTH_LONG).show()
                }
                .build())

        //buttonCtaTintMode = BUTTON_NEXT_FUNCTION_NEXT
    }
}