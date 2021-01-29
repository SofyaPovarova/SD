package main.kotlin.tokenizer

interface TokenizerState {
    fun handle(c: Char): Pair<TokenizerState, List<Token>>
    fun handleEnd(): Pair<TokenizerState, List<Token>> {
        if (this is ErrorState) {
            throw Exception("The input string contains symbols that can't be parsed")
        }
        return Pair(EndState(), emptyList())
    }

}

class StartState : TokenizerState {
    override fun handle(c: Char): Pair<TokenizerState, List<Token>> {
        when (c) {
            in '0'..'9' -> return Pair(NumberState(c - '0'), emptyList())
            ' ' -> return Pair(StartState(), emptyList())
        }

        val token = when (c) {
            '(' -> LeftBraceToken
            ')' -> RightBraceToken
            '+' -> PlusToken
            '-' -> MinusToken
            '*' -> MulToken
            '/' -> DivToken
            else -> null
        } ?: return Pair(ErrorState(), emptyList())

        return Pair(StartState(), listOf(token))
    }
}

class NumberState(private val n: Int) : TokenizerState {
    override fun handle(c: Char): Pair<TokenizerState, List<Token>> {
        if (c in '0'..'9' && n == 0) {
            throw Exception("The input string contains number that starts from 0")
        }
        if (c in '0'..'9') {
            return Pair(NumberState(n * 10 + (c - '0')), emptyList())
        }
        val (state, tokenList) = StartState().handle(c)
        val resList: MutableList<Token> = mutableListOf(NumberToken(n))
        resList.addAll(tokenList)

        return Pair(state, resList)
    }

    override fun handleEnd(): Pair<TokenizerState, List<Token>> = Pair(EndState(), listOf(NumberToken(n)))
}

class ErrorState : TokenizerState {
    override fun handle(c: Char): Pair<TokenizerState, List<Token>> {
        throw Exception("The input string contains symbols that can't be parsed")
    }
}

class EndState : TokenizerState {
    override fun handle(c: Char): Pair<TokenizerState, List<Token>> {
        throw Exception("No characters could be processed in EOF state")
    }
}