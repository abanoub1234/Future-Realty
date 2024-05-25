package com.example.futurerealty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database


class LoginScreen : Fragment() {
    var mAuth: FirebaseAuth? = null
    lateinit var Email: TextInputEditText
    lateinit var Pass: TextInputEditText

    val Realtydatabase = Firebase.database
    val RealtyRef = Realtydatabase.getReference("USER")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        Email = view.findViewById(R.id.email_edt)
        Pass = view.findViewById(R.id.password_edt)


        view.findViewById<Button>(R.id.login_btn).setOnClickListener {
            var email = Email.text.toString()
            var pass = Pass.text.toString()
            loginUser(email, pass)

        }



        view.findViewById<Button>(R.id.signup_btn).setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainer, SignUpScreen())
                addToBackStack(null)
            }
        }


    }


    private fun loginUser(email: String, password: String)
    {
        if (email.isNotEmpty() && password.isNotEmpty())
        {
            mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    var user=mAuth?.currentUser

                    if (user!!.isEmailVerified)
                    {
                        parentFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(R.id.fragmentContainer, HomeScreen())
                            addToBackStack(null) }

                    }
                    else
                    {
                        Toast.makeText(requireContext(), "please Verify your email ", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(requireContext(), "Email or Password Incorrect!!", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}