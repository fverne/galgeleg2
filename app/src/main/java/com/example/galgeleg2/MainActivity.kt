package com.example.galgeleg2

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import galgeleg.Galgelogik
import java.util.*

class MainActivity : AppCompatActivity(), View.OnKeyListener {
    private lateinit var txtinput: TextView
    private lateinit var livestxt: TextView
    private lateinit var debugtxt: TextView
    private lateinit var debugtxt2: TextView
    private lateinit var galgeimg: ImageView
    private val galgelogik: Galgelogik = Galgelogik()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        galgeimg = findViewById(R.id.galgeimg)
        galgeimg.setImageResource(R.drawable.galge)

        txtinput = findViewById(R.id.guessinput)
        txtinput.setOnKeyListener(this)

        livestxt = findViewById(R.id.lives)
        livestxt.text = "Du har 6 liv før du taber."

        debugtxt = findViewById(R.id.test)

        debugtxt2 = findViewById(R.id.test2)
        debugtxt2.text = galgelogik.synligtOrd

    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {

        // Changes enter button on keyboard to execute following code.
        if (event.action == KeyEvent.ACTION_DOWN &&
            keyCode == KeyEvent.KEYCODE_ENTER
        ) {
            if (txtinput.length() == 1) {
                galgelogik.gætBogstav(txtinput.text.toString().decapitalize(Locale.getDefault()))

                if (galgelogik.erSidsteBogstavKorrekt()) {
                    debugtxt2.text = galgelogik.synligtOrd
                    Toast.makeText(this, "Dit gæt var korrekt!", Toast.LENGTH_LONG).show()
                } else {
                    livestxt.text = "Du har " + (6-galgelogik.antalForkerteBogstaver) + " liv tilbage."
                    Toast.makeText(this, "Dit gæt var forkert...", Toast.LENGTH_LONG).show()

                    // switch case that changes the image
                    when(galgelogik.antalForkerteBogstaver) {
                        0 -> galgeimg.setImageResource(R.drawable.galge)
                        1 -> galgeimg.setImageResource(R.drawable.forkert1)
                        2 -> galgeimg.setImageResource(R.drawable.forkert2)
                        3 -> galgeimg.setImageResource(R.drawable.forkert3)
                        4 -> galgeimg.setImageResource(R.drawable.forkert4)
                        5 -> galgeimg.setImageResource(R.drawable.forkert5)
                        else -> galgeimg.setImageResource(R.drawable.forkert6)
                    }

                }

                debugtxt.text = "Brugte Bogstaver: " + galgelogik.brugteBogstaver.toString()
                txtinput.text = null

            }
            return true
        }
        return false
    }

}