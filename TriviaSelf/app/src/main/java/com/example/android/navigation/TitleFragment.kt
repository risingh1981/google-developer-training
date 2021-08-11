package com.example.android.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding


/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    override fun onCreateView
                (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container,false)
        // Change below to incorporate NavDirections for SafeArgs:
        //binding.playButton.setOnClickListener { view: View -> view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment) }
        binding.playButton.setOnClickListener { view: View -> view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment()) }
        //binding.rulesButton.setOnClickListener { view:View -> view.findNavController().navigate(R.id.action_titleFragment_to_rulesFragment) }
        binding.rulesButton.setOnClickListener { view:View -> view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToRulesFragment()) }
        //binding.aboutButton.setOnClickListener { view:View -> view.findNavController().navigate(R.id.action_titleFragment_to_aboutFragment) }
        binding.aboutButton.setOnClickListener { view:View -> view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToAboutFragment()) }

        setHasOptionsMenu(true)

        // Add Logging:
        Log.i("TitleFragment", "onCreateView() Called")

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.i("TitleFragment","onStart() Called")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("TitleFragment","onViewCreated() Called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("TitleFragment","onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("TitleFragment","onStop() Called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("TitleFragment","onDestroyView() Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TitleFragment","onDestroy() Called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("TitleFragment","onDetach() Called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("TitleFragment","onResume() Called")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("TitleFragment","onAttach() Called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TitleFragment", "onCreate() Called")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}