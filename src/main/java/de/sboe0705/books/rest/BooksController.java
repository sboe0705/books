package de.sboe0705.books.rest;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.sboe0705.books.data.BooksRepository;
import de.sboe0705.books.model.Book;

@RestController
public class BooksController {

	@Autowired
	private BooksRepository bookRepository;

	@GetMapping("/books")
	public List<Book> getBooks() {
		List<Book> books = new ArrayList<>();
		bookRepository.findAll() //
				.forEach(books::add);
		return books;
	}

	@GetMapping("/book/{id}")
	public Book getBook(@PathVariable long id) {
		return bookRepository.findById(id) //
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Book ID does not exist!"));
	}

}
