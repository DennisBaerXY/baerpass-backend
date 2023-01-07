package com.dennisdevelops.models.db

import org.jetbrains.exposed.sql.Table

object Hashing_Algorithms: Table("Hashing_Algorithms") {
	val id = integer("id").autoIncrement()
	val name = varchar("name", 255)
	override val primaryKey = PrimaryKey(id)
}
