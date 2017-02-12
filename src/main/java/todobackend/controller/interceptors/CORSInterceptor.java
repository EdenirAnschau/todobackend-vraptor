package todobackend.controller.interceptors;

import static br.com.caelum.vraptor.view.Results.http;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

@Intercepts
@RequestScoped
public class CORSInterceptor {

  @Inject
  private Result result;
  
  protected CORSInterceptor() {
    this(null);
  }

  @Inject
  public CORSInterceptor(Result result) {
    this.result = result;
  }

  @AroundCall
  public void intercept(SimpleInterceptorStack stack) {
    result.use(http()).addHeader("Access-Control-Allow-Origin", "*");
    stack.next();
  }
  
}