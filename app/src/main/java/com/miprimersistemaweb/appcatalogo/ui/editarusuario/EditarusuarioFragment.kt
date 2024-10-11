package com.miprimersistemaweb.appcatalogo.ui.editarusuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.miprimersistemaweb.appcatalogo.databinding.FragmentEditarusuarioBinding

class EditarusuarioFragment : Fragment() {

    private var _binding: FragmentEditarusuarioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       /* val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)*/

        _binding = FragmentEditarusuarioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}