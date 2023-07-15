package com.example.mainrobot.ui.home
//
//import android.content.pm.ActivityInfo
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.mainrobot.databinding.FragmentHomeBinding
//
//class HomeFragment : Fragment() {
//
//    private var _binding: FragmentHomeBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        val textView: TextView = binding.inputEditText
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mainrobot.databinding.FragmentHomeBinding
import com.example.mainrobot.RobocarViewModel
class HomeFragment : Fragment() {

    private lateinit var robocarViewModel: RobocarViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val addressEditText: EditText = binding.inputEditText
        val saveButton: Button = binding.saveButton

        robocarViewModel = ViewModelProvider(requireActivity()).get(RobocarViewModel::class.java)

        saveButton.setOnClickListener {
            val address = addressEditText.text.toString()
            robocarViewModel.setRobocarAddress(address)
            Log.d("HomeFragment", "Robocar address saved: $address")
            Toast.makeText(requireContext(), "Robocar address saved", Toast.LENGTH_SHORT).show()

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
