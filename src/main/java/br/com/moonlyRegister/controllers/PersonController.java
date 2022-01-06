package br.com.moonlyRegister.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.moonlyRegister.service.PersonService;
import br.com.moonlyRegister.vo.PersonVO;
import br.com.moonlyRegister.vo.PersonVOV2;

// @CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequestMapping("api/v1/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> index(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortOrder = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortOrder, "firstname"));

		Page<PersonVO> personVO = personService.index(pageable);
		personVO.stream().forEach(p -> p.add(linkTo(PersonController.class).slash(p.getKey()).withSelfRel()));

		return ResponseEntity.ok(assembler.toModel(personVO));
	}

	@GetMapping(value = "/findpersonbyname/{firstname}", produces = { "application/json", "application/xml",
			"application/x-yaml" })
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonByName(
			@PathVariable("firstname") String firstname, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortOrder = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortOrder, "firstname"));

		Page<PersonVO> personVO = personService.findPersonByName(firstname, pageable);
		personVO.stream().forEach(p -> p.add(linkTo(PersonController.class).slash(p.getKey()).withSelfRel()));

		return ResponseEntity.ok(assembler.toModel(personVO));
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

	@PatchMapping(value = "/disable/{id}", produces = { "application/json", "application/xml",
			"application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO disablePerson(@PathVariable("id") Long id) {
		PersonVO personResponseVO = personService.disablePerson(id);
		return personResponseVO.add(linkTo(PersonController.class).slash(personResponseVO.getKey()).withSelfRel());
	}

	@PatchMapping(value = "/enable/{id}", produces = { "application/json", "application/xml",
			"application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO enablePerson(@PathVariable("id") Long id) {
		PersonVO personResponseVO = personService.enablePerson(id);
		return personResponseVO.add(linkTo(PersonController.class).slash(personResponseVO.getKey()).withSelfRel());
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		personService.delete(id);
	}
}
