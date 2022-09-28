package io.yasa.platform

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import io.yasa.platform.extension.fragmentKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.subKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class BaseFragment : Fragment(),
    KodeinAware {
    override val kodein: Kodein by subKodein(fragmentKodein()) {
        bind<LifecycleOwner>(overrides = true) with provider { this@BaseFragment }
    }
}