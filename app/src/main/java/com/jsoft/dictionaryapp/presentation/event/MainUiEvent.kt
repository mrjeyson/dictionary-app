package com.jsoft.dictionaryapp.presentation.event

sealed class MainUiEvents {

    data class OnSearchWordChange(val newWord: String) : MainUiEvents()
    object OnSearchClick : MainUiEvents()
}