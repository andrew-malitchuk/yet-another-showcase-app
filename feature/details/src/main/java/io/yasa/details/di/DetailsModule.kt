package io.yasa.details.di

import io.yasa.details.DetailsViewModel
import io.yasa.di.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val detailsModule = Kodein.Module("detailsModule") {

    bindViewModel<DetailsViewModel>() with provider {
        DetailsViewModel(instance())
    }
}