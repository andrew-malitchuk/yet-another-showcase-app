package io.yasa.domain.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.yasa.domain.model.BreweryDomainModel
import io.yasa.domain.usecase.BreweriesUseCase
import logcat.logcat

private const val FIRST_PAGE = 1;

class BreweriesPagingSource(
    private val useCase: BreweriesUseCase,
    private val sort: String? = null,
    private val searchQuery: String? = null
) : PagingSource<Int, BreweryDomainModel>() {

    override fun getRefreshKey(state: PagingState<Int, BreweryDomainModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BreweryDomainModel> {
        return try {
            val page = params.key ?: FIRST_PAGE
//            val response = useCase.getBreweries(page)
//            val response = useCase.getAndSaveBreweries(page)
            val response = useCase.getAndSaveBreweries(page,sort,)
            logcat { response.toString() }
            LoadResult.Page(
                data = response,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}