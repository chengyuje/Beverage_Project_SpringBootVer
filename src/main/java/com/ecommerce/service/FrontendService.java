package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.Info.CheckoutCompleteInfo;
import com.ecommerce.Info.GoodsDataInfo;
import com.ecommerce.Info.MemberInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo;
import com.ecommerce.dao.BackendCriteriaDao;
import com.ecommerce.dao.BeverageOrderJpaDao;
import com.ecommerce.dao.FrontendCriteriaDao;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageOrder;
import com.ecommerce.mapper.GoodsOrderMapper;
import com.ecommerce.model.CheckoutReceipt;
import com.ecommerce.model.PageUtility;

@Service
public class FrontendService {

	@Autowired
	private FrontendCriteriaDao frontendCriteriaDao;

	@Autowired
	private BackendCriteriaDao backendCriteriaDao;

	@Autowired
	private BeverageOrderJpaDao beverageOrderJpaDao;
	
	@Autowired
	private GoodsOrderMapper converter;

	public GoodsDataInfo queryPageGoodsWithKeyword(String searchKeyword, PageUtility pageUtility, Pageable pageable) {
		Page<BeverageGoods> pageGoods = frontendCriteriaDao.queryPageGoodsWithKeyword(searchKeyword, pageable);
		int totalDataSize = (int) pageGoods.getTotalElements();
		int currentPageNo = pageUtility.getCurrentPageNo();
		int pageDataSize = pageUtility.getPageDataSize();
		int pageIconSize = pageUtility.getPageIconSize();
		List<Integer> pageList = getPageList(totalDataSize, pageDataSize);
		int endPageNo = pageList.get(pageList.size() - 1);
		List<Integer> showPageIcon = getShowPageIcon(currentPageNo, pageIconSize, endPageNo);
		pageUtility = pageUtility.toBuilder().totalDataSize(totalDataSize).endPageNo(endPageNo)
				.showPageIcon(showPageIcon).build();

		GoodsDataInfo goodsDataInfo = GoodsDataInfo.builder().dataList(pageGoods.toList()).pageUtility(pageUtility)
				.build();
		return goodsDataInfo;
	}

//	public CheckoutCompleteInfo checkoutGoods(MemberInfo sessionMemberInfo, List<ShoppingCartGoodsInfo> cartGoods) {
//		List<BeverageOrder> orderList = new ArrayList<>();
//		List<CheckoutReceipt> receiptList = new ArrayList<>();
//		int totalAmount = 0;
//		for (ShoppingCartGoodsInfo eachCartGoods : cartGoods) {
//			BeverageGoods goods = backendCriteriaDao.queryGoodsByID(eachCartGoods.getGoodsID());
//			int dbQuantity = goods.getGoodsQuantity();
//			int buyQuantity = eachCartGoods.getBuyQuantity();
//			if (dbQuantity < buyQuantity) {
//				buyQuantity = dbQuantity;
//			}
//			CheckoutReceipt receipt = CheckoutReceipt.builder().goodsName(eachCartGoods.getGoodsName())
//					.buyPrice(eachCartGoods.getBuyPrice()).buyQuantity(buyQuantity).build();
//			receiptList.add(receipt);
//
//			totalAmount += buyQuantity * eachCartGoods.getBuyPrice();
//			goods.setGoodsQuantity(dbQuantity - buyQuantity);
//			BeverageOrder order = BeverageOrder.builder().orderDate(LocalDateTime.now())
//					.customerID(sessionMemberInfo.getIdCardNo()).goodsID(eachCartGoods.getGoodsID())
//					.goodsBuyPrice(eachCartGoods.getBuyPrice()).buyQuantity(eachCartGoods.getBuyQuantity()).goods(goods)
//					.build();
//			orderList.add(order);
//		}
//		beverageOrderJpaDao.saveAll(orderList);
//		CheckoutCompleteInfo checkoutCompleteInfo = CheckoutCompleteInfo.builder()
//				.customerName(sessionMemberInfo.getCustomerName()).receipt(receiptList).totalAmount(totalAmount)
//				.build();
//
//		return checkoutCompleteInfo;
//	}
	
	public CheckoutCompleteInfo checkoutGoods(MemberInfo sessionMemberInfo, List<ShoppingCartGoodsInfo> cartGoods) {
		List<BeverageOrder> orderList = new ArrayList<>();
		List<CheckoutReceipt> receiptList = new ArrayList<>();
		int totalAmount = 0;
		for (ShoppingCartGoodsInfo eachCartGoods : cartGoods) {
			BeverageGoods goods = backendCriteriaDao.queryGoodsByID(eachCartGoods.getGoodsID());
			int dbQuantity = goods.getGoodsQuantity();
			int buyQuantity = eachCartGoods.getBuyQuantity();
			if (dbQuantity < buyQuantity) {
				buyQuantity = dbQuantity;
			}
			CheckoutReceipt receipt = converter.convertToEntity(eachCartGoods, goods.getGoodsImageName());
			receiptList.add(receipt);
			totalAmount += buyQuantity * eachCartGoods.getBuyPrice();
			goods.setGoodsQuantity(dbQuantity - buyQuantity);
			BeverageOrder order =converter.convertToEntity(eachCartGoods, LocalDateTime.now(), sessionMemberInfo.getIdCardNo(), goods); 
			orderList.add(order);
		}
		beverageOrderJpaDao.saveAll(orderList);
		CheckoutCompleteInfo checkoutCompleteInfo =converter.convertToEntity(sessionMemberInfo.getCustomerName(), receiptList, totalAmount);
		return checkoutCompleteInfo;
	}

	public List<Integer> getPageList(int totalDataSize, int pageDataSize) {
		List<Integer> pageList = new ArrayList<>();
		for (Integer i = 1; i <= totalDataSize / pageDataSize; i++) {
			pageList.add(i);
			if (i == totalDataSize / pageDataSize && totalDataSize % pageDataSize != 0) {
				pageList.add(++i);
			}
		}
		return pageList;
	}

	public List<Integer> getShowPageIcon(int currentPageNo, int pageIconSize, int endPageNo) {
		List<Integer> showPageIcon = new ArrayList<>();
		Integer startPage = 1;
		if ((currentPageNo % pageIconSize == 1 || pageIconSize == 1) && currentPageNo != 1)
			startPage = currentPageNo;
		else if (currentPageNo == endPageNo || currentPageNo % pageIconSize == 0) {
			for (int i = currentPageNo; i >= 1; i--) {
				if (i % pageIconSize == 1) {
					startPage = i;
					break;
				}
			}
		}

		while (showPageIcon.size() < pageIconSize) {
			if (startPage > endPageNo)
				break;
			showPageIcon.add(startPage++);
		}
		return showPageIcon;
	}

}
