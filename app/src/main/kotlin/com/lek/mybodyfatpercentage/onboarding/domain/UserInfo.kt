package com.lek.mybodyfatpercentage.onboarding.domain

data class UserInfo(
    val name: String = "",
    val age: Int? = null,
    val bodyWeight: Double? = null,
    val gender: Gender = Gender.UNKNOWN
) {
    override fun toString(): String = "$name, $age, $bodyWeight, ${gender.name}"
}

fun UserInfo?.isInvalid() = this == null ||
        name.isEmpty() || age == null || bodyWeight == null || gender == Gender.UNKNOWN

fun String.toUserInfo(): UserInfo {

    val dataSplit = split(",")

    return UserInfo(
        dataSplit.firstOrNull().orEmpty(),
        dataSplit.getOrNull(1)?.trim()?.toInt(),
        dataSplit.getOrNull(2)?.trim()?.toDouble(),
        dataSplit.getOrNull(3)?.trim().orEmpty().toGender()
    )
}

enum class Gender {
    MALE, FEMALE, UNKNOWN
}

enum class MeasurementIndex {
    FIRST, SECOND, THIRD
}

fun String.toGender(): Gender =
    Gender.values().firstOrNull { it.name == this } ?: Gender.UNKNOWN