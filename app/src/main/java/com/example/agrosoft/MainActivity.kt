package com.example.agrosoft

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.agrosoft.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pictureButton: Button
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    private  val cametaRequestCode : Int = 100
    private val pickImageCode : Int = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setTheme(R.style.Theme_AgroSoft)
        setContentView(binding.root)

        // initiallizing the views
        init()

        // onclick function onthe button
        pictureButton.setOnClickListener {
            openImageChooser()
        }

    }

    private fun init(){
        pictureButton = binding.button
        imageView =binding.imageView
        textView = binding.textView
    }

    // function for choosing the right intent
    private fun openImageChooser() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooserIntent = Intent.createChooser(pickPhotoIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePictureIntent))
        startActivityForResult(chooserIntent, pickImageCode)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                cametaRequestCode -> {
                    // Image captured from camera
                    // Check if data contains a bitmap
                    val bitmap: Bitmap? = data?.extras?.get("data") as? Bitmap
                    bitmap?.let {
                        // Set the bitmap to the ImageView
                        imageView.setImageBitmap(bitmap)
                    } ?: run {
                        Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
                    }
                }
                pickImageCode -> {
                    // Image selected from gallery
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let {
                        // Set the URI directly to the ImageView
                        imageView.setImageURI(selectedImageUri)
                    } ?: run {
                        Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}