package be.vdab.pizzaluigi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.entities.Employee;

@Controller
public class EmployeeController {

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("employee", "employee1", new Employee());
    }
    
}
