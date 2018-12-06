package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.BooksHttpHandler;
import katy.library.dao.BookDao;
import katy.library.dao.LibraryCardDao;
import katy.library.dao.map.BookMapDao;
import katy.library.dao.map.LibraryCardMapDao;
import katy.library.service.BookService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BookIntegrationMapTest extends BookIntegrationTestTemplate{

    private static HttpServer httpServer;
    private static String address;
    private static CloseableHttpClient httpClient;
    private static BookDao bookDao;
    private static LibraryCardDao libraryCardDao;

    protected CloseableHttpClient getHttpServer() { return httpClient;}

    protected String getAddress() {
        return address;
    }

    public BookDao setBookDao() { return bookDao; }

    public LibraryCardDao getLibraryCardDao() { return libraryCardDao; }


    @BeforeAll
    public static void init() throws IOException {

        httpClient = HttpClientBuilder.create().build();
        httpServer = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        bookDao = new BookMapDao();
        libraryCardDao = new LibraryCardMapDao();

        final BooksHttpHandler handler = new BooksHttpHandler(new BookService(bookDao, libraryCardDao));

        httpServer.createContext(handler.path(), handler);

        httpServer.start();

        address = "http:/" + httpServer.getAddress();
    }

    @AfterAll
    public static void teardown() throws IOException {
        httpClient.close();
        httpServer.stop(0);
    }

    @BeforeEach
    void cleanData() {

        bookDao.fullList().forEach(b -> bookDao.delete(b.getId()));
        libraryCardDao.fullList().forEach(lc -> libraryCardDao.delete(lc.getId()));
    }
}
