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


class LostActivity : AppCompatActivity() {
    private lateinit var livestxt: TextView
    private lateinit var debugtxt: TextView
    private lateinit var debugtxt2: TextView
    private lateinit var galgeimg: ImageView
    private val galgelogik: Galgelogik = Galgelogik()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost)

        galgeimg = findViewById(R.id.galgeimg)
        galgeimg.setImageResource(R.drawable.forkert6)

        livestxt = findViewById(R.id.lives)
        livestxt.text = "Du har tabt! Prøv igen."

        debugtxt = findViewById(R.id.test)
        debugtxt.text = "Ordet var: " + intent.getStringExtra("word")

        debugtxt2 = findViewById(R.id.test2)
        debugtxt2.text = null
    }
}