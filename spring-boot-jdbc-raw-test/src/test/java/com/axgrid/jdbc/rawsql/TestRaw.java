package com.axgrid.jdbc.rawsql;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Import(TestApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "debug=true")
@Slf4j
public class TestRaw {

    @Autowired
    MyRawDAO dao;

    @Test
    public void testCreateObject() throws Exception {
        var o = new MyRawObject();
        o.setAge(18);
        o.setEnumInt(MySimpleEnum.Yes);
        o.setEnumString(MySimpleEnum.Yes);
        o.setName("tens name");
        var include = new MyIncludedJsonObject();
        include.setTextField("Hello");
        o.setIncludedJsonObject(include);
        long id = dao.createObject(o);
        Assert.assertNotEquals(id, 0);
        log.info("New object ID is {}", id);
        var o2 = dao.getById(id);
        log.info("New received object is :{}", o2);
        Assert.assertNotNull(o2.getIncludedJsonObject());
        Assert.assertNotNull(o2.getTime());
        log.info("oTime:{} o2Time:{}", o.getTime().getTime(), o2.getTime().getTime());
        Assert.assertEquals(o.getAge(), o2.getAge());
        Assert.assertEquals(o.getIncludedJsonObject().getTextField(), o2.getIncludedJsonObject().getTextField());
        Assert.assertEquals(o.getName(), o2.getName());
    }

    @Test
    public void testNullObject() throws Exception {
        MyRawObject o = dao.getById(Long.MAX_VALUE);
        Assert.assertNull(o);
        try {
            o = dao.getByIdWithException(Long.MAX_VALUE);
            Assert.fail();
        }catch (EmptyResultDataAccessException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testCreateObjectWithId() throws Exception {
        MyRawObject o = new MyRawObject();
        o.setAge(18);
        o.setEnumInt(MySimpleEnum.Yes);
        o.setEnumString(MySimpleEnum.Yes);
        o.setName("test name");
        o = dao.createObject2(o);
        log.info("NewObject:{}",o);
        Assert.assertNotNull(o.getId());
        Assert.assertNotEquals((long)o.getId(), 0);
    }

    @Test
    public void testListObject() throws Exception {
        for(int i=0;i<100;i++) {
            var o = new MyRawObject();
            o.setAge(i);
            dao.createObject(o);
        }

        Assert.assertEquals(dao.getByAge(50).size(), 49);


    }

    @Test
    public void testEnumMappingObject() throws Exception {

        MyRawObject o = new MyRawObject();
        o.setEnumInt(MySimpleEnum.Yes);
        dao.createObject(o);

        o = new MyRawObject();
        o.setEnumInt(MySimpleEnum.No);
        dao.createObject(o);

        List<MyRawObject> res = dao.getByEnum2(MySimpleEnum.Yes);
        Assert.assertNotEquals(res.size(), 0);
        Assert.assertTrue(res.stream().allMatch(item -> item.getEnumInt() == MySimpleEnum.Yes));
        res = dao.getByEnum2(MySimpleEnum.No);
        Assert.assertNotEquals(res.size(), 0);
        Assert.assertTrue(res.stream().allMatch(item -> item.getEnumInt() == MySimpleEnum.No));
    }

    @Test
    public void testQuerySimpleData() {
        MyRawObject o = new MyRawObject();
        o.setAge(18);
        o.setEnumInt(MySimpleEnum.Yes);
        o.setIncludedJsonObject(new MyIncludedJsonObject("help"));

        var d = new Date();
        o.setTime(d);
        long id = dao.createObject(o);

        int age = dao.getAgeById(id);
        Assert.assertEquals(18, age);
        Date date = dao.getDateById(id);
        Assert.assertNotNull(date);
        Assert.assertEquals(Math.round(date.getTime()/1000.0), Math.round(d.getTime() / 1000.0));

        MyIncludedJsonObject obj = dao.getObjectById(id);
        Assert.assertNotNull(obj);
        Assert.assertEquals(obj.getTextField(), "help");
    }


}
