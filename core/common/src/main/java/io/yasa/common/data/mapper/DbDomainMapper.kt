package io.yasa.common.data.mapper

import io.yasa.common.data.model.DbModel
import io.yasa.common.data.model.DomainModel

abstract class DbDomainMapper<From : DbModel, To : DomainModel> : BaseMapper<From, To>()