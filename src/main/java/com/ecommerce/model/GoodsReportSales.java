package com.ecommerce.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class GoodsReportSales {
	
	private String customerName;
	
	private Long goodsID;
	
	private String goodsName;
	
	private Long goodsBuyPrice;
	
	private Long buyQuantity;
	
	private Long orderID;
	
	private LocalDateTime orderDate;
	

}
