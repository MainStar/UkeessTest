package ukeesTest.controllers;

import org.springframework.ui.Model;
import ukeesTest.model.Employee;

import java.util.List;

public abstract class BaseController {

    protected Model generateModelForEmployeeController(Model model, Employee employee, String action){
        model.addAttribute("action", action);
        model.addAttribute("employeeId", employee.getEmployeeId());
        model.addAttribute("name", employee.getName());
        model.addAttribute("empActive", String.valueOf(employee.isEmpActive()));
        model.addAttribute("department", employee.getDepartment().getDepName());
        return model;
    }
}
