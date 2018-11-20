package be.vdab.pizzaluigi.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.services.PizzaService;

@Controller
@RequestMapping("mandje")
public class MandjeController {
	private final Mandje mandje;
	private final PizzaService pizzaservice;
	MandjeController(Mandje mandje, PizzaService pizzaService){
		this.mandje = mandje;
		this.pizzaservice = pizzaService;
	}
	
	private List<Pizza> maakPizzasVanPizzaIds(List<Long> pizzaIds){
		List<Pizza> pizzas = new ArrayList<>(pizzaIds.size());
		for(long id:pizzaIds) {
			pizzaservice.read(id).ifPresent(pizza -> pizzas.add(pizza));
		}
		return pizzas;
	}
	
	private static final String VIEW = "mandje";
	@GetMapping
	ModelAndView toonMandje() {
		return new ModelAndView(VIEW)
				.addObject(new MandjeForm())
				.addObject("allePizzas", pizzaservice.findAll())
				.addObject("pizzasInMandje", maakPizzasVanPizzaIds(mandje.getPizzaIds()));
	}
	
	private static final String REDIRECT_NA_TOEVOEGEN = "redirect:/mandje";
	@PostMapping
	String voegPizzaToeAanMandje(MandjeForm form) {
		mandje.addPizzaId(form.getPizzaId());
		return REDIRECT_NA_TOEVOEGEN;
	}
}
