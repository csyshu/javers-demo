package com.csy.javers.demo.service;

import com.csy.javers.demo.bean.model.Children;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author
 * @description 人员表
 * @date 2022-06-17
 */
@JaversSpringDataAuditable
public interface ChildrenRepository extends JpaRepository<Children, Integer> {

}