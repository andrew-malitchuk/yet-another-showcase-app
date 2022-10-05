package io.yasa.models.data.model

import io.yasa.common.data.model.UiModel

data class BreweryUiModel(
    var id: String? = null,
    var name: String? = null,
    // TODO: add enum
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
) : UiModel