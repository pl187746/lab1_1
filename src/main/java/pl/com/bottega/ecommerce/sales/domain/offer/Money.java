package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;

public class Money {
	public final BigDecimal value;
	public final String currency;

	public Money(BigDecimal value, String currency) {
		super();
		this.value = value;
		this.currency = currency;
	}
}