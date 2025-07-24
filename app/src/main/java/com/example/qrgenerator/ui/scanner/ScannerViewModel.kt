package com.example.qrgenerator.ui.scanner

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class ScannerViewModel : ViewModel() {
    private val scanner = getScannerWithOptions()

    // LiveData to hold the scanned barcode value
    private val _scannedValue = MutableLiveData<String?>()
    val scannedValue: LiveData<String?> = _scannedValue

    fun startCamera(
        context: Context, // context is needed here for CameraProvider and MainExecutor
        surfaceProvider: Preview.SurfaceProvider,
        viewLifecycleOwner: LifecycleOwner // pass lifecycle owner from Fragment
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(surfaceProvider)
            }

            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            analysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                processImageProxy(imageProxy, context)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    analysis
                )
            } catch (e: Exception) {
                Log.e("NURS", "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy, context: Context) {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close() // make sure to close imageProxy if mediaImage is null
            return
        }
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    val value = barcode.rawValue
                    _scannedValue.postValue(value)
                    Log.d("NURS", "The code found: $value")
                }
            }
            .addOnFailureListener {
                Log.e("NURS", "Scan failed", it)
            }
            .addOnCompleteListener {
                imageProxy.close() // must be called on every frame analyzed
            }
    }

    fun copyToClipboard(context: Context, textToCopy: String) {
        if(textToCopy.isNotEmpty()){
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("Scanned Text", textToCopy)
            clipboard.setPrimaryClip(clip)
            Log.d("NURS", "Successfully copied to the clipboard: $textToCopy")
        }
    }

    private fun getScannerWithOptions(): BarcodeScanner {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()
        return BarcodeScanning.getClient(options)
    }

    fun pause(context: Context){
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()
        cameraProvider.unbindAll()
    }

    override fun onCleared() {
        super.onCleared()
        scanner.close()
        Log.d("NURS", "ScannerViewModel cleared and scanner closed.")
    }
}