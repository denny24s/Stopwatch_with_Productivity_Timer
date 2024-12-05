package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var settingsButton: Button
    private lateinit var progressBar: ProgressBar

    private var seconds = 0
    private var running = false
    private var handler = Handler(Looper.getMainLooper())
    private var upperLimit: Int? = null  

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        startButton = findViewById(R.id.startButton)
        resetButton = findViewById(R.id.resetButton)
        settingsButton = findViewById(R.id.settingsButton)
        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = ProgressBar.INVISIBLE

        updateTime()

        startButton.setOnClickListener {
            if (!running) {
                running = true
                progressBar.visibility = ProgressBar.VISIBLE
                runTimer()
                settingsButton.isEnabled = false
            }
        }

        resetButton.setOnClickListener {
            running = false
            handler.removeCallbacks(runnable)
            seconds = 0
            updateTime()
            progressBar.visibility = ProgressBar.INVISIBLE
            textView.setTextColor(Color.BLACK)  
            settingsButton.isEnabled = true
        }

        settingsButton.setOnClickListener {
            showSettingsDialog()
        }
    }

    private fun runTimer() {
        handler.postDelayed(runnable, 1000)
    }

    private val runnable = object : Runnable {
        override fun run() {
            if (running) {
                seconds++
                updateTime()

                
                if (upperLimit != null && seconds >= upperLimit!!) {
                    textView.setTextColor(Color.RED)
                }

                changeProgressBarColor()
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun updateTime() {
        val minutes = seconds / 60
        val secs = seconds % 60
        textView.text = String.format("%02d:%02d", minutes, secs)
    }

    private fun changeProgressBarColor() {
        val randomColor = Color.rgb(Random.nextInt(200), Random.nextInt(200), Random.nextInt(200))
        progressBar.indeterminateTintList = ColorStateList.valueOf(randomColor)
    }

 
    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_settings, null)
        val editText = dialogView.findViewById<EditText>(R.id.upperLimitEditText)

        builder.setView(dialogView)
            .setTitle("Set Upper Limit")
            .setPositiveButton("OK") { _, _ ->
                val input = editText.text.toString()
                if (input.isNotEmpty()) {
                    upperLimit = input.toInt()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}
