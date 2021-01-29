package main.kotlin.visitors

import main.kotlin.tokenizer.Token

class PrintVisitor: TokenVisitor<Unit> {
    override fun visit(token: Token) {
        println(token)
    }

    override fun visit(tokens: List<Token>) {
        tokens.forEach { it.accept(this) }
    }
}