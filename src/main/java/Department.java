import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Department {
  public int id;
  public String name;
  public ArrayList<Employee> employees = new ArrayList<>();


  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO departments (name) VALUES (:name)", true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

public void addProject(int _project) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO departments_project_link (department_id, project_id) VALUES (:department_id, :project_id)";
    con.createQuery(sql)
    .addParameter("department_id", this.id)
    .addParameter("project_id", _project)
    .executeUpdate();
  }
}

public List<Project> getAllProjects() {
  List<Integer> projectIds;

  List<Project> projects = new ArrayList<>();


  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT project_id FROM department_project_link WHERE department_id=:department_id";
    projectIds = con.createQuery(sql)
    .addParameter("department_id", this.id)
    executeAndFetch(Integer.class);

    for (projectId : projectIds) {
      String sql2 = "SELECT * FROM projects WHERE id=:project_id"
      Project project = con.createQuery(sql2)
      .addParameter("project_id", projectId)
      .executeAndFetchFirst(Project.class);

      projects.add(project);

    }
  }
  return projects;
}

}
