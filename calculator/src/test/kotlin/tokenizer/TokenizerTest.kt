package tokenizer

import main.kotlin.tokenizer.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import java.io.IOException
import java.nio.file.Files
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows

internal class TokenizerTest {
    var tokenizer = Tokenizer()

    @Before
    fun `set up`() {
        tokenizer = Tokenizer()
    }

    @Test
    fun `correct expression`() {
        val expression = "(23 + 10)"
        val tokens = tokenizer.tokenize(expression)
        val expectedTokens = arrayListOf(LeftBraceToken, NumberToken(23), PlusToken, NumberToken(10), RightBraceToken)

        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `incorrect symbols`() {
        val expression = "(23$+ 10)"

        val exception = Assertions.assertThrows(Exception::class.java) {
            tokenizer.tokenize(expression)
        }

        assertEquals(exception.message, "The input string contains symbols that can't be parsed")
    }

    @Test
    fun `incorrect symbols at the end`() {
        val expression = "(23+ 10)W"

        val exception = Assertions.assertThrows(Exception::class.java) {
            tokenizer.tokenize(expression)
        }

        assertEquals(exception.message, "The input string contains symbols that can't be parsed")
    }

    @Test
    fun `number starts from 0`() {
        val expression = "(23+ 010)"

        val exception = Assertions.assertThrows(Exception::class.java) {
            tokenizer.tokenize(expression)
        }

        assertEquals(exception.message, "The input string contains number that starts from 0")
    }
}