package com.dennisdevelops.repositorys

import com.dennisdevelops.models.User
import com.dennisdevelops.models.db.Hashing_Algorithms
import com.dennisdevelops.models.db.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository() {

	fun getUser(email: String): User? {
		return getUserByEmail(email)
	}

	fun getUserById(id: Int): User? {

		val user = transaction {
			Users.select(Users.id eq id).map {
				User(
					it[Users.id],
					it[Users.username],
					it[Users.email],
					it[Users.password],
					it[Users.salt],
					Hashing_Algorithms.select(Hashing_Algorithms.id eq it[Users.hashing_algorithm])
						.map {
							it[Hashing_Algorithms.name]
						}.first()
				)
			}
		}
		return user.firstOrNull()
	}

	fun insertUser( user: User): User? {
		val newUser = transaction {
			Users.insert {
				it[username] = user.username
				it[email] = user.email
				it[password] = user.password
				it[salt] = user.salt
				it[hashing_algorithm] =
					Hashing_Algorithms.select(Hashing_Algorithms.name eq user.hashing_algorithm)
						.map {
							it[Hashing_Algorithms.id]
						}.firstOrNull() ?: (Hashing_Algorithms.insert {
						it[name] = user.hashing_algorithm
					} get Hashing_Algorithms.id)

			}
		}
		return getUserById(newUser[Users.id])
	}

	fun updateUser(user: User): User? {
		val updatedUser = transaction {
			Users.update({ Users.id eq user.id!! }) {
				it[username] = user.username
				it[email] = user.email

			}
		}
		return getUserById(updatedUser)
	}
	fun updateUserPassword(user: User): User? {


		val updatedUser = transaction {
			Users.update({ Users.id eq user.id!! }) {
				it[password] = user.password
				it[salt] = user.salt
				it[hashing_algorithm] = Hashing_Algorithms.select(Hashing_Algorithms.name eq user.hashing_algorithm).map {
					it[Hashing_Algorithms.id]
				}.first()
			}
		}
		return getUserById(updatedUser)
	}

	fun deleteUser(id: Int): Boolean {
		val deletedUser = transaction {
			Users.deleteWhere { Users.id eq id }
		}
		return deletedUser > 0
	}

	fun getUserByEmail(email: String): User? {
		val user = transaction {
			// Log
			addLogger(StdOutSqlLogger)
			Users.select(Users.email eq email).map {
				User(
					it[Users.id],
					it[Users.username],
					it[Users.email],
					it[Users.password],
					it[Users.salt],
					Hashing_Algorithms.select(Hashing_Algorithms.id eq it[Users.hashing_algorithm])
						.map {
							it[Hashing_Algorithms.name]
						}.first()
				)
			}
		}
		return user.firstOrNull()
	}


}
