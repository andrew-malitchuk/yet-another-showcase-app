package io.yasa.common.data.mapper

import io.yasa.common.data.model.NetModel
import io.yasa.common.data.model.RepoModel

abstract class NetRepoMapper<From : NetModel, To : RepoModel> : BaseMapper<From, To>()