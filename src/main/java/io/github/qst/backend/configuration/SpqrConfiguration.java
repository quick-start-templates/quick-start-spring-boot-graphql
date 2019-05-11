package io.github.qst.backend.configuration;

import io.github.qst.backend.spqr.OrderMapper;
import io.github.qst.backend.spqr.PageableArgumentInjector;
import io.github.qst.backend.spqr.PageableMapper;
import io.github.qst.backend.spqr.SortMapper;
import io.leangen.graphql.ExtensionProvider;
import io.leangen.graphql.GeneratorConfiguration;
import io.leangen.graphql.generator.mapping.ArgumentInjector;
import io.leangen.graphql.generator.mapping.TypeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kezhenxu94
 */
@Configuration
public class SpqrConfiguration {
  @Bean
  public ExtensionProvider<GeneratorConfiguration, TypeMapper> pageableInputField() {
    return (config, defaults) -> defaults.prepend(new PageableMapper()).prepend(new SortMapper()).prepend(new OrderMapper());
  }

  @Bean
  public ExtensionProvider<GeneratorConfiguration, ArgumentInjector> pageableArgumentInjector() {
    return (config, defaults) -> defaults.prepend(new PageableArgumentInjector());
  }
}
