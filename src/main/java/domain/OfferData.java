package domain;

public class OfferData {
	public double price;
	public int status;
	public String description;
	
	public OfferData(double price, int status, String description) {
		this.status = status;
		this.price = price;
		this.description = description;
	}
	
}
