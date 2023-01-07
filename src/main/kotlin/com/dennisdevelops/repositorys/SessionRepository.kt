package com.dennisdevelops.repositorys

import com.dennisdevelops.models.UserSession
import com.dennisdevelops.models.db.User_sessions
import com.dennisdevelops.models.db.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class SessionRepository() {


	fun createSession(userId: Int):UserSession? {

		val session = transaction {
			addLogger(StdOutSqlLogger)
			User_sessions.insert {
				it[user_id] = userId
			}
		}
		return getSession(session[User_sessions.id])


	}

	fun getSession(id: Int): UserSession? {

		val session = transaction {
			addLogger(StdOutSqlLogger)
			User_sessions.select(User_sessions.id eq id).map {
				UserSession(
					it[User_sessions.id],
					it[User_sessions.user_id],
					Users.select(Users.id eq it[User_sessions.user_id]).map {
						it[Users.username]
					}.first()
				)
			}
		}

		return session.firstOrNull()

	}

	fun validateSession(sessionId: Int): Boolean {
		return getSession(sessionId) != null
	}


}