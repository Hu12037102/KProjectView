package com.hu.xiaobai.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.hu.xiaobai.view.util.ScreenUtils

open class ItemView : RelativeLayout {
    private lateinit var mTopLine: View
    private lateinit var mTvLeft: TextView
    private lateinit var mTvRight: TextView
    private lateinit var mBottomLine: View
    private lateinit var mRootView: View
    private var onItemClickListener: OnItemClickListener? = null
   fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
       this.onItemClickListener = onItemClickListener;
   }

    constructor(context: Context) : super(context) {
        initView(context)
        initEvent()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        initView(context)
        initAttrs(context, attrs)
        initEvent()
    }


    private fun initEvent() {
        mRootView.setOnClickListener {

            onItemClickListener?.onItemClick(it)
        }
    }

    private fun initView(context: Context) {
        val view: View = View.inflate(context, R.layout.view_item, this)
        mRootView = view.findViewById(R.id.root_view)
        mTopLine = view.findViewById(R.id.top_line)
        mTvLeft = view.findViewById(R.id.tv_left)
        mTvRight = view.findViewById(R.id.tv_right)
        mBottomLine = view.findViewById(R.id.bottom_line)

    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView)
        if (typedArray.indexCount > 0) {
            mTvLeft.setCompoundDrawablesWithIntrinsicBounds(typedArray.getDrawable(R.styleable.ItemView_title_left_icon),
                    typedArray.getDrawable(R.styleable.ItemView_title_right_icon), typedArray.getDrawable(R.styleable.ItemView_title_top_icon),
                    typedArray.getDrawable(R.styleable.ItemView_title_bottom_icon))
            mTvLeft.text = typedArray.getString(R.styleable.ItemView_title_data)
            mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.ItemView_title_text_size, mTvLeft.textSize.toInt()).toFloat())
            mTvLeft.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(R.styleable.ItemView_title_drawable_padding, 0))
            mTvLeft.compoundDrawablePadding = typedArray.getDimensionPixelSize(R.styleable.ItemView_title_drawable_padding, 0)
            when (typedArray.getBoolean(R.styleable.ItemView_show_top_line, true)) {
                true -> mTopLine.visibility = View.VISIBLE
                false -> mTopLine.visibility = View.GONE
            }
            mTopLine.setBackgroundColor(typedArray.getColor(R.styleable.ItemView_bottom_line_color, ContextCompat.getColor(context, R.color.colorFFEFEFEF)))
            setLineHeight(mTopLine, ScreenUtils.dp2px(context, 0.5f))
            setMargin(mTopLine, typedArray.getDimensionPixelSize(R.styleable.ItemView_top_line_margin_left, 0), typedArray.getDimensionPixelSize(R.styleable.ItemView_top_line_margin_top, 0),
                    typedArray.getDimensionPixelSize(R.styleable.ItemView_top_line_margin_right, 0), typedArray.getDimensionPixelSize(R.styleable.ItemView_top_line_margin_bottom, 0))

            mTvRight.setCompoundDrawablesWithIntrinsicBounds(typedArray.getDrawable(R.styleable.ItemView_content_left_icon),
                    typedArray.getDrawable(R.styleable.ItemView_content_top_icon), typedArray.getDrawable(R.styleable.ItemView_content_right_icon),
                    typedArray.getDrawable(R.styleable.ItemView_content_bottom_icon))
            mTvRight.text = typedArray.getString(R.styleable.ItemView_content_data)
            mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.ItemView_content_text_size, mTvRight.textSize.toInt()).toFloat())
            mTvRight.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(R.styleable.ItemView_content_drawable_padding, 0))
            mTvRight.compoundDrawablePadding = typedArray.getDimensionPixelSize(R.styleable.ItemView_content_drawable_padding, 0)
            setLineHeight(mBottomLine, ScreenUtils.dp2px(context, 0.5f))
            setMargin(mTopLine, typedArray.getDimensionPixelSize(R.styleable.ItemView_bottom_line_margin_left, 0), typedArray.getDimensionPixelSize(R.styleable.ItemView_bottom_line_margin_top, 0),
                    typedArray.getDimensionPixelSize(R.styleable.ItemView_bottom_line_margin_right, 0), typedArray.getDimensionPixelSize(R.styleable.ItemView_bottom_line_margin_bottom, 0))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRootView.background = typedArray.getDrawable(R.styleable.ItemView_selector_drawable)
            } else {
                mRootView.setBackgroundDrawable(typedArray.getDrawable(R.styleable.ItemView_selector_drawable))
            }
            typedArray.recycle()
        }
    }

    private fun setLineHeight(line: View, lineHeight: Int) {
        val lineParams = line.layoutParams
        lineParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        lineParams.height = lineHeight
        line.layoutParams = lineParams
    }

    private fun setMargin(view: View, leftMargin: Int, topMargin: Int, rightMargin: Int, bottomMargin: Int) {
        val layoutParams = view.layoutParams as RelativeLayout.LayoutParams
        layoutParams.leftMargin = leftMargin
        layoutParams.topMargin = topMargin
        layoutParams.rightMargin = rightMargin
        layoutParams.bottomMargin = bottomMargin
        view.layoutParams = layoutParams
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightMode: Int = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize: Int = MeasureSpec.getSize(heightMeasureSpec)
        var resultHeight = ScreenUtils.dp2px(context, Contast.DEFAULT_VIEW_HEIGHT.toFloat())
        if (heightMode == MeasureSpec.EXACTLY)
            resultHeight = heightSize
        else
            ScreenUtils.setDefaultRootViewSize(context, mRootView as ViewGroup)
        setMeasuredDimension(ScreenUtils.screenWidth(context), resultHeight)
    }


    interface OnItemClickListener {
        fun onItemClick(view: View)
    }

}