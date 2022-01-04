package com.example.snake.viewModels

import androidx.lifecycle.ViewModel

class ResultViewModel: ViewModel() {

    var highScoreName: String = ""
    var highScoreValue: Double = 0.0

    fun updateScore(score: Double, name: String) {
        this.highScoreName = name
        this.highScoreValue = score
    }

    fun getScore(): Double { return this.highScoreValue }

    fun getName(): String { return this.highScoreName}
}