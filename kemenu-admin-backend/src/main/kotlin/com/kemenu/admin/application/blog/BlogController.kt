package com.kemenu.admin.application.blog

import com.kemenu.admin.application.HttpExceptionFactory.conflict
import com.kemenu.admin.application.HttpExceptionFactory.notFound
import com.kemenu.admin.application.HttpExceptionFactory.server
import com.kemenu.admin.application.security.IntrospectiveService
import com.kemenu.admin.application.utils.logger
import com.kemenu.admin.domain.BlogPost
import com.kemenu.admin.infrastructure.BlogPostSpringMongoRepository
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import javax.validation.Valid

@RestController
@RequestMapping("/v1/blog")
class BlogController(
        private val repository: BlogPostSpringMongoRepository,
        private val introspectiveService: IntrospectiveService
) {
    val logger = logger()

    @GetMapping(produces = [TEXT_EVENT_STREAM_VALUE])
    fun findAll() = repository.findAll()
            .doOnError {
                logger.error("Error listing blog posts", it)
                throw it
            }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String) = repository
            .findById(id)
            .doOnError {
                logger.error("Error finding blog post $id", it)
                throw server()
            }
            .switchIfEmpty { throw notFound() }

    @PostMapping
    fun create(@RequestBody @Valid request: BlogPostRequest,
               @RequestHeader(value = "Authorization") token: String): Mono<BlogPost> {
        val post = PostMapper.toNewEntity(introspectiveService.whoAmI(token), request)
        val blogPost = BlogPostMapper.toNewEntity(post, request)
        val blogToSave = blogPost.addPost(post)
        return repository.save(blogToSave)
                .doOnError {
                    logger.error("Error while creating blog post", it)
                    throw it
                }
    }

    @PostMapping("/{id}/post")
    fun createPost(@PathVariable id: String,
                   @RequestBody @Valid request: BlogPostRequest,
                   @RequestHeader(value = "Authorization") token: String): Mono<BlogPost> {
        val post = PostMapper.toNewEntity(introspectiveService.whoAmI(token), request)
        return repository
                .findById(id)
                .switchIfEmpty { Mono.error(notFound()) }
                .flatMap { blog ->
                    if (blog.getPost(request.locale) != null) {
                        return@flatMap Mono.error(conflict())
                    }
                    val blogWithPost = blog.addPost(post)
                    repository.save(blogWithPost).doOnError { throw it }
                }
                .doOnError {
                    logger.error("Error while updating blog post", it)
                    throw it
                }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String,
               @RequestBody @Valid request: BlogPostRequest,
               @RequestHeader(value = "Authorization") token: String) = repository
            .findById(id)
            .switchIfEmpty { Mono.error(notFound()) }
            .flatMap { old ->
                val blog = BlogPostMapper.toUpdateEntity(old, request)
                val oldPost = blog.getPost(request.locale) ?: return@flatMap Mono.error(notFound())
                val updatedEntity = blog.addPost(PostMapper.toUpdateEntity(oldPost, introspectiveService.whoAmI(token), request))
                repository.save(updatedEntity).doOnError { throw it }
            }
            .doOnError {
                logger.error("Error while updating blog post", it)
                throw it
            }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = repository
            .findById(id)
            .switchIfEmpty(Mono.error(notFound()))
            .flatMap { repository.deleteById(id) }
            .doOnError {
                logger.error("Error while deleting blog post", it)
                throw it
            }
}