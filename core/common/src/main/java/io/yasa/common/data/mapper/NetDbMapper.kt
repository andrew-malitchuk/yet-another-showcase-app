package io.yasa.common.data.mapper

import io.yasa.common.data.model.DbModel
import io.yasa.common.data.model.NetModel

abstract class NetDbMapper<From : NetModel, To : DbModel> : BaseMapper<NetModel, DbModel>()