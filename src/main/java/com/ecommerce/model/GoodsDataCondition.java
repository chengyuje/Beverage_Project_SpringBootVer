package com.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class GoodsDataCondition {

	private Integer goodsID;

	private String goodsName;

	private String priceSort;

	private Integer startPrice;

	private Integer endPrice;

	private Integer quantity;

	private String status;

}
