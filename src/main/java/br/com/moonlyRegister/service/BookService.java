package br.com.moonlyRegister.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.moonlyRegister.exception.GenericException;
import br.com.moonlyRegister.mapper.serialization.converter.DozerConverter;
import br.com.moonlyRegister.model.Book;
import br.com.moonlyRegister.repository.BookRepository;
import br.com.moonlyRegister.vo.BookVO;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Page<BookVO> index(Pageable pageable) {
		var page = bookRepository.findAll(pageable);
		return page.map(this::convertToBookVO);
	}

	public Page<BookVO> findBookByTitle(String title, Pageable pageable) {
		var page = bookRepository.findBookByTitle(title, pageable);
		return page.map(this::convertToBookVO);
	}

	public BookVO get(Long id) {
		var entity = bookRepository.findById(id).orElseThrow(
				() -> new GenericException(String.format("Não foi possível encontrar livro com o código %s.", id)));
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public BookVO store(BookVO bookVO) {
		var entity = bookRepository.save(DozerConverter.parseObject(bookVO, Book.class));
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public BookVO update(Long id, BookVO bookVO) {
		Book bookExist = bookRepository.findById(id).orElseThrow(
				() -> new GenericException(String.format("Não foi possível encontrar livro com o código %s.", id)));
		Book book = DozerConverter.parseObject(bookVO, Book.class);
		BeanUtils.copyProperties(book, bookExist, "id");

		return DozerConverter.parseObject(bookRepository.save(bookExist), BookVO.class);
	}

	public void delete(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(
				() -> new GenericException(String.format("Não foi possível encontrar livro com o código %s.", id)));
		bookRepository.delete(book);
	}

	private BookVO convertToBookVO(Book book) {
		return DozerConverter.parseObject(book, BookVO.class);
	}

}
