package com.kemenu.dark.admin.infrastructure

import com.kemenu.dark.admin.domain.Customer
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CustomerSpringMongoRepository : ReactiveMongoRepository<Customer, String>