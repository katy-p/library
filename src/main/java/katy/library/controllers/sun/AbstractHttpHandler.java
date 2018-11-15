package katy.library.controllers.sun;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        log.info("Start processing request on uri '{}'", exchange.getRequestURI());
        String response;
        boolean error = false;

        try {
            response = produseResponse(exchange);
        } catch (Exception e) {
            error = true;
            response = handleException(e);
        }

        if (!response.endsWith("\n")) {
            response = response + "\n";
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(error ? 500 : 200, bytes.length);
        exchange.getResponseBody().write(bytes);
        log.info("End processing request on uri '{}'", exchange.getRequestURI());
    }

    private String produseResponse(HttpExchange exchange) {

        switch (exchange.getRequestMethod().toUpperCase()) {
            case "GET":
                return onGet(exchange.getRequestURI());
            case "DELETE":
                return onDelete(exchange.getRequestURI());
            case "PUT":
                return onPut(exchange.getRequestURI(), null);
            case "POST":
                return onPost(exchange.getRequestURI(), null);
            default:
                throw new RuntimeException("Unsupported method: " + exchange.getRequestMethod());
        }
    }

    public abstract String path();

    protected abstract String onPost(URI requestURI, InputStream requestBody);

    protected abstract String onPut(URI requestURI, InputStream requestBody);

    protected abstract String onDelete(URI requestURI);

    protected abstract String onGet(URI requestURI);


    protected String handleException(Exception e) {
        log.error("Exception in controller", e);

        return "Exception: " + e.getMessage();
    }
}
