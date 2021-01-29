package visitors

import main.kotlin.tokenizer.NumberToken
import main.kotlin.tokenizer.PlusToken
import main.kotlin.tokenizer.Tokenizer
import main.kotlin.visitors.CalcVisitor
import main.kotlin.visitors.ParserVisitor
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class CalcVisitorTest {
    var tokenizer = Tokenizer()
    var parserVisitor = ParserVisitor()
    var calcVisitor = CalcVisitor()

    @Before
    fun `set up`() {
        tokenizer = Tokenizer()
        parserVisitor = ParserVisitor()
        calcVisitor = CalcVisitor()
    }

    @Test
    fun `correct expression`() {
        val expression = "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"
        val tokens = tokenizer.tokenize(expression)
        val rpnTokens = parserVisitor.visit(tokens)
        val calc = calcVisitor.visit(rpnTokens)

        kotlin.test.assertEquals(1279.0, calc)
    }

    @Test
    fun `extra operator at the end`() {
        val expression = "(23 + 10) - 4 *"
        val tokens = tokenizer.tokenize(expression)
        val rpnTokens = parserVisitor.visit(tokens)

        val exception = assertThrows(Exception::class.java) {
            calcVisitor.visit(rpnTokens)
        }

        kotlin.test.assertEquals("The input expression contains syntax error", exception.message)
    }

    @Test
    fun `extra operator in the middle`() {
        val expression = "(23 +/ 10) - 4"
        val tokens = tokenizer.tokenize(expression)
        val rpnTokens = parserVisitor.visit(tokens)

        val exception = assertThrows(Exception::class.java) {
            calcVisitor.visit(rpnTokens)
        }

        kotlin.test.assertEquals("The input expression contains syntax error", exception.message)
    }

    @Test
    fun `division by 0`() {
        val expression = "(23 + 1 / 0) - 4"
        val tokens = tokenizer.tokenize(expression)
        val rpnTokens = parserVisitor.visit(tokens)

        val exception = assertThrows(Exception::class.java) {
            calcVisitor.visit(rpnTokens)
        }

        kotlin.test.assertEquals("The input expression contains division by zero", exception.message)
    }

}