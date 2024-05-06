package com.jsoft.dictionaryapp.domain.model

data class WordItem(
    val word: String,
    val meanings: List<Meaning>,
    val phonetic: String
)