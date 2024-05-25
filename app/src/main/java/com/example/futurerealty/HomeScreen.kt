package com.example.futurerealty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeScreen : Fragment() , RealtyAdapter.OnItemClickListener
{

    private lateinit var realtyAdapter: RealtyAdapter
    private lateinit var realtyRecyclerView: RecyclerView
    private lateinit var realtyArrayList: ArrayList<realityData>
    private lateinit var databaseRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        realtyRecyclerView = view.findViewById(R.id.HomeRecyclerView)
        realtyRecyclerView.layoutManager = LinearLayoutManager(context)
        realtyRecyclerView.setHasFixedSize(true)
        realtyArrayList = arrayListOf()

        realtyAdapter = RealtyAdapter(realtyArrayList , this)
        realtyRecyclerView.adapter = realtyAdapter

        databaseRef = FirebaseDatabase.getInstance().getReference("Realty")

        fetchRealtyData()






        view.findViewById<Button>(R.id.profile_btn).setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainer, ProfileScreen())
                addToBackStack(null)
            }
        }


        view.findViewById<Button>(R.id.favorite_btn).setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainer, FavoritScreen())
                addToBackStack(null)
            }
        }


        view.findViewById<FloatingActionButton>(R.id.addBtnFab).setOnClickListener{

            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainer, AaddCommercial())
                addToBackStack(null)
            }

        }

    }



    private fun fetchRealtyData()
    {
        databaseRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if (snapshot.exists())
                {
                    realtyArrayList.clear()
                    for (realtySnapshot in snapshot.children)
                    {
                        val realty = realtySnapshot.getValue(realityData::class.java)
                        if (realty != null)
                        {
                            realtyArrayList.add(realty)
                        }
                    }
                    realtyAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError)
            {

            }
        })
    }

    override fun onItemClick(realtyData: realityData, position: Int)
    {

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, DetailsScreen())
            addToBackStack(null)
        }
    }


}



