package com.example.futurerealty


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class AaddCommercial : Fragment() {


    lateinit var imageOfRealty: ImageView
    lateinit var priceOfRealty: TextInputEditText
    lateinit var informationOfRealty: TextInputEditText
    lateinit var locationOfRealty: TextInputEditText
    lateinit var contactOfRealty: TextInputEditText

    private val realtyStorage = FirebaseStorage.getInstance().reference
    private val realtyRef = FirebaseDatabase.getInstance().getReference("Realty")
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aadd_commercial, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageOfRealty = view.findViewById(R.id.AddImage)
        priceOfRealty = view.findViewById(R.id.addPriceEditText)
        informationOfRealty = view.findViewById(R.id.AddInformationsEditText)
        locationOfRealty = view.findViewById(R.id.addLocationEditText)
        contactOfRealty = view.findViewById(R.id.addContactsEditText)

        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    uploadAndDisplayImage(uri)
                }
            }

        imageOfRealty.setOnClickListener {
            getContent.launch("image/*")
        }

        view.findViewById<Button>(R.id.addComercialBTN).setOnClickListener {
            val price = priceOfRealty.text.toString()
            val description = informationOfRealty.text.toString()
            val location = locationOfRealty.text.toString()
            val contact = contactOfRealty.text.toString()

            if (imageUrl != null) {
                val newRealty = Realty(price, description, location, "+2$contact", imageUrl)
                pushRealtyData(newRealty)
            } else {
                Toast.makeText(requireContext(), "Please upload an image first", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


    private fun pushRealtyData(realty: Realty) {
        realtyRef.push().setValue(realty).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT)
                    .show()
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragmentContainer, HomeScreen())
                    addToBackStack(null)
                }
            } else {
                Toast.makeText(requireContext(), "Failed to save data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadAndDisplayImage(uriImage: Uri) {
        val filepath = realtyStorage.child("imageRealty/${System.currentTimeMillis()}")
        filepath.putFile(uriImage).addOnSuccessListener {
            filepath.downloadUrl.addOnSuccessListener { uri ->
                imageUrl = uri.toString()
                Picasso.get().load(uri).into(imageOfRealty)
                Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
        }
    }





    /* private fun updateRealtyWithImageUrl(imageUrl: String)
   {
       val newRealty = Realty(
           priceOfRealty.text.toString(),
           informationOfRealty.text.toString(),
           locationOfRealty.text.toString(),
           "+2" + contactOfRealty.text.toString(),
           imageUrl
       )
       pushRealtyData(newRealty)
   }*/


}










