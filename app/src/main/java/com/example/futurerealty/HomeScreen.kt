package com.example.futurerealty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
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



}