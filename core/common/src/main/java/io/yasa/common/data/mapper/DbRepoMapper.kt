package io.yasa.common.data.mapper

import io.yasa.common.data.model.DbModel
import io.yasa.common.data.model.RepoModel

abstract class DbRepoMapper<From : DbModel, To : RepoModel> : BaseMapper<From, To>()