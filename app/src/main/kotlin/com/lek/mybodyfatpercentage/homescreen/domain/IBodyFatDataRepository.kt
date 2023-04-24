package com.lek.mybodyfatpercentage.homescreen.domain

import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatCalculationData
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData
import kotlinx.coroutines.flow.Flow

interface IBodyFatDataRepository {
    val bodyFatFlow: Flow<List<BodyFatData>>
    fun createReading(readingData: BodyFatCalculationData): BodyFatData
    fun getReadings(): List<BodyFatData>
    fun deleteReading(timeAdded: Long)
    fun deleteAllReadings()
    fun saveReading(reading: BodyFatData)
}