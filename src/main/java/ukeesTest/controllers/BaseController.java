package ukeesTest.controllers;

import org.springframework.ui.Model;
import ukeesTest.model.Employee;

import java.util.List;

public abstract class BaseController {

    protected Model generateModelForMainController(Model model, List<Employee> employees){
        for (int i = 0; i < 5; i++){
            model.addAttribute("viewEmID_" + (i + 1), employees.get(i).getEmployeeId());
            model.addAttribute("editEmID_" + (i + 1), employees.get(i).getEmployeeId());
            model.addAttribute("employeeId_" + (i + 1), employees.get(i).getEmployeeId());
            model.addAttribute("name_" + (i + 1), employees.get(i).getName());
            model.addAttribute("empActive_" + (i + 1), String.valueOf(employees.get(i).isEmpActive()));
            model.addAttribute("depName_" + (i + 1), employees.get(i).getDepartment().getDepName());
            model.addAttribute("deleteEmpId_" + (i + 1), employees.get(i).getEmployeeId());
        }
        return model;
    }

    protected Model generateModelForEmployeeController(Model model, Employee employee, String action){
        model.addAttribute("action", action);
        model.addAttribute("employeeId", employee.getEmployeeId());
        model.addAttribute("name", employee.getName());
        model.addAttribute("empActive", String.valueOf(employee.isEmpActive()));
        model.addAttribute("department", employee.getDepartment().getDepName());
        return model;
    }
}
