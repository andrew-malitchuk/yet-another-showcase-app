package io.yasa.list

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
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
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.yasa.di.kodeinViewModel
import io.yasa.list.adapter.BreweryAdapter
import io.yasa.list.databinding.FragmentListBinding
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.ui.viewbinding.edittext.debounce
import io.yasa.ui.viewbinding.snap.GravitySnapHelper
import io.yasa.ui.viewbinding.viewBinding
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
                this.adapter = this@ListFragment.adapter
                addItemDecoration(divider)
                layoutManager = if (isPortrait(requireContext())) {
                    LinearLayoutManager(requireContext())
                } else {
                    GridLayoutManager(requireContext(), 2)
                }
                GravitySnapHelper(Gravity.TOP).attachToRecyclerView(this)
            }

            mbtgFeatures.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (checkedId == btnSort.id) {
                    llSort.isVisible = isChecked
                }
                if (checkedId == btnFilter.id) {
                    llSort.isVisible = isChecked
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
            }

            with(viewModel) {
                lifecycleScope.launchWhenStarted {
                    searchFlow.collect { uiList ->
                        uiList?.let {
                            adapter?.submitData(PagingData.from(it))
                        }
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
            }
        }

    }

    private fun addSearchTag(query: String?) {
        if (query == null) {
            with(viewBinding) {
                clTags.children.filter { it != clFlow && it.tag == "query" }.forEach {
                    clTags.removeView(it)
                }
            }
            return
        }
        val tag =
            MaterialButton(ContextThemeWrapper(requireContext(), io.yasa.ui.R.style.Tag)).apply {
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

    private fun addSortTag(sort: Pair<String, String>? = null) {
        if (sort == null) {
            with(viewBinding) {
                clTags.children.filter { it != clFlow && it.tag == "sort" }.forEach {
                    clTags.removeView(it)
                }
            }
            return
        }
        val tag =
            MaterialButton(ContextThemeWrapper(requireContext(), io.yasa.ui.R.style.Tag)).apply {
                id = View.generateViewId()
                text = "Search: $sort"
                tag = "query"
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