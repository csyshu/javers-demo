package com.csy.javers.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.csy.javers.demo.bean.PersonDto;
import com.csy.javers.demo.bean.model.Person;
import com.csy.javers.demo.service.PersonRepository;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.javers.core.json.JsonConverter;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author shuyun.cheng
 * @version 1.0
 * @desc
 * @date 2022-06-10 17:48
 */
@RestController
@RequestMapping("/javers/diff")
public class JaversDiffController {
    @Resource
    private Javers javers;
    @Resource
    private PersonRepository personRepository;

    @PostMapping("/diffObj")
    public String diffObj(@RequestBody String diff) {
        JSONObject jsonObject = JSONObject.parseObject(diff);
        return javers.getJsonConverter().toJson(javers.compare(jsonObject.getJSONObject("obj1"),
                jsonObject.getJSONObject("obj2")));
    }

    @GetMapping("/saveOrUpdatePersons")
    public void saveOrUpdatePersons() {
        Person person = personRepository.findById(1).orElseGet(() -> new Person("tom", 12, "TJ", 1));
        person.setAge(person.getAge() + 1);
        personRepository.save(person);
    }

    @RequestMapping("/persons")
    public String getPersonChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Person.class);
        Changes changes = javers.findChanges(jqlQuery.build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping("/person/{id}")
    public String getPersonChanges(@PathVariable Integer id) {
        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(id, Person.class);
        Changes changes = javers.findChanges(jqlQuery.build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping("/person/snapshots")
    public String getPersonSnapshots() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Person.class);
        List<CdoSnapshot> changes = new ArrayList<>(javers.findSnapshots(jqlQuery.build()));
        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate()));
        JsonConverter jsonConverter = javers.getJsonConverter();
        return jsonConverter.toJson(changes);
    }

    @RequestMapping("/person/{left}/diff/{right}")
    public String getPersonSnapshots(@PathVariable int left, @PathVariable int right) {
        Person l = personRepository.findById(left).get();
        Person p = personRepository.findById(right).get();
        Diff diff = javers.compare(l, p);
        return javers.getJsonConverter().toJson(diff.getChanges());
    }

    @GetMapping("/getResult")
    public String getResult() {
        PersonDto p1 = new PersonDto(1, "tom", 10, "BJ", null);
        javers.commit("personDto", p1);
        p1.setAge(11);
        javers.commit("personDto", p1);
        PersonDto p2 = new PersonDto(1, "jerry", 11, "BJ", null);
        javers.commit("personDto", p2);
        // 执行查询
        JqlQuery personQuery = QueryBuilder.byClass(PersonDto.class).build();
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
        return null;
    }

}
