package com.ecommerce.Info;

import java.util.List;

import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.model.PageUtility;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class GoodsDataInfo {

	private List<BeverageGoods> dataList;

	private PageUtility pageUtility;

}
