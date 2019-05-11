package io.github.qst.backend.service;

import io.github.qst.backend.dao.bean.Author;
import io.github.qst.backend.dao.bean.Book;
import io.github.qst.backend.dao.repository.AuthorRepo;
import io.github.qst.backend.dao.repository.BookRepo;
import io.github.qst.backend.dao.specifications.BookSpecifications;
import io.github.qst.backend.dto.CreateAuthor;
import io.github.qst.backend.dto.UpdateAuthor;
import io.github.qst.backend.exception.NoSuchResourceException;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import static io.github.qst.backend.dao.specifications.AuthorSpecifications.all;
import static io.github.qst.backend.dao.specifications.AuthorSpecifications.descriptionLike;
import static io.github.qst.backend.dao.specifications.AuthorSpecifications.nameLike;
import static io.github.qst.backend.dao.specifications.BookSpecifications.authorIs;
import static io.github.qst.backend.dao.specifications.BookSpecifications.titleLike;

/**
 * @author kezhenxu94
 */
@Service
@GraphQLApi
@SuppressWarnings("unused")
public class AuthorService {
  private final BookRepo bookRepo;
  private final AuthorRepo authorRepo;

  public AuthorService(final BookRepo bookRepo,
                       final AuthorRepo authorRepo) {
    this.bookRepo = bookRepo;
    this.authorRepo = authorRepo;
  }

  @GraphQLQuery
  public Page<Author> findAuthors(final String q, final Pageable pageable) {
    Specification<Author> specification = all();
    if (!StringUtils.isEmpty(q)) {
      final Specification<Author> qSpecification = nameLike(q).or(descriptionLike(q));
      specification = specification.and(qSpecification);
    }
    return authorRepo.findAll(specification, pageable);
  }

  @GraphQLQuery
  public Author findAuthor(final long id) {
    return authorRepo.findById(id).orElseThrow(
        () -> new NoSuchResourceException("author", id)
    );
  }

  @GraphQLMutation
  @Transactional
  public Author createAuthor(final CreateAuthor createAuthor) {
    final Author author = new Author();
    BeanUtils.copyProperties(createAuthor, author);
    return authorRepo.save(author);
  }

  @GraphQLMutation
  @Transactional
  public Author updateAuthor(final UpdateAuthor updateAuthor) {
    final long authorId = updateAuthor.getId();
    authorRepo.findById(authorId).orElseThrow(
        () -> new NoSuchResourceException("author", authorId)
    );
    final Author author = new Author();
    BeanUtils.copyProperties(updateAuthor, author);
    return authorRepo.save(author);
  }

  @GraphQLMutation
  @Transactional
  public boolean deleteAuthor(final long id) {
    authorRepo.deleteById(id);
    return true;
  }

  @GraphQLQuery
  public Page<Book> books(@GraphQLContext final Author author,
                          final String q,
                          final Pageable pageable) {
    assert author != null && author.getId() != null;
    Specification<Book> spec = authorIs(author.getId());
    if (!StringUtils.isEmpty(q)) {
      spec = spec.and(titleLike(q).or(BookSpecifications.descriptionLike(q)));
    }
    return bookRepo.findAll(spec, pageable);
  }
}
