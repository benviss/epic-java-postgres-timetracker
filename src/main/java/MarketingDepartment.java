import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class MarketingDepartment extends Department {

  public MarketingDepartment (String name) {
    this.name = name;
  }

  public List<Employee> allEmployees() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM employees WHERE department_id=:id";
      return con.createQuery(sql)
      .addParameter ("id", id)
      .executeAndFetch(Employee.class);
    }
  }
}
