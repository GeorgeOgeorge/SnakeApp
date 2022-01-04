package com.example.snake

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.snake.databinding.ActivityMainBinding
import com.example.snake.models.Conf
import com.example.snake.models.Table
import com.example.snake.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val activityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) when (it.data?.extras?.get("codPage")) {
                2 -> this.viewModel.createNewGame(it.data!!.extras!!.get("conf") as Conf)
                3 -> this.viewModel.updateGame(it.data!!.extras!!.get("table") as Table)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        this.viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        this.binding.lifecycleOwner = this
        this.binding.viewModel = viewModel

    }

    override fun onStart() {
        super.onStart()
        val preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE)
        this.viewModel.updateConf(
            Conf(
                preferences.getString("difficulty", "2")!!.toInt(),
                preferences.getString("mapSize", "2")!!.toInt()
            )
        )
    }

    override fun onStop() {
        super.onStop()
        getSharedPreferences("MY_PREFS", MODE_PRIVATE).edit().apply {
            putString("difficulty", viewModel.table.getConf().getDifficulty().toString())
            putString("mapSize", viewModel.table.getConf().getMapSize().toString())
            apply()
        }
    }

    fun openGame(v: View) {
        this.activityForResult.launch(
            Intent(this, GameActivity::class.java).apply {
                putExtra("table", viewModel.table)
            }
        )
    }

    fun openConfig(v: View) {
        this.activityForResult.launch(
            Intent(this, ConfigActivity::class.java).apply {
                putExtra("conf", viewModel.table.getConf())
            }
        )
    }

    //fazer placar de maior na tela inicial
    //corrigir continuar
}