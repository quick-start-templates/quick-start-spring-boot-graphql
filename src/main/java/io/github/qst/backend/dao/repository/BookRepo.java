package io.github.qst.backend.dao.repository;

import io.github.qst.backend.dao.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author kezhenxu94
 */
public interface BookRepo extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
