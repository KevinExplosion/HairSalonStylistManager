import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Clients.all().size(), 0);
  }
}
