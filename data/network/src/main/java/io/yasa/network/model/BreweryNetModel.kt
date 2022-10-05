package io.yasa.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreweryNetModel(
    @Json(name = "id") var id: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "brewery_type") var breweryType: String? = null,
    @Json(name = "street") var street: String? = null,
    @Json(name = "address_2") var address2: String? = null,
    @Json(name = "address_3") var address3: String? = null,
    @Json(name = "city") var city: String? = null,
    @Json(name = "state") var state: String? = null,
    @Json(name = "county_province") var countyProvince: String? = null,
    @Json(name = "postal_code") var postalCode: String? = null,
    @Json(name = "country") var country: String? = null,
    @Json(name = "longitude") var longitude: String? = null,
    @Json(name = "latitude") var latitude: String? = null,
    @Json(name = "phone") var phone: String? = null,
    @Json(name = "website_url") var websiteUrl: String? = null,
    @Json(name = "updated_at") var updatedAt: String? = null,
    @Json(name = "created_at") var createdAt: String? = null
)