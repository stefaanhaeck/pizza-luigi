package be.vdab.pizzaluigi.web;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class VanTotPrijsForm1 {
	@NotNull @PositiveOrZero
	private BigDecimal van;
	@NotNull @PositiveOrZero
	private BigDecimal tot;
	
	public BigDecimal getVan() {
		return van;
	}
	public void setVan(BigDecimal van) {
		this.van = van;
	}
	public BigDecimal getTot() {
		return tot;
	}
	public void setTot(BigDecimal tot) {
		this.tot = tot;
	}
	
	
}
