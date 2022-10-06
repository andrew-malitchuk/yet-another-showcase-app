package io.yasa.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.yasa.common.data.model.DbModel

@Entity(tableName = "Breweries")
data class BreweryDbModel(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "brewery_type") var breweryType: String? = null,
    @ColumnInfo(name = "street") var street: String? = null,
    @ColumnInfo(name = "address_2") var address2: String? = null,
    @ColumnInfo(name = "address_3") var address3: String? = null,
    @ColumnInfo(name = "city") var city: String? = null,
    @ColumnInfo(name = "state") var state: String? = null,
    @ColumnInfo(name = "county_province") var countyProvince: String? = null,
    @ColumnInfo(name = "postal_code") var postalCode: String? = null,
    @ColumnInfo(name = "country") var country: String? = null,
    @ColumnInfo(name = "longitude") var longitude: String? = null,
    @ColumnInfo(name = "latitude") var latitude: String? = null,
    @ColumnInfo(name = "phone") var phone: String? = null,
    @ColumnInfo(name = "website_url") var websiteUrl: String? = null,
    @ColumnInfo(name = "updated_at") var updatedAt: String? = null,
    @ColumnInfo(name = "created_at") var createdAt: String? = null
) : DbModel