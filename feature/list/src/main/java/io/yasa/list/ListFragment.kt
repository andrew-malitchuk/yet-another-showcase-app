package io.yasa.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.yasa.list.databinding.FragmentListBinding
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.ui.viewbinding.viewBinding

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewBinding by viewBinding(FragmentListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            button.setOnClickListener {
                (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.DetailsFlow)
            }
        }
    }

}