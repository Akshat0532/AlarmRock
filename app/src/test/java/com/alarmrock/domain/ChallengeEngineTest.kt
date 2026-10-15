package com.alarmrock.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ChallengeEngineTest {
    private val engine = ChallengeEngine()

    @Test
    fun generateMathQuestionProducesValidAnswer() {
        val question = engine.generateMathQuestion(ChallengeDifficulty.EASY)
        val expected = when (question.operator) {
            '+' -> question.left + question.right
            '-' -> question.left - question.right
            '*' -> question.left * question.right
            '/' -> question.left / question.right
            else -> question.answer
        }
        assertEquals(expected, question.answer)
    }

    @Test
    fun validateMathAnswerHandlesCorrectAndIncorrectValues() {
        val question = MathQuestion(7, 3, '+', 10)
        assertTrue(engine.validateMathAnswer(question, 10))
        assertFalse(engine.validateMathAnswer(question, 11))
    }

    @Test
    fun sequenceLengthMatchesRequestedCount() {
        val result = engine.nextChallengeSequence(listOf(ChallengeKind.MATH, ChallengeKind.QR), 5)
        assertEquals(5, result.size)
    }

    @Test
    fun validationForMemorySequenceWorks() {
        val expected = listOf(3, 1, 4, 2)
        assertTrue(engine.validateMemorySequence(expected, listOf(3, 1, 4, 2)))
        assertFalse(engine.validateMemorySequence(expected, listOf(3, 1, 2, 4)))
    }
}
