import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfLastNamesAreTheSame() {
    Client firstClient = new Client("Public", "Jane");
    Client secondClient = new Client("Public", "Jane");
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_returnsTrueIfLastNamesAreTheSame() {
    Client myClient = new Client("Doe", "Jane");
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }
}
