package com.example.phonebook.helpers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Helper {
    public static String createLinkHeader(String baseUrl, int page, int size, String sortBy, String direction, int totalPages) {
        StringBuilder linkHeader = new StringBuilder();

        String url = baseUrl + "?page=%d&size=%d&sortBy=%s&dir=%s";
        
        if (page > 0) {
            linkHeader.append(String.format("<" + url + ">; rel=\"curr\",", page, size, sortBy, direction));
            linkHeader.append(String.format("<" + url + ">; rel=\"first\",", 1, size, sortBy, direction));
            if(page - 1 > 0){
                linkHeader.append(String.format("<" + url + ">; rel=\"prev\",", page - 1, size, sortBy, direction));
            }
        }

        if (page < totalPages) {
            linkHeader.append(String.format("<" + url + ">; rel=\"last\"", totalPages - 1, size, sortBy, direction));
            if(page + 1 < totalPages){
                linkHeader.append(String.format("<" + url + ">; rel=\"next\",", page + 1, size, sortBy, direction));
            }
        }
        

        return linkHeader.toString();
    }

    public static String getFullUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequestUri(request)
                .replaceQuery(null)
                .build()
                .toUriString();
    }
}
