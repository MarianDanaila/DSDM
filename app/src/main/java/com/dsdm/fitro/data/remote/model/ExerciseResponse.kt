package com.dsdm.fitro.data.remote.model

import com.google.gson.annotations.SerializedName

data class ExerciseResponse(
    val id: Int,
    val category: Category?,
    val muscles: List<Muscle>?,
    val translations: List<Translation>?
) {
    data class Category(val name: String)
    data class Muscle(
        @SerializedName("name_en") val nameEn: String
    )
    data class Translation(
        val name: String?,
        val language: Int,
        val description: String?
    )

    fun getEnglishName(): String? =
        translations?.firstOrNull { it.language == 2 }?.name?.takeIf { it.isNotBlank() }

    fun getEnglishDescription(): String? =
        translations?.firstOrNull { it.language == 2 }?.description
}
