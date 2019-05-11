package io.github.qst.backend.service;

import io.github.qst.backend.dao.bean.Book;
import io.github.qst.backend.dao.repository.BookRepo;
import io.github.qst.backend.dto.CreateBook;
import io.github.qst.backend.dto.UpdateBook;
import io.github.qst.backend.exception.NoSuchResourceException;
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
import java.util.Objects;

import static io.github.qst.backend.dao.specifications.BookSpecifications.all;
import static io.github.qst.backend.dao.specifications.BookSpecifications.authorIs;
import static io.github.qst.backend.dao.specifications.BookSpecifications.descriptionLike;
import static io.github.qst.backend.dao.specifications.BookSpecifications.titleLike;

/**
 * @author kezhenxu94
 */
@Service
@GraphQLApi
@SuppressWarnings("unused")
public class BookService {
  private final BookRepo bookRepo;

  public BookService(final BookRepo bookRepo) {
    this.bookRepo = bookRepo;
  }

  @GraphQLQuery
  public Page<Book> findBooks(final Long authorId, final String q, final Pageable pageable) {
    Specification<Book> specification = all();
    if (!StringUtils.isEmpty(q)) {
      final Specification<Book> qSpecification = titleLike(q).or(descriptionLike(q));
      specification = specification.and(qSpecification);
    }
    if (Objects.nonNull(authorId) && authorId > 0) {
      specification = specification.and(authorIs(authorId));
    }
    return bookRepo.findAll(specification, pageable);
  }

  @GraphQLMutation
  @Transactional
  public Book createBook(final CreateBook createBook) {
    final Book book = new Book();
    BeanUtils.copyProperties(createBook, book);
    return bookRepo.save(book);
  }

  @GraphQLMutation
  @Transactional
  public boolean deleteBook(final long id) {
    bookRepo.deleteById(id);
    return true;
  }

  @GraphQLMutation
  @Transactional
  public Book updateBook(final UpdateBook updateBook) {
    final long bookId = updateBook.getId();
    final Book existedBook = bookRepo.findById(bookId).orElseThrow(
        () -> new NoSuchResourceException("book", bookId)
    );
    final Book book = new Book();
    BeanUtils.copyProperties(updateBook, book);
    book.setAuthorId(existedBook.getAuthorId());
    return bookRepo.save(book);
  }
}
