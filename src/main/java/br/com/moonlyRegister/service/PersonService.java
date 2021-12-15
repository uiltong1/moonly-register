package br.com.moonlyRegister.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.moonlyRegister.exception.GenericException;
import br.com.moonlyRegister.mapper.serialization.converter.DozerConverter;
import br.com.moonlyRegister.mapper.serialization.converter.PersonConverter;
import br.com.moonlyRegister.model.Person;
import br.com.moonlyRegister.repository.PersonRepository;
import br.com.moonlyRegister.vo.PersonVO;
import br.com.moonlyRegister.vo.PersonVOV2;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public List<PersonVO> index() {
		return DozerConverter.parseListObjects(personRepository.findAll(), PersonVO.class);
	}

	public PersonVO get(Long id) {
		var person = personRepository.findById(id).orElseThrow(
				() -> new GenericException(String.format("Pessoa com o código %s não foi encontrada.", id)));
		return DozerConverter.parseObject(person, PersonVO.class);
	}

	public PersonVO store(PersonVO personVO) {
		var person = personRepository.save(DozerConverter.parseObject(personVO, Person.class));
		return DozerConverter.parseObject(person, PersonVO.class);
	}

	public PersonVOV2 storeV2(PersonVOV2 personVOV2) {
		var person = personRepository.save(PersonConverter.converterVOtoEntity(personVOV2));
		return PersonConverter.converterEntityToVO(person);
	}

	public PersonVO update(Long id, PersonVO personVO) {
		Person personExist = personRepository.findById(id).orElseThrow(
				() -> new GenericException(String.format("Pessoa com o código %s não foi encontrada.", id)));
		var person = DozerConverter.parseObject(personVO, Person.class);
		BeanUtils.copyProperties(person, personExist, "id");
		return DozerConverter.parseObject(personRepository.save(personExist), PersonVO.class);
	}

	public void delete(Long id) {
		Person person = personRepository.findById(id).orElseThrow();
		personRepository.delete(person);
	}
}
