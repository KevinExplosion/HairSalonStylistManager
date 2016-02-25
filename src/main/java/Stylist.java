import java.util.List;
import org.sql2o.*;

//establish class
public class Stylist {
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
  public Stylist(String last_name, String first_name) {
    this.last_name = last_name;
    this.first_name = first_name;
  }

  @Override
  public boolean equals(Object otherStylist) {
    if(!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getLast_name().equals(newStylist.getLast_name()) &&
             this.getFirst_name().equals(newStylist.getFirst_name()) &&
             this.getId() == newStylist.getId();
    }
  }

  //all method
  public static List<Stylist> all() {
    String sql = "SELECT id, last_name, first_name FROM stylist";
    //Connection object
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylist (last_name, first_name) VALUES (:last_name, :first_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("last_name", this.last_name)
        .addParameter("first_name", this.first_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
        return stylist;
    }
  }
}
