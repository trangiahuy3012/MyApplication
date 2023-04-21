package com.example.myapplication.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.app.MyApp
import com.example.myapplication.app.ViewModelFactory
import com.example.myapplication.database.AccountDatabase
import com.example.myapplication.databinding.FragmentSignInBinding
import com.example.myapplication.viewModels.SignInViewModel


class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    lateinit var viewModel: SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory((activity?.application as MyApp).prefs,
                AccountDatabase.getDatabase(activity?.application as MyApp))
        ).get(
            SignInViewModel::class.java
        )

//        /// old style
        viewModel = ViewModelProvider(
            this,
        ).get(
            SignInViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerErrorEvent()
        registerLoginSuccess()
        binding.buttonLogin.setOnClickListener {
            val email = binding.emailInputTextLayout.editText?.text.toString().trim()
            val password = binding.passwordTextLayout.editText?.text.toString().trim()
            viewModel.loginWithDatabase(email, password)
        }
        binding.signUpText.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

    private fun registerLoginSuccess() {
        viewModel.saveSuccessEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(binding.root.context, "SignIn Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                }
            }
        }
    }

    private fun registerErrorEvent() {
        viewModel.loginFailedEvent.observe(viewLifecycleOwner) { errMsg ->
            if (viewModel.saveSuccessEvent.value?.peekContent() == false) {
                Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

}