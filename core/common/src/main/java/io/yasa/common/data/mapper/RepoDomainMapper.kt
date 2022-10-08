package io.yasa.common.data.mapper

import io.yasa.common.data.model.DomainModel
import io.yasa.common.data.model.RepoModel

abstract class RepoDomainMapper<From : RepoModel, To : DomainModel> : BaseMapper<From, To>()