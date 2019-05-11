package io.github.qst.backend.dto;

/**
 * @author kezhenxu94
 */
public class UpdateBook {
  private long id;
  private String title;
  private String description;

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
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
