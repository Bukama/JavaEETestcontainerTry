
package de.test.genericdao.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;

/**
 * Util class for Data Access Objects.
 */
public final class DaoUtil {

  // Name of the Oracles TO_CHAR function.
  private static final String ORACLE_TO_CHAR = "TO_CHAR";

  private DaoUtil() {
    throw new AssertionError("Suppress default constructor for noninstantiability");
  }

  /**
   * Creates and Expression to call oracles TO_CHAR function.
   * Call is executed via the parameter.
   * 
   * @param criteriaBuilder
   *          The CriteriaBuilder to create the expression.
   * @param stringParam
   *          The ParameterExpression to be used for the TO_CHAR call.
   * @return The created Expression
   */
  public static Expression<String> toCharFunctionCall(CriteriaBuilder criteriaBuilder,
      ParameterExpression<String> stringParam) {
    return criteriaBuilder.function(ORACLE_TO_CHAR, String.class, stringParam);
  }

}
