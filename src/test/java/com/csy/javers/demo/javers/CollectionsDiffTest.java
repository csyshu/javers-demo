package com.csy.javers.demo.javers;

import com.csy.javers.demo.bean.ChildrenDto;
import com.csy.javers.demo.bean.PersonDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ValueChange;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author shuyun.cheng
 * @version 1.0
 * @desc 集合对比
 * @date 2022-06-08 11:18
 */
public class CollectionsDiffTest {

    private Javers getJavers() {
        return JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE).build();
    }

    @Test
    public void collectionDiffTest() {
        Javers javers = getJavers();
        PersonDto old = new PersonDto(1, "韩信", 33, "BJ", Sets.newHashSet(new ChildrenDto(1, "韩信的孩子", 5, "BJ", null)));

        PersonDto new1 = new PersonDto(1, "高渐离", 32, "BJ", Sets.newHashSet(new ChildrenDto(2, "高渐离的孩子", 2, "BJ", null)));
        PersonDto new2 = new PersonDto(2, "杨戬", 35, "TJ", Sets.newHashSet(new ChildrenDto(3, "杨戬的孩子", 6, "TJ", null)));

        Diff diff = javers.compareCollections(Lists.newArrayList(old),
                Lists.newArrayList(new1, new2), PersonDto.class);
        System.out.println(diff.prettyPrint());
        List<Change> changes = diff.getChanges();
//        Changes changes = diff.getChanges();
        for (Change change : changes) {
            if ((change instanceof NewObject)) {
                System.out.println("新增改动: " + change);
//                change.getAffectedObject().ifPresent(System.out::println);

            }

            if ((change instanceof ObjectRemoved)) {
                System.out.println("删除改动: " + change);
//                change.getAffectedObject().ifPresent(System.out::println);
            }

            if ((change instanceof ValueChange)) {
                System.out.println("修改改动: " + change);
//                change.getAffectedObject().ifPresent(System.out::println);
            }

        }

    }
}
