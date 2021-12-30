package com.example.snake

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.snake.databinding.ActivityGameBinding
import com.example.snake.models.Table
import com.example.snake.viewModels.GameViewModel
import kotlin.concurrent.thread

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var viewModel: GameViewModel
    private var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        this.viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        this.binding.lifecycleOwner = this
        this.binding.viewModel = viewModel
        val inflater: LayoutInflater = LayoutInflater.from(this)
        this.viewModel.updateTable(intent.extras?.get("table") as Table)

        val grid = this.binding.grid
        val table = this.viewModel.gettable().getTable()

        grid.apply {
            rowCount = viewModel.gettable().getTableHeight()
            columnCount = viewModel.gettable().getTableWidth()
        }

        for (i in 0 until table.size) {
            for (j in 0 until table[0].size) {
                table[i][j] = inflater.inflate(R.layout.table, grid, false) as ImageView
                grid.addView(table[i][j])
            }
        }
        this.startGame(table)
    }

    private fun startGame(table: Array<Array<ImageView?>>) {
        thread {
            while (true) {
                Thread.sleep(200)
                runOnUiThread {
                    printTable(table)
                    if (this.viewModel.checkCollision()) {
                        //chamar tela de resultado
                    } else {
                        //checar se vitoria - checar controles - mostrar score
                        if (this.viewModel.checkFruit()) this.viewModel.updateScore()
                        this.viewModel.snakeWallCollision()
                        this.viewModel.snakeWalk()
                    }
                }
            }
        }
    }

    private fun printTable(table: Array<Array<ImageView?>>) {
        for (i in 0 until table.size) {
            for (j in 0 until table[0].size) {
                table[i][j]!!.setImageBitmap(Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888).apply {
                        eraseColor(Color.BLACK)
                })
            }
        }
        this.viewModel.gettable().getSnake().body.forEach {
            table[it[0]][it[1]]!!
                .setImageBitmap(Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888).apply { eraseColor(Color.GREEN) }
            )
        }
        table[this.viewModel.gettable().getFruitLocation()[0]][this.viewModel.gettable().getFruitLocation()[1]]!!
            .setImageBitmap(Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888).apply { eraseColor(Color.RED) }
        )
    }

}