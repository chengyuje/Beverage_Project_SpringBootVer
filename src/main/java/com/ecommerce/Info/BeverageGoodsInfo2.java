package com.ecommerce.Info;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.entity.BeverageOrder;

import lombok.Data;

@Data
public class BeverageGoodsInfo2 {

	private BigDecimal goodsID;

	private String goodsName;

	private String goodsDescription;

	private Integer goodsPrice;

	private Integer goodsQuantity;

	private String goodsImageName;

	private String goodsStatus;

	private List<BeverageOrder> beverageOrders;

}
