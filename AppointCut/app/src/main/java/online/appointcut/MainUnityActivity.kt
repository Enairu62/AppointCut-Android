package online.appointcut

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import com.google.mlkit.vision.face.FaceContour
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity
import online.appointcut.databinding.ActivityUnityBinding

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

                var points =  f.getContour(FaceContour.NOSE_BRIDGE)?.points
                if(points != null) {
                    val coords = points[0]
                    Log.i("Before Move Cube", "${coords.x}, ${coords.y}")
                    moveCube(coords.x, coords.y)
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


    private fun moveCube(x:Float,y: Float){
        val xScaled:Float = ((x*-1)+540/2)*scale
        val yScaled:Float = ((y*-1)+960/2)*scale
        val s = "$xScaled,$yScaled,0"
        UnityPlayer.UnitySendMessage("Cube","move",s)
    }

    private fun modifyScale(f:Float){
        scale+=f
        binding.scaler.text = scale.toString()
    }
}