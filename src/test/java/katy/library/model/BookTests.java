package katy.library.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BookTests {

    @Test
    public void newbook() {
        final Book book = Book.builder()
                .id(1)
                .title("DiscWorld")
                .author("Terry Pratchett")
                .build();


        assertEquals(1, book.getId());
        assertEquals("DiscWorld", book.getTitle());
        assertEquals("Terry Pratchett", book.getAuthor());

        assertEquals("Book(id=1, title=DiscWorld, author=Terry Pratchett)", book.toString());


        final Book book2 = new Book(1, new String("DiscWorld"), "Terry Pratchett");

        assertEquals(true, book.equals(book2));
        assertEquals(book, book2);

        assertEquals(book.hashCode(), book2.hashCode());

        final Book book3 = new Book(1, "DiscWorld1", "Terry Pratchett");

        assertNotEquals(book, book3);

        final Book book4 = new Book(1, null, "Terry Pratchett");

        assertNotEquals(book, book4);
        assertNotEquals(book4, book);

        assertEquals(new Book(1, "myName", "Terry Pratchett"), book.withTitle("myName"));
    }
}
