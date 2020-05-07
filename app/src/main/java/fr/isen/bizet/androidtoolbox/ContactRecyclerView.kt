package fr.isen.bizet.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_permissions_piece.view.*

class ContactRecyclerView(private val contacts: List<String>) :
    RecyclerView.Adapter<ContactRecyclerView.ContactViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
            ContactViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_permissions_piece, parent, false)
            )

        override fun getItemCount(): Int = contacts.size

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            holder.contactName.text = contacts[position]
        }

        class ContactViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView) {
            val contactName: TextView = contactView.contactName
        }
}