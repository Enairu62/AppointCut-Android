package DataModels

/**
 * @property authStatus Type of user or invalid input
 * @property firstName First name of the user
 * @property lastName Last name of the user
 * @property token access token of bound to the user
 */
data class User (
    val authStatus: AuthStatus,
    val firstName: String?,
    val lastName: String?,
    val token: String?
){
    companion object{
        /**
         * The type of user that logged in
         */
        enum class AuthStatus{
            DESK,BARBER,CUSTOMER,EMAIL,PASSWORD
        }
    }
}

