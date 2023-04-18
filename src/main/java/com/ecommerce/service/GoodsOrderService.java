package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dao.BeverageGoodsJpaDao;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageOrder;
import com.ecommerce.mapper.GoodsOrderMapper;
import com.ecommerce.vo.GoodsOrderVo;
import com.ecommerce.vo.OrderVo;

@Service
public class GoodsOrderService {

	@Autowired
	private BeverageGoodsJpaDao beverageGoodsJpaDao;
	
	@Autowired
	private GoodsOrderMapper converter;

//	public BeverageGoods createGoodsAndOrder(GoodsOrderVo goodsOrderVo) {
//		BeverageGoods goods = BeverageGoods.builder()
//								.goodsName(goodsOrderVo.getGoodsName())
//								.goodsDescription(goodsOrderVo.getDescription())
//								.goodsImageName(goodsOrderVo.getImageName())
//								.goodsPrice(goodsOrderVo.getPrice())
//								.goodsQuantity(goodsOrderVo.getQuantity())
//								.goodsStatus(goodsOrderVo.getStatus()).build();
//		goods = backendDao.save(goods);
//		List<BeverageOrder> beverageOrders = new ArrayList<>();
//		for (OrderVo orderVo : goodsOrderVo.getOrderVos()) {
//			BeverageOrder beverageOrder = BeverageOrder.builder()
//							.goodsID(goods.getGoodsID().longValue())
//							.orderDate(orderVo.getOrderDate())
//							.customerID(orderVo.getCustomerID())
//							.buyQuantity(orderVo.getBuyQuantity())
//							.goodsBuyPrice(orderVo.getGoodsBuyPrice()).build();
//			beverageOrders.add(beverageOrder);
//		}
//		goods.setBeverageOrders(beverageOrders);
//
//		return backendDao.save(goods);
//	}
	
	public BeverageGoods createGoodsAndOrder(GoodsOrderVo goodsOrderVo) {
		BeverageGoods goods = converter.convertToEntity(goodsOrderVo);
		goods = beverageGoodsJpaDao.save(goods);
		List<BeverageOrder> beverageOrders = new ArrayList<>();
		for (OrderVo orderVo : goodsOrderVo.getOrderVos()) {
			BeverageOrder beverageOrder = converter.convertToEntity(orderVo,goods.getGoodsID());
			beverageOrders.add(beverageOrder);
		}
		goods.setBeverageOrders(beverageOrders);

		return beverageGoodsJpaDao.save(goods);
	}

//	@Transactional
//	public BeverageGoods updateGoodsAndOrder(GoodsOrderVo goodsOrderVo) {
//		BeverageGoods goods = null;
//		Optional<BeverageGoods> optGoods = backendDao.findById(new BigDecimal(goodsOrderVo.getGoodsID()));
//		if (optGoods.isPresent()) {
//			goods = optGoods.get();
//			if (goodsOrderVo.getGoodsName() != null)
//				goods.setGoodsName(goodsOrderVo.getGoodsName());
//			if (goodsOrderVo.getDescription() != null)
//				goods.setGoodsDescription(goodsOrderVo.getDescription());
//			if (goodsOrderVo.getImageName() != null)
//				goods.setGoodsImageName(goodsOrderVo.getImageName());
//			if (goodsOrderVo.getPrice() != 0)
//				goods.setGoodsPrice(goodsOrderVo.getPrice());
//			if (goodsOrderVo.getQuantity() != 0)
//				goods.setGoodsQuantity(goodsOrderVo.getQuantity());
//			if (goodsOrderVo.getStatus() != null)
//				goods.setGoodsStatus(goodsOrderVo.getStatus());
//
//			List<BeverageOrder> renewOrder = new ArrayList<>();
//			List<BeverageOrder> dbOrder = goods.getBeverageOrders();
//			for (OrderVo orderVo : goodsOrderVo.getOrderVos()) {
//				BeverageOrder beverageOrder = BeverageOrder.builder().orderID(orderVo.getOrderID()).build();
//				if (dbOrder.contains(beverageOrder))
//					beverageOrder = dbOrder.get(dbOrder.indexOf(beverageOrder));
//				if (orderVo.getOrderDate() != null)
//					beverageOrder.setOrderDate(orderVo.getOrderDate());
//				if (orderVo.getCustomerID() != null)
//					beverageOrder.setCustomerID(orderVo.getCustomerID());
//				beverageOrder.setGoodsID(goods.getGoodsID().longValue());
//				beverageOrder.setGoodsBuyPrice(goods.getGoodsPrice());
//				if (orderVo.getBuyQuantity() != 0)
//					beverageOrder.setBuyQuantity(orderVo.getBuyQuantity());
//				renewOrder.add(beverageOrder);
//			}
//			dbOrder.clear();
//			dbOrder.addAll(renewOrder);
//		}
//		return goods;
//	}
	
	@Transactional
	public BeverageGoods updateGoodsAndOrder(GoodsOrderVo goodsOrderVo) {
		BeverageGoods goods = null;
		BigDecimal goodsID=new BigDecimal(goodsOrderVo.getGoodsID());
		Optional<BeverageGoods> optGoods = beverageGoodsJpaDao.findById(goodsID);
		if (optGoods.isPresent()) {
			goods = optGoods.get();
			converter.updateEntity(goodsOrderVo, goods);
			List<BeverageOrder> renewOrder = new ArrayList<>();
			List<BeverageOrder> dbOrder = goods.getBeverageOrders();
			for (OrderVo orderVo : goodsOrderVo.getOrderVos()) {
				BeverageOrder beverageOrder = BeverageOrder.builder().orderID(orderVo.getOrderID()).build();
				if (dbOrder.contains(beverageOrder))
					beverageOrder = dbOrder.get(dbOrder.indexOf(beverageOrder));
				converter.updateEntity(goodsID, orderVo, beverageOrder);
				renewOrder.add(beverageOrder);
			}
			dbOrder.clear();
			dbOrder.addAll(renewOrder);
		}
		return goods;
	}


}
