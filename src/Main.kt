import java.io.File

val vars = mutableMapOf<String, Int>()
val labels = mutableMapOf<String, Int>()
var asm = File("/Users/antoniomatijevic/Documents/Kotlin/ACSL Assembly/src/test.acsm").readLines()
var i = 0
var acc = 0

fun getVal(sp: List<String>, l: Int): Int {
    return if (sp[l-1] in vars) {
        vars[sp[l-1]]!!
    } else {
        sp[l-1].toInt()
    }
}

fun main() {
    asm.forEachIndexed { index, el ->
        val sp = el.split(" ")
        if (sp.size == 3 || sp.getOrNull(1) == "END") {
            labels[el.split(" ").first()] = index
        }
    }
    while (i < asm.size) {
        val sp = asm[i].split(" ")
        val l = sp.size
        if (l > 1) {
            if (sp[l-2] == "LOAD") {
                acc = getVal(sp, l)
            } else if (sp[l-2] == "DC") {
                vars[sp[0]] = sp[l-1].toInt()
            } else if (sp[l-2] == "ADD") {
                acc += getVal(sp, l)
            } else if (sp[l-2] == "MULT") {
                acc *= getVal(sp, l)
            } else if (sp[l-2] == "SUB") {
                acc -= getVal(sp, l)
            } else if (sp[l-2] == "DIV") {
                acc /= getVal(sp, l)
            } else if (sp[l-2] == "STORE") {
                vars[sp[l-1]] = acc
            } else if (sp[l-2] == "READ") {
                vars[sp[l-1]] = readln().toInt()
            } else if (sp[l-2] == "PRINT") {
                println(getVal(sp, l))
            } else if (sp[l-2] == "BG") {
                if (acc > 0) {
                    i = labels[sp[l-1]]!!-1
                }
            } else if (sp[l-2] == "BE") {
                if (acc == 0) {
                    i = labels[sp[l-1]]!!-1
                }
            } else if (sp[l-2] == "BL") {
                if (acc < 0) {
                    i = labels[sp[l-1]]!!-1
                }
            } else if (sp[l-2] == "BU") {
                i = labels[sp[l-1]]!!-1
            } else if (sp[l-1] == "END") {
                println("\nProgram ended with exit code: 0\n")
            }
        } else if (sp.getOrNull(0) == "END") {
            println("\nProgram ended with exit code: 0\n")
        }
        i++
    }
}