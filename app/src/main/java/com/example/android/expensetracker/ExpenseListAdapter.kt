package com.example.android.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class ExpenseListAdapter : ListAdapter<Expense, ExpenseListAdapter.ExpenseViewHolder>(EXPENSES_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = getItem(position)
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val formattedDate = sdf.format(current.date)
        holder.bind("Витрата: " + current.amount.toString() + " грн.\nДата додавання: " + formattedDate.toString())
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val expenseItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String) {
            expenseItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ExpenseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ExpenseViewHolder(view)
            }
        }
    }

    companion object {
        private val EXPENSES_COMPARATOR = object : DiffUtil.ItemCallback<Expense>() {
            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem.amount == newItem.amount
            }

            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem.amount == newItem.amount
            }
        }
    }
}
