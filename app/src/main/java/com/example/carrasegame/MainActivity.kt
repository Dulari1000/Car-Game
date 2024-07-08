package com.example.carrasegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), CarGame {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameView: View
    lateinit var score: TextView
    lateinit var highScoreTextView: TextView // Add this line

    // Add this method to retrieve the high score from SharedPreferences
    private fun displayHighScore() {
        val sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val highScore = sharedPreferences.getInt("HighScore", 0)
        highScoreTextView.text = "High Score: $highScore"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScoreTextView = findViewById(R.id.highScore) // Initialize highScoreTextView

        displayHighScore() // Call the method to display the high score

        startBtn.setOnClickListener {
            mGameView = com.example.carcity.View(this, this)
            mGameView.setBackgroundResource(R.drawable.road)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            highScoreTextView.visibility = View.GONE // Hide high score TextView when game starts
        }
    }

    override fun closeGame(mScore: Int) {
        score.text = "Score : $mScore"
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        highScoreTextView.visibility = View.VISIBLE // Show high score TextView when game ends

        val sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val currentHighScore = sharedPreferences.getInt("HighScore", 0)
        if (mScore > currentHighScore) {
            val editor = sharedPreferences.edit()
            editor.putInt("HighScore", mScore)
            editor.apply()
            highScoreTextView.text = "High Score: $mScore" // Update high score TextView if a new high score is achieved
        }
    }
}
