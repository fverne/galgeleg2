package com.example.galgeleg2

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import galgeleg.Galgelogik
import java.util.*


class WonActivity : AppCompatActivity() {
    private lateinit var livestxt: TextView
    private lateinit var debugtxt: TextView
    private lateinit var debugtxt2: TextView
    private lateinit var galgeimg: ImageView
    private val galgelogik: Galgelogik = Galgelogik()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_won)

        galgeimg = findViewById(R.id.galgeimg)
        galgeimg.setImageResource(R.drawable.galge)

        livestxt = findViewById(R.id.lives)
        livestxt.text = "Du brugte "+ intent.getIntExtra("tries", 0) + " forsøg."

        debugtxt = findViewById(R.id.test)
        debugtxt.text = null

        debugtxt2 = findViewById(R.id.test2)
        debugtxt2.text = null

    }
}