package main.kotlin.visitors

import main.kotlin.tokenizer.Token

interface TokenVisitor<T> {
    fun visit(token: Token)
    fun visit(tokens: List<Token>): T
}