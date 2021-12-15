package br.com.moonlyRegister.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.moonlyRegister.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
