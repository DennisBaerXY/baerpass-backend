package com.dennisdevelops.plugins

import com.dennisdevelops.models.db.Hashing_Algorithms
import com.dennisdevelops.models.db.User_sessions
import com.dennisdevelops.models.db.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.configureDatabase() {

	val config = HikariConfig().apply {
		driverClassName = "org.postgresql.Driver"
		jdbcUrl = "jdbc:postgresql://localhost:5432/bearpass"
		username = "postgres"
		password = "postgres"
		maximumPoolSize = 5
	}

	val dataSource = HikariDataSource(config)
	Database.connect(dataSource, ) // Exposed
	transaction {
		addLogger(StdOutSqlLogger)
		SchemaUtils.create(Users,User_sessions, Hashing_Algorithms)
	}


}