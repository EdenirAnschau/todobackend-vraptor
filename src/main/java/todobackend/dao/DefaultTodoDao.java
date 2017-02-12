package todobackend.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import todobackend.model.Todo;

public class DefaultTodoDao implements TodoDao {

  private final EntityManager em;

  @Inject
  public DefaultTodoDao(EntityManager em) {
    this.em = em;
  }

  @Override
  public void add(Todo todo) {
    em.persist(todo);
  }

  @Override
  public Todo findById(Long id) {
    return em.find(Todo.class, id);
  }

  @Override
  public List<Todo> listAll() {
    return em.createQuery("select t from Todo t", Todo.class).getResultList();
  }

  @Override
  public void deleteAll() {
    em.createQuery("delete from Todo").executeUpdate();
  }

  @Override
  public void deleteById(Long id) {
    em.createQuery("delete from Todo t where t.id=:id")
      .setParameter("id", id)
      .executeUpdate();
  }

  @Override
  public void update(Todo todo) {
    em.merge(todo);
  }
  
}