package com.example.galgeleg2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LostActivity : AppCompatActivity() {
    private lateinit var livestxt: TextView
    private lateinit var debugtxt: TextView
    private lateinit var debugtxt2: TextView
    private lateinit var galgeimg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost)

        galgeimg = findViewById(R.id.galgeimg)
        galgeimg.setImageResource(R.drawable.forkert6)

        livestxt = findViewById(R.id.lives)
        livestxt.text = getString(R.string.pressback)

        debugtxt = findViewById(R.id.test)
        debugtxt.text = getString(R.string.wordwas) + intent.getStringExtra("word")

        debugtxt2 = findViewById(R.id.test2)
        debugtxt2.text = getString(R.string.youlost)
    }
}