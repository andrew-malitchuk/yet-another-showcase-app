package io.yasa.repository.impl.mapper

import io.yasa.common.data.mapper.NetDbMapper
import io.yasa.database.model.BreweryDbModel
import io.yasa.network.model.BreweryNetModel

class BreweryNetDbMapper : NetDbMapper<BreweryNetModel, BreweryDbModel>() {

    override fun mapTo(from: BreweryNetModel) = BreweryDbModel(
        from.id?:"",
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
        from.websiteUrl,
    )

    override fun mapFrom(to: BreweryDbModel) = BreweryNetModel(
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
        to.websiteUrl,
    )

}