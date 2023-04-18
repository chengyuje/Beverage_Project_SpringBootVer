package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Info.MemberInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo;
import com.ecommerce.dao.MemberCriteriaDao;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageMember;
import com.ecommerce.mapper.BeverageGoodsMapper;

@Service
public class MemberService {

	@Autowired
	private MemberCriteriaDao memberCriteriaDao;

	@Autowired
	private BeverageGoodsMapper converter;

	public BeverageMember login(MemberInfo memberInfo) {
		return memberCriteriaDao.login(memberInfo);
	}

	public List<ShoppingCartGoodsInfo> addCartGoods(List<ShoppingCartGoodsInfo> cartGoods, long id, long buyQuantity) {
		BeverageGoods dbGoodsInfo = memberCriteriaDao.findGoodsByID(id);
		ShoppingCartGoodsInfo cartGoodsInfo = ShoppingCartGoodsInfo.builder().goodsID(id).build();
		if (cartGoods.contains(cartGoodsInfo)) {
			int goodsIndex = cartGoods.indexOf(cartGoodsInfo);
			cartGoodsInfo = cartGoods.get(goodsIndex);
			int cartGoodsBuyQuantity = cartGoodsInfo.getBuyQuantity();
			cartGoodsInfo=converter.checkGoodsInfo(dbGoodsInfo, cartGoodsBuyQuantity, (int)buyQuantity);
			cartGoods.set(goodsIndex, cartGoodsInfo);
		} else {
			int cartGoodsBuyQuantity=0;
			cartGoodsInfo=converter.checkGoodsInfo(dbGoodsInfo, cartGoodsBuyQuantity, (int)buyQuantity);
			cartGoods.add(cartGoodsInfo);
		}
		return cartGoods;
	}

}
