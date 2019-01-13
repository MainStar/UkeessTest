package ukeesTest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukeesTest.dao.DataFactory;
import ukeesTest.model.Employee;

import java.sql.SQLException;

@Controller
public class EmployeeController extends BaseController {

    @PutMapping("/employee/{empId}")
    public String editEmployee(Model model, @RequestBody Employee employee) {
        try {
            DataFactory.editEmployee(employee);
            Employee employee1 = DataFactory.getEmployeeById(employee.getEmployeeId());
            model = generateModelForEmployeeController(model, employee1, "edited");
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorNumber", "500");
        }
        return "actions";
    }

    @GetMapping("/employee/{empId}")
    public String getEmployee(Model model, @PathVariable int empId) {
        Employee employee = null;
        try {
            employee = DataFactory.getEmployeeById(empId);
            model = generateModelForEmployeeController(model, employee, "found");
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorNumber", "500");
            return "error";
        }
        return "actions";
    }

    @GetMapping("/employee/{empId}/delete")
    public String deleteEmployee(Model model,@PathVariable int empId) {
        Employee employee = null;
        try {
            employee = DataFactory.getEmployeeById(empId);
            DataFactory.deleteEmployee(empId);
            model = generateModelForEmployeeController(model, employee, "deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorNumber", "500");
            return "error";
        }
        return "actions";
    }

}
