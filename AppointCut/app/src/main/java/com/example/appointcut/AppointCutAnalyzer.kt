package com.example.appointcut

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.util.*

object AppointCutAnalyzer: ImageAnalysis.Analyzer {

    private val listeners:LinkedList<OnSuccessListener<MutableList<Face>>> = LinkedList()
    fun addOnSuccessListener(l:OnSuccessListener<MutableList<Face>>){
        listeners.add(l)
    }



    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage = imageProxy.image
        if (mediaImage != null){
            val image = InputImage.fromMediaImage(mediaImage,imageProxy.imageInfo.rotationDegrees)
            val options = FaceDetectorOptions.Builder()
                .enableTracking()
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()
            val detector = FaceDetection.getClient(options)

            val result = detector.process(image)
                .addOnSuccessListener { faces->
                    Log.i("SuccessListener","Found face")
                    for(l:OnSuccessListener<MutableList<Face>> in listeners){
                        l.onSuccess(faces)
                    }
                }
                .addOnFailureListener{e ->
                    Log.e("detector","Error occured",e)
                }
                .addOnCompleteListener { _->imageProxy.close() }
        }
    }
}