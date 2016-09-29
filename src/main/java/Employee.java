import java.util.ArrayList;
import java.util.List;
import java.util.*;
import org.sql2o.*;
import java.sql.Timestamp;

public class Employee {
  private int id;
  private String name;
  private String position;
  private int rate;
  private int department_id;

  public Employee (String name, String position, int rate, int departmentId) {
    this.position = position;
    this.rate = rate;
    this.department_id = departmentId;
    this.name = name;
  }

  public void save () {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO employees (name, position, rate, department_id) VALUES (:name, :position, :rate, :department_id)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("position", position)
      .addParameter("rate", rate)
      .addParameter("name", name)
      .addParameter("department_id", department_id)
      .executeUpdate()
      .getKey();
    }
  }



  public static Employee find (int _id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM employees WHERE id=:id";
      return con.createQuery(sql)
      .addParameter("id", _id)
      .executeAndFetchFirst(Employee.class);
    }
  }

  public void addTask(int task_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO employee_task_time (employee_id, task_id) VALUES (:employee_id, :task_id)";
      con.createQuery(sql)
      .addParameter("employee_id", this.id)
      .addParameter("task_id", task_id)
      .executeUpdate();
    }
  }

  public void startTaskTimer() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE employee_task_time SET timestart = now() WHERE id=:id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void endTaskTimer() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE employee_task_time SET timeend = now() WHERE id=:id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void addTotalTime(int _task_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT timestart FROM employee_task_time WHERE employee_id=:employee_id AND task_id=:task_id";
      Timestamp timestart = con.createQuery(sql)
      .addParameter("employee_id",this.id)
      .addParameter("task_id", _task_id)
      .executeAndFetchFirst(Timestamp.class);

      String sql2 = "SELECT timeend FROM employee_task_time WHERE employee_id=:employee_id AND task_id=:task_id";
      Timestamp timeend = con.createQuery(sql2)
      .addParameter("employee_id",this.id)
      .addParameter("task_id", _task_id)
      .executeAndFetchFirst(Timestamp.class);

      long totalTime = timeend.getTime() - timestart.getTime();

      String sql3 = "UPDATE employee_task_time SET totalTime = :totalTime WHERE employee_id=:employee_id AND task_id=:task_id";
      con.createQuery(sql3)
      .addParameter("employee_id",this.id)
      .addParameter("task_id", _task_id)
      .addParameter("totalTime", (int) totalTime)
      .executeUpdate();
    }
  }

  public static Integer getTotalTimeGivenEmployeeId(int employee_id, int task_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT totalTime FROM employee_task_time WHERE employee_id=:employee_id AND task_id=:task_id";
      return con.createQuery(sql)
      .addParameter("employee_id",employee_id)
      .addParameter("task_id", task_id)
      .executeAndFetchFirst(Integer.class);
    }
  }

  public int getId () {
    return id;
  }

  public String getPosition() {
    return position;
  }

  public int getRate () {
    return rate;
  }

  public int getDepartmentId () {
    return department_id;
  }
}
