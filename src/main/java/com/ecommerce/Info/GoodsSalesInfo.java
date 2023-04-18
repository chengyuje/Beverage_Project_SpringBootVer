package com.ecommerce.Info;

import java.util.List;

import com.ecommerce.model.GoodsReportSales;
import com.ecommerce.model.PageUtility;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class GoodsSalesInfo {
	
	private List<GoodsReportSales> goodsReportSalesList;

	private PageUtility pageUtility;
}
