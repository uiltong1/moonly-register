package br.com.moonlyRegister.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import br.com.moonlyRegister.service.BookService;
import br.com.moonlyRegister.vo.BookVO;

@RequestMapping("/api/v1/book")
@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<BookVO> index() {
		List<BookVO> booksVO = bookService.index();
		booksVO.stream().forEach(b -> b.add(linkTo(BookController.class).slash(b.getKey()).withSelfRel()));
		return booksVO;
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
