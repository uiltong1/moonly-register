package br.com.moonlyRegister.mapper.serialization.converter;

import br.com.moonlyRegister.model.Person;
import br.com.moonlyRegister.vo.PersonVOV2;

public class PersonConverter {

	public static PersonVOV2 converterEntityToVO(Person person) {
		PersonVOV2 personVoV2 = new PersonVOV2();
		personVoV2.setKey(person.getId());
		personVoV2.setFirstname(person.getFirstname());
		personVoV2.setLastname(person.getLastname());
		personVoV2.setGender(person.getGender());
		personVoV2.setAddress(person.getAddress());
		personVoV2.setBirthday(person.getBirthday());
		return personVoV2;
	}

	public static Person converterVOtoEntity(PersonVOV2 personVoV2) {
		Person person = new Person();
		person.setId(personVoV2.getKey());
		person.setFirstname(personVoV2.getFirstname());
		person.setLastname(personVoV2.getLastname());
		person.setGender(personVoV2.getGender());
		person.setAddress(personVoV2.getAddress());
		person.setBirthday(personVoV2.getBirthday());
		return person;
	}

}
