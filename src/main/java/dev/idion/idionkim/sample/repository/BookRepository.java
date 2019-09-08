package dev.idion.idionkim.sample.repository;

import dev.idion.idionkim.sample.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
