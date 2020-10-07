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
import org.springframework.web.bind.annotation.DeleteMapping;

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

    // POST http://localhost:8080/clans post valid clan

    @Test
    public void createClanTestValidClan() throws JsonProcessingException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject clanJsonObject = new JSONObject();
        clanJsonObject.put("name", "name1");

        HttpEntity<String> request = new HttpEntity<String>(clanJsonObject.toString(), headers);    //Construyo la request entera, con body(en JSON)+header
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("http://localhost:" + port + "/clans", request, String.class);    //Al URL le manda la Request y recibe una
                                                                                                    // ResponseEntity con el Httpstatus.CREATED y
                                                                                                    //  el body del objeto creado

        assertThat(responseEntityStr.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());

        assertThat(root.path("name").asText()).isEqualTo("name1");

    }

    // POST http://localhost:8080/clans post invalid clan
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

    // @PutMapping("/clans/{clanId}")
    @Test
    public void testUpdateClanValidClan() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json

        //Primero tengo que hacer el post por la APIrest para que me de la ID creada, si es que voy a correr este test con todos los otros test
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject clanJsonObject = new JSONObject();
        clanJsonObject.put("name", "name1");

        HttpEntity<String> request = new HttpEntity<String>(clanJsonObject.toString(), headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("http://localhost:" + port + "/clans", request, String.class);

        assertThat(responseEntityStr.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //JSonNode me sirve para mappear el body de la responseEntity y poder obtener el los atributos "id", "description", "name" y "language"
        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());
        String clanId=root.path("id").asText();

        //Una vez que obtuve el ID, puedo hacer el put en el mismo ID con un "name" nuevo.
        JSONObject clanJsonObjectUpdated = new JSONObject();
        clanJsonObjectUpdated.put("name", "nameUpdated");
        HttpEntity<String> requestUpdated = new HttpEntity<String>(clanJsonObjectUpdated.toString(), headers);
        ResponseEntity<String> responseEntityStrUpdated = restTemplate.exchange("http://localhost:" + port + "/clans/"+clanId,
                HttpMethod.PUT,
                requestUpdated,
                String.class );
        assertThat(responseEntityStrUpdated.getStatusCode()).isEqualTo(HttpStatus.OK);
        root=objectMapper.readTree(responseEntityStrUpdated.getBody());
        assertThat(root.path("name").asText()).isEqualTo("nameUpdated");


    }

    @Test
    public void testUpdateClanClanNotFound() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject clanJsonObject = new JSONObject();
        clanJsonObject.put("id", "999");
        clanJsonObject.put("name", "name1");

        HttpEntity<String> request = new HttpEntity<String>(clanJsonObject.toString(), headers);

        ResponseEntity<String> responseEntityStr = restTemplate.exchange("http://localhost:" + port + "/clans/"+999,
                HttpMethod.PUT,
                request,
                String.class );

        assertThat(responseEntityStr.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    //@DeleteMapping("/clans/{clanId}")
    public void testDeleteClan() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json

        //Primero tengo que hacer el post por la APIrest para que me de la ID creada, si es que voy a correr este test con todos los otros test
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject clanJsonObject = new JSONObject();
        clanJsonObject.put("name", "name1");

        HttpEntity<String> request = new HttpEntity<String>(clanJsonObject.toString(), headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("http://localhost:" + port + "/clans", request, String.class);

        assertThat(responseEntityStr.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //JSonNode me sirve para mappear el body de la responseEntity y poder obtener el los atributos "id", "description", "name" y "language"
        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());
        String clanId=root.path("id").asText();

        //Una vez que obtuve el ID, puedo hacer el delete en el mismo ID.

        ResponseEntity<String> responseEntityDeleted = restTemplate.exchange("http://localhost:" + port + "/clans/"+clanId,
                HttpMethod.DELETE,
                request,
                String.class);
        assertThat(responseEntityDeleted.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}