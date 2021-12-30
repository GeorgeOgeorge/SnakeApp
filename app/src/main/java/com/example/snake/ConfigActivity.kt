package com.example.snake

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.snake.databinding.ActivityConfigBinding
import com.example.snake.models.Conf
import com.example.snake.viewModels.ConfigViewModel

class ConfigActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigBinding
    private lateinit var viewModel: ConfigViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        this.viewModel = ViewModelProvider(this)[ConfigViewModel::class.java]
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_config)
        this.binding.lifecycleOwner = this
        this.binding.viewModel = viewModel

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        viewModel.updateConf(intent.extras?.get("conf") as Conf)
        this.checkConfigStates()
    }

    fun confirmConf(v: View) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("conf", viewModel.conf)
            putExtra("codPage", 2)
        })
        finish()
    }

    fun cancelConf(v: View) {
        setResult(Activity.RESULT_CANCELED, Intent())
        finish()
    }

    private fun checkConfigStates() {
        when(this.viewModel.conf.getDifficulty()) {
            1 -> this.binding.Difficulty1.toggle()
            2 -> this.binding.Difficulty2.toggle()
            3 -> this.binding.Difficulty3.toggle()
        }
        when(this.viewModel.conf.getMapSize()) {
            1 -> this.binding.mapSize1.toggle()
            2 -> this.binding.mapSize2.toggle()
        }
    }

}