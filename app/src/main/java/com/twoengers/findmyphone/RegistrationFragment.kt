package com.twoengers.findmyphone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.navigation.findNavController
import com.twoengers.findmyphone.retrofit.Api


class RegistrationFragment : Fragment() {
    lateinit var buttonReg2: Button
    lateinit var buttonLogin2: Button
    lateinit var inputEmail2: EditText
    lateinit var inputPassword2: EditText
    lateinit var inputName2: EditText
    lateinit var inputSurname2: EditText
    lateinit var inputEmailParent2: EditText
    lateinit var checkParent: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onResume() {
        super.onResume()
        buttonReg2 = requireView().findViewById(R.id.buttonReg2)
        buttonLogin2 = requireView().findViewById(R.id.buttonLogin2)
        inputEmail2 = requireView().findViewById(R.id.inputEmail2)
        inputPassword2 = requireView().findViewById(R.id.inputPassword2)
        inputName2 = requireView().findViewById(R.id.inputName)
        inputSurname2 = requireView().findViewById(R.id.inputSurname)
        inputEmailParent2 = requireView().findViewById(R.id.inputEmailParent)
        checkParent = requireView().findViewById(R.id.switch1)

        buttonReg2.setOnClickListener {
            val api = Api(this::goToMain)
            api.registration(
                inputEmail2.text.toString(),
                inputName2.text.toString(),
                inputSurname2.text.toString(),
                checkParent.isChecked,
                inputEmailParent2.text.toString(),
                inputPassword2.text.toString()
            )
        }
        buttonLogin2.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        checkParent.setOnCheckedChangeListener { compoundButton, b ->
            User.is_parent = b
            if (!b) {
                inputEmailParent2.visibility = View.VISIBLE
            } else {
                inputEmailParent2.visibility = View.GONE
            }
        }
    }

    fun goToMain() {
        if (User.is_parent) {
            requireView().findNavController().navigate(R.id.action_registrationFragment_to_parentFragment)
        } else {
            requireView().findNavController().navigate(R.id.action_registrationFragment_to_childFragment)
        }
    }
}