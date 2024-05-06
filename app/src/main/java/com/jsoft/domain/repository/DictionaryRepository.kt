package com.jsoft.domain.repository

import com.jsoft.domain.model.WordItem
import com.jsoft.util.Result
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {

    suspend fun getWord(word: String): Flow<Result<WordItem>>
}