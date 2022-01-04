package com.example.snake

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.snake.databinding.ActivityResultBinding
import com.example.snake.viewModels.ResultViewModel

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        this.viewModel = ViewModelProvider(this)[ResultViewModel::class.java]
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_result)
        this.binding.lifecycleOwner = this
        this.binding.viewModel = viewModel

        this.initHighScore()

        this.binding.finalScore.text = intent.extras?.get("score").toString()
        if ((intent.extras?.get("status") as Boolean) || (intent.extras?.get("score") as Double) >= this.viewModel.getScore())
            this.binding.imageView2.setImageResource(R.drawable.snake_hat_removebg_preview)
        else
            this.binding.imageView2.setImageResource(R.drawable.istockphoto_1294539302_612x612_removebg_preview)
    }

    fun close(v: View) {
        this.updateScore()
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    private fun initHighScore() {
        val preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE)
        this.viewModel.updateScore(
            preferences.getString("highScoreValue", "0.0")!!.toDouble(),
            preferences.getString("highScoreName", "")!!.toString()
        )
    }

    private fun updateScore() {
        val currentScore = intent.extras?.get("score") as Double
        val currentName = this.binding.name.text.toString()
        if (currentScore > this.viewModel.getScore()) {
            getSharedPreferences("MY_PREFS", MODE_PRIVATE).edit().apply {
                putString("highScoreValue", currentScore.toString())
                putString("highScoreName", currentName)
                apply()
            }
        }
    }

}