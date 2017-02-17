package todobackend.dao;

import java.util.List;

import todobackend.model.Todo;

public interface TodoDao {

  void add(Todo todo);
  
  Todo findById(Long id);
  
  List<Todo> listAll();
  
  void deleteAll();
  
  void deleteById(Long id);
 
  void update(Todo todo);
  
}