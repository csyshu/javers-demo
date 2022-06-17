package com.csy.javers.demo.bean.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author
 * @description 人员表
 * @date 2022-06-17
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_children")
public class Children implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * name
     */
    @Column(name = "name")
    private String name;

    /**
     * age
     */
    @Column(name = "age")
    private Integer age;

    /**
     * address
     */
    @Column(name = "address")
    private String address;

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Children children = (Children) o;
        return id != null && Objects.equals(id, children.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}