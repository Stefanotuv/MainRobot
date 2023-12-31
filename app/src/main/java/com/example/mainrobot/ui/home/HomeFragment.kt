package com.example.mainrobot.ui.home

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
import com.example.mainrobot.ApiClient
class HomeFragment : Fragment() {

    private lateinit var robocarViewModel: RobocarViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val apiClient = ApiClient()
    private val addressRobotCar = "http://192.168.2.242/api"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val addressEditText: EditText = binding.inputEditText
        val saveConnect: Button = binding.saveConnect

        robocarViewModel = ViewModelProvider(requireActivity()).get(RobocarViewModel::class.java)

        saveConnect.setOnClickListener {
            val address = addressEditText.text.toString()
            robocarViewModel.setRobocarAddress(address)
            Log.d("HomeFragment", "Robocar address saved: $address")
            Toast.makeText(requireContext(), "Robocar address saved", Toast.LENGTH_SHORT).show()
            apiClient.sendRequest(address, "GET") { response ->
                // Handle the API response here
                Log.d("RobotCarFragment", "API Response: $response")
                activity?.runOnUiThread {
                    if (response != "") {
                        saveConnect.text = "Disconnect"
                    } else {
                        // Handle unsuccessful response if needed
                    }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
