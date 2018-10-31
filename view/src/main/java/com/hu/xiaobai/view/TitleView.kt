package com.hu.xiaobai.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.hu.xiaobai.view.util.ScreenUtils

class TitleView : RelativeLayout {
    private lateinit var mRootView: ViewGroup
    private lateinit var mTvBack: TextView
    private lateinit var mTvCenter: TextView
    private lateinit var mTvSure: TextView
    private var mIsBackFinish = true
    private var onTitleItemClickListener: OnTitleItemClickListener? = null
    private var onTitleBackClickListener: OnTitleBackClickListener? = null
    private var onTitleSureClickListener: OnTitleSureClickListener? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
        initAttrs(context, attrs)
        initEvent()

    }

    private fun initView(context: Context) {
        val view: View = View.inflate(context, R.layout.view_title, this)
        mRootView = view.findViewById(R.id.root_view)
        mTvBack = view.findViewById(R.id.tv_back)
        mTvCenter = view.findViewById(R.id.tv_center)
        mTvSure = view.findViewById(R.id.tv_sure)
    }

    private fun initEvent() {
        mTvBack.setOnClickListener {
            onTitleItemClickListener?.onBackClick(it)
            onTitleBackClickListener?.onBackClick(it)
        }
        mTvSure.setOnClickListener {
            onTitleItemClickListener?.onSureClick(it)
            onTitleSureClickListener?.onSureClick(it)
        }


    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
        if (typeArray.indexCount > 0) {
            mRootView.setBackgroundColor(typeArray.getColor(R.styleable.TitleView_group_color, ContextCompat.getColor(context, R.color.colorFFFFFFFF)))
            mIsBackFinish = typeArray.getBoolean(R.styleable.TitleView_back_click_is_finish, true)
            mTvBack.setPadding(typeArray.getDimensionPixelSize(R.styleable.TitleView_back_text_padding_left, ScreenUtils.dp2px(context, 0f)),
                    typeArray.getDimensionPixelSize(R.styleable.TitleView_back_text_padding_top, ScreenUtils.dp2px(context, 0f)),
                    typeArray.getDimensionPixelSize(R.styleable.TitleView_back_text_padding_right, ScreenUtils.dp2px(context, 0f)),
                    typeArray.getDimensionPixelSize(R.styleable.TitleView_back_text_padding_bottom, ScreenUtils.dp2px(context, 0f)))

            mTvBack.setCompoundDrawablesWithIntrinsicBounds(typeArray.getDrawable(R.styleable.TitleView_back_left_drawable),
                    typeArray.getDrawable(R.styleable.TitleView_back_top_drawable), typeArray.getDrawable(R.styleable.TitleView_back_right_drawable),
                    typeArray.getDrawable(R.styleable.TitleView_back_bottom_drawable))
            mTvBack.text = typeArray.getString(R.styleable.TitleView_back_data)
            mTvBack.textSize = typeArray.getDimension(R.styleable.TitleView_back_text_size, mTvBack.textSize)
            mTvBack.setTextColor(typeArray.getColor(R.styleable.TitleView_back_text_color, ContextCompat.getColor(context, R.color.colorFF333333)))
            mTvBack.compoundDrawablePadding = typeArray.getDimensionPixelSize(R.styleable.TitleView_back_drawable_padding, 0)
            mTvCenter.text = typeArray.getString(R.styleable.TitleView_center_data)
            mTvCenter.textSize = typeArray.getDimension(R.styleable.TitleView_center_text_size, mTvCenter.textSize)
            mTvCenter.setTextColor(typeArray.getColor(R.styleable.TitleView_center_text_color, ContextCompat.getColor(context, R.color.colorFF333333)))
            mTvSure.text = typeArray.getString(R.styleable.TitleView_sure_data)
            mTvSure.setCompoundDrawablesWithIntrinsicBounds(typeArray.getDrawable(R.styleable.TitleView_sure_left_drawable),
                    typeArray.getDrawable(R.styleable.TitleView_sure_top_drawable), typeArray.getDrawable(R.styleable.TitleView_sure_right_drawable),
                    typeArray.getDrawable(R.styleable.TitleView_sure_bottom_drawable))
            mTvSure.textSize = typeArray.getDimensionPixelSize(R.styleable.TitleView_sure_text_size, mTvSure.textSize.toInt()).toFloat()
            mTvSure.setTextColor(typeArray.getColor(R.styleable.TitleView_sure_text_color, ContextCompat.getColor(context, R.color.colorFF333333)))
            mTvSure.setPadding(typeArray.getDimensionPixelSize(R.styleable.TitleView_sure_text_padding_left, ScreenUtils.dp2px(context, 0f)),
                    typeArray.getDimensionPixelSize(R.styleable.TitleView_sure_text_padding_top, ScreenUtils.dp2px(context, 0f)),
                    typeArray.getDimensionPixelSize(R.styleable.TitleView_sure_text_padding_right, ScreenUtils.dp2px(context, 0f)),
                    typeArray.getDimensionPixelSize(R.styleable.TitleView_sure_text_padding_bottom, ScreenUtils.dp2px(context, 0f)))
            mTvSure.compoundDrawablePadding = typeArray.getDimensionPixelSize(R.styleable.TitleView_sure_drawable_padding, 0)
            typeArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        val measureHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        var resultHeightSize = ScreenUtils.dp2px(getContext(), Contast.DEFAULT_VIEW_HEIGHT.toFloat())
        if (measureHeightMode == MeasureSpec.EXACTLY) {
            resultHeightSize = measureHeightSize
        } else {
            ScreenUtils.setDefaultRootViewSize(context, mRootView)
        }
        setMeasuredDimension(ScreenUtils.screenWidth(context), resultHeightSize)
    }

    interface OnTitleItemClickListener {
        fun onBackClick(v: View)
        fun onSureClick(v: View)
    }

    interface OnTitleBackClickListener {
        fun onBackClick(view: View)
    }

    interface OnTitleSureClickListener {
        fun onSureClick(view: View)
    }

}