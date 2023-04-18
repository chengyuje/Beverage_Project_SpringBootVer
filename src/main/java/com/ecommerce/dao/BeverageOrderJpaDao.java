package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ecommerce.entity.BeverageOrder;

public interface BeverageOrderJpaDao extends JpaRepository<BeverageOrder, Long>, JpaSpecificationExecutor<BeverageOrder>{

}
