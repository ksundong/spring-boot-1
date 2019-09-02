package dev.idion.idionkim.springboot1.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("season")
public class SeasonProperties {
	private String seasonName;
	private String seasonMonth;
	private String seasonFlower;
	private String seasonWeather;
}
