package io.yasa.common.data.mapper

import io.yasa.common.data.model.BaseModel

abstract class BaseMapper<From : BaseModel, To : BaseModel> {

    abstract fun mapTo(from: From): To

    abstract fun mapFrom(to: To): From

}