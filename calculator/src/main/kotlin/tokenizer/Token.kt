package main.kotlin.tokenizer

import main.kotlin.visitors.TokenVisitor

sealed class Token {
    fun accept(visitor: TokenVisitor<*>) = visitor.visit(this)
    open fun getPriority(): Int = 0
}

data class NumberToken(val n: Int): Token() {
    override fun toString() = "NUMBER($n)"
}

object LeftBraceToken: Token() {
    override fun toString() = "LEFT"
}

object RightBraceToken: Token() {
    override fun toString() = "RIGHT"
}

sealed class OperationToken: Token() {
    open fun applyOperator(a: Double, b: Double): Double = a
}

object PlusToken: OperationToken() {
    override fun getPriority() = 1
    override fun toString() = "PLUS"
    override fun applyOperator(a: Double, b: Double): Double = a + b
}

object MinusToken: OperationToken() {
    override fun getPriority() = 1
    override fun toString() = "MINUS"
    override fun applyOperator(a: Double, b: Double): Double = a - b
}

object MulToken: OperationToken() {
    override fun getPriority() = 2
    override fun toString() = "MUL"
    override fun applyOperator(a: Double, b: Double): Double = a * b
}

object DivToken: OperationToken() {
    override fun getPriority() = 2
    override fun toString() = "DIV"
    override fun applyOperator(a: Double, b: Double): Double = a / b
}
