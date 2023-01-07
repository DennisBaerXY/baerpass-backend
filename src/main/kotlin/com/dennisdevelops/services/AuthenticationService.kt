package com.dennisdevelops.services

import com.dennisdevelops.models.User
import com.dennisdevelops.repositorys.UserRepository
import org.mindrot.jbcrypt.BCrypt
import javax.naming.AuthenticationException

class AuthenticationService(private val userRepository: UserRepository) {

	fun registerUser(username: String, email: String, password: String): User? {

		try {
			val salt = BCrypt.gensalt()
			val hashedPassword = BCrypt.hashpw(password, salt)
			val user = User(null, username, email, hashedPassword, salt, "bcrypt")
			return userRepository.insertUser(user)
		} catch (e: Exception) {
			println(e)
			return null
		}

	}

	fun loginUser(email: String, password: String): User {
		try {
			val user = userRepository.getUser(email)
			if (user != null) {
				if (BCrypt.checkpw(password, user.password)) {
					return user
				} else {
					throw AuthenticationException("Invalid password")
				}
			} else {
				throw AuthenticationException("User does not exist")
			}
		} catch (e: Exception) {
			println(e)
			throw AuthenticationException("Invalid password")
		}

	}




}