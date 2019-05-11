package io.github.qst.backend.dao.bean;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author kezhenxu94
 */
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Book {
  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, updatable = false)
  private long authorId;

  @Column(nullable = false, updatable = false, unique = true)
  private String isbn;

  @Column(nullable = false, updatable = false)
  private String title;

  @Column(nullable = false, length = 65535)
  private String description;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(final long authorId) {
    this.authorId = authorId;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(final String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }
}
