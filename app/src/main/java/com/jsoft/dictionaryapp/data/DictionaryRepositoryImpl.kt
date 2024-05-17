package com.jsoft.dictionaryapp.data

import android.app.Application
import com.jsoft.dictionaryapp.R
import com.jsoft.dictionaryapp.data.api.DictionaryApi
import com.jsoft.dictionaryapp.data.mapper.toWordItem
import com.jsoft.dictionaryapp.domain.model.WordItem
import com.jsoft.dictionaryapp.domain.repository.DictionaryRepository
import com.jsoft.dictionaryapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject



class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val application: Application
) : DictionaryRepository {
    override suspend fun getWord(word: String): Flow<Result<WordItem>> {
        return flow {
            emit(Result.Loading(true))
            val remoteWordResultDto = try {
                dictionaryApi.getWordResult(word)
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.can_t_get_result)))
                emit(Result.Loading(false))
                return@flow

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.can_t_get_result)))
                emit(Result.Loading(false))
                return@flow

            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.can_t_get_result)))
                emit(Result.Loading(false))
                return@flow
            }
            remoteWordResultDto?.let { wordResultDto ->
                wordResultDto[0]?.let { wordItemDto ->
                    emit(Result.Success(wordItemDto.toWordItem()))
                    emit(Result.Loading(false))
                    return@flow
                }
            }

            emit(Result.Loading(false))
        }
    }
}
