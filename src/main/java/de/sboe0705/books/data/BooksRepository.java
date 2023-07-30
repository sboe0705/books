package de.sboe0705.books.data;

import org.springframework.data.repository.CrudRepository;

import de.sboe0705.books.model.Book;

public interface BooksRepository extends CrudRepository<Book, Long> {

}
	