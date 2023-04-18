package com.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ecommerce.Info.BeverageGoodsInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageOrder;
import com.ecommerce.model.GoodsReportSales;
import com.ecommerce.vo.BeverageGoodsVo;

@Mapper
(
	componentModel = "spring", 
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
	uses= {MapperUtility.class}
)
public interface BeverageGoodsMapper {

//	BeverageGoodsMapper INSTANCE = Mappers.getMapper(BeverageGoodsMapper.class);
	
	MapperUtility utility = MapperUtility.getMapperUtility_Instance();

	@Mapping(target = "goodsID", ignore = true)
	@Mapping(source = "description", target = "goodsDescription")
	@Mapping(source = "price", target = "goodsPrice")
	@Mapping(source = "quantity", target = "goodsQuantity")
	@Mapping(source = "imageName", target = "goodsImageName")
	@Mapping(source = "status", target = "goodsStatus")
	BeverageGoods convertToEntity(BeverageGoodsVo beverageGoodsVo);

//	@InheritInverseConfiguration(name="convertToEntity")
//	BeverageGoodsVo convertToVo(BeverageGoods beverageGoods);

	@Mapping(source = "goodsDescription", target = "description")
	@Mapping(source = "goodsPrice", target = "price")
	@Mapping(source = "goodsQuantity", target = "quantity")
	@Mapping(source = "goodsImageName", target = "imageName")
	@Mapping(source = "goodsStatus", target = "status")
	BeverageGoodsInfo convertToVo(BeverageGoods beverageGoods);
		
	@Mapping(target = "buyQuantity", expression = "java(utility.renewCartGoodsQuantity(dbGoodsInfo.getGoodsQuantity(),cartGoodsQuantity,buyQuantity))")
	@Mapping(source = "dbGoodsInfo.goodsPrice", target = "buyPrice")
	@Mapping(source = "dbGoodsInfo.goodsDescription", target = "description")
	ShoppingCartGoodsInfo checkGoodsInfo(BeverageGoods dbGoodsInfo,int cartGoodsQuantity, int buyQuantity);

	@Mapping(target = "goodsID", ignore = true)
	@Mapping(source = "description", target = "goodsDescription", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "goodsPrice", expression = "java(utility.goodsPriceCheck(beverageGoodsVo.getPrice(),beverageGoods.getGoodsPrice()))")
	@Mapping(target = "goodsQuantity", expression = "java(utility.goodsQuantityCheck(beverageGoodsVo.getQuantity(),beverageGoods.getGoodsQuantity()))")
	@Mapping(source = "imageName", target = "goodsImageName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "status", target = "goodsStatus", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntity(BeverageGoodsVo beverageGoodsVo, @MappingTarget BeverageGoods beverageGoods);
	
	List<BeverageGoodsInfo> convertToVo(List<BeverageGoods> goodsList);
	
//	List<GoodsReportSales> convertToVoPart(List<BeverageOrder> orderList,@Context String customerName);
//	
//	default GoodsReportSales middle(BeverageOrder beverageOrder,@Context String customerName,@Context Object goodsName) {
//		return test(beverageOrder,customerName,(String)goodsName);
//	}
//	GoodsReportSales test(BeverageOrder beverageOrder, String customerName, String goodsName);
	
}
