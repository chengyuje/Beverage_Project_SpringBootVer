package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Info.BeverageGoodsInfo2;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.mapper.GoodsOrderMapper;
import com.ecommerce.service.GoodsOrderService;
import com.ecommerce.vo.GoodsOrderVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/GoodsOrderController")
public class GoodsOrderController {

	@Autowired
	private GoodsOrderService goodsOrderService;
	
	@Autowired
	private GoodsOrderMapper converter;

	@ApiOperation("新增商品(一對多練習)")
	@PostMapping(value = "/createGoodsOrder")
	public ResponseEntity<BeverageGoodsInfo2> createGoodsAndOrder(@RequestBody GoodsOrderVo goodsOrderVo) {
// 新增「一筆商品」同時新增「多筆訂單」48、49、50
/*
{
  "goodsName": "iPhone 12 Pro",
  "description": "超瓷晶盾面板 霧面質感玻璃機背 不鏽鋼設計",
  "imageName": "iPhone12.jpg",
  "price": 32000,
  "quantity": 10,
  "status": "1",
  "orderVos": [
    {
      "orderDate": "2023-03-01T00:00:00",
      "customerID": "A124243295",
      "buyQuantity": 1,
      "goodsBuyPrice": 32000
    },
    {
      "orderDate": "2023-03-02T00:00:00",
      "customerID": "D201663865",
      "buyQuantity": 1,
      "goodsBuyPrice": 32000
    },
    {
      "orderDate": "2023-03-03T00:00:00",
      "customerID": "J213664153",
      "buyQuantity": 1,
      "goodsBuyPrice": 32000
    }
  ]
}
*/
		BeverageGoods goods = goodsOrderService.createGoodsAndOrder(goodsOrderVo);
		return ResponseEntity.ok(converter.convertToVo(goods));
	}
	
	@ApiOperation(value = "更新商品(一對多練習)")
	@PutMapping(value = "/updateGoods")
	public ResponseEntity<BeverageGoodsInfo2> updateGoodsAndOrder(@RequestBody GoodsOrderVo goodsOrderVo) {
// 更新「一筆商品」同時更新「多筆訂單」
/*
前端只傳入須要更新的部份欄位,未傳入的欄位不更新!
UPDATE * 1 筆商品(29)
UPDATE * 2 筆訂單(1、2)
INSERT * 2 筆訂單(4、5)
DELETE * 1 筆訂單(3)
{
  "goodsID": 29,
  "goodsName": "iPhone 12 Pro(256GB)",
  "price": 36666,
  "orderVos": [
    {
      "orderID": 1,
      "orderDate": "2023-04-01T00:00:00"
    },
    {
      "orderID": 2,
      "buyQuantity": 3
    },
    {
      "orderDate": "2023-05-01T00:00:00",
      "customerID": "F126873254",
      "buyQuantity": 1,
      "goodsBuyPrice": 36666
    },
    {
      "orderDate": "2023-05-02T00:00:00",
      "customerID": "G436565447",
      "buyQuantity": 1,
      "goodsBuyPrice": 36666
    }
  ]
}
			
*/
		BeverageGoods goods = goodsOrderService.updateGoodsAndOrder(goodsOrderVo);
		return ResponseEntity.ok(converter.convertToVo(goods));
	}
}
