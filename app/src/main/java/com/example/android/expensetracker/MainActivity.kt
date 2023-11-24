package com.example.android.expensetracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Date

class MainActivity : AppCompatActivity() {

    private val newExpenseActivityRequestCode = 1
    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((application as ExpensesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ExpenseListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        expenseViewModel.allExpenses.observe(owner = this) { expenses ->
            // Update the cached copy of the words in the adapter.
            expenses.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewExpenseActivity::class.java)
            startActivityForResult(intent, newExpenseActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newExpenseActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewExpenseActivity.EXTRA_REPLY)?.let { reply ->
                // Convert the String reply to Double
                val amount = reply.toDoubleOrNull() ?: 0.0

                // Create a new Expense object with the amount and the current date
                val expense = Expense(amount, Date())

                // Insert the expense into the ViewModel
                expenseViewModel.insert(expense)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
