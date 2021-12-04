package com.example.appointcut

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PointView(context:Context, attr: AttributeSet?) : View (context,attr) {

    var xPoint = 100F;
    var yPoint = 100F;
    var paint = Paint()

    init {
        paint.color= Color.RED
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        Log.i("Point Draw", "drawing point")
        canvas.apply{
            drawCircle(xPoint,yPoint,25F,paint)
        }
    }
}