package com.mobdeve.s20.group.one.mco2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NotificationAdapter(
    private val notifications: MutableList<NotificationItem>
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvMessage: TextView = view.findViewById(R.id.tvMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_layout, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.tvDate.text = notification.date
        holder.tvTime.text = notification.time
        holder.tvMessage.text = notification.message
    }

    override fun getItemCount(): Int = notifications.size

    fun addNotification(notification: NotificationItem) {
        notifications.add(notification)
        notifyItemInserted(notifications.size - 1)
    }
}

