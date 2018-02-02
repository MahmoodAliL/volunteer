package com.teaml.iq.volunteer.ui.intro

import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.teaml.iq.volunteer.R
import org.jetbrains.anko.toast


/**
 * Created by ali on 1/31/2018.
 */

class MainIntroActivity : IntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false


        buttonCtaTintMode = BUTTON_CTA_TINT_MODE_TEXT

        autoplay(3000, INFINITE)

        isButtonCtaVisible = true
        buttonCtaLabel = "SIGN IN"
        setButtonCtaClickListener {
            toast("button sign in clicked ").show()
        }

        addSlide(SimpleSlide.Builder()
                .title(R.string.intro_evaluation)
                .description(R.string.intro_evaluation_description)
                .background(R.color.color_intro_evaluation)
                .backgroundDark(R.color.color_dark_intro_evaluation)
                .image(R.drawable.evaluation_icon)
                .build())

        addSlide(SimpleSlide.Builder()
                .title(R.string.intro_evaluation)
                .description(R.string.intro_evaluation_description)
                .image(R.drawable.skills_icon)
                .background(R.color.color_intro_skills)
                .backgroundDark(R.color.color_dark_intro_skills)
                .scrollable(false)
                .build())

        addSlide(SimpleSlide.Builder()
                .title(R.string.intro_evaluation)
                .description(R.string.intro_evaluation_description)
                .image(R.drawable.find_jobs)
                .background(R.color.color_intro_find_jobs)
                .backgroundDark(R.color.color_dark_intro_find_jobs)
                .build())

        addSlide(SimpleSlide.Builder()
                .title(R.string.intro_evaluation)
                .description(R.string.intro_evaluation_description)
                .image(R.drawable.chart_icon)
                .background(R.color.color_intro_chart)
                .backgroundDark(R.color.color_dark_intro_chart)
                .build())



    }
}