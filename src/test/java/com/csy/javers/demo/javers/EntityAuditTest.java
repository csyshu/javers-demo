package com.csy.javers.demo.javers;

import com.csy.javers.demo.bean.PersonDto;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author shuyun.cheng
 * @version 1.0
 * @desc 审计
 * @date 2022-06-07 15:36
 */
@SpringBootTest
public class EntityAuditTest {

    @Resource
    private Javers javers;

    /**
     * 对比两个相同简单对象
     */
    @Test
    public void testDiffOfSameObj() {
        PersonDto p1 = new PersonDto(1, "tom", 10, "BJ", null);
        javers.commit("person", p1);
        p1.setAge(11);
        javers.commit("person", p1);
        PersonDto p2 = new PersonDto(1, "jerry", 11, "BJ", null);
        javers.commit("person", p2);
        // 执行查询
        JqlQuery personQuery = QueryBuilder.byInstanceId("person", PersonDto.class).build();
        Changes changes = javers.findChanges(personQuery);
        System.out.println("FindChanges:");
        System.out.println(changes.prettyPrint());

        System.out.println("FindShadows:");
        List<Shadow<Object>> shadows = javers.findShadows(personQuery);
        for (Shadow<Object> shadow : shadows) {
            System.out.println(shadow);
        }

        System.out.println("FindSnapshots:");
        List<CdoSnapshot> snapshots = javers.findSnapshots(personQuery);
        for (CdoSnapshot snapshot : snapshots) {
            System.out.println(snapshot);
        }
        System.out.println("FindShadowsAndStream:");
        Stream<Shadow<Object>> shadowsAndStream = javers.findShadowsAndStream(personQuery);
        shadowsAndStream.forEach(System.out::println);
    }

}
