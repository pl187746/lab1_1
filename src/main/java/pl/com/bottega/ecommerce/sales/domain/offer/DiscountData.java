package pl.com.bottega.ecommerce.sales.domain.offer;

public class DiscountData {
	public String discountCause;
	public Money discount;

	public DiscountData(String discountCause, Money discount) {
		super();
		this.discountCause = discountCause;
		this.discount = discount;
	}

}