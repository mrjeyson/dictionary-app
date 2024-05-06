package com.jsoft.dictionaryapp.presentation.state

import com.jsoft.domain.model.WordItem

data class MainState(
    val isLoading: Boolean = false,
    val searchWord: String = "",

    val wordItem: WordItem? = null
)
