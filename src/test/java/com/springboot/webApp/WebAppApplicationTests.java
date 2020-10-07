package com.springboot.webApp;

import static org.assertj.core.api.Assertions.*;


import com.springboot.webApp.model.Clan;
import com.springboot.webApp.repository.ClanRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class WebAppApplicationTests {

	@Autowired
	ClanRepository clanRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void createClanTestAndExistsClanByName(){
		Clan clan = new Clan("description1", "language1", "name1");
		clan.setId(Long.valueOf("99"));
		clanRepository.save(clan);
		boolean exists = clanRepository.existsClanByName("name1");
		assertThat(exists).isTrue();
	}

	@Test
	public void testFindAllWithList() {
		Clan clan1 = new Clan("description1", "language1", "name1");
		Clan clan2 = new Clan("description2", "language2", "name2");
		clanRepository.save(clan1);
		clanRepository.save(clan2);
		List<Clan> clans = clanRepository.findAll();
		assertThat(clans).size().isEqualTo(3);
	}
	@Test
	public void testFailClanRepositorySaveMissingName() {
		Clan clan1 = new Clan();
		clan1.setLanguage("language1");
		clan1.setDescription("description1");
		assertThatExceptionOfType(ConstraintViolationException.class)
				.isThrownBy(
						()->clanRepository.save(clan1));

	}



	}
