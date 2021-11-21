package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.lang.NumberFormatException


class MainActivity : AppCompatActivity() {

    var result : String? = null
    var input : String? = ""
    var numberField : EditText? = null
    var operand: Double? = null
    var lastOperation = "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberField = findViewById(R.id.numberField)
        numberField!!.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("OPERATION", lastOperation)
        outState.putDouble("OPERAND", operand!!)
        super.onSaveInstanceState(outState)
    }

    fun onNumberClick(view: View) {
        val button: Button = view as Button
        val sb = StringBuilder()
        input = sb.append(input).append(button.getText()).toString()
        val sb2 = StringBuilder()
        numberField!!.setText(sb2.append(numberField!!.text).append(input))
        if (lastOperation == "=" && operand != null) {
            operand = null
        }
    }

    fun onOperationClick(view: View) {
        val button: Button = view as Button
        val op: String = button.getText().toString()
        val sb = StringBuilder()
        numberField!!.setText(sb.append(numberField!!.text).append(op).toString())
        var number = input.toString()
        input=""
        if (number.length > 0) {
            number = number.replace(',', '.')
            try {
                performOperation(java.lang.Double.valueOf(number), op)
            } catch (ex: NumberFormatException) {
                numberField!!.setText("")
            }
        }
        lastOperation = op
//        operationField!!.text = lastOperation
    }

    private fun performOperation(number: Double, operation: String) {

        // если операнд ранее не был установлен (при вводе самой первой операции)
        if (operand == null) {
            operand = number
        } else {
            if (lastOperation == "=") {
                lastOperation = operation
            }
            when (lastOperation) {
                "=" -> operand = number
                "/" -> if (number == 0.0) {
                    operand = 0.0
                } else {
                    operand = operand!! / number;
                }
                "*" -> operand = operand!! * number
                "+" -> operand = operand!! + number
                "-" -> operand = operand!! - number
            }
        }
        val sb = StringBuilder()
//        numberField!!.setText(operand.toString().replace('.', ','))
        result = operand.toString().replace('.', ',')
        if (operation == "=") numberField!!.setText(result)
//        numberField!!.setText("")
    }
}