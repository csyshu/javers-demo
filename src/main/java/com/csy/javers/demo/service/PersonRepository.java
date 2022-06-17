package com.csy.javers.demo.service;

import com.csy.javers.demo.bean.model.Person;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @description 人员表
 * @date 2022-06-17
 */
@JaversSpringDataAuditable
public interface PersonRepository extends JpaRepository<Person, Integer> {

}