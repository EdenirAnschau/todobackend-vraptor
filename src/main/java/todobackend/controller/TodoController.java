package todobackend.controller;

import static br.com.caelum.vraptor.view.Results.http;
import static br.com.caelum.vraptor.view.Results.json;
import static br.com.caelum.vraptor.view.Results.status;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.HeaderParam;
import br.com.caelum.vraptor.Options;
import br.com.caelum.vraptor.Patch;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import todobackend.dao.TodoDao;
import todobackend.model.Todo;

@Controller
public class TodoController {

  private Result result;
  private TodoDao todoDao;
  private final String serverAdress;
  
  protected TodoController() {
    this(null, null, null);
  }
  
  @Inject
  public TodoController(Result result, TodoDao todoDao, 
      @Named("serverAddress") String serverAdress) {
    this.result = result;
    this.todoDao = todoDao;
    this.serverAdress = serverAdress;
  }

  @Get
  @Path("/todos")
  public void getAll() {
    List<Todo> all = todoDao.listAll();
    Iterable<Todo> todosWithUrl = Iterables.transform(all, new Function<Todo, Todo>() {
      @Override
      public Todo apply(Todo input) {
        input.createUrl(serverAdress);
        return input;
      }
    });
    result.use(json()).
      withoutRoot().
      from(ImmutableList.copyOf(todosWithUrl)).
      serialize();
  }
  
  @Get
  @Path("/todos/{id}")
  public void getOne(Long id) {
    Todo todo = todoDao.findById(id);
    if(todo == null) {
      result.use(status()).notFound();
      return;
    }
    todo.createUrl(serverAdress);
    
    result.use(json()).
      withoutRoot().
      from(todo).
      serialize();    
  }
  
  @Consumes("application/json")
  @Post
  @Path("/todos")
  public void create(Todo todo) {
    todoDao.add(todo);
    todo.createUrl(serverAdress);
    
    result.use(json()).
      withoutRoot().
      from(todo).
      serialize();
    result.use(status()).created();
  }
  
  @Delete
  @Path("/todos")
  public void deleteAll() {
    todoDao.deleteAll();
    result.use(status()).accepted();
  }
  
  @Delete
  @Path("/todos/{id}")
  public void deleteById(Long id) {
    todoDao.deleteById(id);
    result.use(status()).ok();
  }
  
  @Consumes("application/json")
  @Patch
  @Path("/todos/{id}")
  public void update(Todo todo, Long id) {
    Todo todoToBeUpdated = todoDao.findById(id);
    if(todoToBeUpdated == null) {
      result.use(status()).notFound();
      return;
    }
    todoDao.update(todoToBeUpdated.update(todo));
    todoToBeUpdated.createUrl(serverAdress);
    result.use(json()).
      withoutRoot().
      from(todoToBeUpdated).
      serialize();
  }
  
  @Options
  @Path("/*")
  public void corsHeaders(
      @HeaderParam("Access-Control-Request-Headers") String acRequestHeaders,
      @HeaderParam("Access-Control-Request-Method") String acRequestMethod) {
    
    result.use(http()).addHeader("Access-Control-Allow-Headers", acRequestHeaders);
    result.use(http()).addHeader("Access-Control-Allow-Methods", acRequestMethod);    
  }
  
}