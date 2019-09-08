package dev.idion.idionkim.sample.domain;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Book {

	@Id
	@GeneratedValue
	private Integer idx;

	@Column
	private String title;

	@Column
	private LocalDateTime publishedAt;

	@Builder
	public Book(String title, LocalDateTime publishedAt) {
		this.title = title;
		this.publishedAt = publishedAt;
	}

}
