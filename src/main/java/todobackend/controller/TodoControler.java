package todobackend.controller;

import static br.com.caelum.vraptor.view.Results.status;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("/todo")
public class TodoControler {

  private Result result;
  
  @Inject
  public TodoControler(Result result) {
    this.result = result;
  }
  
  public TodoControler() {
    this(null);
  }

  @Path("/hello")
  public void test() {
    result.use(status()).ok();
  }
  
}