package com.lek.mybodyfatpercentage.homescreen.data

import android.content.SharedPreferences
import com.lek.mybodyfatpercentage.homescreen.domain.IBodyFatDataRepository
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatCalculationData
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData
import com.lek.mybodyfatpercentage.homescreen.domain.model.bodyFatDataFromStringList
import com.lek.mybodyfatpercentage.onboarding.domain.Gender
import kotlin.math.pow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

private const val BODY_FAT_READING_KEY = "com.lek.mybodyfatpercentage.readings"

class BodyFatDataRepository(private val preference: SharedPreferences) : IBodyFatDataRepository {

    private var bodyFataDataList: MutableList<BodyFatData> = mutableListOf()

    private val mutableBodyFatDataListFlow: MutableSharedFlow<List<BodyFatData>> = MutableSharedFlow(replay = 1)

    private fun emitChanges() {
        bodyFataDataList.sortBy { it.date }
        bodyFataDataList.reverse()
        if (bodyFataDataList.size > 0) {
            bodyFataDataList.map { it.isFirst = false }
            val first = bodyFataDataList.first()
            first.isFirst = true
            bodyFataDataList.removeAt(0)
            bodyFataDataList.add(0, first)
        }
        val newList = mutableListOf<BodyFatData>()
        newList.addAll(bodyFataDataList)
        mutableBodyFatDataListFlow.tryEmit(newList)
    }

    init {
        loadBodyFatData()
    }

    private fun loadBodyFatData() {
        val readings = preference.getString(BODY_FAT_READING_KEY, "").orEmpty()
        bodyFataDataList = bodyFatDataFromStringList(readings).toMutableList()
        emitChanges()
    }

    override val bodyFatFlow: Flow<List<BodyFatData>>
        get() = mutableBodyFatDataListFlow

    override fun createReading(readingData: BodyFatCalculationData): BodyFatData {
        val sumOfSkinFolds =
            readingData.firstReading + readingData.secondReading + readingData.thirdRecording

        val bodyFatPercentage = when (readingData.gender) {
            Gender.FEMALE -> {
                getBodyFatPercentageForFemale(sumOfSkinFolds, readingData)
            }
            Gender.MALE -> {
                getBodyFatPercentageForMale(sumOfSkinFolds, readingData)
            }
            else -> {
                throw IllegalArgumentException("Unsupported Gender to calculate body fat %")
            }
        }

        return BodyFatData(
            date = System.currentTimeMillis(),
            reading = bodyFatPercentage,
            bodyWeightUsed = readingData.weight
        )
    }

    override fun saveReading(reading: BodyFatData) {
        bodyFataDataList.add(0, reading)
        saveChanges()
    }

    override fun getReadings(): List<BodyFatData> = bodyFataDataList

    override fun deleteReading(timeAdded: Long) {
        if (bodyFataDataList.any { it.date == timeAdded }) {
            bodyFataDataList.removeIf { it.date == timeAdded }
            saveChanges()
        }
    }

    private fun saveChanges() {
        emitChanges()
        preference.edit().putString(BODY_FAT_READING_KEY, bodyFataDataList.toString()).apply()
    }

    override fun deleteAllReadings() {
        bodyFataDataList = mutableListOf()
        saveChanges()
    }

    private fun getBodyFatPercentageForMale(
        sumOfSkinFolds: Double,
        readingData: BodyFatCalculationData
    ) = Math.round((495 / (1.10938 - (0.0008267 * sumOfSkinFolds) +
            (0.0000016 * sumOfSkinFolds.pow(2)) - 0.0002574 * readingData.age) - 450) * 100.0) / 100.0

    private fun getBodyFatPercentageForFemale(
        sumOfSkinFolds: Double,
        readingData: BodyFatCalculationData
    ) = Math.round((495 / (1.0994921 - (0.0009929 * sumOfSkinFolds) +
            (0.0000023 * sumOfSkinFolds.pow(2)) - 0.0001392 * readingData.age) - 450) * 100.0) / 100.0
}