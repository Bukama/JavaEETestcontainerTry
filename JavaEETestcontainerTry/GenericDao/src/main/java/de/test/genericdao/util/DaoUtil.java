
package de.test.genericdao.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.criteria.*;
import java.sql.SQLException;
import java.util.Date;

/**
 * Hilfsklasse für Data Access Objects.
 */
public final class DaoUtil {

  private static final Logger LOG = LogManager.getLogger();

  /** Bezeichnung für Oracles TO_CHAR Funktion. */
  private static final String ORACLE_TO_CHAR = "TO_CHAR";

  private static final String ORACLE_SQLSTATE_CONSTRAINTVIOLATION = "23000";
  private static final int ORACLE_SQLERROR_UNIQUECONSTRAINTVIOLATION = 1;

  private DaoUtil() {
    throw new AssertionError("Suppress default constructor for noninstantiability");
  }

  /**
   * Erstellt ein Predicate für eine Criteria-Query und filtert auf einen gültigen Zeitraum gegenüber dem übergebenem
   * Datum.
   * 
   * @param criteriaBuilder
   *          Der {@link CriteriaBuilder}, der zum Erstellen des Predicate verwendet werden soll.
   * @param von
   *          Der {@link Path}, der das Von-Datum repräsentiert.
   * @param bis
   *          Der {@link Path}, der das Bis-Datum repräsentiert.
   * @param date
   *          Das Datum, gegen das der Zeitraum geprüft werden soll.
   * @return Das Predicate-Objekt das anhand der Parameter erzeugt worden ist.
   */
  public static <D extends Date> Predicate createPredicateValidPeriod(CriteriaBuilder criteriaBuilder, Path<D> von,
      Path<D> bis, D date) {
    LOG.entry(criteriaBuilder, von, bis, date);

    Predicate predicate = criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(von, date),
        criteriaBuilder.or(criteriaBuilder.isNull(bis), criteriaBuilder.greaterThan(bis, date)));

    LOG.exit(predicate);
    return predicate;
  }

  /**
   * Erstellt ein Predicate für eine Criteria-Query und filtert auf einen gültigen Zeitraum gegenüber der aktuellen
   * Systemzeit.
   * 
   * @param criteriaBuilder
   *          Der {@link CriteriaBuilder}, der zum Erstellen des Predicate verwendet werden soll.
   * @param von
   *          Der {@link Path}, der das Von-Datum repräsentiert.
   * @param bis
   *          Der {@link Path}, der das Bis-Datum repräsentiert.
   * @return Das Predicate-Objekt das anhand der Parameter erzeugt worden ist.
   */
  public static Predicate createPredicateValidPeriod(CriteriaBuilder criteriaBuilder, Path<Date> von, Path<Date> bis) {
    LOG.entry(criteriaBuilder, von, bis);

    Predicate predicate = createPredicateValidPeriod(criteriaBuilder, von, bis, new Date());

    LOG.exit(predicate);
    return predicate;
  }

  /**
   * Erstellt ein Predicate zur Überprüfung auf einen Gültigkeitszeitraum.
   * 
   * @param criteriaBuilder
   *          Das zu nutzende {@link CriteriaBuilder} Objekt.
   * @param vonPath
   *          Der {@link Path}, der das Von-Datum repräsentiert.
   * @param vonParam
   *          Die {@link ParameterExpression} für das Von-Datum
   * @param bisPath
   *          Der {@link Path}, der das Bis-Datum repräsentiert.
   * @param bisParam
   *          Die {@link ParameterExpression} für das Bis-Datum.
   * @return Das erzeugte Predicate Objekt.
   */
  public static <D extends Date> Predicate validPeriod(CriteriaBuilder criteriaBuilder, Path<D> vonPath,
      ParameterExpression<D> vonParam, Path<D> bisPath, ParameterExpression<D> bisParam) {
    LOG.entry(criteriaBuilder, vonPath, vonParam, bisPath, bisParam);

    Predicate predicate = criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(vonPath, vonParam),
        criteriaBuilder.or(criteriaBuilder.isNull(bisPath), criteriaBuilder.greaterThan(bisPath, bisParam)));

    LOG.exit(predicate);
    return predicate;
  }

  /**
   * Erstellt eine {@link Expression} für den Aufruf der Oracle TO_CHAR Funktion. Der Aufruf erfolgt für den übergebenen
   * Parameter.
   * 
   * @param criteriaBuilder
   *          Das zu nutzende {@link CriteriaBuilder} Objekt.
   * @param stringParam
   *          Die {@link ParameterExpression}, für die der TO_CHAR Aufruf verwendet werden soll.
   * @return Die erzeugte {@link Expression}.
   */
  public static Expression<String> toCharFunctionCall(CriteriaBuilder criteriaBuilder,
      ParameterExpression<String> stringParam) {
    LOG.entry(criteriaBuilder);

    Expression<String> toCharFunction = criteriaBuilder.function(ORACLE_TO_CHAR, String.class, stringParam);

    LOG.exit(toCharFunction);
    return toCharFunction;
  }

  /**
   * Bestimmt, ob die übergebene Exception oder einer ihrer Gründe einen Oracle-Unique-Constraint verletzt.
   * 
   * @param exception
   *          Die Exception, die geprüft werden soll.
   * @return boolean
   */
  public static boolean isDuplicateKeyException(Exception exception) {
    LOG.entry(exception);

    if (null == exception) {
      throw new IllegalArgumentException("Zu prüfende Exception darf nicht NULL sein!");
    }

    Throwable cause = exception;
    while (cause != null) {

      if (cause instanceof SQLException) {
        SQLException sqlE = (SQLException) cause;

        if (ORACLE_SQLSTATE_CONSTRAINTVIOLATION.equals(sqlE.getSQLState())
            && ORACLE_SQLERROR_UNIQUECONSTRAINTVIOLATION == sqlE.getErrorCode()) {
          LOG.exit(true);
          return true;
        }
      }

      cause = cause.getCause();
    }

    LOG.exit(false);
    return false;
  }

}
