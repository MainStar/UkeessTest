package ukeesTest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukeesTest.dao.DataFactory;
import ukeesTest.model.Employee;

import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController extends BaseController {

    private List<Employee> employees;

    @RequestMapping("/")
    public String index(Model model){
        try {
            employees = DataFactory.getEmployeesByPage(1);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorNumber", "500");
            return "error";
        }
        model = generateModelForMainController(model, employees);
        return "index";
    }

    @GetMapping("/{pageNumber}")
    public String getPageByNumber(Model model, @PathVariable int pageNumber){
        try {
            employees = DataFactory.getEmployeesByPage(pageNumber);
            model = generateModelForMainController(model, employees);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorNumber", "500");
            return "error";
        }
        return "index";
    }

    @GetMapping("/search/{searchFragment}")
    public String searchEmployeeByFragment(Model model, @PathVariable String searchFragment) {
        try {
            employees = DataFactory.searchByFragment(searchFragment);
            model.addAttribute("action", "found");
            model.addAttribute("name", employees.get(0).getName());
            model.addAttribute("department", employees.get(0).getDepartment().getDepName());
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorNumber", "500");
            return "error";
        }
        return "actions";
    }
}
