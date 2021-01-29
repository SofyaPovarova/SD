package main.kotlin.visitors

import main.kotlin.tokenizer.*
import java.util.*

class ParserVisitor: TokenVisitor<List<Token>> {
    private val stack = Stack<Token>()
    private val tokens: MutableList<Token> = mutableListOf()

    override fun visit(token: Token) {
        when(token) {
            is NumberToken -> tokens.add(token)
            is OperationToken -> visitOperatorToken(token)
            is LeftBraceToken -> stack.push(token)
            is RightBraceToken -> visitRightBrace()
        }
    }

    private fun visitRightBrace() {
        while (stack.isNotEmpty() && stack.peek() != LeftBraceToken) {
            tokens.add(stack.pop())
        }
        if (stack.isNotEmpty() && stack.peek() == LeftBraceToken) {
            stack.pop()
        } else {
            throw Exception("The input expression contains syntax error")
        }
    }

    private fun visitOperatorToken(token: OperationToken) {
        val priority = token.getPriority()

        while (!stack.empty() && stack.peek().getPriority() >= priority) {
            tokens.add(stack.pop())
        }

        stack.push(token)
    }

    override fun visit(tokens: List<Token>): List<Token> {
        tokens.forEach { it.accept(this) }
        if (!stack.contains(LeftBraceToken) && !stack.contains(RightBraceToken)) {
            while (stack.isNotEmpty()) {
                this.tokens.add(stack.pop())
            }

            return this.tokens
        }

        throw Exception("The input expression contains syntax error")
    }

}