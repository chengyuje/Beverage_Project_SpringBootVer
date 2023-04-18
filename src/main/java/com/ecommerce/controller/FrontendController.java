package com.ecommerce.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Info.CheckoutCompleteInfo;
import com.ecommerce.Info.GoodsDataInfo;
import com.ecommerce.Info.MemberInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo;
import com.ecommerce.model.PageUtility;
import com.ecommerce.service.FrontendService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/FrontendController")
public class FrontendController {

	private static Logger logger = LoggerFactory.getLogger(FrontendController.class);

	@Autowired
	private HttpSession httpSession;

	@Resource(name = "sessionMemberInfo")
	private MemberInfo sessionMemberInfo;

	@Resource(name = "sessionCartGoods")
	private List<ShoppingCartGoodsInfo> cartGoods;

	@Autowired
	private FrontendService frontendService;

	@ApiOperation(value = "購物網-前臺-查詢商品列表")
	@GetMapping(value = "/queryGoodsData")
	public ResponseEntity<GoodsDataInfo> queryPageGoodsWithKeyword(@RequestParam(required = false) String searchKeyword,
			@RequestParam int currentPageNo, @RequestParam int pageDataSize, @RequestParam int pagesIconSize) {
		Pageable pageable = PageRequest.of(currentPageNo - 1, pageDataSize, Sort.Direction.ASC, "goodsID");

		PageUtility pageUtility = PageUtility.builder()
								  .currentPageNo(currentPageNo).pageDataSize(pageDataSize)
								  .pageIconSize(pagesIconSize).build();

		GoodsDataInfo goodsDataInfo = frontendService.queryPageGoodsWithKeyword(searchKeyword, pageUtility, pageable);

		return ResponseEntity.ok(goodsDataInfo);
	}

	@ApiOperation(value = "購物網-前臺-結帳購物車商品")
	@PostMapping(value = "/checkoutGoods")
	public ResponseEntity<CheckoutCompleteInfo> checkoutGoods() {

		logger.info("HttpSession checkoutGoods:" + httpSession.getId());
		logger.info("CheckoutGoods:" + sessionMemberInfo.toString());

		CheckoutCompleteInfo checkoutCompleteInfo = frontendService.checkoutGoods(sessionMemberInfo, cartGoods);

		return ResponseEntity.ok(checkoutCompleteInfo);
	}
}
