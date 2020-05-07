package com.nbs.decorado.customview

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.widget.AppCompatEditText
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.graphics.Canvas
import com.nbs.decorado.COUNTRY_INDONESIA
import com.nbs.decorado.LANGUAGE_INDONESIA
import com.nbs.decorado.getCurrencySymbol
import com.nbs.nucleox.R
import java.util.Locale

class CurrencyEditTextView : AppCompatEditText {

    // Use indonesia locale

    private var locale: Locale = Locale(LANGUAGE_INDONESIA, COUNTRY_INDONESIA)

    private var prefixDivider: String = " "

    private var editTextPrefix = locale.getCurrencySymbol() + prefixDivider

    private val MAX_LENGTH = 20

    private var currencyTextWatcher: CurrencyTextWatcher? = null


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        parseAttrs(attrs)
        this.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        this.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_LENGTH))
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditTextView)

            try {
                typedArray.getString(R.styleable.CurrencyEditTextView_currency_prefix)?.let {
                    editTextPrefix = it
                }
                this.hint = editTextPrefix
                currencyTextWatcher = CurrencyTextWatcher(this, editTextPrefix, locale)

            } finally {
                typedArray.recycle()
            }
        }
    }

    fun getValue(): Double {
        return currencyTextWatcher?.value?.toDouble() ?: 0.0
    }

    fun setPrefix(editTextPrefix: String) {
        this.editTextPrefix = editTextPrefix
        this.hint = editTextPrefix
        currencyTextWatcher = CurrencyTextWatcher(
            this,
            editTextPrefix,
            locale
        )
    }

    fun setLocale(locale: Locale) {
        this.editTextPrefix = locale.getCurrencySymbol() + prefixDivider
        this.hint = editTextPrefix
        currencyTextWatcher = CurrencyTextWatcher(
            this,
            editTextPrefix,
            locale
        )
    }

    fun setPrefixDivider(prefixDivider: String) {
        this.editTextPrefix = locale.getCurrencySymbol() + prefixDivider
        this.hint = editTextPrefix
        currencyTextWatcher = CurrencyTextWatcher(
            this,
            editTextPrefix,
            locale
        )
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            this.addTextChangedListener(currencyTextWatcher)
        } else {
            this.removeTextChangedListener(currencyTextWatcher)
        }
        handleCaseCurrencyEmpty(focused)
    }

    private fun handleCaseCurrencyEmpty(focused: Boolean) {
        text?.let {
            if (focused) {
                if (it.toString().isEmpty()) {
                    setText(editTextPrefix)
                    setSelection(editTextPrefix.length)
                }
            } else {
                if (it.toString() == editTextPrefix) {
                    setSelection(editTextPrefix.length)
                }
            }
        }
    }

    override fun onDrawForeground(canvas: Canvas?) {
        text?.let {
            setSelection(it.length)
        }
        super.onDrawForeground(canvas)
    }

    private fun Locale.getCurrencySymbol(): String {
        return Currency.getInstance(this).getSymbol(this) ?: "Rp"
    }
}