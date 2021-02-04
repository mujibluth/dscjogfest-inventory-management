package com.inventorylibrary.app.ui.addBook

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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.inventorylibrary.app.databinding.AddBookFragmentBinding
import com.inventorylibrary.app.entity.BookEntity
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

class AddBookFragment : Fragment() {

    private val viewModel: AddBookViewModel by viewModel()
    private var _binding: AddBookFragmentBinding? = null
    private val binding: AddBookFragmentBinding get() = _binding!!
    private var requestCreator: RequestCreator? = null

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddBookFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding){
            saveButton.setOnClickListener {
                val title = titleEt.text
                val writer = writerEt.text
                val genre = genreEt.text
                val year = yearEt.text
                val isbn = isbnEt.text
                val stock = stockEt.text
                val rack = rackEt.text
                val note: String? = notesEt.text.let { it?.toString() }

                if (!isValidForm(title, titleLayout)) return@setOnClickListener
                if (!isValidForm(writer, writerLayout)) return@setOnClickListener
                if (!isValidForm(genre, genreLayout)) return@setOnClickListener
                if (!isValidForm(year, yearLayout)) return@setOnClickListener
                if (!isValidForm(isbn, isbnLayout)) return@setOnClickListener
                if (!isValidForm(stock, stockLayout)) return@setOnClickListener
                if (!isValidForm(rack, rackLayout)) return@setOnClickListener

                progressBar.visibility = View.VISIBLE
                saveButton.isEnabled = false
                lifecycleScope.launch(Dispatchers.IO){
                    var uri: Uri? = null

                    val imageName = Calendar.getInstance().timeInMillis.toString() +"_"+ Random().nextInt(100).toString() + ".webp"
                    if (requestCreator != null){
                        val image = File(ContextCompat.getExternalFilesDirs(requireContext(), Environment.DIRECTORY_PICTURES).first().absolutePath, imageName)
                        val fos = FileOutputStream(image)
                        @Suppress("DEPRECATION")
                        requestCreator?.get()?.compress(Bitmap.CompressFormat.WEBP, 100, fos)
                        fos.close()
                        uri = Uri.fromFile(image)
                    }

                    val book = BookEntity(
                        bookID = null,
                        title = title.toString(),
                        writer = writer.toString(),
                        genre = genre.toString(),
                        year = year.toString().toInt(),
                        isbn = isbn.toString(),
                        stock = stock.toString().toInt(),
                        rack = rack.toString(),
                        notes = note,
                        coverImagePath = uri?.toString()
                    )

                    try {
                        viewModel.addBook(book)
                        withContext(Dispatchers.Main){
                            findNavController().navigateUp()
                            Toast.makeText(
                                requireContext(),
                                "Berhasil menambahkan ${book.title}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch (e: Exception){
                        withContext(Dispatchers.Main){
                            saveButton.isEnabled = true
                            progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            pickImageButton.setOnClickListener {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }else{
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                }
            }
        }

    }


    private fun pickImageFromGallery(){
        Intent(Intent.ACTION_PICK).apply {
            type = "image/"
        }.also {
            startActivityForResult(it, 2)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.first() == PackageManager.PERMISSION_GRANTED){
            pickImageFromGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2){
            val uri = data?.data
            requestCreator = Picasso.get().load(uri)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .centerCrop()
                .resize(binding.bookCoverImage.width, binding.bookCoverImage.height)

            requestCreator?.into(binding.bookCoverImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}