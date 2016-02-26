import java.util.List;
import org.sql2o.*;

//establish class
public class Client {
  //member variables
  private int id;
  private int stylistId;
  private String lastName;
  private String firstName;

  //getters
  public int getStylistId() {
    return stylistId;
  }

  public int getId() {
    return id;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  //Constructor (String/int/bool/etc. arguments)
  public Client(String lastName, String firstName, int stylistId) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.stylistId = stylistId;
  }

  @Override
  public boolean equals(Object otherClient) {
    if(!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getLastName().equals(newClient.getLastName()) &&
             this.getFirstName().equals(newClient.getFirstName()) &&
             this.getId() == newClient.getId() &&
             this.getStylistId() == newClient.getStylistId();
    }
  }

  //all method
  public static List<Client> all() {
    String sql = "SELECT id, lastName, firstName, stylistId FROM clients";
    //Connection object
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (lastName, firstName, stylistId) VALUES (:lastName, :firstName, :stylistId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("lastName", this.lastName)
        .addParameter("firstName", this.firstName)
        .addParameter("stylistId", this.stylistId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where id=:id";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
        return client;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
