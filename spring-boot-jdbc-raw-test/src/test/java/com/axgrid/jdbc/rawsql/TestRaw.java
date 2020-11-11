package com.axgrid.jdbc.rawsql;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        Assert.assertEquals(o.getTime(), o2.getTime());
        Assert.assertEquals(o.getAge(), o2.getAge());
        Assert.assertEquals(o.getIncludedJsonObject().getTextField(), o2.getIncludedJsonObject().getTextField());
        Assert.assertEquals(o.getName(), o2.getName());
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
}
