package com.example.mainrobot.ui.configurations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mainrobot.databinding.FragmentConfigurationsBinding
import org.json.JSONObject
import com.example.mainrobot.ApiClient

class ConfigurationsFragment : Fragment() {

    private lateinit var binding: FragmentConfigurationsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentConfigurationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frontCameraIpEditText = binding.frontCameraIpEditText
        val backCameraIpEditText = binding.backCameraIpEditText
        val speedSeekBar = binding.speedSeekBar
        val wifiSsidEditText = binding.wifiSsidEditText
        val wifiPasswordEditText = binding.wifiPasswordEditText
        val showPasswordCheckBox = binding.showPasswordCheckBox
        val apModeSwitch = binding.apModeSwitch
        val saveButton = binding.saveButton
        val resetButton = binding.resetButton

        saveButton.setOnClickListener {
            // Read the input values
            val frontCameraIp = frontCameraIpEditText.text.toString()
            val backCameraIp = backCameraIpEditText.text.toString()
            val speed = speedSeekBar.progress
            val wifiSsid = wifiSsidEditText.text.toString()
            val wifiPassword = wifiPasswordEditText.text.toString()
            val isShowPasswordChecked = showPasswordCheckBox.isChecked
            val isApModeSelected = apModeSwitch.isChecked

            // Create a JSON object with the input data
            val inputData = JSONObject().apply {
                put("frontCameraIp", frontCameraIp)
                put("backCameraIp", backCameraIp)
                put("speed", speed)
                put("wifiSsid", wifiSsid)
                put("wifiPassword", wifiPassword)
                put("showPassword", isShowPasswordChecked)
                put("apMode", isApModeSelected)
            }

            // Send the JSON data through the API
            val apiClient = ApiClient()
            val address_robotcar = "http://192.168.2.242/api"
            apiClient.sendRequest(address_robotcar, "POST", inputData) { response ->
                // Handle the API response here
                Log.d("ConfigurationsFragment", "API Response: $response")
            }

            // Show a toast message to indicate the save operation
            Toast.makeText(requireContext(), "Configuration saved", Toast.LENGTH_SHORT).show()
        }

        // TODO: Implement the logic for the resetButton click event
    }

    // ...
}
