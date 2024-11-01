
fun maiorDeIdade(idade: Int): Boolean {
    return if (idade >= 18) true else false
}

fun contaX(str: String): Int {
    return str.count { it == 'x' }
}

fun bmi(m: Float, h: Float): Float {
    print("aaa")
    if (h <= 0) throw ArithmeticException()
    return (m/h*h)
}

fun main() {

    val x = bmi(100f, 1.92f)
    println(x)
}

