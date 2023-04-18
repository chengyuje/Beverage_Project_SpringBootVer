package com.ecommerce.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageMember;
import com.ecommerce.entity.BeverageOrder;
import com.ecommerce.model.GoodsDataCondition;
import com.ecommerce.model.GoodsReportSales;
import com.ecommerce.model.GoodsSalesCondition;

@Repository
public class BackendCriteriaDao {

	@PersistenceContext(name = "oracleEntityManager")
	private EntityManager entityManager;

	@Autowired
	private BeverageGoodsJpaDao beverageGoodsJpaDao;

	@Autowired
	private BeverageOrderJpaDao beverageOrderJpaDao;

	public List<BeverageGoods> queryAllGoods() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageGoods> cq = cb.createQuery(BeverageGoods.class);
		Root<BeverageGoods> beverageGoods = cq.from(BeverageGoods.class);
		TypedQuery<BeverageGoods> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	public BeverageGoods queryGoodsByID(long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageGoods> cq = cb.createQuery(BeverageGoods.class);
		Root<BeverageGoods> root = cq.from(BeverageGoods.class);
		Predicate goodsID = cb.in(root.get("goodsID")).value(id);
		cq.where(goodsID);
		TypedQuery<BeverageGoods> query = entityManager.createQuery(cq);
		return query.getSingleResult();
	}

	public Page<BeverageGoods> queryPageGoodsWithCondition(GoodsDataCondition condition, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageGoods> cq = cb.createQuery(BeverageGoods.class);
		Root<BeverageGoods> root = cq.from(BeverageGoods.class);
		Specification<BeverageGoods> specification = getSpecification(condition);
		return beverageGoodsJpaDao.findAll(specification, pageable);
	}

	public Page<GoodsReportSales> queryGoodsSales(GoodsSalesCondition condition, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<GoodsReportSales> cq = cb.createQuery(GoodsReportSales.class);
		Root<BeverageOrder> beverageOrder = cq.from(BeverageOrder.class);
		LocalDateTime startDate = convertToLocalDateTime(condition.getStartDate() + " 00:00:00");
		LocalDateTime endDate = convertToLocalDateTime(condition.getEndDate() + " 23:59:59");

		Join<BeverageOrder, BeverageGoods> joinGoods = beverageOrder.join("goods", JoinType.INNER);
		Join<BeverageOrder, BeverageMember> joinMember = beverageOrder.join("member", JoinType.INNER);

		cq.multiselect(
			joinMember.get("customerName"), beverageOrder.get("goodsID"), 
			joinGoods.get("goodsName"),beverageOrder.get("goodsBuyPrice"), 
			beverageOrder.get("buyQuantity"), beverageOrder.get("orderID"), beverageOrder.get("orderDate")
		).where(cb.between(beverageOrder.get("orderDate"), startDate, endDate));
		
		TypedQuery<GoodsReportSales> query = entityManager.createQuery(cq);
		List<GoodsReportSales> salesList = query.getResultList();
		Page<GoodsReportSales> reportPageList = new PageImpl<>(salesList, pageable, salesList.size());
		return reportPageList;
	}

	private Specification<BeverageGoods> getSpecification(GoodsDataCondition condition) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<>();
			if (condition.getGoodsID() != null)
				predicateList.add(criteriaBuilder.in(root.get("goodsID")).value(condition.getGoodsID()));

			if (condition.getGoodsName() != null)
				predicateList.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("goodsName")),
						"%" + condition.getGoodsName().toUpperCase() + "%"));

			if (condition.getQuantity() != null)
				predicateList.add(criteriaBuilder.le(root.get("goodsQuantity"), condition.getQuantity()));

			if (condition.getStartPrice() != null && condition.getEndPrice() != null)
				predicateList.add(criteriaBuilder.between(root.get("goodsPrice"), condition.getStartPrice(),
						condition.getEndPrice()));

			if (condition.getStatus() != null)
				predicateList.add(criteriaBuilder.in(root.get("goodsStatus")).value(condition.getStatus()));

			Predicate[] predicateArray = new Predicate[predicateList.size()];

			return criteriaBuilder.and(predicateList.toArray(predicateArray));
		};
	}

	private Specification<BeverageOrder> getSpecification(GoodsSalesCondition condition) {
		return (root, query, criteriaBuilder) -> {
			LocalDateTime startDate = convertToLocalDateTime(condition.getStartDate() + " 00:00:00");
			LocalDateTime endDate = convertToLocalDateTime(condition.getEndDate() + " 23:59:59");
			List<Predicate> predicateList = new ArrayList<>();
			Join<BeverageOrder, BeverageGoods> joinGoods = root.join("goods", JoinType.INNER);
			Join<BeverageOrder, BeverageMember> joinMember = root.join("member", JoinType.INNER);
			predicateList.add(query.multiselect(joinMember.get("customerName").alias("IDENTIFICATION_NO"),
					root.get("goodsID"), joinGoods.get("goodsName"), root.get("goodsBuyPrice"), root.get("buyQuantity"),
					root.get("orderID"), root.get("orderDate")).getRestriction());
			if (condition.getStartDate() != null && condition.getEndDate() != null)
				predicateList.add(criteriaBuilder.between(root.get("orderDate"), startDate, endDate));
			Predicate[] predicateArray = new Predicate[predicateList.size()];
			return criteriaBuilder.and(predicateList.toArray(predicateArray));
		};
	}

	private LocalDateTime convertToLocalDateTime(String date) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(date, df);
		return localDateTime;
	}
}
