import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.lang.*;

public class EmployeeTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();




  @Test

  public void AddTotalTime_AddsTotalTime_true() {
    Employee newEmployee = new Employee("Bob", "CFO", 299, 1);
    Task newTask = new Task("Create Working Test", 1);

    newTask.save();
    newEmployee.save();

    newEmployee.addTask(newTask.getId());

    newEmployee.startTaskTimer();
    try {
    Thread.sleep(6000);
    } catch (Exception e) {
    }
    
    newEmployee.endTaskTimer();

    newEmployee.addTotalTime(newTask.getId());

    assertTrue(Employee.getTotalTimeGivenEmployeeId(newEmployee.getId(),newTask.getId()) > 0);
  }
}
