package com.ecommerce.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Info.MemberInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo;
import com.ecommerce.entity.BeverageMember;
import com.ecommerce.service.MemberService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/MemberController")
public class MemberController {

	private static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Resource(name = "sessionMemberInfo")
	private MemberInfo sessionMemberInfo;

	@Resource(name = "sessionCartGoods")
	private List<ShoppingCartGoodsInfo> cartGoods;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private MemberService memberService;
	
//	@Autowired(required=true)
//	private SessionStatus sessionStatus;
//	
//	@Autowired
//	private ApplicationContext applicationContext;

	@ApiOperation(value = "購物網-會員-檢查登入")
	@GetMapping(value = "/checkLogin")
	public ResponseEntity<MemberInfo> checkLogin() {
		logger.info("HttpSession checkLogin:" + httpSession.getId());
		logger.info("CheckLogin:" + sessionMemberInfo.toString());
		return ResponseEntity.ok(sessionMemberInfo);
	}

	@ApiOperation(value = "購物網-會員-登入")
	@PostMapping(value = "/login")
	public ResponseEntity<MemberInfo> login(@RequestBody MemberInfo memberInfo) {
		/*
		 * { "idCardNo": "A124243295", "customerPassword": "123" } {
		 * "identificationNo":"G436565447", "customerPassword": "123" }
		 * 
		 */
		logger.info("HttpSession Login:" + httpSession.getId());
		logger.info("Before:" + sessionMemberInfo.toString());
		logger.info("After:" + sessionMemberInfo.toString());

		BeverageMember member = memberService.login(memberInfo);

		if (member != null) {
			sessionMemberInfo.setIsLogin(true);
			sessionMemberInfo.setLoginMessage("登入成功！");
			sessionMemberInfo.setIdCardNo(member.getIdCardNo());
			sessionMemberInfo.setCustomerName(member.getCustomerName());
		} else {
			sessionMemberInfo.setIsLogin(false);
			sessionMemberInfo.setLoginMessage("輸入密碼錯誤！");
			sessionMemberInfo.setIdCardNo(memberInfo.getIdCardNo());
		}

		return ResponseEntity.ok(sessionMemberInfo);
	}

	@ApiOperation(value = "購物網-會員-登出")
	@GetMapping(value = "/logout")
	public ResponseEntity<MemberInfo> logout() {
		logger.info("HttpSession logout:" + httpSession.getId());
//		DefaultListableBeanFactory clbf=(DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
//		if(clbf.containsBeanDefinition("sessionMemberInfo")) {
//			sessionMemberInfo=(MemberInfo)clbf.getBean("sessionMemberInfo");
//			logger.info(sessionMemberInfo.getCustomerName());
//			mi=MemberInfo.builder().customerName("Test").customerPassword("test").build();
//			clbf.destroyBean(sessionMemberInfo);
//			clbf.removeBeanDefinition("sessionMemberInfo");
//			logger.info("exist:"+clbf.containsBeanDefinition("sessionMemberInfo"));
//		}
//		ApplicationContext ac=new AnnotationConfigApplicationContext(EcommerceConfig.class);
//		MemberInfo mi=(MemberInfo)ac.getBean("sessionMemberInfo");
//		logger.info(mi.getCustomerName());
//		((BeanDefinitionRegistry) applicationContext).removeBeanDefinition("sessionMemberInfo");

		httpSession.invalidate();
		return ResponseEntity.ok(sessionMemberInfo);
	}

	@ApiOperation(value = "商品加入購物車")
	@PostMapping(value = "/addCartGoods")
	public ResponseEntity<List<ShoppingCartGoodsInfo>> addCartGoods(@RequestParam long id,
			@RequestParam long buyQuantity) {
		/*
		 * { "goodsID": 28, "goodsName": "Java Chip", "description":
		 * "暢銷口味之一，以摩卡醬、乳品及可可碎片調製，加上細緻鮮奶油及摩卡醬，濃厚的巧克力風味。", "imageName":
		 * "20130813154445805.jpg", "price": 145, "quantity": 17 }
		 * 
		 * { "goodsID": 3, "goodsName": "柳橙檸檬蜂蜜水", "description":
		 * "廣受喜愛的蜂蜜水，搭配柳橙與檸檬汁，酸甜的好滋味，尾韻更帶有柑橘清香。", "imageName": "2021110210202761.jpg",
		 * "price": 20, "quantity": 16 }
		 */
		cartGoods = memberService.addCartGoods(cartGoods, id, buyQuantity);
		return ResponseEntity.ok(cartGoods);
	}

	@ApiOperation(value = "查尋購物車商品")
	@GetMapping(value = "/queryCartGoods")
	public ResponseEntity<List<ShoppingCartGoodsInfo>> queryCartGoods() {
		return ResponseEntity.ok(cartGoods);
	}

	@ApiOperation(value = "清空購物車商品")
	@DeleteMapping(value = "/clearCartGoods")
	public ResponseEntity<List<ShoppingCartGoodsInfo>> clearCartGoods() {
		cartGoods.clear();
		return ResponseEntity.ok(cartGoods);
	}

}
