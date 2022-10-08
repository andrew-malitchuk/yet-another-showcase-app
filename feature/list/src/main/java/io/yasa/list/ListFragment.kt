package io.yasa.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import io.yasa.di.kodeinViewModel
import io.yasa.list.adapter.BreweryAdapter
import io.yasa.list.databinding.FragmentListBinding
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.ui.viewbinding.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import logcat.logcat
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class ListFragment : Fragment(R.layout.fragment_list), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewBinding by viewBinding(FragmentListBinding::bind)

    private var adapter: BreweryAdapter? = null

    private val viewModel: ListViewModel by kodeinViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BreweryAdapter(requireContext()) {
            (requireActivity() as? ToFlowNavigatable)?.navigateToFlow(NavigationFlow.DetailsFlow)
        }
        with(viewBinding) {
            rvItems.apply {
                this.adapter = this@ListFragment.adapter
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.refreshBreweries()
        }

        lifecycleScope.launch {
            viewModel.breweriesStateFlow.collect { breweryList ->
                adapter?.submitList(breweryList)
            }
        }

        lifecycleScope.launch {
            viewModel.getBrewery("10-56-brewing-company-knox").let { breweryItem ->
                logcat("list") { breweryItem.toString() }
            }
        }

        viewModel.getAndSaveBreweries(1)

    }

}