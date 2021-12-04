package online.appointcut

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import online.appointcut.MainUnityActivity
import com.google.common.util.concurrent.ListenableFuture
import online.appointcut.databinding.ActivityAugmentBinding

class AugmentActivity : AppCompatActivity() {
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var binding: ActivityAugmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAugmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //camerax stuff
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))

        val unityIntent = Intent(this, MainUnityActivity::class.java)
        startActivity(unityIntent)
    }

//    override fun onBackPressed() {
//        finish()
//    }

    //MOAR camerax stuff
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        var preview: Preview = Preview.Builder()
            .build()

        //get screen resolution
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        //reduce resolution for use with analyzer
        val resolution: Size = Size((size.x/2).toInt(),(size.y/2).toInt())

        //back to camerax stuff
        var imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setTargetResolution(resolution)
            .build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), AppointCutAnalyzer)

        var cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()

        preview.setSurfaceProvider(binding.previewView.surfaceProvider)

        var camera = cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            preview,
            imageAnalysis
        )
    }
}