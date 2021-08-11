package com.example.colormyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
        // To add image as background:
        //var boxTwoTextView = findViewById<TextView>(R.id.box_two_text)
        //boxTwoTextView.setBackgroundResource(R.drawable.<image>)
    }

    private fun setListeners() {
        val boxOneTextView = findViewById<TextView>(R.id.box_one_text)
        val boxTwoTextView = findViewById<TextView>(R.id.box_two_text)
        val boxThreeTextView = findViewById<TextView>(R.id.box_three_text)
        val boxFourTextView = findViewById<TextView>(R.id.box_four_text)
        val boxFiveTextView = findViewById<TextView>(R.id.box_five_text)
        val constrainLayoutView = findViewById<View>(R.id.constraint_layout)
        val clickableViews: List<View> = listOf(boxOneTextView,boxTwoTextView,boxThreeTextView,boxFourTextView,boxFiveTextView,constrainLayoutView)
        for (item in clickableViews) {
            item.setOnClickListener { makeColored(it) }
        }
    }

    private fun makeColored(view: View) {
        when (view.id) {

            // Boxes using Color class colors for background
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)

            // Boxes using Android color resources for background
            R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_green_light)
            R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_green_dark)
            R.id.box_five_text -> view.setBackgroundResource(android.R.color.holo_green_light)

            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }
}