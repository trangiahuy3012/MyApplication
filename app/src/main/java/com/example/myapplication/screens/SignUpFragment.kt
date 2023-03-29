package com.example.myapplication.screens.signup

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
import com.example.myapplication.databinding.FragmentSignUpBinding
import com.example.myapplication.data.DataStore
import com.example.myapplication.viewModels.SignUpViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    // Obtain ViewModel from ViewModelProviders
    private lateinit var viewModel: SignUpViewModel
    private lateinit var  binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.viewModel = viewModel

        binding.textViewSignin.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

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
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
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
                DataStore.userDataStore.add(
                    mutableMapOf("fullName" to binding.fullNameInputText.text.toString(),
                        "email" to binding.emailInputText.text.toString(),
                        "password" to binding.passwordInputText.text.toString())
                )
                Toast.makeText(binding.root.context, "SignUp Success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}