package io.yasa.common.data.mapper

import io.yasa.common.data.model.DomainModel
import io.yasa.common.data.model.UiModel

abstract class DomainUiMapper<From : DomainModel, To : UiModel> : BaseMapper<From, To>()