package com.example.authorizzationtesttask.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.authorizzationtesttask.Data.Payment
import com.example.authorizzationtesttask.R
import kotlin.random.Random

class PaymentsAdapter(private val payments: List<Payment>) : RecyclerView.Adapter<PaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        Log.d("PaymentsAdapter", "Здесь все норм")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
        return PaymentViewHolder(view)

    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = payments[position]
        holder.idView.text = "ID: ${payment.id}"
        Log.d("PaymentsAdapter", "${payment.id}")
        holder.amountView.text = "Amount: ${payment.amount}"
        Log.d("PaymentsAdapter", "${payment.amount}")
        holder.dateView.text = "Date: ${payment.date}"
        Log.d("PaymentsAdapter", "${payment.date}")
    }

    override fun getItemCount() = payments.size
}