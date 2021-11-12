package DataModels

/**
 * @property firstName First name of the user
 * @property lastName Last name of the user
 * @property token access token of bound to the user
 * @property type type of user
 */
data class User (
    val firstName: String,
    val lastName: String,
    val token: String,
    val type: UserType
)

/**
 * The type of user that logged in
 */
enum class UserType{
    BARBER, CUSTOMER
}