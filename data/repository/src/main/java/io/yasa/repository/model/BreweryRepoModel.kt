package io.yasa.repository.model

import io.yasa.common.data.model.RepoModel

data class BreweryRepoModel(
    var id: String? = null,
    var name: String? = null,
    var breweryType: String? = null,
    var street: String? = null,
    var address2: String? = null,
    var address3: String? = null,
    var city: String? = null,
    var state: String? = null,
    var countyProvince: String? = null,
    var postalCode: String? = null,
    var country: String? = null,
    var longitude: String? = null,
    var latitude: String? = null,
    var phone: String? = null,
    var websiteUrl: String? = null,
) : RepoModel
