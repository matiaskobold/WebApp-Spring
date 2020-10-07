package com.springboot.webApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.webApp.model.Clan;
import com.springboot.webApp.repository.ClanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    // http//localhost:8080/clans
    @Test
    public void returnClansTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(); //Para Jackson convertir object->Json
        Clan clan1 = new Clan("description1", "language1", "name1");
        Clan clan2 = new Clan("description2", "language2", "name2");
        clanRepository.save(clan1);
        clanRepository.save(clan2);
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/clans", String.class))
                .isEqualTo("["+objectMapper.writeValueAsString(clan1)+","+objectMapper.writeValueAsString(clan2)+"]");


    }






}