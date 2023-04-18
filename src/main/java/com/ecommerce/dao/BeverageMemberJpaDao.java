package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageMember;

@Repository
public interface BeverageMemberJpaDao extends JpaRepository<BeverageMember, String> {

}
