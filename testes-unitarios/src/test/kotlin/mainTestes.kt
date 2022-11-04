import org.junit.jupiter.api.*

class mainTest {

    @Test // Anotação que informa ao JUnit que esse é um método de teste
    @DisplayName("Tesde maior de idade") // Anotação opcional para mudar o nome que aparece na apuração dos testes
    fun testMaiorDeIdade(){
        // Aqui vamos testar a função maiorDeIdade que implementamos na main.kt

        // Esse teste falha se a função não retornar true
        Assertions.assertTrue(maiorDeIdade(18))
        Assertions.assertTrue(maiorDeIdade(19))
        Assertions.assertTrue(maiorDeIdade(100))

        // Esse teste falha se a função não retornar false
        Assertions.assertFalse(maiorDeIdade(10))

        // Cria um bloco que dispara uma falha se algum das Assetions falhar. Não importa qual
        Assertions.assertAll(
            {Assertions.assertFalse(maiorDeIdade(10))},
            {Assertions.assertFalse(maiorDeIdade(2))},
            {Assertions.assertTrue(maiorDeIdade(100))},
        )
    }

    @Test
    @Disabled // Desabilita um teste para que a gente possa testar alguma parte do código que depende dele e a gente sabe vai falhar (por exemplo, pode ser que não tenha sido implementado ainda)
    fun testaAlgumaParteQueVaiFalhar(){
        // Dispara uma falha. É usado aqui apenas para simular
        fail("Falhou!")
    }

    @Test
    fun testaContaX(){
        // Fazer um teste que depende de alguma premissa, ou seja, algo tem que ser verdade ou falso para que
        // o teste continue. O teste principal só acontece se a premissa for verdadeira, caso contrário, ele falha
        Assumptions.assumeTrue(maiorDeIdade(18))
        Assertions.assertEquals(contaX("xxx"), 3)
    }

    @Test
    fun testaBMI(){
        // Testando se um método retorna uma excessão que estamos esperando
        assertThrows<ArithmeticException> { bmi(100f, 0f) }
    }
}