package io.yasa.list

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.yasa.di.kodeinViewModel
import io.yasa.list.adapter.BreweryAdapter
import io.yasa.list.databinding.FragmentListBinding
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.ui.viewbinding.snap.GravitySnapHelper
import io.yasa.ui.viewbinding.viewBinding
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
        adapter = BreweryAdapter(requireContext()) { uiModel ->
            uiModel.id?.let {
                (requireActivity() as? ToFlowNavigatable)?.navigateToFlow(
                    NavigationFlow.DetailsFlow(
                        it
                    )
                )
            }
        }
        with(viewBinding) {
            val divider = MaterialDividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            ).apply {
                isLastItemDecorated = false
            }

            rvItems.apply {
                this.adapter = this@ListFragment.adapter
                addItemDecoration(divider)
                GravitySnapHelper(Gravity.TOP).attachToRecyclerView(this)
            }
        }

        lifecycleScope.launch {
            viewModel.getBreweries().collect { pagingData ->
                logcat { pagingData.toString() }
                adapter?.submitData(pagingData)
            }
        }

    }

}