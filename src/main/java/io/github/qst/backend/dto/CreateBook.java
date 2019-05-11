package io.github.qst.backend.dto;

/**
 * @author kezhenxu94
 */
public class CreateBook {
  private long authorId;
  private String isbn;
  private String title;
  private String description;

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
