package com.app.android_pixabay.domain.repository

import com.app.android_pixabay.commons.util.ConstantTest.Companion.HITSCOMMENT
import com.app.android_pixabay.commons.util.ConstantTest.Companion.HITSLIKES
import com.app.android_pixabay.commons.util.ConstantTest.Companion.HITS_ID
import com.app.android_pixabay.commons.util.ConstantTest.Companion.HITS_TAG
import com.app.android_pixabay.commons.util.ConstantTest.Companion.HITS_USER
import com.app.android_pixabay.commons.util.ConstantTest.Companion.PREVIEW_URL
import com.app.android_pixabay.commons.util.SequenceList
import com.app.android_pixabay.domain.entities.HitsListGenerator.getEmptyHitsListData
import com.app.android_pixabay.domain.entities.HitsListGenerator.getSuccessHitsListData
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import retrofit2.Response

@RunWith(JUnit4::class)
class PixabayRepositoryTest {

    private val pixabayRepository : PixabayRepository = mock()

    @Test
    fun verifyApiModelToEntityModelMustReturnSameValues() {

        val response = Response.success(getSuccessHitsListData())

        runBlocking {
            `when`(pixabayRepository.fetchPixabayData(anyString(), anyInt())).thenAnswer(SequenceList(listOf(response)))
        }

        val expectedResult = Response.success(getSuccessHitsListData())

        assertEquals(expectedResult.body(), response.body())

        assertEquals(1, response.body()?.hits?.size)
        assertEquals(HITS_ID, response.body()?.hits?.get(0)?.id)
        assertTrue(response.body()?.hits?.get(0)?.user == HITS_USER)
        assertTrue(response.body()?.hits?.get(0)?.tags == HITS_TAG)
        assertFalse(response.body()?.hits?.get(0)?.previewURL == PREVIEW_URL)
        assertFalse(response.body()?.hits?.get(0)?.likes == HITSLIKES)
        assertTrue(response.body()?.hits?.get(0)?.comments == HITSCOMMENT)
    }

    @Test
    fun verifyResultWhenRepoMockReturnEmptyState() {

        val response = Response.success(getEmptyHitsListData())

        runBlocking {
            `when`(pixabayRepository.fetchPixabayData(anyString(), anyInt())).thenAnswer(SequenceList(listOf(response)))
        }

        val expectedResult = Response.success(getEmptyHitsListData())

        assertEquals(expectedResult.body(), response.body())

        assertEquals(0, response.body()?.hits?.size)
        assertNotNull(null,response.body()?.hits)
        response.body()?.hits?.isNullOrEmpty()?.let { assertTrue(it) }
        response.body()?.hits?.isNotEmpty()?.let { assertFalse(it) }
    }
}