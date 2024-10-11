package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
    private val notificationId = 393939
    private val channelId = "org.hyperskill"  // Channel ID for notifications

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

        createNotificationChannel()  // Create the notification channel

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

                // Check if upperLimit is valid (not null and greater than 0)
                if (upperLimit != null && upperLimit!! > 0 && seconds >= upperLimit!!) {
                    textView.setTextColor(Color.RED)
                    sendNotification()  // Send notification only when the upperLimit is positive and exceeded
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

    // Create a notification channel for devices running Android O and above
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Stopwatch Notification Channel"
            val descriptionText = "Channel for stopwatch time exceeded notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Send a notification when the upper limit is reached
    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)  // Use your drawable for the icon
            .setContentTitle("Stopwatch Notification")
            .setContentText("Time exceeded")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)  // Only alert once
            .setAutoCancel(true)  // Automatically dismiss notification on tap
            .setOngoing(true)  // Makes the notification ongoing until user action

        // Set flags using bitwise OR for insistent
        val notification = builder.build()
        notification.flags = notification.flags or NotificationCompat.FLAG_ONLY_ALERT_ONCE
        notification.flags = notification.flags or NotificationCompat.FLAG_INSISTENT

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, notification)
        }
    }
}
