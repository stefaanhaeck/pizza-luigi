package be.vdab.pizzaluigi.repositories;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.vdab.exceptions.PizzaNietGevondenException;
import be.vdab.pizzaluigi.entities.Pizza;

@Repository
public class jdbcPizzaRepository implements PizzaRepository{
	private final NamedParameterJdbcTemplate template;
	private static final String SELECT_AANTAL_PIZZAS = "select count(*) from pizzas";
	private static final String DELETE_PIZZA = "delete from pizzas where id=:id";
	private static final String UPDATE_PIZZA =   "update pizzas set naam = :naam, prijs = :prijs, pikant = :pikant where id = :id"; 
	private final SimpleJdbcInsert insert;
	private static final String SELECT_ALL =   "select id, naam, prijs, pikant from pizzas order by id";
	private static final String SELECT_BY_PRIJS_BETWEEN =   "select id, naam, prijs, pikant from pizzas where prijs between :van and :tot order by prijs"; 
	private static final String READ =   "select id, naam, prijs, pikant from pizzas where id= :id";
	private static final String SELECT_UNIEKE_PRIJZEN =   "select distinct prijs from pizzas order by prijs"; 
	private final RowMapper<Pizza> pizzaRowMapper = (resultSet, rowNum) ->
	new Pizza(resultSet.getLong("id"), resultSet.getString("naam"),     
			resultSet.getBigDecimal("prijs"),     
			resultSet.getBoolean("pikant"));
	private final RowMapper <BigDecimal> prijzenRowMapper = (resultSet, rowNun) -> resultSet.getBigDecimal("prijs");
	private static final String SELECT_BY_PRIJS =   "select id, naam, prijs, pikant from pizzas where prijs= :prijs"; 
	
	public jdbcPizzaRepository(NamedParameterJdbcTemplate  template, DataSource dataSource) {
		this.template = template;
		this.insert = new SimpleJdbcInsert(dataSource);
		insert.withTableName("pizzas");
		insert.usingGeneratedKeyColumns("id");
	}

	@Override
	public void create(Pizza pizza) {
		Map<String, Object> kolomWaarden = new HashMap<>();
		kolomWaarden.put("naam", pizza.getNaam());
		kolomWaarden.put("prijs", pizza.getPrijs());
		kolomWaarden.put("pikant", pizza.isPikant());
		Number id = insert.executeAndReturnKey(kolomWaarden);
		pizza.setId(id.longValue());
		
	}

	@Override
	public Optional<Pizza> read(long id) {
		try {
			return Optional.of(template.queryForObject(READ, 
					Collections.singletonMap("id", id), pizzaRowMapper));
		}catch(IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}
	}

	@Override
	public void update(Pizza pizza) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("naam", pizza.getNaam());
		parameters.put("prijs", pizza.getPrijs());
		parameters.put("pikant", pizza.isPikant());
		parameters.put("id", pizza.getId());
		if(template.update(UPDATE_PIZZA, parameters)==0) {
			throw new PizzaNietGevondenException();
		}
	}

	@Override
	public void delete(long id) {
		template.update(DELETE_PIZZA, Collections.singletonMap("id", id));
		
	}

	@Override
	public List<Pizza> findAll() {
		 return template.query(SELECT_ALL, pizzaRowMapper);
	}

	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("van", van);
		parameters.put("tot", tot);
		return template.query(SELECT_BY_PRIJS_BETWEEN, parameters, pizzaRowMapper);
	}

	@Override
	public long findAantalPizzas() {
		return (long) template.queryForObject(SELECT_AANTAL_PIZZAS, Collections.EMPTY_MAP, Long.class);
	}

	@Override
	public List<BigDecimal> findUniekePrijzen() {
		return template.query(SELECT_UNIEKE_PRIJZEN, prijzenRowMapper);
	}

	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		return template.query(SELECT_BY_PRIJS, Collections.singletonMap("prijs", prijs), pizzaRowMapper);
	}
}
