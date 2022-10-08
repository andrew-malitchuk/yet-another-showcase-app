package io.yasa.list.di

import androidx.lifecycle.ViewModelProvider
import io.yasa.di.ViewModelFactory
import io.yasa.di.bindViewModel
import io.yasa.list.ListViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val listModule = Kodein.Module("listModule") {
//    bindViewModel<ListViewModel>() with provider {
//        ListViewModel(
//            instance()
//        )
//    }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

    bindViewModel<ListViewModel>() with provider {
        ListViewModel(instance())
    }
}