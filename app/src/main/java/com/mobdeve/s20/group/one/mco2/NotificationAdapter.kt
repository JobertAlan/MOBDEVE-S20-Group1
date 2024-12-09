package com.mobdeve.s20.group.one.mco2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class NotificationAdapter(
    private val notifications: MutableList<NotificationItem>, context: Context):
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvMessage: TextView = view.findViewById(R.id.tvMessage)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)

        fun bindData(notification: NotificationItem) {
            tvDate.text = notification.date
            tvTime.text = notification.time
            tvMessage.text = notification.message
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_layout, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindData(notifications[position])

        val notification = notifications[position]

        holder.btnDelete.setOnClickListener {
            val removedPosition = holder.adapterPosition // Get the adapter position at runtime
            if (removedPosition != RecyclerView.NO_POSITION) {
                notifications.removeAt(removedPosition)
                notifyItemRemoved(removedPosition)
                notifyItemRangeChanged(removedPosition, notifications.size) // Update positions of subsequent items
                Toast.makeText(holder.itemView.context, "Notification deleted", Toast.LENGTH_SHORT).show()
            }

        }



    }

    override fun getItemCount(): Int = notifications.size

    fun addNotification(notification: NotificationItem) {
        notifications.add(notification)
        notifyItemInserted(notifications.size - 1)
    }
}

