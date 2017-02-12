package todobackend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ServerAddressTest {

  @Mock
  private HttpServletRequest request;
  private ServerAddressProducer urlProducer;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    urlProducer = new ServerAddressProducer(request);
  }
  
  @Test
  public void serverAddress() {
    when(request.getScheme()).thenReturn("http");
    when(request.getServerName()).thenReturn("localhost");
    when(request.getServerPort()).thenReturn(8080);
    
    assertEquals("http://localhost:8080", urlProducer.produce());
  }
  
  @Test
  public void serverAddressAppWithoutPort() {
    when(request.getScheme()).thenReturn("http");
    when(request.getServerName()).thenReturn("localhost");
    
    assertEquals("http://localhost", urlProducer.produce());
  }

}