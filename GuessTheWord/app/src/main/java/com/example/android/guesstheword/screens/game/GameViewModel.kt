package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    init {
        Log.i("GameViewModel", "GVM Created!")

        resetList()
        nextWord()

    }
    // Moved over from GameFragment
    var word = ""
    var score = 0
    private lateinit var wordList: MutableList<String>

    fun resetList() {
        wordList = mutableListOf("queen","hospital","basketball","cat","change","snail","soup", "calendar", "sad", "desk", "guitar", "home", "railway", "zebra", "jelly", "car", "crow", "trade", "bag", "roll", "bubble")
        wordList.shuffle()
    }

    fun nextWord() {
        if (!wordList.isEmpty()) {
            //Select and remove a word from the list
            word = wordList.removeAt(0)
        }
    // Below removed because these are being called in GameFragment.kt
    //updateWordText()
        //updateScoreText()
    }
    fun onSkip() {
        score--
        nextWord()
    }

    fun onCorrect() {
        score++
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GVM Destroyed!")
    }
}