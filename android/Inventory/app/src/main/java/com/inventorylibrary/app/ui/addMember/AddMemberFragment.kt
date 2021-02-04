package com.inventorylibrary.app.ui.addMember

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.inventorylibrary.app.databinding.AddMemberFragmentBinding
import com.inventorylibrary.app.entity.MemberEntity
import com.inventorylibrary.app.utils.isValidForm
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.util.*

class AddMemberFragment : Fragment() {

    private val viewModel: AddMemberViewModel by viewModel()
    private var _binding: AddMemberFragmentBinding? = null
    private val binding: AddMemberFragmentBinding get() = _binding!!
    private var profilePictureRequestCreator: RequestCreator? = null
    private var profileIdentityRequestCreator: RequestCreator? = null

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddMemberFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding){
            pickProfileImageButton.setOnClickListener {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    pickProfilePictureFromGallery()
                }else{
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                }
            }

            changeIdentityImageButton.setOnClickListener {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    pickIdentityPictureFromGallery()
                }else{
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2)
                }
            }

            saveButton.setOnClickListener { saveButton ->
                val name = nameEt.text
                val birthPlace = birthPlaceEt.text
                val address = addressEt.text
                val job = jobEt.text
                val phoneNumber = phoneNumberEt.text
                val email = emailEt.text
                val sex = sexRadioGroup.children.indexOfFirst { (it as RadioButton).isChecked }

                if (!isValidForm(name, nameLayout)) return@setOnClickListener
                if (!isValidForm(birthPlace, birthPlaceLayout)) return@setOnClickListener
                if (!isValidForm(address, addressLayout)) return@setOnClickListener
                if (!isValidForm(job, jobLayout)) return@setOnClickListener
                if (!isValidForm(phoneNumber, phoneNumberLayout)) return@setOnClickListener
                if (!isValidForm(email, emailLayout)) return@setOnClickListener

                progressBar.visibility = View.VISIBLE
                saveButton.isEnabled = false

                lifecycleScope.launch(Dispatchers.IO){

                    var profilePictureUri: Uri? = null
                    var memberIdentityUri: Uri? = null

                    if (profilePictureRequestCreator != null){
                        val imageName = Calendar.getInstance().timeInMillis.toString() +"_"+ Random().nextInt(100).toString() + ".webp"
                        val image = File(ContextCompat.getExternalFilesDirs(requireContext(), Environment.DIRECTORY_PICTURES).first().absolutePath, imageName)
                        val fos = FileOutputStream(image)
                        @Suppress("DEPRECATION")
                        profilePictureRequestCreator?.get()?.compress(Bitmap.CompressFormat.WEBP, 100, fos)
                        fos.close()
                        profilePictureUri = Uri.fromFile(image)
                    }

                    if (profileIdentityRequestCreator != null){
                        val imageName = Calendar.getInstance().timeInMillis.toString() +"_"+ Random().nextInt(100).toString() + ".webp"
                        val image = File(ContextCompat.getExternalFilesDirs(requireContext(), Environment.DIRECTORY_PICTURES).first().absolutePath, imageName)
                        val fos = FileOutputStream(image)
                        @Suppress("DEPRECATION")
                        profileIdentityRequestCreator?.get()?.compress(Bitmap.CompressFormat.WEBP, 100, fos)
                        fos.close()
                        memberIdentityUri = Uri.fromFile(image)
                    }

                    val member = MemberEntity(
                        memberID = null,
                        name = name.toString(),
                        birthPlace = birthPlace.toString(),
                        address = address.toString(),
                        sex = sex,
                        job = job.toString(),
                        phone = phoneNumber.toString(),
                        email = email.toString(),
                        profilePicture = profilePictureUri?.toString(),
                        identityPicture = memberIdentityUri?.toString(),
                        joinDate = Calendar.getInstance().timeInMillis
                    )

                    try {
                        viewModel.addMember(member)
                        withContext(Dispatchers.Main){
                            findNavController().navigateUp()
                            Toast.makeText(
                                requireContext(),
                                "Berhasil menambahkan ${member.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch (e: Exception){
                        withContext(Dispatchers.Main){
                            progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
                            saveButton.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun pickProfilePictureFromGallery(){
        Intent(Intent.ACTION_PICK).apply {
            type = "image/"
        }.also {
            startActivityForResult(it, 1)
        }

    }

    private fun pickIdentityPictureFromGallery(){
        Intent(Intent.ACTION_PICK).apply {
            type = "image/"
        }.also {
            startActivityForResult(it, 2)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.first() == PackageManager.PERMISSION_GRANTED){
            if (requestCode == 1) pickProfilePictureFromGallery() else pickIdentityPictureFromGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){

            val uri = data?.data
            if (requestCode == 1){
                profilePictureRequestCreator = Picasso.get().load(uri)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .centerCrop()
                    .resize(binding.profilePictureImage.width, binding.profilePictureImage.height)

                profilePictureRequestCreator?.into(binding.profilePictureImage)
            }

            if (requestCode == 2){
                profileIdentityRequestCreator = Picasso.get().load(uri)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .centerCrop()
                    .resize(binding.identityImage.width, binding.identityImage.height)

                profileIdentityRequestCreator?.into(binding.identityImage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}