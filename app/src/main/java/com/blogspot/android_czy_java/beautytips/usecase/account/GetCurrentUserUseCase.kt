package com.blogspot.android_czy_java.beautytips.usecase.account

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository

class GetCurrentUserUseCase(private val userRepository: UserRepository) {

    fun execute() = userRepository.getCurrentUser()
}