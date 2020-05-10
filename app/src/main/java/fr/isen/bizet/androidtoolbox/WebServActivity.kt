package fr.isen.bizet.androidtoolbox

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.bizet.androidtoolbox.API.User
import kotlinx.android.synthetic.main.activity_webserv.*

class WebServActivity : AppCompatActivity( ){

    private val link = "https://randomuser.me/api/?results=30"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webserv)

        getDataAPI()

    }

    private fun getDataAPI() : User {
        val queue = Volley.newRequestQueue(this)
        var user = User()
        val request = JsonObjectRequest(
            Request.Method.GET,
            link,
            null,
            Response.Listener{ response ->
                val gson = Gson()
                user = gson.fromJson(response.toString(), User::class.java)
                user_recyclerView.layoutManager = LinearLayoutManager(this)
                user_recyclerView.adapter = UserRecyclerView(user, this)
                user_recyclerView.visibility = View.VISIBLE
            },
            Response.ErrorListener {
                Toast.makeText(this, "Uh oh, something has gone wrong.", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        return user
    }
}