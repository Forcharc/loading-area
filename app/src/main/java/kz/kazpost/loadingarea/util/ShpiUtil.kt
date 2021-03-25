package kz.kazpost.loadingarea.util

object ShpiUtil {
    fun isLabel(shpi: String): Boolean {
        return shpi.length == 16 && shpi.startsWith('G')
    }

    fun isMail(shpi: String): Boolean {
        return shpi.length == 13 && shpi[0].isLetter() && shpi[1].isLetter() && shpi[12].isLetter() && shpi[11].isLetter()
    }

    fun isBInvoice(shpi: String): Boolean {
        return shpi.length == 16 && shpi.startsWith('B')
    }

    fun isSInvoice(shpi: String): Boolean {
        return shpi.length == 16 && shpi.startsWith('S')
    }

    fun isMjd(shpi: String): Boolean {
        return shpi.length == 29
    }
}