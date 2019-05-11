package io.github.qst.backend.dao.repository;

import io.github.qst.backend.dao.bean.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author kezhenxu94
 */
public interface AuthorRepo extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
}
