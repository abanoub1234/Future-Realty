package com.example.futurerealty

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.commit


class SplashScreen : Fragment()
{


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        val splashLogo: ImageView = view.findViewById(R.id.splash_logo)

        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

        val slideUpAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)


        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener
        {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation)
            {
                splashLogo.startAnimation(slideUpAnimation)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        splashLogo.startAnimation(fadeInAnimation)


        Handler(Looper.getMainLooper()).postDelayed({
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainer, LoginScreen())
            }
        }, 3000)



    }



}