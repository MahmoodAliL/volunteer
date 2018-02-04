package com.teaml.iq.volunteer.ui.intro

import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.teaml.iq.volunteer.R
import org.jetbrains.anko.toast
import javax.inject.Inject


/**
 * Created by ali on 1/31/2018.
 */

class MainIntroActivity : IntroActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        // setup CTA Button
        // Comment because there is error in library
        /*isButtonCtaVisible = true
        buttonCtaLabel = "SIGN IN"
        buttonCtaTintMode = BUTTON_CTA_TINT_MODE_TEXT
        setButtonCtaClickListener { toast("button sign in clicked ").show() }*/

        //setupSlides()
    }

/*
    private fun setupSlides() {
        autoplay(5000, INFINITE)
        addSlide(SimpleSlide.Builder()
                .title(R.string.intro_evaluation)
                .description(R.string.intro_evaluation_description)
                .background(R.color.color_intro_evaluation)
                .backgroundDark(R.color.color_dark_intro_evaluation)
                .image(R.drawable.evaluation_icon)
                .buttonCtaLabel(R.string.common_signin_button_text)
                .buttonCtaClickListener { openSignInActivity() }
                .build())

        addSlide(SimpleSlide.Builder()
                .title(R.string.intro_evaluation)
                .description(R.string.intro_evaluation_description)
                .image(R.drawable.skills_icon)
                .background(R.color.color_intro_skills)
                .backgroundDark(R.color.color_dark_intro_skills)
                // because there is error in library
                .buttonCtaLabel(R.string.common_signin_button_text)
                .buttonCtaClickListener { openSignInActivity() }
                .build())

        addSlide(SimpleSlide.Builder()
                .title(R.string.intro_evaluation)
                .description(R.string.intro_evaluation_description)
                .image(R.drawable.find_jobs)
                .background(R.color.color_intro_find_jobs)
                .backgroundDark(R.color.color_dark_intro_find_jobs)
                // because there is error in library
                .buttonCtaLabel(R.string.common_signin_button_text)
                .buttonCtaClickListener { openSignInActivity() }
                .build())

        addSlide(SimpleSlide.Builder()
                .title(R.string.intro_evaluation)
                .description(R.string.intro_evaluation_description)
                .image(R.drawable.chart_icon)
                .background(R.color.color_intro_chart)
                .backgroundDark(R.color.color_dark_intro_chart)
                // because there is error in library
                .buttonCtaLabel(R.string.common_signin_button_text)
                .buttonCtaClickListener { openSignInActivity() }
                .build())
    }
*/


    private fun openSignInActivity() {

    }


}