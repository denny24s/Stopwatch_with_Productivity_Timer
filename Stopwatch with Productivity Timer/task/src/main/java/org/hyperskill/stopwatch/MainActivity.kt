package org.hyperskill.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button

    private var seconds = 0
    private var running = false
    private var handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        startButton = findViewById(R.id.startButton)
        resetButton = findViewById(R.id.resetButton)


        updateTime()


        startButton.setOnClickListener {
            if (!running) {
                running = true
                runTimer()
            }
        }

        resetButton.setOnClickListener {
            running = false
            handler.removeCallbacks(runnable)
            seconds = 0
            updateTime()
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
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun updateTime() {
        val minutes = seconds / 60
        val secs = seconds % 60
        textView.text = String.format("%02d:%02d", minutes, secs)
    }
}