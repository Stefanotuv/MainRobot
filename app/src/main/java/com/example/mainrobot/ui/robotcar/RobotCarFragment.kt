package com.example.mainrobot.ui.robotcar


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mainrobot.databinding.FragmentRobotcarBinding
import com.example.mainrobot.JoystickView

class RobotCarFragment : Fragment(), JoystickView.JoystickListener {

    private lateinit var binding: FragmentRobotcarBinding
    private lateinit var webView: WebView
    private lateinit var joystickTopCoordinates: TextView
    private lateinit var joystickBottomCoordinates: TextView
    private val videoUrl = "http://192.168.2.235:81/stream" // Replace with your IP camera video URL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRobotcarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the WebView
        webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.userAgentString =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36"
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl(videoUrl)

        // Initialize the TextViews
        joystickTopCoordinates = binding.joystickTopCoordinates
        joystickBottomCoordinates = binding.joystickBottomCoordinates

        // Initialize the JoystickViews
        binding.joystickTop.setJoystickListener(this)
        binding.joystickBottom.setJoystickListener(object : JoystickView.JoystickListener {
            override fun onJoystickMoved(x: Float, y: Float) {
                val (scaledX, scaledY) = scaleCoordinate(x, y)
                joystickBottomCoordinates.text =
                    "X: ${String.format("%.2f", scaledX)}\nY: ${String.format("%.2f", scaledY)}"
            }

            override fun onJoystickReleased() {
                joystickBottomCoordinates.text = "X: 0.00\nY: 0.00"
            }
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setJoystickPositions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView.destroy()
    }

    override fun onJoystickMoved(x: Float, y: Float) {
        val (scaledX, scaledY) = scaleCoordinate(x, y)
        joystickTopCoordinates.text = "X: ${String.format("%.2f", scaledX)}\nY: ${String.format("%.2f", scaledY)}"
    }

    override fun onJoystickReleased() {
        joystickTopCoordinates.text = "X: 0.00\nY: 0.00"
    }

    private fun scaleCoordinate(x: Float, y: Float): Pair<Float, Float> {
        val angle = Math.atan2(y.toDouble(), x.toDouble()).toFloat()
        val radius = 1.0f
        val scaledX = radius * Math.cos(angle.toDouble()).toFloat()
        val scaledY = radius * Math.sin(angle.toDouble()).toFloat()
        return Pair(scaledX, -scaledY)
    }

    private fun setJoystickPositions() {
        val layoutParamsTop = binding.joystickTop.layoutParams as ViewGroup.LayoutParams
        val layoutParamsBottom = binding.joystickBottom.layoutParams as ViewGroup.LayoutParams

        val displayMetrics = resources.displayMetrics
        val joystickSize = (displayMetrics.widthPixels * 0.3).toInt()

        layoutParamsTop.width = joystickSize
        layoutParamsTop.height = joystickSize

        layoutParamsBottom.width = joystickSize
        layoutParamsBottom.height = joystickSize
    }
}
