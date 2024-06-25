package com.example.restapiaston.service;

import com.example.restapiaston.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class UserService {

    private String apiUrl = "http://94.198.50.185:7081/api/users";

    private final RestTemplate restTemplate;

    private String setCookies = "Set-Cookie";
    private String browserCookies = "Cookie";

    public HttpHeaders getHttpHeaders(String sessioId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(setCookies, sessioId);
        return headers;
    }

//    public List<User> getUser() {
//
//        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(apiUrl, User[].class);
//        Object[] objects = responseEntity.getBody();
//
//        System.out.println("\nUsers = " + responseEntity.getBody() + "\n");
//        return List.of((User[]) objects);
//    }


    public String getUser() {

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        System.out.println("\nUsers = " + responseEntity.getBody() + "\n");
        return responseEntity.getBody();
    }


    public String save(User user, String sessionId) {

        HttpHeaders headers = getHttpHeaders(sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> saveResponce = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        System.out.println("\nsaveResponce = " + saveResponce.getBody() + "\n");

        return saveResponce.getBody();
    }


    public String update(User user, String sessionId) {

        HttpHeaders headers = getHttpHeaders(sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> updateResponce = restTemplate.exchange(apiUrl, HttpMethod.PUT, request, String.class);

        System.out.println("\nupdateResponce = " + updateResponce.getBody() + "\n");

        return updateResponce.getBody();
    }


    public String delete(User user, String sessionId) {

        HttpHeaders headers = getHttpHeaders(sessionId);
        HttpEntity<User> request = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponce = restTemplate.exchange(apiUrl + "/" + user.getId(), HttpMethod.DELETE, request, String.class);

        System.out.println("\ndeleteResponce = " + deleteResponce.getBody() + "\n");

        return deleteResponce.getBody();
    }


//    private String getSessionId() {
//        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
//        HttpHeaders headers = response.getHeaders();
//
////        String setCookies = "set-cookie";
//        List<String> stringCookies = headers.get(setCookies);
//        System.out.println("stringCookies = " + stringCookies.get(0) + "\n\n");
//
//
////        return stringCookies.get(0).substring(11, 43);
//        return stringCookies.get(0);
//    }


//    public Optional<String> readCookie(HttpServletRequest request , String name) {
//
//        return Arrays.stream(request.getCookies())
//                .filter(c -> name.equals(c.getName()))
//                .map((Cookie cookie) -> cookie.getValue())
//                .findAny();
//    }


    public String readCookie() {
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
        HttpHeaders headers = response.getHeaders();
        String setCookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        return setCookie;
    }


    public String getCode() {

        String sessionId = readCookie();
        System.out.println("\nsessionId = " + sessionId + "\n");

        User user = new User(3L, "James", "Brown", (byte) 120);
        String firstPart = save(user, sessionId);

        getUser();

        user.setName("Thomas");
        user.setLastName("Shelby");

        String secondPart = update(user, sessionId);
        String thirdPart = delete(user, sessionId);
        String code = firstPart + secondPart + thirdPart;

        return code;
    }

}
