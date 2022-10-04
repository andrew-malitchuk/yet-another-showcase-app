package io.yasa.network.model

import com.squareup.moshi.Json

//@JsonClass(generateAdapter = true)
data class BreweryNetModel(
    @field:Json(name = "id") var id: String? = null,
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "brewery_type") var breweryType: String? = null,
    @field:Json(name = "street") var street: String? = null,
    @field:Json(name = "address_2") var address2: String? = null,
    @field:Json(name = "address_3") var address3: String? = null,
    @field:Json(name = "city") var city: String? = null,
    @field:Json(name = "state") var state: String? = null,
    @field:Json(name = "county_province") var countyProvince: String? = null,
    @field:Json(name = "postal_code") var postalCode: String? = null,
    @field:Json(name = "country") var country: String? = null,
    @field:Json(name = "longitude") var longitude: String? = null,
    @field:Json(name = "latitude") var latitude: String? = null,
    @field:Json(name = "phone") var phone: String? = null,
    @field:Json(name = "website_url") var websiteUrl: String? = null,
    @field:Json(name = "updated_at") var updatedAt: String? = null,
    @field:Json(name = "created_at") var createdAt: String? = null
)