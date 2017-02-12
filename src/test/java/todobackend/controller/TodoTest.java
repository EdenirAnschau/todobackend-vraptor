package todobackend.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import todobackend.model.Todo;

public class TodoTest {

  @Test
  public void shouldCreateUrl()  {
    String server = "http://localhost:8080";
    
    Todo todo = new Todo(); 
    todo.setId(2l);
    todo.setTitle("Test");
    
    assertEquals("http://localhost:8080/todos/2", todo.createUrl(server));
  }
  
  @Test
  public void shouldCreateUrlWithoutPort()  {
    String server = "http://localhost";
    
    Todo todo = new Todo(); 
    todo.setId(2l);
    todo.setTitle("Test");
    
    assertEquals("http://localhost/todos/2", todo.createUrl(server));
  }
  
}