package dev.idion.idionkim.springboot1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(value = "value=test")
public class SpringBoot1ApplicationTests {

	@Value("${value}")
	private String value;

	@Test
	public void contextLoads() {
		assertThat(value, is("test"));
	}

}
