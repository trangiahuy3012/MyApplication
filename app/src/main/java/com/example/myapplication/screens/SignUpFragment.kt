package com.example.myapplication.screens

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
import com.example.myapplication.app.MyApp
import com.example.myapplication.app.ViewModelFactory
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
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                (activity?.application as MyApp).prefs,
                (activity?.application as MyApp).db
            )
        ).get(
            SignUpViewModel::class.java
        )
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

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignUp.setOnClickListener {
            val username = binding.fullNameInputTextLayout.editText?.text.toString().trim()
            val email = binding.emailInputTextLayout.editText?.text.toString().trim()
            val password = binding.passwordTextLayout.editText?.text.toString().trim()
            viewModel.registerUserWithDB(username, email, password)
        }

        viewModel.isSuccessEvent.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                DataStore.userDataStore.add(
                    mutableMapOf("fullName" to binding.fullNameInputText.text.toString(),
                        "email" to binding.emailInputText.text.toString(),
                        "password" to binding.passwordInputText.text.toString())
                )
                Toast.makeText(binding.root.context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isErrorEvent.observe(viewLifecycleOwner) { isFailed ->
            if (isFailed) {
                Toast.makeText(
                    activity, "Đăng ký thất bại", Toast
                        .LENGTH_SHORT
                ).show()
            }
        }
    }
}

