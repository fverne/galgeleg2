package com.example.galgeleg2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import galgeleg.Galgelogik
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors.newSingleThreadExecutor


class MainActivity : AppCompatActivity(), View.OnKeyListener {
    private lateinit var txtinput: TextView
    private lateinit var livestxt: TextView
    private lateinit var debugtxt: TextView
    private lateinit var debugtxt2: TextView
    private lateinit var galgeimg: ImageView
    private val galgelogik: Galgelogik = Galgelogik.getInstance()
    private val handler: Handler = Handler()
    private val bgthread: Executor = newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // if there is no saved data
        if (getArrayList("galgeord").isNullOrEmpty()) {
            // background thread does network stuff
            bgthread.execute {
                galgelogik.hentOrdFraRegneark("2")
                saveArrayList(galgelogik.muligeOrd, "galgeord")
                galgelogik.setMuligeOrd(getArrayList("galgeord"))

                // main thread updates UI once BGthread is done networking
                handler.post {
                    initUI()
                }
            }
        } else { // else, load the saved data and load the UI
            galgelogik.setMuligeOrd(getArrayList("galgeord"))
            galgelogik.startNytSpil()
            initUI()
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {

        // Changes enter button on keyboard to execute following code.
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            if (txtinput.length() == 1) {
                galgelogik.gætBogstav(txtinput.text.toString().decapitalize(Locale.getDefault()))

                if (galgelogik.erSidsteBogstavKorrekt()) { // correct guess
                    debugtxt2.text = galgelogik.synligtOrd
                    Toast.makeText(this, "Korrekt bogstav!", Toast.LENGTH_LONG).show()
                } else { // not correct guesss
                    livestxt.text = getString(R.string.youhave) + (6-galgelogik.antalForkerteBogstaver) + getString(R.string.livesleft)
                    Toast.makeText(this, "Forkert gæt!", Toast.LENGTH_LONG).show()

                    // switch case that changes the image depending on lives left
                    when(galgelogik.antalForkerteBogstaver) {
                        1 -> galgeimg.setImageResource(R.drawable.forkert1)
                        2 -> galgeimg.setImageResource(R.drawable.forkert2)
                        3 -> galgeimg.setImageResource(R.drawable.forkert3)
                        4 -> galgeimg.setImageResource(R.drawable.forkert4)
                        5 -> galgeimg.setImageResource(R.drawable.forkert5)
                        else -> galgeimg.setImageResource(R.drawable.forkert6)
                    }

                }

                //kunne forbedres så det ikke var den rene toString(), så ingen brackets
                debugtxt.text = getString(R.string.usedletters) + galgelogik.brugteBogstaver.toString()
                txtinput.text = null

            }

            // check om spillet er slut, og start tabt/vundet skærmbilledet
            if (galgelogik.erSpilletSlut()) {
                if (galgelogik.erSpilletVundet()) {
                    val i = Intent(this, WonActivity::class.java)
                    i.putExtra("tries", galgelogik.brugteBogstaver.size)
                    startActivity(i)
                } else { //spillet er tabt
                    val i = Intent(this, LostActivity::class.java)
                    i.putExtra("word", galgelogik.ordet)
                    startActivity(i)
                }

                // genstart spillet, så det er klart når man returnerer fra tabt/vundet intentet og vil spille igen
                // test med handler, delay tilføjet for at gøre animationen for intentskiftet mere flydende
                handler.postDelayed(1000) {
                    recreate()
                }

            }
        }

        // returner true så keyboardet ikke forsvinder
        return true
    }


    // https://stackoverflow.com/questions/7057845/save-arraylist-to-sharedpreferences
    // gem listen af ord som et json objekt i preferencemanager
    private fun saveArrayList(list: ArrayList<String?>?, key: String?) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    // https://stackoverflow.com/questions/7057845/save-arraylist-to-sharedpreferences
    // hent listen af data fra preferencemanager som et json objekt, og pak det ud til en arraylist
    private fun getArrayList(key: String?): ArrayList<String?>? {
        val prefs: SharedPreferences = getDefaultSharedPreferences(this)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return gson.fromJson(json, type)
    }


    // metode der sætter UI på skærmbilledet
    private fun initUI() {
        galgeimg = findViewById(R.id.galgeimg)
        galgeimg.setImageResource(R.drawable.galge)

        txtinput = findViewById(R.id.guessinput)
        txtinput.setOnKeyListener(this)
        txtinput.requestFocus()

        livestxt = findViewById(R.id.lives)
        livestxt.text = getString(R.string.livesinit)

        debugtxt = findViewById(R.id.test)
        debugtxt.text = getString(R.string.welcome)

        debugtxt2 = findViewById(R.id.test2)
        debugtxt2.text = galgelogik.synligtOrd
    }
}



