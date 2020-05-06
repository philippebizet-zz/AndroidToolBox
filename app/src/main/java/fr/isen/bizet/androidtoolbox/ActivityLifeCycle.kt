package fr.isen.bizet.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_life_cycle.*
import android.widget.Toast

class ActivityLifeCycle : AppCompatActivity(){

    private var texte :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)
        texte += "onCreate()\n"
        lifeCycleTextView.text = texte
    }

    override fun onPause() {
        super.onPause()
        texte += "onPause()\n"
        lifeCycleTextView.text = texte
    }

    override fun onResume() {
        super.onResume()
        texte += "onResume()\n"
        lifeCycleTextView.text = texte
    }

    override fun onStop() {
        super.onStop()
        texte += "onStop()\n"
        lifeCycleTextView.text = texte
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "Activité détruite", Toast.LENGTH_SHORT).show()
    }
}