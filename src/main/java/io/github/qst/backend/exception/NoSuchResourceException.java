package io.github.qst.backend.exception;

/**
 * @author kezhenxu94
 */
public class NoSuchResourceException extends RuntimeException {
  public NoSuchResourceException(final String resource, final long id) {
    super(
        String.format("No such %s of id: %s", id, resource)
    );
  }
}
