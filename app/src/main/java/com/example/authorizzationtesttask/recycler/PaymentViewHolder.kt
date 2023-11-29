package com.example.authorizzationtesttask.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.authorizzationtesttask.R

class PaymentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val idView: TextView = view.findViewById(R.id.payment_id)
    val amountView: TextView = view.findViewById(R.id.payment_amount)
    val dateView: TextView = view.findViewById(R.id.payment_date)
}