package com.nbs.decorado

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.nbs.decorado.richview.BaseRichView
import com.nbs.nucleosnucleo.view.ErrorableView
import org.jetbrains.anko.find

class DecoradoImageView : BaseRichView, ErrorableView {

    private lateinit var imgSrc: ImageView

    private lateinit var tvError: TextView

    private lateinit var imgDef: ImageView

    private var imgDrawable: Drawable? = null

    private var imgDrawableDef: Drawable? = null

    private var errorMessage: String? = ""

    private var cornerRadius: Float = 0.toFloat()

    private var imgHeightDef: Int = 0

    private var imgWidthDef: Int = 0

    private var scaleIndex = -1

    private var isNeedDeafult = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun getLayout(): Int = R.layout.decorado_image_view

    override fun bindView(view: View) {
        imgSrc = view.find(R.id.imgSrc)
        tvError = view.find(R.id.tvError)
        imgDef = view.find(R.id.imgDef)
    }

    override fun initView(context: Context, attrs: AttributeSet?) {
        super.initView(context, attrs)
        parseAttrs(attrs)
        setImageSrc(imgDrawable)
        setImageDef(imgDrawableDef)
        setSizeImage()
        setScaleTypeImage(scaleIndex)
        setDefaultImage()
    }

    override fun hideError() {
        tvError.visibility = View.INVISIBLE
    }

    override fun isErrorShowing(): Boolean = tvError.visibility == View.VISIBLE

    override fun showError(errorMessage: String) {
        tvError.visibility = View.VISIBLE
        tvError.text = errorMessage
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DecoradoImageView)
            try {
                imgDrawable = typedArray.getDrawable(R.styleable.DecoradoImageView_img_src)
                errorMessage = typedArray.getString(R.styleable.DecoradoImageView_error_message)
                scaleIndex = typedArray.getInt(R.styleable.DecoradoImageView_android_scaleType, -1)
                imgDrawableDef = typedArray.getDrawable(R.styleable.DecoradoImageView_img_default)
                imgWidthDef = typedArray.getInt(R.styleable.DecoradoImageView_img_width_default, 0)
                imgHeightDef = typedArray.getInt(R.styleable.DecoradoImageView_img_height_default, 0)
                isNeedDeafult = typedArray.getBoolean(R.styleable.DecoradoImageView_need_default_image, false)
            } finally {
                typedArray.recycle()
            }
        }
    }

    fun getImageView(): ImageView? = imgSrc

    private fun setScaleTypeImage(scaleTypeIndex: Int) {
        if (scaleIndex > -1) {
            val types = ImageView.ScaleType.values()
            val scaleType = types[scaleTypeIndex]
            imgSrc.scaleType = scaleType
        }
    }

    private fun setSizeImage() {
        val paramsDef = imgDef.layoutParams
        paramsDef.width = if (imgWidthDef == 0) LayoutParams.MATCH_PARENT else imgWidthDef.dp
        paramsDef.height = if (imgHeightDef == 0) LayoutParams.MATCH_PARENT else imgHeightDef.dp
        imgDef.layoutParams = paramsDef
    }

    private fun setImageSrc(imgDrawable: Drawable?) {
        if (imgDrawable != null){
            imgSrc.setImageDrawable(imgDrawable)
        }
    }

    private fun setImageDef(imgDrawableDef: Drawable?) {
        if (imgDrawableDef != null) {
            imgDef.setImageDrawable(imgDrawableDef)
        }
    }

    private fun setDefaultImage() {
        imgDef.visibility = if (isNeedDeafult) View.VISIBLE else View.INVISIBLE
    }

}