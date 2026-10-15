package com.alarmrock.domain

import kotlin.math.abs
import kotlin.random.Random

enum class ChallengeDifficulty { EASY, MEDIUM, HARD }
enum class ChallengeKind { MATH, EXERCISE, QR, MEMORY }

data class MathQuestion(
    val left: Int,
    val right: Int,
    val operator: Char,
    val answer: Int
)

class ChallengeEngine {
    fun generateMathQuestion(difficulty: ChallengeDifficulty): MathQuestion {
        return when (difficulty) {
            ChallengeDifficulty.EASY -> {
                val left = Random.nextInt(1, 12)
                val right = Random.nextInt(1, 12)
                val op = listOf('+', '-').random()
                val answer = if (op == '+') left + right else left - right
                MathQuestion(left, right, op, answer)
            }
            ChallengeDifficulty.MEDIUM -> {
                val left = Random.nextInt(5, 40)
                val right = Random.nextInt(2, 15)
                val op = listOf('+', '-', '*', '/').random()
                if (op == '/') {
                    val divisor = right
                    val quotient = Random.nextInt(2, 10)
                    val dividend = divisor * quotient
                    MathQuestion(dividend, divisor, '/', quotient)
                } else {
                    val answer = when (op) {
                        '+' -> left + right
                        '-' -> left - right
                        '*' -> left * right
                        else -> left + right
                    }
                    MathQuestion(left, right, op, answer)
                }
            }
            ChallengeDifficulty.HARD -> {
                val a = Random.nextInt(3, 29)
                val b = Random.nextInt(2, 18)
                val c = Random.nextInt(1, 12)
                val op = listOf('+', '-', '*', '/').random()
                var answer = 0
                val question = if (op == '/') {
                    val divisor = b.coerceAtLeast(1)
                    val quotient = Random.nextInt(2, 12)
                    val dividend = divisor * quotient + Random.nextInt(0, 3)
                    answer = quotient
                    MathQuestion(dividend, divisor, '/', answer)
                } else {
                    when (op) {
                        '+' -> { answer = a + b + c; MathQuestion(a, b, '+', answer) }
                        '-' -> { answer = a - b; MathQuestion(a, b, '-', answer) }
                        '*' -> { answer = a * b; MathQuestion(a, b, '*', answer) }
                        else -> { answer = a + b; MathQuestion(a, b, '+', answer) }
                    }
                }
                question
            }
        }
    }

    fun validateMathAnswer(question: MathQuestion, answer: Int): Boolean {
        return question.answer == answer
    }

    fun nextChallengeSequence(challengeKinds: List<ChallengeKind>, count: Int): List<ChallengeKind> {
        val source = if (challengeKinds.isEmpty()) listOf(ChallengeKind.MATH) else challengeKinds
        return List(count.coerceAtLeast(1)) { source[it % source.size] }
    }

    fun validateMemorySequence(expected: List<Int>, entered: List<Int>): Boolean {
        return expected == entered
    }

    fun buildStepGoal(targetSteps: Int): Int = abs(targetSteps)
}
