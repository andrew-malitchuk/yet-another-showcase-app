package io.yasa.details.data.mapper

import io.yasa.common.data.mapper.DomainUiMapper
import io.yasa.details.data.model.BreweryUiModel
import io.yasa.domain.model.BreweryDomainModel

class BreweryDomainUiMapper : DomainUiMapper<BreweryDomainModel, BreweryUiModel>() {

    override fun mapFrom(to: BreweryUiModel) = BreweryDomainModel(
        to.id,
        to.name,
        to.breweryType,
        to.street,
        to.address2,
        to.address3,
        to.city,
        to.state,
        to.countyProvince,
        to.postalCode,
        to.country,
        to.longitude,
        to.latitude,
        to.phone,
        to.websiteUrl
    )

    override fun mapTo(from: BreweryDomainModel) = BreweryUiModel(
        from.id,
        from.name,
        from.breweryType,
        from.street,
        from.address2,
        from.address3,
        from.city,
        from.state,
        from.countyProvince,
        from.postalCode,
        from.country,
        from.longitude,
        from.latitude,
        from.phone,
        from.websiteUrl
    )

}