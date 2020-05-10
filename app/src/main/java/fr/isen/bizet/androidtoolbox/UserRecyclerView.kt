package fr.isen.bizet.androidtoolbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fr.isen.bizet.androidtoolbox.API.User
import kotlinx.android.synthetic.main.activity_webserv_piece.view.*

class UserRecyclerView(private val users: User, val context: Context) :
    RecyclerView.Adapter<UserRecyclerView.WebHolder>()
    {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view: View = inflater.inflate(R.layout.activity_webserv_piece, parent, false)
            return WebHolder(users, view, context)
        }

        override fun getItemCount(): Int {
            return users.results.size
        }

        override fun onBindViewHolder(holder: WebHolder, pos: Int) {
            holder.loadInfo(pos)
        }

        class WebHolder(private val us: User, view: View, val context: Context) :
            RecyclerView.ViewHolder(view) {
            private val name: TextView = view.user_name
            private val image: ImageView = view.user_photo
            private val address: TextView = view.user_address
            private val email: TextView = view.user_email


            fun loadInfo(index: Int) {
                val nameAPI =
                    us.results[index].name.first + " " + us.results[index].name.last
                val addressAPI =
                    us.results[index].location.city + "    " + us.results[index].location.country
                Glide.with(context)
                    .load(us.results[index].picture.large)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image)

                name.text = nameAPI
                email.text = us.results[index].email
                address.text = addressAPI
            }
        }
    }