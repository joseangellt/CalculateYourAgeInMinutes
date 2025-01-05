package com.example.datebirthcalculator

import android.app.DatePickerDialog
import android.icu.number.SimpleNotation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var _selectedDate: TextView? = null
    private var _minutesLived: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _selectedDate = findViewById(R.id.selectedDate)
        _minutesLived = findViewById(R.id.minutesLived)
        val buttonDatePicker : Button = findViewById(R.id.btnDatePicker)

        buttonDatePicker.setOnClickListener{
            clickDatePicker()
        }

    }

    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this,
                    "DatePicker works", Toast.LENGTH_LONG).show()

                val selectedDt = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                _selectedDate?.text = selectedDt

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDt)
                theDate?.let{
                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time/60000

                        val minutesLived = (currentDateInMinutes - selectedDateInMinutes)

                        val formatter = NumberFormat.getNumberInstance(Locale.US)
                        val formattedNumber = formatter.format(minutesLived)

                        _minutesLived?.text = formattedNumber
                    }

                }

            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }

}


