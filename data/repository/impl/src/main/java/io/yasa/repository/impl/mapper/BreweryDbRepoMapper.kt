package io.yasa.repository.impl.mapper

import io.yasa.common.data.mapper.DbRepoMapper
import io.yasa.database.model.BreweryDbModel
import io.yasa.repository.model.BreweryRepoModel

class BreweryDbRepoMapper : DbRepoMapper<BreweryDbModel, BreweryRepoModel>() {

    override fun mapTo(from: BreweryDbModel) = BreweryRepoModel(
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
        from.websiteUrl,
    )

    override fun mapFrom(to: BreweryRepoModel) = BreweryDbModel(
        to.id ?: "",
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