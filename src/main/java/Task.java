import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private String title;
  private int project_id;

  public Task (String title, int projectId) {
    this.title = title;
    this.project_id = projectId;
  }

  public void save () {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (title, project_id) VALUES (:title, :project_id)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("title", title)
      .addParameter("project_id", project_id)
      .executeUpdate()
      .getKey();
    }
  }

  public int getId() {
    return id;
  }

  public static Task find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks WHERE id=:id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Task.class);
    }
  }
}
