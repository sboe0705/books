package de.sboe0705.books.data;

import java.util.ArrayList;
import java.util.List;

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
	void testUninitialized() throws Exception {
		Assertions.assertThat(booksController).isNull();
	}

	@Test
	@Sql({ "/testdata.sql" })
	void testSchemaAndCommonCrudMethods() throws Exception {
		Assertions.assertThat(underTest.count()).isEqualTo(10);

		Book book = new Book();
		book.setAuthor("Robert Galbraith");
		book.setTitle("Der Ruf des Kuckucks"); // 2013
		underTest.save(book);
		Assertions.assertThat(book.getId()).isNotNull();

		Assertions.assertThat(underTest.count()).isEqualTo(11);

		underTest.deleteById(book.getId());

		List<Book> books = new ArrayList<>();
		underTest.findAll().forEach(books::add);
		Assertions.assertThat(books).hasSize(10);
	}

}
