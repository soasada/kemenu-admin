package com.kemenu.admin.infrastructure

import com.kemenu.admin.domain.BlogPost
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface BlogPostSpringMongoRepository : ReactiveMongoRepository<BlogPost, String>