package io.github.qst.backend.spqr;

import graphql.schema.GraphQLInputObjectType;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.generator.BuildContext;
import io.leangen.graphql.generator.OperationMapper;
import io.leangen.graphql.generator.mapping.common.ObjectTypeMapper;
import io.leangen.graphql.metadata.InputField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.AnnotatedType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static graphql.schema.GraphQLInputObjectType.newInputObject;

/**
 * @author kezhenxu94
 */
public class PageableMapper extends ObjectTypeMapper {

  @Override
  public GraphQLInputObjectType toGraphQLInputType(final String typeName, final AnnotatedType javaType, final OperationMapper operationMapper, final BuildContext buildContext) {
    final GraphQLInputObjectType.Builder typeBuilder = newInputObject()
        .name(typeName)
        .description(buildContext.typeInfoGenerator.generateInputTypeDescription(javaType, buildContext.messageBundle));

    final Set<InputField> fields = new HashSet<>();
    fields.add(new InputField("page", "Page number, indexed from 1, default to 1", GenericTypeReflector.annotate(int.class), GenericTypeReflector.annotate(int.class), 1, null));
    fields.add(new InputField("size", "Page size, default to 10", GenericTypeReflector.annotate(int.class), GenericTypeReflector.annotate(int.class), 10, null));
    fields.add(new InputField("sort", "Page sorting", GenericTypeReflector.annotate(Sort.class), GenericTypeReflector.annotate(Sort.class), Collections.emptyMap(), null));

    fields.forEach(field -> typeBuilder.field(operationMapper.toGraphQLInputField(field, buildContext)));

    return typeBuilder.build();
  }

  @Override
  public boolean supports(final AnnotatedType type) {
    return GenericTypeReflector.isSuperType(Pageable.class, type.getType());
  }
}
