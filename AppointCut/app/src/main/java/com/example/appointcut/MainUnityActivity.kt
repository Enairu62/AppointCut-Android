package com.example.appointcut

import android.graphics.PointF
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import com.example.appointcut.databinding.ActivityUnityBinding
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceLandmark
import com.unity3d.player.UnityPlayerActivity
import com.unity3d.player.UnityPlayer
import java.util.*
import kotlin.properties.Delegates

class MainUnityActivity: UnityPlayerActivity() {
    private var scale:Float = .01F
    private lateinit var binding: ActivityUnityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUnityBinding.inflate(layoutInflater)
        binding.scaler.text=scale.toString()
        addStuff()

        val amtSmall:Float=0.0001F
        val amtMedium:Float = 0.001F
        val amtLarge:Float = 0.01F
        binding.addSmall.setOnClickListener { _ ->
            modifyScale(amtSmall)
        }
        binding.addMedium.setOnClickListener { _ ->
            modifyScale(amtMedium)
        }
        binding.addLarge.setOnClickListener { _ ->
            modifyScale(amtLarge)
        }
        binding.subtractSmall.setOnClickListener { _ ->
            modifyScale(-amtSmall)
        }
        binding.subtractMedium.setOnClickListener { _ ->
            modifyScale(-amtMedium)
        }
        binding.subtractLarge.setOnClickListener { _ ->
            modifyScale(-amtLarge)
        }

        AppointCutAnalyzer.addOnSuccessListener{faces ->
            for(f in faces){
                rotateCube(f.headEulerAngleX,f.headEulerAngleY,f.headEulerAngleZ)
                Log.i("Face listener","Rotation: ${f.headEulerAngleX},${f.headEulerAngleY},${f.headEulerAngleZ}")

                val z : Float = f.boundingBox.width().toFloat()

                var points =  f.getContour(FaceContour.NOSE_BRIDGE)?.points
                if(points != null) {
                    val coords = points[0]
                    val xScaled:Float = ((coords.x*-1)+540/2)*scale
                    val yScaled:Float = ((coords.y*-1)+960/2)*scale
                    val zScaled:Float = z*scale/2

                    Log.i("Before Move Cube", "${coords.x}, ${coords.y},$zScaled")
                    moveCube(xScaled,yScaled,zScaled)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun addStuff(){
        var unityLayout:FrameLayout = mUnityPlayer
        var layout = binding
        layout.root.removeView(layout.underlay)
        unityLayout.addView(layout.underlay)
//        unityLayout.getChildAt(0).bringToFront()
        layout.root.removeView(layout.overlay)
        unityLayout.addView(layout.overlay)
    }

    private fun rotateCube(x:Float,y:Float,z:Float){
        val zNeg = z*-1
        val s:String = "$x,$y,$zNeg"
        UnityPlayer.UnitySendMessage("Cube","rotate",s)
    }


    private fun moveCube(x:Float,y: Float, z: Float){
        val s = "$x,$y,$z"
        UnityPlayer.UnitySendMessage("Cube","move",s)
    }

    private fun modifyScale(f:Float){
        scale+=f
        binding.scaler.text = scale.toString()
    }
}