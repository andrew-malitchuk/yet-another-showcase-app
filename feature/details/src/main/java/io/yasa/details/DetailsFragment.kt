package io.yasa.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.yasa.details.databinding.FragmentDetailsBinding
import io.yasa.ui.viewbinding.viewBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewBinding by viewBinding(FragmentDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {

        }
    }

}