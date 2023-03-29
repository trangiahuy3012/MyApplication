package com.example.myapplication.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.viewModels.DialogEditor
import com.example.myapplication.viewModels.ProfileViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.viewModel = viewModel

        val applicationCompatibility = (activity as AppCompatActivity)
        binding.textViewFullName.setOnClickListener {
            DialogEditor("fullName", viewModel).show(
                applicationCompatibility.supportFragmentManager,
                "Dialog Editor"
            )
        }
        binding.textViewEmail.setOnClickListener {
            DialogEditor("email", viewModel).show(
                applicationCompatibility.supportFragmentManager,
                "Dialog Editor"
            )
        }
        binding.textViewPhone.setOnClickListener {
            DialogEditor("phoneNumber", viewModel).show(
                applicationCompatibility.supportFragmentManager,
                "Dialog Editor"
            )
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
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
        viewModel.isSuccessEvent.observe(viewLifecycleOwner) { successUserKey ->
            onDialogSubmitFinish(successUserKey)
        }

        viewModel.currentUserData.observe(viewLifecycleOwner){
            if(it == null)
            {
                val controller = findNavController()
                controller.navigate(R.id.action_profileFragment_to_welcomeFragment)
            }
        }
    }

    private fun onDialogSubmitFinish(fieldName: String) {
//        when (fieldName) {
//            "fullName" -> {
//                binding.textViewName.text = DataStore.currentUserData[fieldName]
//                binding.textViewFullName.text = DataStore.currentUserData[fieldName]
//            }
//            "email" -> binding.textViewEmail.text = DataStore.currentUserData[fieldName]
//            "phoneNumber" -> binding.textViewPhone.text = DataStore.currentUserData[fieldName]
//        }
    }
}