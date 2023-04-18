package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageGoods;

@Repository
public interface FrontendJpaDao extends JpaRepository<BeverageGoods, Long>, JpaSpecificationExecutor<BeverageGoods> {

}
