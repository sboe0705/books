package de.sboe0705.books.rest;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import de.sboe0705.books.data.BooksRepository;
import de.sboe0705.books.model.Book;

@WebMvcTest
class BookControllerTest {

	// In @WebMvcTests only the rest layer is initialized!
	// Beans from other layers (@Repository, @Service) has to be mocked.
	@MockBean
	private BooksRepository userRepositoryMock;

	@Autowired
	private MockMvc mockMvc;

	@AfterEach
	public void tearDown() {
		Mockito.verifyNoMoreInteractions(userRepositoryMock);
	}

	@Test
	void testGetBooks() throws Exception {
		Book book = new Book();
		book.setId(1);
		book.setTitle("Die unendliche Geschichte");
		book.setAuthor("Michael Ende");

		// given
		Mockito.when(userRepositoryMock.findAll()) //
				.thenReturn(List.of(book));

		// when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/books"));

		// then
		result.andDo(MockMvcResultHandlers.print()) //
				.andExpect(MockMvcResultMatchers.status().isOk()) //
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo((int) book.getId()))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.equalTo(book.getTitle()))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].author", Matchers.equalTo(book.getAuthor())));

		Mockito.verify(userRepositoryMock).findAll();
	}

	@Test
	void testGetBook() throws Exception {
		Book book = new Book();
		book.setId(1);
		book.setTitle("Die unendliche Geschichte");
		book.setAuthor("Michael Ende");

		// given
		Mockito.when(userRepositoryMock.findById(book.getId())) //
				.thenReturn(Optional.of(book));

		// when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/book/" + book.getId()));

		// then
		result.andDo(MockMvcResultHandlers.print()) //
				.andExpect(MockMvcResultMatchers.status().isOk()) //
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo((int) book.getId()))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.equalTo(book.getTitle()))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$.author", Matchers.equalTo(book.getAuthor())));

		Mockito.verify(userRepositoryMock).findById(book.getId());
	}

	@Test
	void testGetBookNotExisting() throws Exception {
		long id = 1;

		// given
		Mockito.when(userRepositoryMock.findById(id)) //
				.thenReturn(Optional.empty());

		// when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/book/" + id));

		// then
		result.andDo(MockMvcResultHandlers.print()) //
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		Mockito.verify(userRepositoryMock).findById(id);
	}

}