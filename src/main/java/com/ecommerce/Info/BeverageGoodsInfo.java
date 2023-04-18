package com.ecommerce.Info;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "goodsID" })
public class BeverageGoodsInfo {

	private long goodsID;

	private String goodsName;

	private String description;

	private int price;

	private int quantity;

	private String imageName;

	private String status;

}
