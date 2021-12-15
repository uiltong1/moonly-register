package br.com.moonlyRegister.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.moonlyRegister.service.PersonService;
import br.com.moonlyRegister.vo.PersonVO;
import br.com.moonlyRegister.vo.PersonVOV2;

// @CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<PersonVO> index() {
		List<PersonVO> personVO = personService.index();
		personVO.stream().forEach(p -> p.add(linkTo(PersonController.class).slash(p.getKey()).withSelfRel()));
		return personVO;
	}

	// @CrossOrigin(origins = {"http://localhost:8080"})
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO get(@PathVariable("id") Long id) {
		PersonVO personVO = personService.get(id);
		personVO.add(linkTo(PersonController.class).slash(personVO.getKey()).withSelfRel());
		return personVO;
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO store(@RequestBody PersonVO personVO) {
		PersonVO personResponseVO = personService.store(personVO);
		personResponseVO.add(linkTo(PersonController.class).slash(personResponseVO.getKey()).withSelfRel());
		return personResponseVO;
	}

	@PostMapping("/v2")
	public PersonVOV2 storeV2(@RequestBody PersonVOV2 personVOV2) {
		PersonVOV2 personResponseVOV22 = personService.storeV2(personVOV2);
		personResponseVOV22.add(linkTo(PersonController.class).slash(personResponseVOV22.getKey()).withSelfRel());
		return personResponseVOV22;
	}

	@PutMapping(value = "/{id}", produces = { "application/json", "application/xml",
			"application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO update(@PathVariable("id") Long id, @RequestBody PersonVO personVO) {
		PersonVO personResponseVO = personService.update(id, personVO);
		personResponseVO.add(linkTo(PersonController.class).slash(personResponseVO.getKey()).withSelfRel());
		return personResponseVO;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		personService.delete(id);
	}
}
