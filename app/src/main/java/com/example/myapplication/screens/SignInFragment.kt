package com.example.myapplication.screens.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignInBinding
import com.example.myapplication.viewModels.SignInViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private lateinit var viewModel: SignInViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.imageBackButton.setOnClickListener{
            activity?.onBackPressed()
        }

        binding.signUpText.setOnClickListener { onSignUpNavigate() }

        listenerSuccessEvent()
        listenerErrorEvent()
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
         * @return A new instance of fragment SignInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                }
            }

    }

    private fun listenerErrorEvent() {
        viewModel.isErrorEvent.observe(viewLifecycleOwner) { errMsg ->
            Toast.makeText(binding.root.context, errMsg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(viewLifecycleOwner) { isSuccess ->
            if(isSuccess)
            {
                // Success
                Toast.makeText(binding.root.context, "SignIn Success", Toast.LENGTH_SHORT).show()
                val controller = findNavController()
                controller.navigate(R.id.action_signInFragment_to_homeFragment)
            }
        }
    }

    private fun onSignUpNavigate() {
        val controller = findNavController()
        controller.navigate(R.id.action_signInFragment_to_signUpFragment)
    }

}