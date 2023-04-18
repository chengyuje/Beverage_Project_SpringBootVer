package com.ecommerce.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

//@Builder
@SuperBuilder
@NoArgsConstructor
//@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = { "goodsID" })
@Entity
@Table(name = "BEVERAGE_GOODS")
public class BeverageGoods {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_ID_SEQ_GEN")
	@SequenceGenerator(name = "GOODS_ID_SEQ_GEN", sequenceName = "BEVERAGE_GOODS_SEQ", allocationSize = 1)
	@Column(name = "GOODS_ID")
	private BigDecimal goodsID;

	@Column(name = "GOODS_NAME")
	private String goodsName;

	@Column(name = "DESCRIPTION")
	private String goodsDescription;

	@Column(name = "PRICE")
	private Integer goodsPrice;

	@Column(name = "QUANTITY")
	private Integer goodsQuantity;

	@Column(name = "IMAGE_NAME")
	private String goodsImageName;

	@Column(name = "STATUS")
	private String goodsStatus;

	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = { CascadeType.ALL }, 
			orphanRemoval = true, 
			mappedBy = "goodsID"
		)
	private List<BeverageOrder> beverageOrders;

}
