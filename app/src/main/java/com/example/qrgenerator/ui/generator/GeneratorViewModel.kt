package com.example.qrgenerator.ui.generator

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class GeneratorViewModel(): ViewModel() {

    fun generateCode(input: String): Bitmap{
        val multiFormatWriter = MultiFormatWriter()
        val bm = multiFormatWriter.encode(
            input,
            BarcodeFormat.QR_CODE,
            300,
            300
        )
        val barcodeEncoder = BarcodeEncoder()
        return barcodeEncoder.createBitmap(bm)
    }

    fun saveImageToDownloadFolder(imageFile: String, ibitmap: Bitmap, context: Context) {
        try {
            val filePath = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                imageFile
            )
            val outputStream: OutputStream = FileOutputStream(filePath)
            ibitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(
                context,
                imageFile + "Sucсessfully saved in Download Folder",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}