package com.example.myapplication.viewModels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.data.DataStore
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

val profileFieldLabel = mutableMapOf("fullNameLabel" to "Full name",
                                    "emailLabel" to "E-mail",
                                    "phoneNumberLabel" to "Phone number")

class DialogEditor (private val fieldName: String, private val viewModel:ProfileViewModel) : DialogFragment() {
    private lateinit var textInputProfileField:TextInputEditText
    private lateinit var textViewProfileFieldLabel:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.dialog_editor, container, false)

        rootView.findViewById<Button>(R.id.buttonCancel).setOnClickListener { onCancelClick() }
        rootView.findViewById<Button>(R.id.buttonSubmit).setOnClickListener { onSubmitClick() }

        textInputProfileField = rootView.findViewById<TextInputLayout>(R.id.textInputProfileFieldLayout).findViewById(
            R.id.textInputProfileField
        )
        textViewProfileFieldLabel = rootView.findViewById(R.id.textViewProfileFieldLabel)

        textInputProfileField.setText(DataStore.currentUserData.value?.get(fieldName) ?: "")
        textViewProfileFieldLabel.text = profileFieldLabel[fieldName + "Label"]

        return rootView
    }

    private fun onSubmitClick() {

        viewModel.dialogInputValid(textInputProfileField.text, fieldName) { dismiss() }

    }

    private fun onCancelClick() {
        dismiss()
    }
}