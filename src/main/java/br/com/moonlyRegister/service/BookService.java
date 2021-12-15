package br.com.moonlyRegister.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	public List<BookVO> index() {
		return DozerConverter.parseListObjects(bookRepository.findAll(), BookVO.class);
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

}
