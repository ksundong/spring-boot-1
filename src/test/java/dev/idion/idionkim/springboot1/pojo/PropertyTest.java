package dev.idion.idionkim.springboot1.pojo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyTest {
	@Autowired
	FruitProperty fruitProperty;
	
	@Autowired
	SeasonProperties seasonProperties;
	
	@Test
	public void test() {
		List<Fruit> fruitData = fruitProperty.getList();
		
		assertThat(fruitData.get(0).getName(), is("banana"));
		assertThat(fruitData.get(0).getColor(), is("yellow"));
		
		assertThat(fruitData.get(1).getName(), is("apple"));
		assertThat(fruitData.get(1).getColor(), is("red"));
		
		assertThat(fruitData.get(2).getName(), is("water melon"));
		assertThat(fruitData.get(2).getColor(), is("green"));
		
		// Flexible binding
		String seasonName = seasonProperties.getSeasonName();
		String seasonMonth = seasonProperties.getSeasonMonth();
		String seasonFlower = seasonProperties.getSeasonFlower();
		String seasonWeather = seasonProperties.getSeasonWeather();
		
		assertThat(seasonName, is("spring"));
		assertThat(seasonMonth, is("april"));
		assertThat(seasonFlower, is("blossom"));
		assertThat(seasonWeather, is("sunny"));
	}
}
