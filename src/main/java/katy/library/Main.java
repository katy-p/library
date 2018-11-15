package katy.library;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.*;
import katy.library.dao.dummy.AuthorDaoImpl;
import katy.library.dao.dummy.BookDaoImpl;
import katy.library.dao.dummy.LibraryCardDaoImpl;
import katy.library.service.AuthorService;
import katy.library.service.BookService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);

        httpServer.createContext("/dummy", new DummyController());
        httpServer.createContext("/echo", new EchoController());

        final List<AbstractHttpHandler> handlers = new ArrayList<>();
        handlers.add(new BooksHttpHandler(new BookService(new BookDaoImpl(), new LibraryCardDaoImpl())));
        handlers.add(new AuthorsHttpHandler(new AuthorService(new AuthorDaoImpl())));

        for (AbstractHttpHandler handler : handlers) {
            httpServer.createContext(handler.path(), handler);
        }

        httpServer.start();
    }
}
