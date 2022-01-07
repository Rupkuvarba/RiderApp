package com.app.eho.data.repository

import com.app.eho.data.remote.NetworkService
import com.app.eho.data.remote.request.LoginRequest
import com.mindorks.bootcamp.instagram.data.model.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class UserRepository @Inject constructor(
    private val networkService: NetworkService,
) {

    /*fun saveCurrentUser(user: User) {
        userPreferences.setUserId(user.id)
        userPreferences.setUserName(user.name)
        userPreferences.setUserEmail(user.email)
        userPreferences.setAccessToken(user.accessToken)
    }

    fun removeCurrentUser() {
        userPreferences.removeUserId()
        userPreferences.removeUserName()
        userPreferences.removeUserEmail()
        userPreferences.removeAccessToken()
    }

    fun getCurrentUser(): User? {

        val userId = userPreferences.getUserId()
        val userName = userPreferences.getUserName()
        val userEmail = userPreferences.getUserEmail()
        val accessToken = userPreferences.getAccessToken()

        return if (userId !== null && userName != null && userEmail != null && accessToken != null)
            User(userId, userName, userEmail, accessToken)
        else
            null
    }*/

    fun doUserLogin(email: String, password: String): Single<User> =
        networkService.doLoginCall(LoginRequest(email, password))
            .map {
                User(
                    it.userId,
                    it.userName,
                    it.userEmail,
                    it.accessToken,
                    it.profilePicUrl
                )
            }
}