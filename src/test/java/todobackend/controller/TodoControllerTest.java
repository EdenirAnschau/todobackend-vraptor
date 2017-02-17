package todobackend.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.ImmutableList;

import br.com.caelum.vraptor.util.test.MockSerializationResult;
import todobackend.dao.TodoDao;
import todobackend.model.Todo;

public class TodoControllerTest {

  private MockSerializationResult result;
  private TodoController controller;
  @Mock
  private TodoDao todoDao;
  private Todo todo;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    result = new MockSerializationResult();
    controller = new TodoController(result, todoDao, "");
    
    todo = new Todo();
    todo.setId(1l);
    todo.setTitle("my task");
    todo.setOrder(10);
  }

  @Test
  public void shouldListAllTodo() throws Exception {
    when(todoDao.listAll()).thenReturn((ImmutableList.of(todo)));
    controller.getAll();
    assertEquals(
        "[{\"id\":1,\"title\":\"my task\",\"completed\":false,\"order\":10,\"url\":\"/todos/1\"}]",
         result.serializedResult());
  }
  
  @Test
  public void shouldGetById() throws Exception {
    when(todoDao.findById(1l)).thenReturn((todo));
    controller.getOne(1l);
    assertEquals(
        "{\"id\":1,\"title\":\"my task\",\"completed\":false,\"order\":10,\"url\":\"/todos/1\"}",
         result.serializedResult());
  }
  
  @Test
  public void shouldCreateTodo() throws Exception {
    controller.create(todo);
    
    verify(todoDao).add(todo);
    assertEquals(
        "{\"id\":1,\"title\":\"my task\",\"completed\":false,\"order\":10,\"url\":\"/todos/1\"}",
         result.serializedResult());
  }
  
  @Test
  public void shouldDeleteAll() throws Exception {
    controller.deleteAll();
    
    verify(todoDao).deleteAll();
  }
  
  @Test
  public void shouldDeleteById() throws Exception {
    controller.deleteById(1l);
    
    verify(todoDao).deleteById(1l);
  }
  
  @Test
  public void shouldUpdateWithPatch() throws Exception {
    when(todoDao.findById(1l)).thenReturn((todo));
    
    todo.setTitle("task 2");
    todo.setCompleted(true);
    todo.setOrder(1);
    controller.update(todo, 1l);
    
    assertEquals(
        "{\"id\":1,\"title\":\"task 2\",\"completed\":true,\"order\":1,\"url\":\"/todos/1\"}",
         result.serializedResult());
  }

}