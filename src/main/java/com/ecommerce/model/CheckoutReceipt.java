package com.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class CheckoutReceipt {
	
	private String goodsName;
	
	private String goodsImage;
	
	private Integer buyPrice;
	
	private Integer buyQuantity;

}
