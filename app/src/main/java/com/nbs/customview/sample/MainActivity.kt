package com.nbs.customview.sample

import com.nbs.nucleosnucleo.presentation.BaseActivity
import com.nbs.validacion.PassiveValidator
import com.nbs.validacion.Validation
import com.nbs.validacion.ValidationListener
import com.nbs.validacion.util.customRule
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ValidationListener {

    private lateinit var validator: PassiveValidator

    override val layoutResource: Int = R.layout.activity_main

    override fun initAction() {
    }

    override fun initIntent() {
    }

    override fun initLib() {
        initValidation()
    }

    private fun initValidation() {
        val imageValidation = Validation(
            imgDeco, listOf(
                customRule("harus ada image nya", ::getImageDrawabale)
            )
        )

        validator = PassiveValidator(mutableListOf(imageValidation))

        validator.setListener(this)

        btnValidate.setOnClickListener {
            imageValidation.validate()
        }

        btnReset.setOnClickListener {
            imgDeco.getImageView()?.setImageResource(0)
        }

        btnSelectImage.setOnClickListener {
            imgDeco.getImageView()?.setImageResource(R.drawable.bskin)
        }
    }

    private fun getImageDrawabale(): Boolean {
        return imgDeco.getImageView()?.drawable != null
    }

    override fun initProcess() {
    }

    override fun initUI() {
    }

    override fun onValidationFailed() {
    }

    override fun onValidationSuccess() {
    }


}
