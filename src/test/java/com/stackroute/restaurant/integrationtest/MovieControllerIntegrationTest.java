package com.stackroute.restaurant.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.restaurant.Application;
import com.stackroute.restaurant.domain.Restaurant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerIntegrationTest {

	@LocalServerPort
	private int port;

	Restaurant restaurant;

	private TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	HttpEntity<Restaurant> entity;

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Before
	public void setUp() throws Exception {
		restaurant = new Restaurant(1, "Batman", "very good movie sss", "xyz");
		entity = new HttpEntity<Restaurant>(restaurant, headers);

	}

	@After
	public void tearDown() throws Exception {
		restTemplate.exchange(createURLWithPort("/api/v1/delete/1"), HttpMethod.DELETE, entity, String.class);

	}

	/**
	 * Testing save movie method
	 * @throws Exception
	 */
	@Test
	public void testSaveNewRestaurantSuccess() throws Exception {
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/restaurant"), HttpMethod.POST,
				entity, String.class);

		assertNotNull(response);
		String actual = response.getBody();
		assertNotNull(actual);
		assertEquals(201, response.getStatusCodeValue());

	}
	//Test find by Id
	@Test
	public void findRestaurantByIdSuccess() throws Exception {
		restTemplate.exchange(createURLWithPort("/api/v1/restaurant"), HttpMethod.POST,
				entity, String.class);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/getall/1"), HttpMethod.GET,
				entity, String.class);
		
		assertNotNull(response);
		String actual = response.getBody();
		assertNotNull(actual);
		assertEquals(200, response.getStatusCodeValue());

	}
//Test delete by Id
	@Test
	public void deleteRestaurantByIdSuccess() throws Exception {
		restTemplate.exchange(createURLWithPort("/api/v1/restaurant"), HttpMethod.POST,
				entity, String.class);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/delete/1"), HttpMethod.DELETE,
				entity, String.class);
		
		assertNotNull(response);
		String actual = response.getBody();
		assertNull(actual);
		assertEquals(200, response.getStatusCodeValue());

	}
	
	
	// Test get all restaurants
	@Test
	public void getAllRestaurantSuccess() throws Exception {
		restTemplate.exchange(createURLWithPort("/api/v1/restaurant"), HttpMethod.POST,
				entity, String.class);
		
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/getall"), HttpMethod.GET,
				entity, String.class);
		
		assertNotNull(response);
		String actual = response.getBody();
		assertNotNull(actual);
		assertEquals(200, response.getStatusCodeValue());
		

	}

	
	
	
	//Test Update by Id
	@Test
	public void updateRestaurantByIdSuccess() throws Exception {
		restTemplate.exchange(createURLWithPort("/api/v1/restaurant"), HttpMethod.POST,
				entity, String.class);
		restaurant = new Restaurant(1, "Hotel", "img", "xyz");
		entity = new HttpEntity<Restaurant>(restaurant, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/update/1"), HttpMethod.PUT,
				entity, String.class);
		
		assertNotNull(response);
		String actual = response.getBody();
		assertNull(actual);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("img",restaurant.getImage());

	}


}

