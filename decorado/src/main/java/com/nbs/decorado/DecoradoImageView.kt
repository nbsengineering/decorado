package com.nbs.decorado

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.card.MaterialCardView
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

    private lateinit var cvImgSrc: MaterialCardView

    private var imgDrawable: Drawable? = null

    private var errorMessage: String? = ""

    private var cornerRadius: Float = 0.toFloat()

    private var imgHeight: Int = 0

    private var imgWidth: Int = 0

    private var scaleIndex = -1

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
        cvImgSrc = view.find(R.id.cvImg)
    }

    override fun initView(context: Context, attrs: AttributeSet?) {
        super.initView(context, attrs)
        parseAttrs(attrs)
        setImageSrc(imgDrawable)
        setSizeImage(imgWidth, imgHeight)
        setScaleTypeImage(scaleIndex)
    }

    override fun hideError() {
        tvError.visibility = View.GONE
    }

    override fun isErrorShowing(): Boolean = tvError.visibility == View.VISIBLE

    override fun showError(errorMessage: String) {
        tvError.text = errorMessage
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DecoradoImageView)
            try {
                imgDrawable = typedArray.getDrawable(R.styleable.DecoradoImageView_img_src)
                cornerRadius = typedArray.getFloat(R.styleable.DecoradoImageView_img_radius, 0f)
                errorMessage = typedArray.getString(R.styleable.DecoradoImageView_error_message)
                imgWidth = typedArray.getInt(R.styleable.DecoradoImageView_img_width, 0)
                imgHeight = typedArray.getInt(R.styleable.DecoradoImageView_img_height, 0)
                scaleIndex = typedArray.getInt(R.styleable.DecoradoImageView_android_scaleType, -1)
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

    private fun setSizeImage(imgWidth: Int, imgHeight: Int) {
        val params = cvImgSrc.layoutParams
        params.width = if (imgWidth == 0) LayoutParams.MATCH_PARENT else pxToDp(imgWidth, context)
        params.height = if (imgHeight == 0) LayoutParams.MATCH_PARENT else pxToDp(imgHeight, context)
        cvImgSrc.layoutParams = params
    }

    private fun setImageSrc(imgDrawable: Drawable?) {
        imgSrc.setImageDrawable(imgDrawable)
    }




}