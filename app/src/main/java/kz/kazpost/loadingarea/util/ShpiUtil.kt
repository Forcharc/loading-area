package kz.kazpost.loadingarea.util

object ShpiUtil {
    fun isLabel(shpi: String): Boolean {
        return shpi.length == 16 && shpi.startsWith('G')
    }

    fun isMail(shpi: String): Boolean {
        return shpi.length == 13 && shpi.endsWith("KZ")
    }

    fun isBInvoice(shpi: String): Boolean {
        return shpi.length == 16 && shpi.startsWith('B')
    }

    fun isMjd(shpi: String): Boolean {
        return shpi.length == 29
    }
}