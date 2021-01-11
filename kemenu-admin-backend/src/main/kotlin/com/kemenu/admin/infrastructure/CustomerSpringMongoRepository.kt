package com.kemenu.admin.infrastructure

import com.kemenu.admin.domain.Customer
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CustomerSpringMongoRepository : ReactiveMongoRepository<Customer, String>