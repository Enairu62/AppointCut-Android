package com.example.appointcut

import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import com.example.appointcut.databinding.ActivityUnityBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.unity3d.player.UnityPlayerActivity
import com.unity3d.player.UnityPlayer

class MainUnityActivity : UnityPlayerActivity() {
    private var positionScale: Float = .012F
    private var sizeScale: Float = 155F
    private lateinit var binding: ActivityUnityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUnityBinding.inflate(layoutInflater)
        binding.scaler.text = sizeScale.toString()
        addUI()

        val amtSmall = 1F
        val amtMedium = 3F
        val amtLarge = 5F
        binding.addSmall.setOnClickListener {
            modifyScale(amtSmall)
        }
        binding.addMedium.setOnClickListener {
            modifyScale(amtMedium)
        }
        binding.addLarge.setOnClickListener {
            modifyScale(amtLarge)
        }
        binding.subtractSmall.setOnClickListener {
            modifyScale(-amtSmall)
        }
        binding.subtractMedium.setOnClickListener {
            modifyScale(-amtMedium)
        }
        binding.subtractLarge.setOnClickListener {
            modifyScale(-amtLarge)
        }

        AppointCutAnalyzer.addOnSuccessListener(faceListener)
    }

    private val faceListener: OnSuccessListener<MutableList<Face>> = OnSuccessListener { faces ->
        for (f in faces) {
            //rotate the object
            rotateModel(f.headEulerAngleX, f.headEulerAngleY, f.headEulerAngleZ)
            Log.i(
                "Face listener",
                "Rotation: ${f.headEulerAngleX},${f.headEulerAngleY},${f.headEulerAngleZ}"
            )

            //use width to determine distance or scale
            val z: Float = f.boundingBox.width().toFloat()
            binding.boxWidth.text = z.toString()
            val zInverse = z/sizeScale
            //scale the model
            scaleModel(zInverse)


            //get screen resolution
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            Log.i("Screen Size", size.toString())
            val screenCenter = Point()
            screenCenter.x = size.x / 2
            screenCenter.y = size.y / 2

            //position the object
            val points = f.getContour(FaceContour.NOSE_BRIDGE)?.points
            if (points != null) {
                //coord calculations
                val coords = points[0]
                val xScaled: Float = ((coords.x * -1) + screenCenter.x / 2) * positionScale
                val yScaled: Float = ((coords.y * -1) + screenCenter.y / 2) * positionScale
                //move model
                moveModel(xScaled, yScaled, 0F)
                //position debug tracker
                binding.point.xPoint = (coords.x * 2 - size.x) * -1
                binding.point.yPoint = coords.y * 2
                //update the view
                binding.underlay.removeView(binding.point)
                binding.underlay.addView(binding.point)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    private fun addUI() {
        val unityLayout: FrameLayout = mUnityPlayer
        val unityRender : View = unityLayout.getChildAt(0)

        val layout = binding
        layout.root.removeView(layout.overlay)
        unityLayout.addView(layout.overlay)

        unityLayout.removeView(unityRender)
        unityLayout.addView(unityRender)

        layout.root.removeView(layout.underlay)
        unityLayout.addView(layout.underlay)
    }

    private fun rotateModel(x: Float, y: Float, z: Float) {
        val zNeg = z * -1
        val s = "$x,$y,$zNeg"
        UnityPlayer.UnitySendMessage("Cube", "rotate", s)
    }

    private fun moveModel(x: Float, y: Float, z: Float) {
        val s = "$x,$y,$z"
        UnityPlayer.UnitySendMessage("Cube", "move", s)
    }

    private fun scaleModel(scale: Float) {
        val s = "$scale,$scale,$scale"
        UnityPlayer.UnitySendMessage("Cube", "scale", s)
    }

    private fun modifyScale(f: Float) {
        sizeScale += f
        binding.scaler.text = sizeScale.toString()
    }

}