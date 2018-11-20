package be.vdab.pizzaluigi.web;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.pizzaluigi.entities.Employee;
import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.services.EuroService;
import be.vdab.pizzaluigi.services.PizzaService;
import be.vdab.pizzaluigi.valueobjects.Dollar;

@Controller
@RequestMapping("pizzas")
public class PizzaController {	
	private final EuroService euroService;
	private final PizzaService pizzaService;
	
	PizzaController(EuroService euroService, PizzaService pizzaService){
		this.euroService = euroService;
		this.pizzaService = pizzaService;
	}
	
	private static final String PIZZAS_VIEW = "pizzas";
	@GetMapping
	ModelAndView Pizzas() {
		return new ModelAndView(PIZZAS_VIEW, "pizzas", pizzaService.findAll());
	}
	
	private static final String PIZZA_VIEW = "pizza";
	@GetMapping("{id}")
	ModelAndView pizza(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView(PIZZA_VIEW);
		pizzaService.read(id).ifPresent(pizza ->{
			modelAndView.addObject(pizza);
			modelAndView.addObject("inDollar", new Dollar(euroService.naarDollar(pizza.getPrijs())));
		});		
		return modelAndView;
	}
	
	private static final String PRIJZEN_VIEW = "prijzen";
	@GetMapping("prijzen")
	ModelAndView prijzen() {
		return new ModelAndView(PRIJZEN_VIEW, "prijzen", pizzaService.findUniekePrijzen());
	}
	
	@GetMapping(params="prijs")
	ModelAndView pizzasVanPrijs(BigDecimal prijs) {
		return new ModelAndView(PRIJZEN_VIEW, "pizzas",
				pizzaService.findByPrijs(prijs))
				.addObject("prijs", prijs)
				.addObject("prijzen", pizzaService.findUniekePrijzen());
	}

    @GetMapping("vantotprijs")
    public ModelAndView showForm() {
    	VanTotPrijsForm1 form = new VanTotPrijsForm1();
    	form.setVan(BigDecimal.ZERO);
    	form.setTot(BigDecimal.ZERO);
        return new ModelAndView("vantotprijs").addObject(form);
    }
    
	@GetMapping(params = {"van", "tot"})
	ModelAndView findVanTotPrijs(@Valid VanTotPrijsForm1 form, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("vantotprijs");
		if(bindingResult.hasErrors()) {
			return modelAndView;
		}
		List<Pizza> pizzas = pizzaService.findByPrijsBetween(form.getVan(), form.getTot());
		if(pizzas.isEmpty()) {
			bindingResult.reject("geenPizzas");
		}else {
			modelAndView.addObject("pizzas", pizzas);
		}
		return modelAndView;
	}
	
	private static final String TOEVOEGEN_VIEW = "toevoegen";
	private static final String REDIRECT_URL_NA_TOEVOEGEN="redirect:/pizzas";
	@GetMapping("toevoegen")
	ModelAndView toevoegen() {
		return new ModelAndView(TOEVOEGEN_VIEW).addObject(new Pizza());
	}
	
	@PostMapping("toevoegen")
	ModelAndView toevoegen(@Valid Pizza pizza, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return new ModelAndView(TOEVOEGEN_VIEW);
		}
		pizzaService.create(pizza);
		redirectAttributes.addAttribute("boodschap","Pizza toegevoegd");
		return new ModelAndView(REDIRECT_URL_NA_TOEVOEGEN);
	}
}
