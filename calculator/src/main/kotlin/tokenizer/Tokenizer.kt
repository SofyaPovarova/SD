package main.kotlin.tokenizer

class Tokenizer {
    private var state: TokenizerState = StartState()
    private val tokens: MutableList<Token> = mutableListOf()

    fun tokenize(s: String): MutableList<Token> {
        s.forEach { c ->
            val (state, token) = state.handle(c)
            this.state = state
            tokens.addAll(token)
        }
        val (state, token) = state.handleEnd()
        tokens.addAll(token)

        return tokens
    }

}