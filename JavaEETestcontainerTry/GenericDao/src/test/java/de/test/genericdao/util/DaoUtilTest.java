
package de.test.genericdao.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DaoUtilTest {

  @Nested
  @DisplayName("Methode: isDuplicateKeyException")
  class IsDuplicateKeyExceptionTest {

    @DisplayName(" soll TRUE, wenn SQL-Exception mit Status 23000 und Error 1")
    @Test
    void isDuplicateKeyExceptionSollTrueWennSQLExceptionMitStatus23000UndError1() {
      SQLException thrown = new SQLException("Unique contraints violated", "23000", 1);

      boolean result = DaoUtil.isDuplicateKeyException(thrown);

      assertThat(result).isTrue();
    }

    @DisplayName(" soll FALSE, wenn SQL-Exception mit Status 23000 und nicht Error 1")
    @Test
    void isDuplicateKeyExceptionSollFalseWennSQLExceptionMitStatus23000UndNichtErrorCode1() {
      SQLException thrown = new SQLException("Unique contraints violated", "23000", 2);

      boolean result = DaoUtil.isDuplicateKeyException(thrown);

      assertThat(result).isFalse();
    }

    @DisplayName(" soll FALSE, wenn SQL-Exception nicht mit Status 23000")
    @Test
    void isDuplicateKeyExceptionSollFalseWennSQLExceptionNichtMitStatus23000() {
      SQLException thrown = new SQLException("Unique contraints violated", "23001");

      boolean result = DaoUtil.isDuplicateKeyException(thrown);

      assertThat(result).isFalse();
    }

    @DisplayName(" soll FALSE, wenn keine SQL-Exception ")
    @Test
    void isDuplicateKeyExceptionSollFalseWennKeineSQLException() {
      Exception thrown = new IllegalArgumentException("No SQL Exception");

      boolean result = DaoUtil.isDuplicateKeyException(thrown);

      assertThat(result).isFalse();
    }

    @DisplayName(" soll TRUE, wenn SQL-Exception nested mit Status 23000 und Error 1")
    @Test
    void isDuplicateKeyExceptionSollTrueWennSQLExceptionNestedMitStatus23000UndError1() {

      SQLException thrown = new SQLException("Unique contraints violated", "23000", 1);

      IllegalArgumentException expTwo = new IllegalArgumentException("First nested exception", thrown);

      PersistenceException expOne = new PersistenceException("Outer exception", expTwo);

      boolean result = DaoUtil.isDuplicateKeyException(expOne);

      assertThat(result).isTrue();
    }

    @DisplayName(" soll Exception werfen, wenn Parameter NULL")
    @Test
    void isDuplicateKeyExceptionSollExceptionWerfenWennParameterNull() {

      final Throwable thrown = catchThrowable(() -> {
        DaoUtil.isDuplicateKeyException(null);
      });

      String expectedMessage = "Zu prüfende Exception darf nicht NULL sein!";

      assertThat(thrown).isExactlyInstanceOf(IllegalArgumentException.class);
      assertThat(thrown.getMessage()).isEqualTo(expectedMessage);
    }

  }

}
