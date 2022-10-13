package io.yasa.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import io.yasa.details.databinding.FragmentDetailsBinding
import io.yasa.di.kodeinViewModel
import io.yasa.ui.viewbinding.viewBinding
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class DetailsFragment : Fragment(R.layout.fragment_details), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewBinding by viewBinding(FragmentDetailsBinding::bind)

    private val viewModel: DetailsViewModel by kodeinViewModel()

    private val detailsFragmentArgs: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getBrewery(detailsFragmentArgs.id).let { breweryItem ->
                with(viewBinding) {
                    ctlToolbar.title = breweryItem.name
                    ivLogo.load("https://loremflickr.com/320/240")

                }
            }
        }
    }

}