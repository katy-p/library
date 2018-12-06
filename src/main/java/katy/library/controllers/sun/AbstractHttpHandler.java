package katy.library.controllers.sun;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import katy.library.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;

@Slf4j
public abstract class AbstractHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        log.info("Start processing request on uri '{}'", exchange.getRequestURI());

        try {
            String response = produseResponse(exchange);

            if (!response.endsWith("\n")) {
                response = response + "\n";
            }

            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try(final OutputStream responseBody = exchange.getResponseBody()) {
                responseBody.write(bytes);
            }
        } catch (Exception e) {
            handleException(e, exchange);
        }

        log.info("End processing request on uri '{}'", exchange.getRequestURI());
    }

    public OptionalLong getId(URI requestURI){

        String[] params = requestURI.getPath().split("/");

        if (params.length == 3) {
            return OptionalLong.of(Long.parseLong(params[params.length - 1]));
        } else if (params.length == 2) {
            return OptionalLong.empty();
        }
        else {
            throw new RuntimeException("Invalid path");
        }
    }

    protected Map<String, String> getParameters(URI requestURI) {

        Map<String, String> queryParams = new HashMap<>();

        for (String q : requestURI.getQuery().split("&")) {
            String[] qa = q.split("=");

            queryParams.put(URLDecoder.decode(qa[0]), (qa.length == 2) ? URLDecoder.decode(qa[1]) : "");
        }

        return queryParams;
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


    protected void handleException(Exception e, HttpExchange exchange) throws IOException {
        log.error("Exception in controller", e);

        String response;
        final int status;

        if (e instanceof ResourceNotFoundException) {
            status = 404;
            response = e.getMessage();
        } else {
            status = 500;
            response = "Exception: " + e.getMessage();
        }

        if (!response.endsWith("\n")) {
            response = response + "\n";
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        try(final OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write(bytes);
        }
    }
}
