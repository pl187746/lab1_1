package pl.com.bottega.ecommerce.sales.domain.offer;

public class DiscountData {
	public final String discountCause;
	public final Money discount;

	public DiscountData(String discountCause, Money discount) {
		super();
		this.discountCause = discountCause;
		this.discount = discount;
	}

}