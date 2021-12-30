package com.example.snake.models

import java.io.Serializable
import java.util.*

class Snake : Serializable {
    var body: ArrayList<Array<Int>> = arrayListOf(arrayOf(15, 15))
    var direction: Int = arrayOf(1, 2, 3, 4).random()

    fun grow() {
        when (this.direction) {
            1 -> this.body.add(arrayOf(this.body.last()[0], this.body.last()[1] + 1))
            2 -> this.body.add(arrayOf(this.body.last()[0] + 1, this.body.last()[1]))
            3 -> this.body.add(arrayOf(this.body.last()[0], this.body.last()[1] - 1))
            4 -> this.body.add(arrayOf(this.body.last()[0] - 1, this.body.last()[1]))
        }
    }

    fun changeDirection(direction: Int) {
        when (direction) {
            1 -> if (this.direction != 3) this.direction = 1
            2 -> if (this.direction != 4) this.direction = 2
            3 -> if (this.direction != 1) this.direction = 3
            4 -> if (this.direction != 2) this.direction = 4
        }
    }

    fun walk() {
        this.body.apply {
            when (direction) {
                1 -> body.add(0, arrayOf(first()[0], first()[1] - 1))
                2 -> body.add(0, arrayOf(first()[0] + 1, first()[1]))
                3 -> body.add(0, arrayOf(first()[0] - 1, first()[1]))
                4 -> body.add(0, arrayOf(first()[0], first()[1] + 1))
            }
            removeLast()
        }
    }

}