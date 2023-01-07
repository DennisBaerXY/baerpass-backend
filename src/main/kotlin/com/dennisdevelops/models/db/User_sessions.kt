package com.dennisdevelops.models.db

import org.jetbrains.exposed.sql.Table

object User_sessions : Table() {
	val id = integer("id").autoIncrement()
	val user_id = integer("user_id") references Users.id


	override val primaryKey = PrimaryKey(id)
}