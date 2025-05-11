package com.example.qrgenerator.ui.generator

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.qrgenerator.data.Barcode
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GeneratorViewModel() : ViewModel() {

    fun generateCode(input: String): Bitmap {
        val multiFormatWriter = MultiFormatWriter()
        val bm = multiFormatWriter.encode(
            input,
            BarcodeFormat.QR_CODE,
            300,
            300
        )
        val barcodeEncoder = BarcodeEncoder()
        val barcode = Barcode("code")
        Log.e("AMG", barcode.toString())
        return barcodeEncoder.createBitmap(bm)
    }

    fun saveImageToDownloadFolder(ibitmap: Bitmap, context: Context) {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

        try {
            val filePath = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "IMG_$timeStamp.png"
            )

            val outputStream: OutputStream = FileOutputStream(filePath)
            ibitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(
                context,
                "Successfully saved in Pictures Folder",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}