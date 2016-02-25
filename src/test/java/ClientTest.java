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
  public void equals_returnsTrueIfFirstAndLastNamesAreTheSame() {
    Client firstClient = new Client("Public", "Jane", 1);
    Client secondClient = new Client("Public", "Jane", 1);
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_returnsTrueIfLastNamesAreTheSame() {
    Client myClient = new Client("Doe", "Jane", 1);
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

  @Test
  public void save_assignsIdToObject() {
    Client myClient = new Client("Doe", "Jane", 1);
    myClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(myClient.getId(), savedClient.getId());
  }

  @Test
  public void find_findsClientInDatabase_true() {
    Client myClient = new Client("Doe", "Jane", 1);
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getLastName(), "Doe");
  }

  @Test
  public void save_savesStylistIdIntoDB_true() {
    Stylist myStylist = new Stylist("Styles", "Julia");
    myStylist.save();
    Client myClient = new Client("Doe", "Jane", myStylist.getId());
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getStylistId(), myStylist.getId());
  }
}
