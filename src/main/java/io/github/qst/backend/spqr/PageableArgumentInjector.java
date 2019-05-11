package io.github.qst.backend.spqr;

import io.leangen.graphql.generator.mapping.ArgumentInjector;
import io.leangen.graphql.generator.mapping.ArgumentInjectorParams;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author kezhenxu94
 */
public class PageableArgumentInjector implements ArgumentInjector {
  @SuppressWarnings("unchecked")
  @Override
  public Object getArgumentValue(final ArgumentInjectorParams params) {
    final Map<String, Object> input = (Map<String, Object>) params.getInput();
    if (Objects.isNull(input)) {
      return Pageable.unpaged();
    }
    final int page = ((Integer) input.getOrDefault("page", 1)) - 1;
    final int size = ((Integer) input.getOrDefault("size", 20));
    final Map<String, Object> sort = (Map<String, Object>) input.getOrDefault("sort", Collections.emptyMap());
    final List<Map<String, Object>> orders = (List<Map<String, Object>>) sort.getOrDefault("orders", Collections.emptyList());

    Sort s = Sort.unsorted();
    for (final Map<String, Object> o : orders) {
      Object property = o.get("property");
      if (Objects.isNull(property)) {
        continue;
      }
      final Sort.Direction direction = (Sort.Direction) o.getOrDefault("direction", Sort.Direction.ASC);
      final boolean ignoreCase = (Boolean) o.getOrDefault("ignoreCase", false);
      final Sort.Order order = direction == Sort.Direction.ASC
          ? Sort.Order.asc(property.toString().trim())
          : Sort.Order.desc(property.toString().trim());
      s = s.and(Sort.by(ignoreCase ? order.ignoreCase() : order));
    }
    return PageRequest.of(page, size, s);
  }

  @Override
  public boolean supports(final AnnotatedType type, final Parameter parameter) {
    return Pageable.class.equals(type.getType());
  }
}
