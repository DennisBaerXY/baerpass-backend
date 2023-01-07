package com.dennisdevelops.services

import org.koin.dsl.module

val servicesModule = module{
	single { AuthenticationService(get()) }

}