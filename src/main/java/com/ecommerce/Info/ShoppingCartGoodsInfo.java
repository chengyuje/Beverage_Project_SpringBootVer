package com.ecommerce.Info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder=true)
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = { "goodsID" })
public class ShoppingCartGoodsInfo {
	
	private long goodsID;

	private String goodsName;

	private String description;

	private int buyPrice;

	private int buyQuantity;
	
//	private String message;


}
