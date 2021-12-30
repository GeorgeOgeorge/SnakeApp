package com.example.snake.viewModels

import androidx.lifecycle.ViewModel
import com.example.snake.models.Conf

class ConfigViewModel : ViewModel() {
    lateinit var conf: Conf

    fun updateConf(conf: Conf) { this.conf = conf }

    fun changeDifficulty(difficulty: Int) { this.conf.setDifficulty(difficulty) }

    fun changeMapSize(mapSize: Int) { this.conf.setMapSize(mapSize) }

}