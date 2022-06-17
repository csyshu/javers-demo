package com.csy.javers.demo.javers;

import com.csy.javers.demo.bean.ChildrenDto;
import com.csy.javers.demo.bean.PersonDto;
import com.google.common.collect.Sets;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author shuyun.cheng
 * @version 1.0
 * @desc 对比不同
 * @date 2022-06-07 15:36
 */
public class EntityDiffTest {

    private Javers getJavers() {
        return JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE).build();
    }

    /**
     * 对比两个相同简单对象
     */
    @Test
    public void testDiffOfSameObj() {
        Javers javers = getJavers();
        PersonDto p1 = new PersonDto(1, "tom", 10, "BJ", null);
        PersonDto p2 = new PersonDto(1, "jerry", 11, "BJ", null);
        Diff diff = javers.compare(p1, p2);
        //正常打印
        System.out.println("正常打印-------------------------");
        System.out.println(diff);
        //美化打印
        System.out.println("美化打印-------------------------");
        System.out.println(diff.prettyPrint());
        //遍历打印
        System.out.println("遍历打印-------------------------");
        diff.getChanges().forEach(change -> System.out.println("-" + change));
        //json格式输出
        System.out.println("json格式打印-------------------------");
        System.out.println(javers.getJsonConverter().toJson(diff));
    }

    /**
     * 对比两个不同简单对象
     */
    @Test
    public void testDiffOfDiffObj() {
        Javers javers = getJavers();
        PersonDto p1 = new PersonDto(1, "tom", 10, "BJ", null);
        ChildrenDto p2 = new ChildrenDto(2, "jerry", 11, "BJ", null);
        Diff diff = javers.compare(p1, p2);
        //正常打印
        System.out.println("正常打印-------------------------");
        System.out.println(diff);
        //美化打印
        System.out.println("美化打印-------------------------");
        System.out.println(diff.prettyPrint());
        //遍历打印
        System.out.println("遍历打印-------------------------");
        diff.getChanges().forEach(change -> System.out.println("-" + change));
        //json格式输出
        System.out.println("json格式打印-------------------------");
        System.out.println(javers.getJsonConverter().toJson(diff));
    }

    /**
     * 对比两个相同复杂多层对象
     */
    @Test
    public void testDiffOfSameComplexObj() {
        Javers javers = getJavers();
        PersonDto p1 = new PersonDto(1, "tom", 10, "BJ", null);
        PersonDto p2 = new PersonDto(1, "jerry", 11, "BJ", null);
        Set<ChildrenDto> children = Sets.newHashSet(new ChildrenDto(1, "c1", 10, "BJ", null));
        p2.setChildren(children);
        Diff diff = javers.compare(p1, p2);
        //正常打印
        System.out.println("正常打印-------------------------");
        System.out.println(diff);
        //美化打印
        System.out.println("美化打印-------------------------");
        System.out.println(diff.prettyPrint());
        //遍历打印
        System.out.println("遍历打印-------------------------");
        diff.getChanges().forEach(change -> System.out.println("-" + change));
        //json格式输出
        System.out.println("json格式打印-------------------------");
        System.out.println(javers.getJsonConverter().toJson(diff));
    }

    /**
     * 对比两个相同复杂多层对象，按变化类型找出变化点
     */
    @Test
    public void testDiffOfSameComplexObjByType() {
        Javers javers = getJavers();
        PersonDto p1 = new PersonDto(1, "tom", 10, "BJ", null);
        PersonDto p2 = new PersonDto(2, "jerry", 11, "BJ", null);
        Set<ChildrenDto> children = Sets.newHashSet(new ChildrenDto(1, "c1", 10, "BJ", null));
        p2.setChildren(children);
        Diff diff = javers.compare(p1, p2);
//        ValueChange valueChange = diff.getChangesByType(InitialValueChange.class).get(0);
//        System.out.println(valueChange);
        System.out.println(javers.getJsonConverter().toJson(diff));
    }


}
