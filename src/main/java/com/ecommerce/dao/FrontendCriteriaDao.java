package com.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.model.GoodsDataCondition;

@Repository
public class FrontendCriteriaDao {
	
	@PersistenceContext(name = "oracleEntityManager")
	private EntityManager entityManager;
	
	@Autowired
	private FrontendJpaDao frontendJpaDao;
	
	public Page<BeverageGoods> queryPageGoodsWithKeyword(String searchKeyword, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageGoods> cq = cb.createQuery(BeverageGoods.class);
		Root<BeverageGoods> root = cq.from(BeverageGoods.class);
		if(searchKeyword==null)  return frontendJpaDao.findAll(pageable);
		Specification<BeverageGoods> specification = getSpecification(searchKeyword);
		return frontendJpaDao.findAll(specification, pageable);
	}

	private Specification<BeverageGoods> getSpecification(String searchKeyword){
		return (root,query,criteriaBuilder)->{
			Predicate assemble=criteriaBuilder.like(criteriaBuilder.upper(root.get("goodsName")),"%"+searchKeyword.toUpperCase()+"%");
			return assemble;
		};
	}
}
