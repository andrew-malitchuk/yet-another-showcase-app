package io.yasa.list

import android.content.Context
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.yasa.di.kodeinViewModel
import io.yasa.list.adapter.BreweryAdapter
import io.yasa.list.adapter.loadstate.BreweryLoadStateAdapter
import io.yasa.list.databinding.FragmentListBinding
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.ui.viewbinding.edittext.debounce
import io.yasa.ui.viewbinding.snap.GravitySnapHelper
import io.yasa.ui.viewbinding.viewBinding
import kotlinx.coroutines.flow.collectLatest
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
            isInPortrait = isPortrait(requireContext()),
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
                layoutManager = if (isPortrait(requireContext())) {
                    LinearLayoutManager(requireContext())
                } else {
                    GridLayoutManager(requireContext(), 2)
                }
                adapter = this@ListFragment.adapter?.withLoadStateFooter(
                    footer = BreweryLoadStateAdapter(requireContext()) {
                        this@ListFragment.adapter?.retry()
                    }
                )
                GravitySnapHelper(Gravity.TOP).attachToRecyclerView(this)
            }

            mbtgFeatures.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (checkedId == btnSort.id) {
                    llSort.isVisible = isChecked

                    if (!isChecked) {
                        // Clear tags
                        addSortTag(null)
                    } else {
                        // Fulfill sort
                        viewModel.sort()
                    }

                }
                if (checkedId == btnFilter.id) {
                    llSort.isVisible = isChecked
                }
            }

            mbtgSortField.addOnButtonCheckedListener { group, checkedId, isChecked ->
                when (checkedId) {
                    btnName.id -> {
                        viewModel.sort(field = ListViewModel.SortField.NAME)
                    }
                    btnType.id -> {
                        viewModel.sort(field = ListViewModel.SortField.TYPE)
                    }
                    btnDate.id -> {
                        viewModel.sort(field = ListViewModel.SortField.DATE)
                    }
                }

            }

            mbtgSortOrder.addOnButtonCheckedListener { group, checkedId, isChecked ->
                when (checkedId) {
                    btnAsc.id -> {
                        viewModel.sort(order = ListViewModel.Order.ASC)
                    }
                    btnDesc.id -> {
                        viewModel.sort(order = ListViewModel.Order.DESC)
                    }
                }
            }


            textField.setEndIconOnClickListener {
                tietInput.setText("")
                adapter?.refresh()
                addSearchTag(null)
            }

            tietInput.debounce(500L) {
                Toast.makeText(context, it ?: "", Toast.LENGTH_SHORT).show()
                if (!it.isNullOrEmpty()) {
                    viewModel.search(it.toString())
                    addSearchTag(it.toString())
                }
            }

            srlRefresh.setOnRefreshListener {
                adapter?.refresh()
                viewBinding.rvItems.isNestedScrollingEnabled = false
            }
            btnRetry.setOnClickListener { adapter?.refresh() }

            with(viewModel) {
                lifecycleScope.launchWhenStarted {
                    searchFlow.collect { uiList ->
                        uiList?.let {
                            adapter?.submitData(PagingData.from(it))
                        }
                    }
                }
                lifecycleScope.launch {
                    sortFlow.collect {
                        logcat("sortFlow") { "$it" }
                        addSortTag(it)
                        adapter?.refresh()
                    }
                }
            }


        }

        KeyboardVisibilityEvent.registerEventListener(requireActivity()) {
            if (!it) {
                viewBinding.tietInput.clearFocus()
            }
        }

        lifecycleScope.launch {
            viewModel.getBreweries().collect { pagingData ->
                logcat { pagingData.toString() }
                adapter?.submitData(pagingData)
                viewBinding.srlRefresh.isRefreshing = false
                viewBinding.rvItems.isNestedScrollingEnabled = true
            }
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

    private fun addSortTag(sort: Pair<ListViewModel.SortField, ListViewModel.Order>? = null) {
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

    private fun isPortrait(context: Context): Boolean {
        return context.resources.getBoolean(io.yasa.ui.R.bool.is_portrait)
    }

}