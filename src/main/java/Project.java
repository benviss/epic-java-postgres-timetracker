import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Project {
  private int id;
  private String title;
  private int client_id;

  public Project (String title, int client_id) {
    this.title = title;
    this.client_id = client_id;
  }

  public void save () {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO projects (title, client_id) VALUES (:title, :client)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("title", title)
      .addParameter("client", client_id)
      .executeUpdate()
      .getKey();
    }
  }

  public static Project find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM projects WHERE id=:id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Project.class);
    }
  }

}
