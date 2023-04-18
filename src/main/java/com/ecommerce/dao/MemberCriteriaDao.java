package com.ecommerce.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.ecommerce.Info.MemberInfo;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageMember;

@Repository
public class MemberCriteriaDao {

	@PersistenceContext(name = "oracleEntityManager")
	private EntityManager entityManager;
	

	public BeverageMember login(MemberInfo memberInfo) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageMember> cq = cb.createQuery(BeverageMember.class);
		Root<BeverageMember> beverageMember = cq.from(BeverageMember.class);

		Predicate idCardNo = cb.in(beverageMember.get("idCardNo")).value(memberInfo.getIdCardNo());
		Predicate password = cb.in(beverageMember.get("password")).value(memberInfo.getCustomerPassword());
		Predicate assemble = cb.and(idCardNo, password);
		TypedQuery<BeverageMember> query = entityManager.createQuery(cq.where(assemble));
		
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	
	}
	
	public BeverageGoods findGoodsByID(long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageGoods> cq = cb.createQuery(BeverageGoods.class);
		Root<BeverageGoods> beverageGoods = cq.from(BeverageGoods.class);
		Predicate goodsID = cb.in(beverageGoods.get("goodsID")).value(id);
		cq.where(goodsID);
		TypedQuery<BeverageGoods> query = entityManager.createQuery(cq);
		return query.getSingleResult();
	}
	

}
