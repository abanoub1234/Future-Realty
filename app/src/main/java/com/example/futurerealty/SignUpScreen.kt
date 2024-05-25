package com.example.futurerealty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.futurerealty.databinding.FragmentSignUpScreenBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// Your other imports
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpScreen : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var Realtydatabase: DatabaseReference
    private lateinit var binding: FragmentSignUpScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Realtydatabase = FirebaseDatabase.getInstance().getReference("users")
        mAuth = FirebaseAuth.getInstance()

        binding.registerBtn.setOnClickListener {
            val enteredUsername = binding.userEDT.text.toString().trim()
            val mobileNum = binding.mobileEDT.text.toString()
            val email = binding.emailEDT.text.toString()
            val password = binding.passwordEDT.text.toString()

            if (enteredUsername.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                Realtydatabase.child(enteredUsername).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result.exists()) {
                            Toast.makeText(requireContext(), "Username already taken. Please choose a different one.", Toast.LENGTH_SHORT).show()
                        } else {
                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authTask ->
                                if (authTask.isSuccessful) {
                                    val user = mAuth.currentUser
                                    user?.let {
                                        it.sendEmailVerification().addOnCompleteListener { verificationTask ->
                                            if (verificationTask.isSuccessful) {
                                                val uid = it.uid
                                                val newUser = user(enteredUsername, mobileNum, email, password)
                                                Realtydatabase.child(uid).setValue(newUser).addOnCompleteListener { dbTask ->
                                                    if (dbTask.isSuccessful) {
                                                        Toast.makeText(requireContext(), "Registration successful. Please check your email for verification.", Toast.LENGTH_SHORT).show()
                                                        parentFragmentManager.commit {
                                                            setReorderingAllowed(true)
                                                            replace(R.id.fragmentContainer, LoginScreen())
                                                            addToBackStack(null)
                                                        }
                                                    } else {
                                                        Toast.makeText(requireContext(), "Failed to save user data: ${dbTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(requireContext(), "Failed to send verification email: ${verificationTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(requireContext(), "Registration failed: ${authTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error checking username: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please complete the data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}