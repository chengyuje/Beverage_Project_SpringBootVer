package com.ecommerce.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BeverageGoodsVo {

	private long goodsID;

	private String goodsName;

	private MultipartFile file;

	private String description;

	private int price;

	private int quantity;

	private String imageName;

	private String status;

}
