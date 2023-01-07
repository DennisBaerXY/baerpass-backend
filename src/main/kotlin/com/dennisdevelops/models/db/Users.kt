package com.dennisdevelops.models.db

import org.jetbrains.exposed.sql.Table

object Users : Table() {
	val id = integer("id").autoIncrement()
	val username = varchar("username", 255)
	val email = varchar("email", 255).uniqueIndex()
	val password = varchar("password", 255)
	val salt = varchar("salt", 255)
	val hashing_algorithm = integer("hashing_algorithm") references Hashing_Algorithms.id

	override val primaryKey = PrimaryKey(id)
}
