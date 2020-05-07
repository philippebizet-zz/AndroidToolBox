package fr.isen.bizet.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_save_file.*
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ActivitySave : AppCompatActivity(){

    var how_old = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_file)

        saveButton.setOnClickListener {
            saveDataToFile(
                lastName.text.toString(),
                firstName.text.toString(),
                date.text.toString()
            )
        }

        readButton.setOnClickListener {
            readDataFromFile()
        }

        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

                val today = Calendar.getInstance()
                date.text = sdf.format(cal.time)

                how_old = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR)

                if (today.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR))
                    how_old--

            }

        date.setOnClickListener {
            showDatePicker(dateSetListener)
        }
    }

    private fun showDatePicker(dateSetListener: DatePickerDialog.OnDateSetListener) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            this@ActivitySave, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun readDataFromFile() {
        val data = File(cacheDir.absolutePath, "my_file.json").readText()
        val jsonObject = JSONObject(data)

        val lastName = jsonObject.optString(KEY_LAST_NAME)
        val firstName = jsonObject.optString(KEY_FIRST_NAME)
        val date = jsonObject.optString(KEY_BIRTH_DATE)

        AlertDialog.Builder(this)
            .setTitle("Lecture du fichier")
            .setMessage("Nom : $lastName \nPrénom : $firstName \nDate : $date \nAge : $how_old")
            .create()
            .show()
    }

    private fun saveDataToFile(lastName: String, firstName: String, birthDate: String) {
        val jsonObject = JSONObject()
        jsonObject.put(KEY_LAST_NAME, lastName)
        jsonObject.put(KEY_FIRST_NAME, firstName)
        jsonObject.put(KEY_BIRTH_DATE, birthDate)

        val data = jsonObject.toString()
        File(cacheDir.absolutePath, "my_file.json").writeText(data)
    }

    companion object {
        private const val KEY_LAST_NAME = "lastname"
        private const val KEY_FIRST_NAME = "firstname"
        private const val KEY_BIRTH_DATE = "date"
    }
}
