
package de.test.genericdao.dao;

import java.util.List;

/**
 * Generischeres Interface für DAOs. Es bietet typsichere Methoden für Create, Read, Update und Delete an.
 * 
 */
public interface IGenericDao<T> {

  /**
   * Legt das übergebene, generische Objekt an und liefert die Entität zurück. Nach dem Anlegen des Objektes erfolgt
   * immer ein Flush gegen die Datenbank.
   * 
   * @param generic
   *          Das generische Objekt, das angelegt werden soll.
   * @return Das angelegte generische Objekt
   */
  T create(T generic);

  /**
   * Legt das übergebene, generische Object an und liefert die Entität zurück. Über den boolschen Parameter kann
   * gesteuert werden, ob ein Flush gegen die Datenbank erfolgen soll.
   * 
   * @param generic
   *          Das generische Objekt, das angelegt werden soll.
   * @param flush
   *          Der Schalter bestimmt, ob ein Flush gegen die Datenbank erfolgen soll.
   * @return Das angelegte generische Objekt
   */
  T create(T generic, boolean flush);

  /**
   * Legt die übergebene Liste von generischen Objekten an und liefert deren Entitäten zurück. Nach dem Anlegen der
   * Objekte erfolgt immer ein Flush gegen die Datenbank.
   * 
   * @param genericList
   *          Die Liste von generischen Objekten, die angelegt werden sollen.
   * @return Die Liste der angelegten Objekte
   */
  List<T> create(List<T> genericList);

  /**
   * Legt die übergebene Liste von generischen Objekten an und liefert deren Entitäten zurück. Über den boolschen
   * Parameter kann gesteuert werden, ob ein Flush gegen die Datenbank erfolgen soll.
   * 
   * @param genericList
   *          Die Liste von generischen Objekten, die angelegt werden sollen.
   * @param flush
   *          Der Schalter bestimmt, ob ein Flush gegen die Datenbank erfolgen soll.
   * @return Die Liste der angelegten Objekte
   */
  List<T> create(List<T> genericList, boolean flush);

  /**
   * Löscht das übergebene Objekt. Nach dem Löschen des Objektes erfolgt immer ein Flush gegen die Datenbank.
   * 
   * @param id
   *          Das Object, das gelöscht werden soll.
   */
  void delete(Object id);

  /**
   * Löscht das übergene Objekt. Über den boolschen Parameter kann gesteuert werden, ob ein Flush gegen die Datenbank
   * erfolgen soll.
   * 
   * @param id
   *          Das Object, das gelöscht werden soll.
   * @param flush
   *          Der Schalter bestimmt, ob ein Flush gegen die Datenbank erfolgen soll.
   */
  void delete(Object id, boolean flush);

  /**
   * Aktualisiert das übergebene Objekt und liefert die aktualisierte Entität zurück. Nach dem Update des Objektes
   * erfolgt immer ein Flush gegen die Datenbank.
   * 
   * @param generic
   *          Das Objekt, das in der Datenbank aktualisiert werden soll
   * @return Das aktualisierte Objekt
   */
  T update(T generic);

  /**
   * Aktualisiert das übergebene Objekt und liefert die aktualisierte Entität zurück. Über den boolschen Parameter kann
   * gesteuert werden, ob ein Flush gegen die Datenbank erfolgen soll.
   * 
   * @param generic
   *          Das Objekt, das in der Datenbank aktualisiert werden soll.
   * @param flush
   *          Der Schalter bestimmt, ob ein Flush gegen die Datenbank erfolgen soll.
   * @return Das aktualisierte Objekt
   */
  T update(T generic, boolean flush);

  /**
   * Aktualisiert die übergebene Liste von Objekten und liefert deren aktualisierte Entitäten wieder zurück. Nach dem
   * Update erfolgt immer ein Flush gegen die Datenbank.
   * 
   * @param genericList
   *          Die Liste der Objekte, die aktualisiert werden sollen.
   * @return Die Liste der aktualisierten Objekte.
   */
  List<T> update(List<T> genericList);

  /**
   * Aktualisiert die übergebene Liste von Objekten und liefert deren aktualisierte Entitäten wieder zurück. Über den
   * boolschen Parameter kann gesteuert werden, ob ein Flush gegen die Datenbank erfolgen soll.
   * 
   * @param genericList
   *          Die Liste der Objekte, die aktualisiert werden sollen.
   * @param flush
   *          Der Schalter bestimmt, ob ein Flush gegen die Datenbank erfolgen soll.
   * @return Die Liste der aktualisierten Objekte.
   */
  List<T> update(List<T> genericList, boolean flush);

  /**
   * Findet das Objekt mit der übergebenen Objekt ID und liefert die Entität zurück. Wird als Parameter NULL übergeben,
   * gibt die Methode ebenfalls NULL zurück.
   * 
   * @param id
   *          Die Objekt ID zu dem das dazugehörige Objekt in der Datenbank gefunden werden soll
   * @return Das gefundene Objekt.
   */
  T find(Object id);

  /**
   * Liefert alle Objekte vom generischen Typ zurueck.
   * 
   * @return Liste aller Objekte des generischen Typs.
   */
  List<T> findAll();

}
