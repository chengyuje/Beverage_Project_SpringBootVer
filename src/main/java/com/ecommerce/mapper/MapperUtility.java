package com.ecommerce.mapper;

import org.mapstruct.Named;

public class MapperUtility {

	private static MapperUtility utility = new MapperUtility();

	private MapperUtility() {}
	
	public static MapperUtility getMapperUtility_Instance() {
		return utility;
	}

	@Named(value = "goodsPriceCheck")
	public Integer goodsPriceCheck(int voPrice, int goodsPrice) {
		if (voPrice != 0)
			return Integer.valueOf(voPrice);
		else
			return Integer.valueOf(goodsPrice);
	}

	@Named(value = "goodsQuantityCheck")
	public Integer goodsQuantityCheck(int voQuantity, int goodsQuantity) {
		if (voQuantity != 0)
			return Integer.valueOf(voQuantity);
		else
			return Integer.valueOf(goodsQuantity);
	}

	@Named(value = "orderPriceCheck")
	public long orderPriceCheck(long voPrice, long goodsPrice) {
		if (voPrice != 0)
			return voPrice;
		else
			return goodsPrice;
	}

	@Named(value = "orderQuantityCheck")
	public long orderQuantityCheck(long voQuantity, long goodsQuantity) {
		if (voQuantity != 0)
			return voQuantity;
		else
			return goodsQuantity;
	}

	public int renewCartGoodsQuantity(int dbQuantity, int cartQuantity, int buyQuantity) {
		int renewBuyQuantity = 0;
		if (dbQuantity >= cartQuantity + buyQuantity) {
			renewBuyQuantity = cartQuantity + buyQuantity;
		} else {
			renewBuyQuantity = dbQuantity;
		}
		return renewBuyQuantity;
	}
	

}
