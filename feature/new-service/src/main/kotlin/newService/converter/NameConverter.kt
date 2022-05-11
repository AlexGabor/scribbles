package newService.converter

import java.util.*

fun String.kebabToCamelCase(): String = this.replace(Regex("-.")) {
    it.value.replace("-", "").uppercase(Locale.US)
}