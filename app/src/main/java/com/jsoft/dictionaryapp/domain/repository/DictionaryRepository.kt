package com.jsoft.dictionaryapp.domain.repository

import com.jsoft.dictionaryapp.domain.model.WordItem
import com.jsoft.dictionaryapp.util.Result
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {

    suspend fun getWord(word: String): Flow<Result<WordItem>>
}