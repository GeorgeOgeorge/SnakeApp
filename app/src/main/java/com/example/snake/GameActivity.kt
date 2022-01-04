package com.example.snake

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
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
    private val activityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            finish()
        }

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
        grid.rowCount = viewModel.gettable().getTableHeight()
        grid.columnCount = viewModel.gettable().getTableWidth()
        for (i in 0..(table.size - 1)) {
            for (j in 0..(table[0].size - 1)) {
                table[i][j] = inflater.inflate(R.layout.table, grid, false) as ImageView
                grid.addView(table[i][j])
            }
        }
        this.startGame(table)
    }

    private fun startGame(table: Array<Array<ImageView?>>) {
        thread {
            while (!this.isPaused) {
                Thread.sleep(this.viewModel.gettable().getFrameRate().toLong())
                runOnUiThread {
                    printTable(table)
                    if (this.viewModel.checkCollision()) this.closeGame(
                        false,
                        this.viewModel.table.getScore()
                    )
                    else if (this.viewModel.checkVictory()) this.closeGame(
                        true,
                        this.viewModel.table.getScore()
                    )
                    if (this.viewModel.checkFruit()) {
                        this.viewModel.updateScore()
                        this.binding.score.text = this.viewModel.getScore()
                    }
                    this.viewModel.snakeWalk()
                    this.viewModel.snakeWallCollision()
                }
            }
        }
    }

    fun pause(v: View) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("codPage", 3)
            putExtra("gameStatus", false)
            putExtra("table", viewModel.table)
        })
        finish()
    }

    private fun printTable(table: Array<Array<ImageView?>>) {
        for (i in 0..table.size - 1) {
            for (j in 0..table[0].size - 1) {
                table[i][j]!!.setImageBitmap(
                    Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888).apply {
                        eraseColor(Color.BLACK)
                    })
            }
        }
        this.viewModel.gettable().getSnake().body.forEach {
            table[it[0]][it[1]]!!
                .setImageBitmap(
                    Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888)
                        .apply { eraseColor(Color.GREEN) }
                )
        }
        table[this.viewModel.gettable().getFruitLocation()[0]][this.viewModel.gettable()
            .getFruitLocation()[1]]!!
            .setImageBitmap(
                Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888).apply { eraseColor(Color.RED) }
            )
    }

    private fun closeGame(status: Boolean, score: Double) {
        this.activityForResult.launch(
            Intent(this, ResultActivity::class.java).apply {
                putExtra("status", status)
                putExtra("score", score)
            }
        )
    }

}

