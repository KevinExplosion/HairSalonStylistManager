import java.util.List;
import org.sql2o.*;
import java.util.Arrays;

//establish class
public class Stylist {
  //member variables
  private int id;
  private String lastName;
  private String firstName;

  //getters
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
  public Stylist(String lastName, String firstName) {
    this.lastName = lastName;
    this.firstName = firstName;
  }

  @Override
  public boolean equals(Object otherStylist) {
    if(!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getLastName().equals(newStylist.getLastName()) &&
             this.getFirstName().equals(newStylist.getFirstName()) &&
             this.getId() == newStylist.getId();
    }
  }

  //all method
  public static List<Stylist> all() {
    String sql = "SELECT id, lastName, firstName FROM stylists";
    //Connection object
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (lastName, firstName) VALUES (:lastName, :firstName)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("lastName", this.lastName)
        .addParameter("firstName", this.firstName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
        return stylist;
    }
  }

  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where stylistId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Client.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
