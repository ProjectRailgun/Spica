package co.bangumi.common.model

data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val email_confirmed: Boolean,
    val level: Int
)