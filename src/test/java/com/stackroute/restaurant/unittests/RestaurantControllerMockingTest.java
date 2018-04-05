package com.stackroute.restaurant.unittests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.restaurant.controller.RestaurantController;
import com.stackroute.restaurant.domain.Restaurant;
import com.stackroute.restaurant.repositories.RestaurantRepository;
import com.stackroute.restaurant.services.RestaurantService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantControllerMockingTest {

	private MockMvc restaurantMockMvc;
    
	
	@Mock
	Restaurant restaurant;
	
	@Mock
	RestaurantRepository restaurantRepository;
	
	@MockBean
	RestaurantService restaurantService;
    
	@InjectMocks
	 RestaurantController restaurantController = new RestaurantController();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		restaurantMockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
	}

	@After
	public void tearDown() throws Exception {
	}
	

//	@Test
//    public void testAddRestaurant() throws Exception {
//        
//        String restaurantJson = "{\"id\":\"4\",\"name\":\"quizine\",\"image\":\"imageURL\",\"location\":\"bangalore\"}";
//        Restaurant mockRestaurant = new Restaurant(4,"quizine", "imageURL","bangalore");
//        
//        Mockito.when(restaurantService.addRestaurant(Mockito.any(Restaurant.class)))
//            .thenReturn(true);
//        
//        MvcResult result = restaurantMockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurant")
//                .content(restaurantJson)
//                .contentType(MediaType.APPLICATION_JSON))
//            .andReturn();
//        String expected = "{id:4,name:quizine,image:imageURL,location:bangalore}";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
	
	@Test
	public void testUpdateRestaurant() throws Exception{
		
//String restaurantJson = "{\"id\":\"1\",\"name\":\"quizine\",\"image\":\"imageURL\",\"location\":\"bangalore\"}";				
Restaurant updatedRestaurant=new Restaurant(1,"john","John","password2");
//Optional<Restaurant> options= Optional.of(updatedRestaurant); 
//Mockito.when(restaurantService.updateRestaurantById(updatedRestaurant)).thenReturn(updatedRestaurant);
restaurantMockMvc.perform(put("/api/v1/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedRestaurant)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.name", is("john")))
//                .andExpect(jsonPath("$.location", is("John")))
//                .andExpect(jsonPath("$.password", is("password2")));
	
	

	}
	
	
	@Test
    public void testCreateNewUser() throws Exception {
		
		Restaurant restaurant=new Restaurant(1,"johh","John","password");
		
	
        restaurantMockMvc.perform(post("/api/v1/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(restaurant))) 
                .andExpect(status().isCreated());
        
        verify(restaurantService , times(1)).addRestaurant(Mockito.any(Restaurant.class));
//                .andExpect(jsonPath("$.name", is("john")))
//                .andExpect(jsonPath("$.location", is("John")))
//                .andExpect(jsonPath("$.image", is("password")));
    }
	
	@Test
    public void testFindById() throws Exception {
        
        String restaurantJson = "{\"id\":\"4\",\"name\":\"quizine\",\"image\":\"imageURL\",\"location\":\"bangalore\"}";
        Restaurant mockRestaurant = new Restaurant(4,"quizine", "imageURL","bangalore");
        
        Optional<Restaurant> optional = Optional.of(mockRestaurant); 
        
        Mockito.when(restaurantService.findRestaurantById(4))
        .thenReturn(optional);
        
        MvcResult result = restaurantMockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getall/4")
                    .content(restaurantJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String expected = "{id:4,name:quizine,image:imageURL,location:bangalore}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
	@Test
	public void testDeleteById() throws Exception {
		
		
        Restaurant mockRestaurant = new Restaurant(4,"quizine", "imageURL","bangalore");
        //Mockito.doNothing().when(restaurantService).deleteRestaurantById(4);
		
		restaurantMockMvc.perform(delete("/api/v1/delete/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockRestaurant)))
                .andExpect(status().isOk());
		
	}
	
	

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
