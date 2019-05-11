package io.github.qst.backend.dao.specifications;

import io.github.qst.backend.dao.bean.Book;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author kezhenxu94
 */
public final class BookSpecifications {
  public static Specification<Book> all() {
    return (root, query, cb) -> cb.and();
  }

  public static Specification<Book> authorIs(final long authorId) {
    return (root, query, cb) -> cb.equal(root.get("authorId"), authorId);
  }

  public static Specification<Book> titleLike(final String q) {
    return (root, query, cb) -> cb.like(root.get("title"), String.format("%%%s%%", q));
  }

  public static Specification<Book> descriptionLike(final String q) {
    return (root, query, cb) -> cb.like(root.get("description"), String.format("%%%s%%", q));
  }
}
