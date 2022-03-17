package com.openclassrooms.realestatemanager.presentation.create


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import kotlinx.coroutines.flow.update
import pl.aprilapps.easyphotopicker.*
import java.io.File
import java.io.FileInputStream
import java.util.*


class PicturesStepFragment : Fragment() {

    private val inputPiece get() = requireView().findViewById<TextInputEditText>(R.id.input_picture_piece)
    private val inputDescription get() = requireView().findViewById<TextInputEditText>(R.id.input_picture_description)
    private val buttonSelectPicture get() = requireView().findViewById<Button>(R.id.select_picture)
    private val buttonUpload get() = requireView().findViewById<Button>(R.id.upload_picture)

    private lateinit var easyImage: EasyImage

    private lateinit var fileToUpdate: File

    var storage: FirebaseStorage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pictures_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        buttonSelectPicture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 111)
            } else {
                pickPictures()
            }
        }

        buttonUpload.setOnClickListener {
            uploadPicture()
        }
    }

    private fun pickPictures() {
        easyImage = EasyImage.Builder(requireContext()) // Chooser only
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .setCopyImagesToPublicGalleryFolder(false) // Sets the name for images stored if setCopyImagesToPublicGalleryFolder = true
            .setFolderName("EasyImage sample") // Allow multiple picking
            .allowMultiple(false)
            .build()
        easyImage.openChooser(this)
    }

    private fun uploadPicture() {
        val storageRef = storage.reference
        val newPictureId = "property/${FirebaseAuth.getInstance().currentUser?.email}/${inputPiece.text}-${Date().time}.jpg"
        val mountainsRef = storageRef.child(newPictureId)
        val uploadTask = mountainsRef.putStream(FileInputStream(fileToUpdate))
        uploadTask.addOnFailureListener {
            println("error here picture upload failed")
            (requireActivity() as CreatePropertyActivity).viewModel.screenState.value = ScreenStateError("Error")
        }.addOnSuccessListener { taskSnapshot ->
            println("success full added here")
            (requireActivity() as CreatePropertyActivity).viewModel.pictureList.update {
                it.toMutableList().plus(PictureEntity(
                    newPictureId,
                    newPictureId,
                    inputDescription.text.toString(),
                    inputPiece.text.toString()
                ))
            }
            inputDescription.text?.clear()
            inputPiece.text?.clear()
            (requireActivity() as CreatePropertyActivity).viewModel.screenState.value = ScreenStateSuccess("Success")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        pickPictures()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 111) {
            pickPictures()
        } else {
            easyImage.handleActivityResult(
                requestCode,
                resultCode,
                data,
                requireActivity(),
                object : DefaultCallback() {
                    override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                        fileToUpdate = imageFiles[0].file
                    }

                    override fun onImagePickerError(error: Throwable, source: MediaSource) {
                        //Some error handling
                        error.printStackTrace()
                    }

                    override fun onCanceled(source: MediaSource) {
                        //Not necessary to remove any files manually anymore
                    }
                })
        }
    }
}