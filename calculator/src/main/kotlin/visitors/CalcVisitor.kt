package main.kotlin.visitors

import main.kotlin.tokenizer.*
import java.util.*

class CalcVisitor: TokenVisitor<Double> {
    private val evaluation = Stack<Double>()

    override fun visit(token: Token) {
        when (token) {
            is NumberToken -> evaluation.push(token.n.toDouble())
            is OperationToken -> visitOperatorToken(token)
        }
    }

    private fun visitOperatorToken(token: OperationToken) {
        if (evaluation.size < 2) {
            throw Exception("The input expression contains syntax error")
        }
        val a = evaluation.pop()
        val b = evaluation.pop()

        if (token is DivToken && a == 0.0) {
            throw Exception("The input expression contains division by zero")
        }

        evaluation.push(token.applyOperator(b.toDouble(), a.toDouble()))
    }

    override fun visit(tokens: List<Token>): Double {
        tokens.forEach { it.accept(this) }
        return evaluation.peek()
    }
}