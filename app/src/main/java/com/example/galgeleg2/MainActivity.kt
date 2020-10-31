package com.example.galgeleg2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import galgeleg.Galgelogik

class MainActivity : AppCompatActivity() {
    private lateinit var record: TextView
    private val spil: Galgelogik = Galgelogik()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        record = findViewById(R.id.test)
        record.setText(spil.getOrdet())
    }
}