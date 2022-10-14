package io.yasa.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
                    if (isPortrait(requireContext())) {
                        mtToolbar.apply {
                            navigationIcon = ContextCompat.getDrawable(
                                context,
                                io.yasa.ui.R.drawable.ic_baseline_arrow_back_24
                            )
                            setNavigationIconTint(
                                ContextCompat.getColor(
                                    context,
                                    io.yasa.ui.R.color.md_theme_light_surface
                                )
                            )
                            this.setOnClickListener {
                                findNavController().popBackStack()
                            }
                        }
                    }else{
                        mtToolbar.navigationIcon=null
                    }
                    ivLogo.load("https://loremflickr.com/320/240")
                    tvName.text = breweryItem.name
                    tvAddress.text = breweryItem.fullAddress
                    tvType.text = breweryItem.breweryType
                    tvPhone.text = breweryItem.phone
                    breweryItem.websiteUrl?.let {
                        tvWebsite.apply {
                            isVisible = true
                            text = it
                        }
                        tvWebsiteField.isVisible = true
                    }
                }
            }
        }
    }

    fun isPortrait(context: Context): Boolean {
        return context.resources.getBoolean(io.yasa.ui.R.bool.is_portrait)
    }

}