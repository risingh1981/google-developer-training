package com.example.diceroller

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random


// Extra Add on: Add a button to the app labeled "Reset" that appears just below the Roll button.
// Have that button reset the result text view to 0.

// Challenge : Add 2 dice
class MainActivity : AppCompatActivity() {

    lateinit var diceImage1: ImageView
    lateinit var diceImage2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.text = "Let's Roll"
        val resetButton: Button = findViewById(R.id.reset_button)

        diceImage1 = findViewById(R.id.dice_image)
        diceImage2 = findViewById(R.id.dice_image2)
        rollButton.setOnClickListener { rollDice() }
        resetButton.setOnClickListener { resetTo0() }
        //resetButton.setOnClickListener { Toast.makeText(this,"button clicked", Toast.LENGTH_SHORT).show() }
        //Toast.makeText(applicationContext,"this is toast in onCreate",Toast.LENGTH_SHORT).show()


    }
    private fun getRandomDiceImage() : Int {
        val drawableResource = when (Random.nextInt(6) + 1) {
            1 -> R.drawable.dice_1 // These calls return a resId (resource Id for each drawable resource)
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        //Toast.makeText(applicationContext,"this is toast message $drawableResource",Toast.LENGTH_SHORT).show()
        return drawableResource
    }

    private fun resetTo0() {
        val drawableResource = R.drawable.empty_dice
        diceImage1.setImageResource((drawableResource))
        diceImage2.setImageResource((drawableResource))
    }

    private fun rollDice() {
        // Generate Random number 0 - 5 and add 1 to get a number between 1 - 6
        val drawableResource1 = getRandomDiceImage()
        val drawableResource2 = getRandomDiceImage()
        diceImage1.setImageResource(drawableResource1)
        diceImage2.setImageResource(drawableResource2)
    }


}