package com.ltchen.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AppTest {

    @Autowired
    @Qualifier("dateFormat1")
    private SimpleDateFormat dateFormat1;

    @Autowired
    @Qualifier("dateFormat2")
    private SimpleDateFormat dateFormat2;

    @Test

    public void propertyTest() {

        Assert.assertNotNull(dateFormat1);
        Assert.assertNotNull(dateFormat2);

        String date = "2010-10-10 11:12:14";
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date time = ymdhms.parse(date);
            Assert.assertTrue("2010-10-10 11:12:14".equals(dateFormat1.format(time)));
            Assert.assertTrue("2010-10-10 11:12:14".equals(dateFormat2.format(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
