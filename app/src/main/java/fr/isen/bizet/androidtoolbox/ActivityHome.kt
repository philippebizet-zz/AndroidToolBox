package fr.isen.bizet.androidtoolbox

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class ActivityHome : AppCompatActivity() {

    private val USER_PREFS = "user_prefs"
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)

        lifeCycleButton.setOnClickListener {
            val intent = Intent(this, ActivityLifeCycle::class.java)
            startActivity(intent)
        }

        decoButton.setOnClickListener {
            Toast.makeText(applicationContext, "Déconnexion réussi !", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ActivityLogin::class.java)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            this.finish()
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            val intent = Intent(this, ActivitySave::class.java)
            startActivity(intent)
        }

        permissionsButton.setOnClickListener {
            val intent = Intent(this, ActivityPermissions::class.java)
            startActivity(intent)
        }

        webservButton.setOnClickListener {
            val intent = Intent(this, WebServActivity::class.java)
            startActivity(intent)
        }

    }
}
