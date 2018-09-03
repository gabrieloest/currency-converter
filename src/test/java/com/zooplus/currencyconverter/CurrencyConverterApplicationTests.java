package com.zooplus.currencyconverter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.zooplus.currencyconverter.configuration.DevDatasourceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DevDatasourceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class CurrencyConverterApplicationTests {

	@Test
	public void contextLoads() {
	}

}
