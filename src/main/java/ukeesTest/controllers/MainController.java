package ukeesTest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ukeesTest.dao.DataFactory;
import ukeesTest.model.Employee;

import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController {

    private List<Employee> employees;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView modelAndView){
        try {
            employees = DataFactory.getEmployeesByPage(1, DataFactory.getEmployeesCount());
        } catch (SQLException e) {
            e.printStackTrace();
            modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorNumber", "500");
            return modelAndView;
        }
        modelAndView = new ModelAndView("index");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping("/{pageNumber}")
    public ModelAndView getPageByNumber(ModelAndView modelAndView, @PathVariable int pageNumber){
        try {
            employees = DataFactory.getEmployeesByPage(pageNumber, DataFactory.getEmployeesCount());
            modelAndView = new ModelAndView("index");
            modelAndView.addObject("employees", employees);
        } catch (SQLException e) {
            e.printStackTrace();
            modelAndView.addObject("errorNumber", "500");
            return modelAndView;
        }
        return modelAndView;
    }

    @GetMapping("/search/{searchFragment}")
    public ModelAndView searchEmployeeByFragment(ModelAndView modelAndView, @PathVariable String searchFragment) {
        try {
            employees = DataFactory.searchByFragment(searchFragment);
            modelAndView = new ModelAndView("actions");
            modelAndView.addObject("action", "found");
            modelAndView.addObject("employeeId", employees.get(0).getEmployeeId());
            modelAndView.addObject("name", employees.get(0).getName());
            modelAndView.addObject("empActive", String.valueOf(employees.get(0).isEmpActive()));
            modelAndView.addObject("department", employees.get(0).getDepartment().getDepName());
        } catch (SQLException e) {
            e.printStackTrace();
            modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorNumber", "500");
            return modelAndView;
        }
        return modelAndView;
    }
}
