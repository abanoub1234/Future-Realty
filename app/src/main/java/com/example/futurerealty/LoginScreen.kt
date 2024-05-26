package com.example.futurerealty

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database


class LoginScreen : Fragment() {
    var mAuth: FirebaseAuth? = null
    lateinit var Email: TextInputEditText
    lateinit var Pass: TextInputEditText
    lateinit var GoogleBtn : Button

    val Realtydatabase = Firebase.database
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
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
        GoogleBtn = view.findViewById(R.id.login_with_google_btn)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = FirebaseAuth.getInstance()


        GoogleBtn.setOnClickListener(View.OnClickListener {
            signIn()
        })





        view.findViewById<Button>(R.id.login_btn).setOnClickListener {
            var email = Email.text.toString()
            var pass = Pass.text.toString()
            loginUser(email, pass)

        }



        view.findViewById<Button>(R.id.signup_btn).setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainer, SignUpScreen())
                addToBackStack(null)
            }
        }


    }


    private fun signIn()
    {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>)
    {
        try
        {
            val account = completedTask.getResult(ApiException::class.java)
            Toast.makeText(requireContext() , "signInResult:success, account: ${account.email}" , Toast.LENGTH_SHORT).show()
            val bundle = Bundle().apply {
                putString("email", account.email)
                putString("username", account.displayName)
            }
            val homeFragment = HomeScreen().apply {
                arguments = bundle
            }
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, homeFragment)
                addToBackStack(null)
            }
        }
        catch (e: ApiException)
        {
            Toast.makeText(requireContext() , "signInResult:failed code=${e.statusCode}" , Toast.LENGTH_SHORT).show()
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
                            }

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