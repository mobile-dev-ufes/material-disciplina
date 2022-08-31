
fun maiorDeIdade(idade: Int): Boolean {
    return if (idade >= 18) true else false
}

fun contaX(str: String): Int {
    return str.count { it == 'x' }
}

fun bmi(m: Float, h: Float): Float {
    if (h <= 0) throw ArithmeticException()
    return (m/h*h)
}