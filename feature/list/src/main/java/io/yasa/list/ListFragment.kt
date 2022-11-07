package io.yasa.list

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.yasa.di.kodeinViewModel
import io.yasa.list.ListViewModel.Order
import io.yasa.list.ListViewModel.SortField
import io.yasa.list.adapter.BreweryAdapter
import io.yasa.list.adapter.loadstate.BreweryLoadStateAdapter
import io.yasa.list.databinding.FragmentListBinding
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.ui.viewbinding.ext.isPortrait
import io.yasa.ui.viewbinding.ext.isTablet
import io.yasa.ui.viewbinding.snap.GravitySnapHelper
import io.yasa.ui.viewbinding.viewBinding
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import logcat.logcat
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class ListFragment : Fragment(R.layout.fragment_list), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewBinding by viewBinding(FragmentListBinding::bind)

    private var adapter: BreweryAdapter? = null

    private val viewModel: ListViewModel by kodeinViewModel()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BreweryAdapter(
            requireContext(),
            isInPortrait = requireContext().isPortrait(),
            onClick = { uiModel ->
                uiModel.id?.let {
                    (requireActivity() as? ToFlowNavigatable)?.navigateToFlow(
                        NavigationFlow.DetailsFlow(
                            it
                        )
                    )
                }
            }
        )
        with(viewBinding) {
            val divider = MaterialDividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            ).apply {
                isLastItemDecorated = false
            }

            rvItems.apply {
                addItemDecoration(divider)
                layoutManager = if (requireContext().isPortrait()) {
                    LinearLayoutManager(requireContext())
                } else {
                    if (requireContext().isTablet()) {
                        GridLayoutManager(requireContext(), 3)
                    } else {
                        GridLayoutManager(requireContext(), 2)
                    }
                }
                adapter = this@ListFragment.adapter?.withLoadStateFooter(
                    footer = BreweryLoadStateAdapter(requireContext()) {
                        this@ListFragment.adapter?.retry()
                    }
                )
                GravitySnapHelper(Gravity.TOP).attachToRecyclerView(this)
            }

            mbtgFeatures.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (checkedId == btnSort.id) {
                    llSort.isVisible = isChecked
                    if (!isChecked) {
                        // Clear tags
//                        addSortTag(null)
                    } else {
                        // Fulfill sort
                        viewModel.sort()
                    }

                }
            }

            mbtgSortField.addOnButtonCheckedListener { _, checkedId, isChecked ->
                when (checkedId) {
                    btnName.id -> {
                        if (isChecked)
                            viewModel.sort(field = SortField.NAME)
                    }
                    btnType.id -> {
                        if (isChecked)
                            viewModel.sort(field = SortField.TYPE)
                    }
                    btnDate.id -> {
                        if (isChecked)
                            viewModel.sort(field = SortField.DATE)
                    }
                }
            }

            mbtgSortOrder.addOnButtonCheckedListener { group, checkedId, isChecked ->
                when (checkedId) {
                    btnAsc.id -> {
                        if (isChecked)
                            viewModel.sort(order = Order.ASC)
                    }
                    btnDesc.id -> {
                        if (isChecked)
                            viewModel.sort(order = Order.DESC)
                    }
                }
            }

            textField.setEndIconOnClickListener {
                tietInput.setText("")
                viewModel.clearSearch()
                addSearchTag(null)
            }

            tietInput.doAfterTextChanged {
                if (!it.isNullOrEmpty()) {
                    Toast.makeText(context, it ?: "", Toast.LENGTH_SHORT).show()
                    viewModel.search(it.toString())
                    addSearchTag(it.toString())
                }
            }

            srlRefresh.setOnRefreshListener {
                adapter?.refresh()
                viewBinding.rvItems.isNestedScrollingEnabled = false
            }
            btnRetry.setOnClickListener { adapter?.refresh() }

            lifecycleScope.launch {
                viewModel.fooData.collectLatest { pagingData ->
                    logcat { pagingData.toString() }
                    adapter?.submitData(pagingData)
                    viewBinding.srlRefresh.isRefreshing = false
                    viewBinding.rvItems.isNestedScrollingEnabled = true
                }
            }

            with(viewModel) {
                lifecycleScope.launch {
                    fooSortFlow.collect {
                        val sort = it?.sort
                        logcat("sortFlow") { "$sort" }
                        addSortTag(sort)
                        viewModel.sort(sort?.first,sort?.second)
                        adapter?.refresh()
                        viewBinding.rvItems.scrollToPosition(0)
                    }
                }
            }


        }

        KeyboardVisibilityEvent.registerEventListener(requireActivity()) {
            if (!it) {
                viewBinding.tietInput.clearFocus()
            }
        }

        viewBinding.fabUp.setOnClickListener {
            viewBinding.rvItems.scrollToPosition(0)
        }


        lifecycleScope.launch {
            adapter?.loadStateFlow?.collectLatest { loadStates ->
                logcat("loadStates") { "${loadStates.refresh}" }
                with(viewBinding) {

                    if (loadStates.source.refresh is LoadState.NotLoading && loadStates.append.endOfPaginationReached && (adapter?.itemCount
                            ?: 0) < 1
                    ) {
                        //empty
                        rvItems.isVisible = false
                        tvNoData.isVisible = true

                    } else {
                        // default
                        tvNoData.isVisible = false

                        cpbiProgress.isVisible = loadStates.refresh is LoadState.Loading
                        rvItems.isVisible = loadStates.refresh !is LoadState.Error
                        llError.isVisible = loadStates.refresh is LoadState.Error
                    }


                }
            }
        }
    }

    private fun addSearchTag(query: String?) {
        if (query.isNullOrEmpty()) {
            with(viewBinding) {
                clTags.children.filter { it != clFlow && it.tag == "query" }.forEach {
                    clTags.removeView(it)
                }
            }
            return
        }
        val tag = Chip(context).apply {
            id = View.generateViewId()
            text = "Search: $query"
            tag = "query"
            layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        }
        with(viewBinding) {
            val isSearchTagPresent = clTags.children.any { v ->
                v.tag == "query"
            }
            if (isSearchTagPresent) {
                clTags.removeView(clTags.findViewWithTag("query"))
                clFlow.referencedIds = clTags.children.map { it.id }.toList().toIntArray()
            }
            clTags.addView(tag)
        }
    }

    private fun addSortTag(sort: Pair<SortField, Order>? = null) {
        if (sort == null) {
            with(viewBinding) {
                clTags.children.filter { it != clFlow && it.tag == "sort" }.forEach {
                    clTags.removeView(it)
                }
            }
            return
        }
        val tag =
            Chip(context).apply {
                id = View.generateViewId()
                text = "Sort: ${sort.first} (${sort.second})"
                tag = "sort"
                layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            }
        with(viewBinding) {
            val isSearchTagPresent = clTags.children.any { v ->
                v.tag == "sort"
            }
            if (isSearchTagPresent) {
                clTags.removeView(clTags.findViewWithTag("sort"))
                clFlow.referencedIds = clTags.children.map { it.id }.toList().toIntArray()
            }
            clTags.addView(tag)
        }
    }

}