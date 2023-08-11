package de.sboe0705.books.data;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import de.sboe0705.books.model.Book;
import de.sboe0705.books.rest.BooksController;

@DataJpaTest
class BooksRepositoryTest {

	@Autowired
	private BooksRepository underTest;

	// In @DataJpaTests only the data layer is initialized!
	// So no Beans from other layers (@Repository, @Service) are available.
	@Autowired(required = false)
	private BooksController booksController;

	@Test
	void testUninitializedController() throws Exception {
		Assertions.assertThat(booksController).isNull();
	}

	@Test
	@Sql({ "/testdata.sql" })
	void testFindById() throws Exception {
		// given
		long bookId = underTest.findAll().iterator().next().getId();

		// when
		Optional<Book> bookOptional = underTest.findById(bookId);

		// then
		Assertions.assertThat(bookOptional) //
				.isPresent() //
				.get() //
				.extracting(Book::getId, Book::getTitle, Book::getAuthor) //
				.containsExactly(bookId, "Jim Knopf und Lukas der Lokomotivführer", "Michael Ende");
	}

	@Test
	@Sql({ "/testdata.sql" })
	void testFindAllById() throws Exception {
		// given
		Iterator<Book> bookIterator = underTest.findAll().iterator();
		long bookId1 = bookIterator.next().getId();
		long bookId2 = bookIterator.next().getId();

		// when
		Iterable<Book> books = underTest.findAllById(List.of(bookId1, bookId2));

		// then
		Assertions.assertThat(books) //
				.hasSize(2).extracting(Book::getId, Book::getTitle, Book::getAuthor) //
				.containsExactly( //
						Assertions.tuple(bookId1, "Jim Knopf und Lukas der Lokomotivführer", "Michael Ende"), //
						Assertions.tuple(bookId2, "Momo", "Michael Ende") //
				);
	}

	@Test
	@Sql({ "/testdata.sql" })
	void testDelete() {
		// given
		long count = underTest.count();
		long bookId = underTest.findAll().iterator().next().getId();

		// when
		underTest.deleteById(bookId);

		// then
		Assertions.assertThat(underTest.count()).isEqualTo(count - 1);
	}

	@Test
	void testSave() throws Exception {
		Book book = new Book();
		book.setAuthor("Robert Galbraith");
		book.setTitle("Der Ruf des Kuckucks"); // 2013

		// when
		underTest.save(book);

		// then
		Assertions.assertThat(book.getId()).isNotNull();
	}

}
