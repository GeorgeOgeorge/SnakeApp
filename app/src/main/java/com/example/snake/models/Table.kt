package com.example.snake.models

import android.widget.ImageView
import java.io.Serializable

class Table(snake: Snake = Snake(), conf: Conf = Conf()) : Serializable {

    private var _snake: Snake = snake
    private var _conf: Conf = conf
    private var _fruit: Array<Int> = arrayOf(0,0)
    private var table = Array(1){ arrayOfNulls<ImageView>(1)}

    private var _tableHeight: Int = 0
    private var _tableWidth: Int = 0
    private var _frameRate: Int = 0

    private var _score: Double = 0.0
    private var _scoreMultiplier: Double = 0.0

    init {
        when(conf.getDifficulty()) {
            1 -> {
                this._frameRate = 25
                this._scoreMultiplier = 1.0
            }
            2 -> {
                this._frameRate = 50
                this._scoreMultiplier = 1.5
            }
            3 -> {
                this._frameRate = 25
                this._scoreMultiplier = 2.0
            }
        }
        when(conf.getMapSize()) {
            1 -> {
                this._tableHeight = 25
                this._tableWidth = 50
            }
            2 -> {
                this._tableHeight = 50
                this._tableWidth = 50
            }
        }
        this.table = Array(this._tableHeight){arrayOfNulls(this._tableWidth)}
        this.spawnFruit()
    }

    fun getTable(): Array<Array<ImageView?>> { return this.table }

    fun getSnake(): Snake { return this._snake }

    fun getConf(): Conf { return this._conf }

    fun setConf(conf: Conf) { this._conf = conf }

    fun getScore() : Double { return this._score }

    fun getTableHeight() : Int { return this._tableHeight }

    fun getTableWidth() : Int { return this._tableWidth }

    fun setSnakeDirection(direction: Int) { this._snake.changeDirection(direction) }

    fun snakeGrow() { this._snake.grow() }

    fun checkCollision(): Boolean {
        if(this._snake.body.subList(1,this._snake.body.size).contains(this._snake.body[0])) return true
        return false
    }

    fun wallCollision(): Boolean {
        return true
    }

    fun checkFruitCollision() : Boolean {
        return this.getFruitLocation().contentEquals(this._snake.body[0])
    }

    fun spawnFruit() {
        this._fruit[0] = (0 until this.getTableWidth()).random()
        this._fruit[1] = (0 until this.getTableHeight()).random()
    }

    fun getFruitLocation(): Array<Int> { return this._fruit }

    fun updateScore() { this._score = this._score + (10 * this._scoreMultiplier) }

}