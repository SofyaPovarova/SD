package visitors

import main.kotlin.tokenizer.*
import main.kotlin.visitors.ParserVisitor
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*

internal class ParserVisitorTest {
    var tokenizer = Tokenizer()
    var parserVisitor = ParserVisitor()

    @Before
    fun `set up`() {
        tokenizer = Tokenizer()
        parserVisitor = ParserVisitor()
    }

    @Test
    fun `correct expression`() {
        val expression = "(23 + 10)"
        val tokens = tokenizer.tokenize(expression)
        val rpnTokens = parserVisitor.visit(tokens)

        val expectedTokens = arrayListOf(NumberToken(23), NumberToken(10), PlusToken)

        kotlin.test.assertEquals(expectedTokens, rpnTokens)
    }

    @Test
    fun `extra left brace`() {
        val expression = "(23 (+ 10)"
        val tokens = tokenizer.tokenize(expression)

        val exception = assertThrows(Exception::class.java) {
            parserVisitor.visit(tokens)
        }

        kotlin.test.assertEquals("The input expression contains syntax error", exception.message)
    }

    @Test
    fun `extra right brace`() {
        val expression = "(23 + 10))"
        val tokens = tokenizer.tokenize(expression)

        val exception = assertThrows(Exception::class.java) {
            parserVisitor.visit(tokens)
        }

        kotlin.test.assertEquals("The input expression contains syntax error", exception.message)
    }

}