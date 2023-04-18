package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Info.BeverageGoodsInfo;
import com.ecommerce.Info.GoodsDataInfo;
import com.ecommerce.Info.GoodsSalesInfo;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.mapper.BeverageGoodsMapper;
import com.ecommerce.model.GoodsDataCondition;
import com.ecommerce.model.GoodsSalesCondition;
import com.ecommerce.model.PageUtility;
import com.ecommerce.service.BackendService;
import com.ecommerce.vo.BeverageGoodsVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/BackendController")
public class BackendController {

	@Autowired
	private BackendService backendService;

	@Autowired
	private BeverageGoodsMapper converter;

	@ApiOperation(value = "購物網-後臺-查詢全部商品清單")
	@GetMapping(value = "/queryAllGoods")
	public ResponseEntity<List<BeverageGoodsInfo>> queryAllGoods() {
		List<BeverageGoods> goodsDatas = backendService.queryAllGoods();
		return ResponseEntity.ok(converter.convertToVo(goodsDatas));
	}

	@ApiOperation(value = "購物網-後臺-查詢單一商品資料")
	@GetMapping(value = "/queryGoodsByID")
	public ResponseEntity<BeverageGoodsInfo> queryGoodsByID(@RequestParam long goodsID) {
		BeverageGoods goodsData = backendService.queryGoodsByID(goodsID);
		return ResponseEntity.ok(converter.convertToVo(goodsData));
	}

	@ApiOperation(value = "購物網-後臺-查詢商品列表")
	@GetMapping(value = "/queryGoodsData")
	public ResponseEntity<GoodsDataInfo> queryPageGoodsData(@RequestParam(required = false) Integer goodsID,
			@RequestParam(required = false) String goodsName, @RequestParam(required = false) String priceSort,
			@RequestParam(required = false) Integer startPrice, @RequestParam(required = false) Integer endPrice,
			@RequestParam(required = false) Integer quantity, @RequestParam String status,
			@RequestParam int currentPageNo, @RequestParam int pageDataSize, @RequestParam int pagesIconSize) {

		Pageable pageable = PageRequest.of(currentPageNo - 1, pageDataSize, Sort.Direction.DESC, "goodsID");

		PageUtility pageUtility = PageUtility.builder()
				  .currentPageNo(currentPageNo).pageDataSize(pageDataSize)
				  .pageIconSize(pagesIconSize).build();
		
		GoodsDataCondition condition = GoodsDataCondition.builder()
				.goodsID(goodsID).goodsName(goodsName).startPrice(startPrice).endPrice(endPrice)
				.priceSort(priceSort).quantity(quantity).status(status).build();

		GoodsDataInfo goodsDataInfo = backendService.queryPageGoodsWithCondition(condition, pageUtility, pageable);

		return ResponseEntity.ok(goodsDataInfo);
	}

	@ApiOperation(value = "購物網-後臺-商品訂單查詢(一個商品對應到多筆訂單)")
	@GetMapping(value = "/queryGoodsSales")
	public ResponseEntity<GoodsSalesInfo> queryGoodsSales(@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam int currentPageNo, @RequestParam int pageDataSize, @RequestParam int pagesIconSize) {
		/*
		 * startDate:2022/01/01
		 * endDate:2022/12/30
		 * currentPageNo:1 
		 * pageDataSize:3
		 * pagesIconSize:3
		 */
		Pageable pageable = PageRequest.of(currentPageNo - 1, pageDataSize, Sort.Direction.DESC, "orderID");
		
		GoodsSalesCondition condition = GoodsSalesCondition.builder().startDate(startDate).endDate(endDate).build();

		PageUtility pageUtility = PageUtility.builder()
									  .currentPageNo(currentPageNo).pageDataSize(pageDataSize)
									  .pageIconSize(pagesIconSize).build();

		GoodsSalesInfo goodsReportSalesInfo = backendService.queryGoodsSales(condition, pageUtility, pageable);

		return ResponseEntity.ok(goodsReportSalesInfo);
	}

	@ApiOperation(value = "購物網-後臺-商品新增")
	@PostMapping(value = "/createGoods", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<BeverageGoodsInfo> createGoods(@ModelAttribute BeverageGoodsVo beverageGoodsVo)
			throws Exception {
		BeverageGoods goods = backendService.createGoods(beverageGoodsVo);
		return ResponseEntity.ok(converter.convertToVo(goods));
	}

	@ApiOperation(value = "購物網-後臺-商品維護")
	@PostMapping(value = "/updateGoods")
	public ResponseEntity<BeverageGoodsInfo> updateGoods(@ModelAttribute BeverageGoodsVo beverageGoodsVo)
			throws Exception {
		BeverageGoods goods = backendService.updateGoods(beverageGoodsVo);
		return ResponseEntity.ok(converter.convertToVo(goods));
	}

}
