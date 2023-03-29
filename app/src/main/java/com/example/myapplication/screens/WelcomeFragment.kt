package com.example.myapplication.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentWelcomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var buttonStartWithEmailOrPhone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

        activity?.let {
            val preferences = it.getSharedPreferences("PREFERENCE", 0)
            val FirstTime = preferences.getString("FirstTimeRun", "")

            if(FirstTime != "Yes")
            {
                val editor = preferences.edit()
                editor.putString("FirstTimeRun", "Yes")
                editor.apply()

                val controller = findNavController()
                controller.navigate(R.id.action_welcomeFragment_to_onboardingFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.textViewSignin.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_welcomeFragment_to_signInFragment)
        }
        binding.next0.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_welcomeFragment_to_signUpFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WelcomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}