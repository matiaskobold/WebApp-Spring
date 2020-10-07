package com.springboot.webApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.webApp.apiREST.ResourceNotFoundException;
import com.springboot.webApp.model.Clan;
import com.springboot.webApp.repository.ClanRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.HttpMethod.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HttpRequestTest {

    @Autowired
    ClanRepository clanRepository;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/test",
                String.class)).contains("Hello, World");
    }

    // GET http://localhost:8080/clans gets 2 clans
    @Test
    @Order(1)
    public void returnClansTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json
        Clan clan1 = new Clan("description1", "language1", "name1");
        Clan clan2 = new Clan("description2", "language2", "name2");
        clanRepository.save(clan1);
        clanRepository.save(clan2);
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/clans", String.class))
                .isEqualTo("[" + objectMapper.writeValueAsString(clan1) + "," + objectMapper.writeValueAsString(clan2) + "]");


    }

    // GET http://localhost:8080/clans/{id} finds clan
    @Test
    public void getClanByIdTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json
        Clan clan1 = new Clan("description1", "language1", "name1");
        clanRepository.save(clan1);
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/clans/" + clan1.getId(), String.class))
                .isEqualTo(objectMapper.writeValueAsString(clan1));

    }

    // GET http://localhost:8080/clans/{id} clan doesn't and throws ResourceNotFoundException
    @Test
    public void getClanByIdNoClanForIdTest() {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/clans/99", ResourceNotFoundException.class).
                equals(ResourceNotFoundException.class));
    }

    // POST http://localhost:8080/clans post correct clan

    @Test
    public void createClanTestValidClan() throws JsonProcessingException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject clanJsonObject = new JSONObject();
        clanJsonObject.put("name", "name1");

        HttpEntity<String> request = new HttpEntity<String>(clanJsonObject.toString(), headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("http://localhost:" + port + "/clans", request, String.class);

        assertThat(responseEntityStr.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());

        assertThat(root.path("name").asText()).isEqualTo("name1");

    }

    @Test
    public void createClanTestInvalidClan() throws JSONException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject clanJsonObject = new JSONObject();
        clanJsonObject.put("description", "description");
        HttpEntity<String> request = new HttpEntity<String>(clanJsonObject.toString(), headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("http://localhost:" + port + "/clans", request, String.class);

        assertThat(responseEntityStr.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


    }


}