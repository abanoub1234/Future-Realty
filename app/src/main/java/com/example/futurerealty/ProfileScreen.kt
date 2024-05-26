package com.example.futurerealty

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class ProfileScreen : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneTextView = view.findViewById<TextView>(R.id.phoneTxt)
        val nameTextView = view.findViewById<TextView>(R.id.ProfileNameTxtView)
        val emailTextView = view.findViewById<TextView>(R.id.ProfileEmailTxtView)

        val currentUser = auth.currentUser
        currentUser?.let {
            val uid = it.uid
            database = FirebaseDatabase.getInstance().getReference("users").child(uid)

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    if (snapshot.exists())
                    {
                        Toast.makeText(requireContext(), "User data found", Toast.LENGTH_SHORT).show()
                        val userProfile = snapshot.getValue(user::class.java)
                        userProfile?.let { user ->
                            nameTextView.text = user.username
                            phoneTextView.text = user.mobilenum
                            emailTextView.text = user.emailadress
                        }
                    } else {
                        Toast.makeText(requireContext(), "User Data Loaded", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to read value", Toast.LENGTH_SHORT).show()
                }
            })
        }

        view.findViewById<Button>(R.id.logoutBtn).setOnClickListener {
            auth.signOut()
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainer, LoginScreen())
                parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }
}

