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
                this._frameRate = 150
                this._scoreMultiplier = 1.0
            }
            2 -> {
                this._frameRate = 100
                this._scoreMultiplier = 1.5
            }
            3 -> {
                this._frameRate = 50
                this._scoreMultiplier = 2.0
            }
        }
        when(conf.getMapSize()) {
            1 -> {
                this._tableHeight = 20
                this._tableWidth = 20
            }
            2 -> {
                this._tableHeight = 30
                this._tableWidth = 30
            }
        }
        this.table = Array(this._tableHeight){arrayOfNulls(this._tableWidth)}
        this.spawnFruit()
        this.spawnSnake()
    }

    fun getTable(): Array<Array<ImageView?>> { return this.table }

    fun getSnake(): Snake { return this._snake }

    fun getConf(): Conf { return this._conf }

    fun setConf(conf: Conf) { this._conf = conf }

    fun getScore(): Double { return this._score }

    fun getFrameRate(): Int { return this._frameRate }

    fun getTableHeight(): Int { return this._tableHeight }

    fun getTableWidth(): Int { return this._tableWidth }

    fun setSnakeDirection(direction: Int) { this._snake.changeDirection(direction) }

    fun snakeGrow() { this._snake.grow() }

    fun checkCollision(): Boolean {
        this._snake.body.subList(1,this._snake.body.size).forEach{
            if(it.contentEquals(this._snake.body[0])) return true
        }
        return false
    }

    fun wallCollision() {
        this.wallCollisionHeight()
        this.wallCollisionWidth()
    }

    fun checkFruitCollision() : Boolean {
        return this.getFruitLocation().contentEquals(this._snake.body[0])
    }

    fun spawnFruit() {
        this._fruit[0] = (0 until this.getTableWidth() - 1).random()
        this._fruit[1] = (0 until this.getTableHeight() - 1).random()
    }

    fun checkVictory(): Boolean {
        return (this.getTableHeight() * this.getTableWidth()) == this.getSnake().body.size
    }

    fun getFruitLocation(): Array<Int> { return this._fruit }

    fun updateScore() { this._score = this._score + (10 * this._scoreMultiplier) }

    private fun spawnSnake() {
        this._snake.body[0][0] = (0 until this.getTableWidth() - 1).random()
        this._snake.body[0][1] = (0 until this.getTableHeight() - 1).random()
    }

    private fun wallCollisionWidth() {
        if(this.getSnake().body[0][0] >= this.getTableWidth())
            this._snake.body[0][0] = 0
        else if(this.getSnake().body[0][0] < 0)
            this._snake.body[0][0] = this.getTableWidth() - 1
    }

    private fun wallCollisionHeight() {
        if(this.getSnake().body[0][1] >= this.getTableHeight())
            this._snake.body[0][1] = 0
        else if(this.getSnake().body[0][1] < 0)
            this._snake.body[0][1] = this.getTableHeight() - 1
    }

}