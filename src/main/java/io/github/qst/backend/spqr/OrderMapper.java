package io.github.qst.backend.spqr;

import graphql.schema.GraphQLInputObjectType;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.generator.BuildContext;
import io.leangen.graphql.generator.OperationMapper;
import io.leangen.graphql.generator.mapping.common.ObjectTypeMapper;
import io.leangen.graphql.metadata.InputField;
import org.springframework.data.domain.Sort;

import java.lang.reflect.AnnotatedType;
import java.util.HashSet;
import java.util.Set;

import static graphql.schema.GraphQLInputObjectType.newInputObject;

/**
 * @author kezhenxu94
 */
public class OrderMapper extends ObjectTypeMapper {

  @Override
  public GraphQLInputObjectType toGraphQLInputType(final String typeName, final AnnotatedType javaType, final OperationMapper operationMapper, final BuildContext buildContext) {
    GraphQLInputObjectType.Builder typeBuilder = newInputObject()
        .name(typeName)
        .description(buildContext.typeInfoGenerator.generateInputTypeDescription(javaType, buildContext.messageBundle));

    final Set<InputField> fields = new HashSet<>();
    fields.add(new InputField("property", "Sorting property", GenericTypeReflector.annotate(String.class), GenericTypeReflector.annotate(String.class), null, null));
    fields.add(new InputField("direction", "Sorting direction, ASC/DESC", GenericTypeReflector.annotate(Sort.Direction.class), GenericTypeReflector.annotate(Sort.Direction.class), Sort.Direction.ASC, null));
    fields.add(new InputField("ignoreCase", "Whether ignore case when sorting", GenericTypeReflector.annotate(boolean.class), GenericTypeReflector.annotate(boolean.class), false, null));

    fields.forEach(field -> typeBuilder.field(operationMapper.toGraphQLInputField(field, buildContext)));

    return typeBuilder.build();
  }

  @Override
  public boolean supports(final AnnotatedType type) {
    return Sort.Order.class.equals(type.getType());
  }
}
