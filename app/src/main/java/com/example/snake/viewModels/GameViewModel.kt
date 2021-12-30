package com.example.snake.viewModels

import androidx.lifecycle.ViewModel
import com.example.snake.models.Table

class GameViewModel : ViewModel() {
    private lateinit var table: Table

    fun updateTable(table: Table) { this.table = table }

    fun gettable(): Table { return this.table }

    fun changeSnakeDirection(direction: Int) {this.table.setSnakeDirection(direction)}

    private fun snakeGrow() { this.table.snakeGrow() }

    fun checkCollision(): Boolean { return this.table.checkCollision() }

    fun checkFruit(): Boolean { return this.table.checkFruitCollision() }

    fun snakeWalk() { this.table.getSnake().walk() }

    fun getScore(): String { return this.table.getScore().toString() }

    fun updateScore() {
        this.table.updateScore()
        this.table.spawnFruit()
        this.snakeGrow()
    }

}