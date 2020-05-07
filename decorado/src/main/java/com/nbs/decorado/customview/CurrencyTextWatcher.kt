package com.nbs.decorado.customview

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.nbs.decorado.toCurrencyValue
import java.util.Locale

class CurrencyTextWatcher(private val editText: EditText, private val prefix: String, private val locale: Locale) : TextWatcher {

    private var previousCleanString: String? = null

    private val MAX_LENGTH = 20

    var value: Double = 0.0

    override fun afterTextChanged(editable: Editable?) {
        val str = editable.toString()
        if (str.length < prefix.length) {
            editText.setText(prefix)
            editText.setSelection(prefix.length)
            return
        }
        if (str == prefix) {
            value = 0.0
            return
        }
        // cleanString this the string which not contain prefix and , or .
        val cleanString = str.replace(prefix, "").replace("[.]".toRegex(), "").replace(" ", "").replace(prefix.trim(), "")
        // for prevent afterTextChanged recursive call
        if (cleanString == previousCleanString || cleanString.isEmpty()) {
            return
        }
        previousCleanString = cleanString

        val formattedString = format(cleanString)
        editText.removeTextChangedListener(this) // Remove listener
        editText.setText(formattedString)
        value = cleanString.toDoubleOrNull() ?: 0.0
        handleSelection()
        editText.addTextChangedListener(this) // Add back the listener
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //Do Nothing
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //Do Nothing
    }

    private fun format(currencyValue: String): String {
        val formatter = (currencyValue.toDoubleOrNull() ?: 0.0).toCurrencyValue(locale)
        return prefix + formatter
    }

    private fun handleSelection() {
        if (editText.text.length <= MAX_LENGTH) {
            editText.setSelection(editText.text.length)
        } else {
            editText.setSelection(MAX_LENGTH)
        }
    }

    private fun Double.toCurrencyValue(locale: Locale = Locale(LANGUAGE_INDONESIA, COUNTRY_INDONESIA), fractionDigit: Int = 0): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(locale).apply {
            maximumFractionDigits = fractionDigit
        }
        return getNormalizedValue(currencyFormat.format(this))
    }
}