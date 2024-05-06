package com.jsoft.dictionaryapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsoft.dictionaryapp.presentation.event.MainUiEvents
import com.jsoft.dictionaryapp.presentation.state.MainState
import com.jsoft.dictionaryapp.domain.repository.DictionaryRepository
import com.jsoft.dictionaryapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    private var searchJob: Job? = null

    init {
        _mainState.update {
            it.copy(searchWord = "Word")
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            loadWordResult()
        }
    }

    fun onEvent(mainUiEvent: MainUiEvents) {
        when (mainUiEvent) {
            MainUiEvents.OnSearchClick -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    loadWordResult()
                }
            }

            is MainUiEvents.OnSearchWordChange -> {
                _mainState.update {
                    it.copy(searchWord = mainUiEvent.newWord.lowercase())
                }
            }
        }
    }

    private fun loadWordResult() {
        viewModelScope.launch {
            dictionaryRepository.getWord(
                mainState.value.searchWord
            ).collect { result ->
                when (result) {
                    is Result.Error -> Unit

                    is Result.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Result.Success -> {
                        result.data?.let { wordItem ->
                            _mainState.update {
                                it.copy(
                                    wordItem = wordItem
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}