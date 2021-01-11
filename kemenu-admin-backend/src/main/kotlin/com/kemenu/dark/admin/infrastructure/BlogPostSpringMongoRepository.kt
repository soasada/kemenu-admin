package com.kemenu.dark.admin.infrastructure

import com.kemenu.dark.admin.domain.BlogPost
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface BlogPostSpringMongoRepository : ReactiveMongoRepository<BlogPost, String>