package newservice.usecase

fun interface IsValidServiceName {
    operator fun invoke(name: String): Boolean
}

class IsValidServiceNameUseCase : IsValidServiceName {
    override fun invoke(name: String): Boolean {
        return name.matches(Regex("(:[a-zA-Z-]+)+"))
    }
}