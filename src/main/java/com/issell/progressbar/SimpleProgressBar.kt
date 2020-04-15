package com.issell.progressbar

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.ProgressBar
import android.widget.RelativeLayout

const val STATUS_STOP = false
const val STATUS_RUN = true

class SimpleProgressBar constructor(private val activity: Activity) {

    private var context: Context = activity.applicationContext
    private val tag = javaClass.canonicalName
    private val layout = RelativeLayout(context)
    private var status: Boolean = STATUS_STOP
    private var progressBar: ProgressBar? = null
    private var drawableId: Int? = null
    private var width:Int? = null
    private var height:Int? = null
    constructor(activity: Activity, drawableId: Int) : this(activity) {
        this.drawableId = drawableId
    }
    constructor(activity: Activity, drawableId: Int, width:Int, height:Int) : this(activity, drawableId) {
        this.width = width
        this.height = height
    }

    private fun init() {

        if (drawableId != null) {
            progressBar = ProgressBar(context, null, android.R.attr.progressBarStyle)
            val d: Drawable = context.resources.getDrawable(drawableId!!, null)
            progressBar!!.indeterminateDrawable = d

        } else
            progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
        progressBar!!.isIndeterminate = true


        val dpAsPixels = pixelToDip(20)
        setPadding(dpAsPixels)

        val params =
        if(width != null && height != null)
            RelativeLayout.LayoutParams(
                width!!,
                height!!
            )
        else
            RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, params)
        layout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.setBackgroundColor(Color.TRANSPARENT)

        activity.addContentView(layout, layout.layoutParams)
    }

    fun on() {
        if (progressBar == null) {
            init()
        }
        progressBar!!.visibility = View.VISIBLE
        status = STATUS_RUN
    }

    fun off() {
        if (progressBar == null) {
            try {
                throw ProgressException("The ProgressBar instance was not created because the method on() on() was not called before off(). the off() is ignored.")
            } catch (e: ProgressException) {
                Log.e(tag, stackTraceToString(e))
            }
            return
        }
        progressBar!!.visibility = View.VISIBLE
        status = STATUS_STOP
    }

    fun destroy(){
        off()
        (progressBar!!.parent as ViewManager).removeView(progressBar!!)
        progressBar = null
    }

    fun setPadding(dp:Int){
        try {
            if(progressBar == null)
                throw ProgressException("ProgressBar is null. SetPadding() was ignored.")
        } catch(e:ProgressException){
            Log.e(tag, stackTraceToString(e))
        }
        progressBar!!.setPadding(dp, dp, dp, dp)
    }

    private fun pixelToDip(pixel:Int) :Int{
        val scale = context.resources.displayMetrics.density
        return (pixel * scale + 0.5f).toInt()
    }

}
