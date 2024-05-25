package com.example.futurerealty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class DetailsScreen : Fragment()
{
    private lateinit var databaseRef: DatabaseReference
    lateinit var imageView1: ImageView
    lateinit var imageView2: ImageView
    lateinit var imageView3: ImageView
    lateinit var priceTxt: TextView
    lateinit var infoTxt: TextView
    lateinit var contactTxt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        imageView1 = view.findViewById(R.id.firstImage)
        imageView2 = view.findViewById(R.id.secondImage)
        imageView3 = view.findViewById(R.id.thirdImage)
        priceTxt = view.findViewById(R.id.DetailsPriceTxtView)
        infoTxt = view.findViewById(R.id.DetailsInfoTxtView)
        contactTxt = view.findViewById(R.id.DetailsPhoneTxtView)



        val realtyData = arguments?.getParcelable<realityData>("realtyData")
        realtyData?.let {
            priceTxt.text=it.price.toString()
            infoTxt.text=it.description.toString()
            contactTxt.text=it.contact.toString()
            Picasso.get().load(it.imageUrl).into(imageView1)
            Picasso.get().load(it.imageUrl).into(imageView2)
            Picasso.get().load(it.imageUrl).into(imageView3)

        }

















       /* databaseRef = FirebaseDatabase.getInstance().getReference("Realty")

        databaseRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if (snapshot.exists())
                {
                    for (realtySnapshot in snapshot.children)
                    {
                        val realty = realtySnapshot.getValue(realityData::class.java)
                        if (realty != null)
                        {
                            priceTxt.setText(realty.price)
                            infoTxt.setText(realty.description)
                            contactTxt.setText(realty.contact)
                            Picasso.get().load(realty.imageUrl).into(imageView1)
                            Picasso.get().load(realty.imageUrl).into(imageView2)
                            Picasso.get().load(realty.imageUrl).into(imageView3)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError)
            {

            }
        })*/




    }


}