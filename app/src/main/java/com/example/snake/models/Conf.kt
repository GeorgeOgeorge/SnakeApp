package com.example.snake.models

import java.io.Serializable

class Conf(private var _difficulty: Int = 2, private var _mapSize: Int = 2) : Serializable {

    fun setDifficulty(difficulty: Int) { this._difficulty = difficulty }

    fun getDifficulty(): Int { return this._difficulty }

    fun setMapSize(mapSize: Int) { this._mapSize = mapSize }

    fun getMapSize(): Int { return this._mapSize }

}