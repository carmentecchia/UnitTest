package com.example.UnitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Exercise12ApplicationTests {
	@Autowired
	private UserController userController;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoad() {
		assertThat(userController).isNotNull();
	}

	@Test
	public void createUser()throws Exception{
		Users userEntity = new Users(12L,"Carmen","Tecchia","Carmen@gmail.com");
		mockMvc.perform(post("/users/newUser")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userEntity)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Alessio"))
				.andExpect(jsonPath("$.address").value("1039 Anderson"));
	}
	@Test
	public void getUsers()throws Exception{
		Users users = new Users(12L,"Carmen", "Tecchia");
		userRepository.save(users);
		mockMvc.perform(get("/users/getUsers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1L))
				.andExpect(jsonPath("$[0].name").value("Alessio"))
				.andExpect(jsonPath("$[0].address").value("Guacci"));
	}
	@Test
	public void changeAddress()throws Exception{
		Users userEntity = new Users(1L,"Alessio","1039 Anderson");
		userRepository.save(userEntity);
		Users user1= new Users(1L,"Alessio","Via Sebino 29");
		mockMvc.perform(put("/users/changeAddress/{id}",userEntity.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user1)))
				.andExpect(status().isOk())
				.andExpect((jsonPath("$.address").value("Via Sebino 29")));
	}

	@Test
	public void deleteAll() throws Exception{
		Users user = new Users(1L, "Alessio", "1039 Anderson");
		userRepository.save(user);
		mockMvc.perform(delete("/users/deleteUsers"))
				.andExpect(status().isOk());
	}
}