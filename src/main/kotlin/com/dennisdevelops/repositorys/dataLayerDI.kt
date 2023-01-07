package com.dennisdevelops.repositorys

import org.koin.dsl.module


val repositoryModule = module {
	single { UserRepository() }
}