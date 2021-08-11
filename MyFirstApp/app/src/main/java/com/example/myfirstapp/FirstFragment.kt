package com.example.myfirstapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myfirstapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.randomButton.setOnClickListener {
            val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
            val currentCount = showCountTextView.text.toString().toInt()
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(currentCount)
            findNavController().navigate(action)
        }

        //view.findViewById<Button>(R.id.toast_button).setOnClickListener {
        //    val myToast = Toast.makeText(context, "Hello Toast!", Toast.LENGTH_SHORT)
        //    myToast.show()
        //} The code below does the same thing:
        // getString(R.string.toast_button_output) gets the value for toast_button_output stored in strings.xml
        binding.toastButton.setOnClickListener {
            val myToast = Toast.makeText(context, getString(R.string.toast_button_output), Toast.LENGTH_SHORT)
             myToast.show()
        }
        view.findViewById<Button>(R.id.count_button).setOnClickListener { countMe(view) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun countMe(view: View) {
        // Get the TextView
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        // Get the value of the TextView
        val countString = showCountTextView.text.toString()
        // Convert value to an Int and increment it.
        var count = countString.toInt()
        count++
        // Display new value in TextView
        showCountTextView.text = count.toString()
    }
}