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
  public Clients(String last_name, String first_name) {
    this.last_name = last_name;
    this.first_name = first_name;
  }

  //all method
  public static List<Client> all() {
    String sql = "SELECT id, last_name, first_name FROM clients";
    //Connection object
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }
}
