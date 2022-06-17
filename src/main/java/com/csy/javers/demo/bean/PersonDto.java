package com.csy.javers.demo.bean;

import com.csy.javers.demo.bean.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

import java.util.Set;

/**
 * @author shuyun.cheng
 * @version 1.0
 * @desc
 * @date 2022-06-07 15:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeName("PersonDto")
public class PersonDto {
    @Id
    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private Set<ChildrenDto> children;
}
