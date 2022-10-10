package io.yasa.domain.di

import io.yasa.domain.datasource.BreweriesRemoteDataSource
import io.yasa.domain.datasource.BreweriesRemoteDataSourceImp
import io.yasa.domain.usecase.BreweriesUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val domainModule = Kodein.Module("domainModule") {
    bind<BreweriesUseCase>() with provider {
        BreweriesUseCase(
            instance()
        )
    }
    bind<BreweriesRemoteDataSource>() with provider {
        BreweriesRemoteDataSourceImp(
            instance()
        )
    }
}
