package com.example.galgeleg2

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import galgeleg.Galgelogik
import java.util.*

class MainActivity : AppCompatActivity(), View.OnKeyListener {
    private lateinit var txtinput: TextView
    private lateinit var btn: Button
    private lateinit var debugtxt: TextView
    private lateinit var debugtxt2: TextView
    private val galgelogik: Galgelogik = Galgelogik()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        debugtxt = findViewById(R.id.test)
        debugtxt.text = null

        debugtxt2 = findViewById(R.id.test2)
        debugtxt2.text = galgelogik.synligtOrd

        txtinput = findViewById(R.id.guessinput)
        txtinput.setOnKeyListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
        // If the event is a key-down event on the "enter" button
        if (event.action == KeyEvent.ACTION_DOWN &&
            keyCode == KeyEvent.KEYCODE_ENTER
        ) {
            // Perform action on key press
            if (txtinput.length() == 1) {
                galgelogik.gætBogstav(txtinput.text.toString().decapitalize(Locale.getDefault()))

                if (galgelogik.erSidsteBogstavKorrekt()) {
                    debugtxt.text = galgelogik.brugteBogstaver.toString()
                    debugtxt2.text = galgelogik.synligtOrd
                    Toast.makeText(this, "Dit gæt var korrekt!", Toast.LENGTH_LONG).show()
                } else {
                    debugtxt.text = galgelogik.brugteBogstaver.toString()
                    Toast.makeText(this, "Dit gæt var forkert...", Toast.LENGTH_LONG).show()
                }

                txtinput.text = null

            }
            return true
        }
        return false
    }

}