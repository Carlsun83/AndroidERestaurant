package fr.isen.scamacca.androiderestaurant.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.isen.scamacca.androiderestaurant.databinding.RegisterFragmentBinding


private lateinit var binding: RegisterFragmentBinding


class RegisterFragment : Fragment() {


    var delegate: LoginActivityFragmentInteraction? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRegister.setOnClickListener {
            delegate?.makeRequest(
                binding.email.text.toString(),
                binding.password.text.toString(),
                binding.lastname.text.toString(),
                binding.firstname.text.toString(),
                false
            )
        }

        binding.loginButton.setOnClickListener {
            delegate?.showLogin()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginActivityFragmentInteraction) {
            delegate = context
        }
    }
}