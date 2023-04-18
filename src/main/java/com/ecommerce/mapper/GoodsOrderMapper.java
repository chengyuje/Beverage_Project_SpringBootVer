package com.ecommerce.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ecommerce.Info.BeverageGoodsInfo2;
import com.ecommerce.Info.CheckoutCompleteInfo;
import com.ecommerce.Info.GoodsSalesInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageOrder;
import com.ecommerce.model.CheckoutReceipt;
import com.ecommerce.model.GoodsReportSales;
import com.ecommerce.model.PageUtility;
import com.ecommerce.vo.GoodsOrderVo;
import com.ecommerce.vo.OrderVo;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GoodsOrderMapper {

	MapperUtility utility = MapperUtility.getMapperUtility_Instance();

	@Mapping(target = "goodsID", ignore = true)
	@Mapping(source = "description", target = "goodsDescription")
	@Mapping(source = "price", target = "goodsPrice")
	@Mapping(source = "quantity", target = "goodsQuantity")
	@Mapping(source = "imageName", target = "goodsImageName")
	@Mapping(source = "status", target = "goodsStatus")
//	@Mapping(source = "orderVos", target = "beverageOrders")
	BeverageGoods convertToEntity(GoodsOrderVo goodsOrderVo);

	@Mapping(source = "id", target = "goodsID")
	BeverageOrder convertToEntity(OrderVo orderVo, BigDecimal id);

	@Mapping(target = "goodsID", ignore = true)
	@Mapping(source = "description", target = "goodsDescription")
	@Mapping(target = "goodsPrice", expression = "java(utility.goodsPriceCheck(goodsOrderVo.getPrice(),beverageGoods.getGoodsPrice()))")
	@Mapping(target = "goodsQuantity", expression = "java(utility.goodsQuantityCheck(goodsOrderVo.getQuantity(),beverageGoods.getGoodsQuantity()))")
	@Mapping(source = "imageName", target = "goodsImageName")
	@Mapping(source = "status", target = "goodsStatus")
	void updateEntity(GoodsOrderVo goodsOrderVo, @MappingTarget BeverageGoods beverageGoods);

	@Mapping(source = "id", target = "goodsID")
	@Mapping(target = "goodsBuyPrice", expression = "java(utility.orderPriceCheck(orderVo.getGoodsBuyPrice(),beverageOrder.getGoodsBuyPrice()))")
	@Mapping(target = "buyQuantity", expression = "java(utility.orderQuantityCheck(orderVo.getBuyQuantity(),beverageOrder.getBuyQuantity()))")
	void updateEntity(BigDecimal id, OrderVo orderVo, @MappingTarget BeverageOrder beverageOrder);

	BeverageGoodsInfo2 convertToVo(BeverageGoods beverageGoods);

	@Mapping(source = "idCardNo", target = "customerID")
	@Mapping(source = "cartGoods.buyPrice", target = "goodsBuyPrice")
	@Mapping(source = "cartGoods.goodsID", target = "goodsID")
	@Mapping(source = "date", target = "orderDate")
	BeverageOrder convertToEntity(ShoppingCartGoodsInfo cartGoods, LocalDateTime date, String idCardNo, BeverageGoods goods);
	
	CheckoutReceipt convertToEntity(ShoppingCartGoodsInfo cartGoods, String goodsImage);
	
	@Mapping(source = "receiptList", target = "receipt")
	CheckoutCompleteInfo convertToEntity(String customerName, List<CheckoutReceipt> receiptList, Integer totalAmount);
	
	GoodsSalesInfo convertToVo(PageUtility pageUtility,List<GoodsReportSales> goodsReportSalesList);
}
