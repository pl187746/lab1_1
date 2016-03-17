/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;

public class OfferItem {

	private ProductData productData;

	private int quantity;

	private Money totalCost = new Money();

	private DiscountData discountData;

	public OfferItem(String productId, Money productPrice, String productName,
			Date productSnapshotDate, String productType, int quantity) {
		this(productId, productPrice, productName, productSnapshotDate, productType, quantity, null, null);
	}

	public OfferItem(String productId, Money productPrice, String productName,
			Date productSnapshotDate, String productType, int quantity,
			Money discount, String discountCause) {
		this.productData = new ProductData(productId, productPrice, productName, productSnapshotDate, productType);

		this.quantity = quantity;
		
		this.discountData = new DiscountData(discountCause, discount);

		BigDecimal discountValue = new BigDecimal(0);
		if (discount != null)
			discountValue = discountValue.subtract(discount.value);

		this.totalCost.value = productPrice.value
				.multiply(new BigDecimal(quantity)).subtract(discountValue);
	}

	public String getProductId() {
		return productData.productId;
	}
	
	public Money getProductPrice() {
		return productData.productPrice;
	}
	
	public String getProductName() {
		return productData.productName;
	}
	
	public Date getProductSnapshotDate() {
		return productData.productSnapshotDate;
	}
	
	public String getProductType() {
		return productData.productType;
	}

	public BigDecimal getTotalCost() {
		return totalCost.value;
	}

	public String getTotalCostCurrency() {
		return totalCost.currency;
	}

	public Money getDiscount() {
		return discountData.discount;
	}

	public String getDiscountCause() {
		return discountData.discountCause;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((discountData.discount == null) ? 0 : discountData.discount.hashCode());
		result = prime * result + ((productData == null) ? 0 : productData.hashCode());
		result = prime * result + quantity;
		result = prime * result
				+ ((totalCost.value == null) ? 0 : totalCost.value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfferItem other = (OfferItem) obj;
		if (discountData.discount == null) {
			if (other.discountData.discount != null)
				return false;
		} else if (!discountData.discount.equals(other.discountData.discount))
			return false;
		if (productData == null) {
			if (other.productData != null)
				return false;
		} else if (!productData.equals(other.productData))
			return false;
		if (quantity != other.quantity)
			return false;
		if (totalCost.value == null) {
			if (other.totalCost.value != null)
				return false;
		} else if (!totalCost.value.equals(other.totalCost.value))
			return false;
		return true;
	}

	/**
	 * 
	 * @param item
	 * @param delta
	 *            acceptable percentage difference
	 * @return
	 */
	public boolean sameAs(OfferItem other, double delta) {
		if (productData == null) {
			if (other.productData != null)
				return false;
		} else if (!productData.equals(other.productData))
			return false;

		if (quantity != other.quantity)
			return false;

		BigDecimal max, min;
		if (totalCost.value.compareTo(other.totalCost.value) > 0) {
			max = totalCost.value;
			min = other.totalCost.value;
		} else {
			max = other.totalCost.value;
			min = totalCost.value;
		}

		BigDecimal difference = max.subtract(min);
		BigDecimal acceptableDelta = max.multiply(new BigDecimal(delta / 100));

		return acceptableDelta.compareTo(difference) > 0;
	}

}
