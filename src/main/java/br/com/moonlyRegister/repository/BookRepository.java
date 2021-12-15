package br.com.moonlyRegister.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.moonlyRegister.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
