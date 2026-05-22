package com.example.spotlight.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spotlight.R
import com.example.spotlight.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
         binding.imgProfile.setImageResource(R.drawable.profile)
         binding.tvDevName.text = "Abia Farrel Kaysan"
         binding.tvNim.text = "2510511167"
         binding.tvGithub.text = "github.com/Farrelabia"
         binding.tvInstagram.text = "@farrelabia"
         binding.tvEmail.text = "farrelabia@google.com"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
