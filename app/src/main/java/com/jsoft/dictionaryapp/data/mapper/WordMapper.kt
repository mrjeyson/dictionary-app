package com.jsoft.dictionaryapp.data.mapper

import com.jsoft.dictionaryapp.data.dto.DefinitionDto
import com.jsoft.dictionaryapp.data.dto.MeaningDto
import com.jsoft.dictionaryapp.data.dto.WordItemDto
import com.jsoft.domain.model.Definition
import com.jsoft.domain.model.Meaning
import com.jsoft.domain.model.WordItem

fun WordItemDto.toWordItem() = WordItem(
    word = word ?: "",
    meanings = meanings?.map {
        it.toMeaning()
    } ?: emptyList(),
    phonetic = phonetic ?: ""

)

fun MeaningDto.toMeaning() = Meaning(
    definition = definitionDtoDefinition(definitions?.get(0)),
    partOfSpeech = partOfSpeech ?: ""
)

fun definitionDtoDefinition(
    definitionDto: DefinitionDto?
) = Definition(
    definition = definitionDto?.definition ?: "",
    example = definitionDto?.example ?: ""
)