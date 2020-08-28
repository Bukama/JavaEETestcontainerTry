
package de.test.genericdao.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface for dynamic but typesafe CRUD and FIND methods.
 * 
 */
public interface IGenericDao<T> {

  /**
   * Creates the passed, generic object and returns the entity.
   * If an flush on the database occurs depends on the default value.
   * 
   * @param generic
   *          The object to be created
   * @return Created entity
   */
  T create(T generic);

  /**
   * Creates the passed, generic object and returns the entity.
   *
   * @param generic
   *          The object to be created
   * @param flush
   *        Parameter to define if a flush shall be done or not
   * @return Created entity
   */
  T create(T generic, boolean flush);

  /**
   * Creates the passed, generic list of objects and returns the entities.
   * If an flush on the database occurs depends on the default value.
   *
   * @param genericList
   *          The objects to be created
   * @return Created entities, never null.
   */
  List<T> create(List<T> genericList);

  /**
   * Creates the passed, generic list of objects and returns the entities.
   *
   * @param genericList
   *          The objects to be created
   * @param flush
   *        Parameter to define if a flush shall be done or not
   * @return Created list of entities, never null.
   */
  List<T> create(List<T> genericList, boolean flush);

  /**
   * Deletes the given object.
   * If an flush on the database occurs depends on the default value.
   * 
   * @param id
   *          Object to be deleted
   */
  void delete(Object id);

  /**
   * Deletes the given object.
   *
   * @param id
   *          Object to be deleted
   * @param flush
   *        Parameter to define if a flush shall be done or not
   */
  void delete(Object id, boolean flush);

  /**
   * Updates the given object.
   * If an flush on the database occurs depends on the default value.
   *
   * @param generic
   *          Object to be deleted
   * @return The updated object
   */
  T update(T generic);

  /**
   * Updates the given object.
   *
   * @param generic
   *          Object to be deleted
   * @param flush
   *        Parameter to define if a flush shall be done or not
   * @return The updated object
   */
  T update(T generic, boolean flush);

  /**
   * Updates the given list of objects.
   * If an flush on the database occurs depends on the default value.
   *
   * @param genericList
   *          Objects to be updated
   * @return The updated objects, never null.
   */
  List<T> update(List<T> genericList);

  /**
   * Updates the given list of objects.
   *
   * @param genericList
   *          Objects to be updated
   * @param flush
   *        Parameter to define if a flush shall be done or not
   * @return The updated objects, never null.
   */
  List<T> update(List<T> genericList, boolean flush);

  /**
   * Finds the object with the given id.
   * 
   * @param id
   *          Id of the object to be found
   * @return Optional containing the object. Empty if nothing was found or NULL was passed as an id.
   */
  Optional<T> find(Object id);

  /**
   * Finds all objects of the dynamic type.
   * 
   * @return List of objects, never null.
   */
  List<T> findAll();

}
