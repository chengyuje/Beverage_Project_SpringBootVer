package com.ecommerce.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageGoods;

@Repository
public interface BeverageGoodsJpaDao
		extends JpaRepository<BeverageGoods, BigDecimal>, JpaSpecificationExecutor<BeverageGoods> {

}
