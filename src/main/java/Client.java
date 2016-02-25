import java.util.List;
import org.sql2o.*;

//establish class
public class Client {
  //member variables
  private int id;
  private String last_name;
  private String first_name;

  //getters
  public int getId() {
    return id;
  }

  public String getLast_name() {
    return last_name;
  }

  public String getFirst_name() {
    return first_name;
  }

  //Constructor (String/int/bool/etc. arguments)
  public Client(String last_name, String first_name) {
    this.last_name = last_name;
    this.first_name = first_name;
  }

  @Override
  public boolean equals(Object otherClient) {
    if(!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getLast_name().equals(newClient.getLast_name()) &&
             this.getFirst_name().equals(newClient.getFirst_name()) &&
             this.getId() == newClient.getId();
    }
  }

  //all method
  public static List<Client> all() {
    String sql = "SELECT id, last_name, first_name FROM clients";
    //Connection object
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (last_name, first_name) VALUES (:last_name, :first_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("last_name", this.last_name)
        .addParameter("first_name", this.first_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Clients where id=:id";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
        return client;
    }
  }
}
