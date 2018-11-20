package be.vdab.pizzaluigi.valueobjects;

public class Adres {
	private String straat;
	private String huisnr;
	private int postcode;
	private String gemeente;
	
	public Adres(String straat, String huisnr, int postcode, String gemeente) {
		this.straat = straat;
		this.huisnr = huisnr;
		this.postcode = postcode;
		this.gemeente = gemeente;
	}

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public String getHuisnr() {
		return huisnr;
	}

	public void setHuisnr(String huisnr) {
		this.huisnr = huisnr;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getGemeente() {
		return gemeente;
	}

	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}
	
	
}
