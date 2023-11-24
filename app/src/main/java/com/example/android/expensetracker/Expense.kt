package com.example.android.expensetracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expense_table")
data class Expense(
    @PrimaryKey @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "date") val date: Date
)
