package fr.isen.scamacca.androiderestaurant.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.scamacca.androiderestaurant.R
import fr.isen.scamacca.androiderestaurant.databinding.FragmentTemplateBinding

private lateinit var binding: FragmentTemplateBinding

private const val ARG_PARAM1 = "param1"

class DetailFragment() : Fragment() {

    private var param1: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTemplateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

        Picasso.get().load(param1).placeholder(R.drawable.wait).error(
            R.drawable.error
        ).fit().into(binding.imgFragment)

    }

    companion object {
        fun newInstance(param1: String): DetailFragment =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1,param1)
                }
            }
    }
}