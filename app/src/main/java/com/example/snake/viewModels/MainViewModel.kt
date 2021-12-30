package com.example.snake.viewModels

import androidx.lifecycle.ViewModel
import com.example.snake.models.Conf
import com.example.snake.models.Snake
import com.example.snake.models.Table

class MainViewModel : ViewModel() {
    var table : Table = Table()

    fun updateConf(conf: Conf) { this.table.setConf(conf) }

    fun createNewGame(conf: Conf) {
        this.table = Table(Snake(), conf)
    }

}