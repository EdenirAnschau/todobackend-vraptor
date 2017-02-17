package todobackend;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

public class ServerAddressProducer {

  private HttpServletRequest request;

  @Inject
  public ServerAddressProducer(HttpServletRequest request) {
    this.request = request;
  }

  @Produces
  @Named("serverAddress")
  public String produce() {
    String port = request.getServerPort() != 0 ? 
         String.format(":%s", request.getServerPort()) : "";
    return String.format("%s://%s%s", request.getScheme(), 
        request.getServerName(), port);
  }

}