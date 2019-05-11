package io.github.qst.backend.dto;

/**
 * @author kezhenxu94
 */
public class CreateAuthor {
  private String name;
  private String bio;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(final String bio) {
    this.bio = bio;
  }
}
