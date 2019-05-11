package io.github.qst.backend.dao.specifications;

import io.github.qst.backend.dao.bean.Author;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author kezhenxu94
 */
public final class AuthorSpecifications {
  public static Specification<Author> all() {
    return (root, query, cb) -> cb.and();
  }

  public static Specification<Author> nameLike(final String q) {
    return (root, query, cb) -> cb.like(root.get("name"), String.format("%%%s%%", q));
  }

  public static Specification<Author> descriptionLike(final String q) {
    return (root, query, cb) -> cb.like(root.get("bio"), String.format("%%%s%%", q));
  }
}
