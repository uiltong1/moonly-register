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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.moonlyRegister.service.BookService;
import br.com.moonlyRegister.vo.BookVO;

@RequestMapping("/api/v1/book")
@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private PagedResourcesAssembler<BookVO> assembler;

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<PagedModel<EntityModel<BookVO>>> index(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "direction", defaultValue = "desc") String direction) {

		var sortOrder = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortOrder, "title"));

		Page<BookVO> booksVO = bookService.index(pageable);
		booksVO.stream().forEach(b -> b.add(linkTo(BookController.class).slash(b.getKey()).withSelfRel()));
		return ResponseEntity.ok(assembler.toModel(booksVO));
	}

	@GetMapping(value = "/findbookbytitle/{title}", produces = { "application/json", "application/xml",
			"application/x-yaml" })
	public ResponseEntity<PagedModel<EntityModel<BookVO>>> findBookByTitle(@PathVariable("title") String title,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "direction", defaultValue = "desc") String direction) {

		var sortOrder = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortOrder, "title"));

		Page<BookVO> booksVo = bookService.findBookByTitle(title, pageable);
		booksVo.stream().forEach(b -> b.add(linkTo(BookController.class).slash(b.getKey()).withSelfRel()));

		return ResponseEntity.ok(assembler.toModel(booksVo));

	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public BookVO get(@PathVariable("id") Long id) {
		BookVO bookVO = bookService.get(id);
		bookVO.add(linkTo(BookController.class).slash(bookVO.getKey()).withSelfRel());
		return bookVO;
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BookVO store(@RequestBody BookVO bookVO) {
		BookVO bookVOResponse = bookService.store(bookVO);
		bookVOResponse.add(linkTo(BookController.class).slash(bookVOResponse.getKey()).withSelfRel());
		return bookVOResponse;
	}

	@PutMapping(value = "/{id}", produces = { "application/json", "application/xml",
			"application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	public BookVO update(@PathVariable("id") Long id, @RequestBody BookVO bookVO) {
		BookVO bookVOResponse = bookService.update(id, bookVO);
		bookVOResponse.add(linkTo(BookController.class).slash(bookVOResponse.getKey()).withSelfRel());
		return bookVOResponse;
	}

	@DeleteMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public void delete(@PathVariable("id") Long id) {
		bookService.delete(id);
	}
}
