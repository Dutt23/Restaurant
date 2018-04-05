package com.stackroute.restaurant.unittests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.stackroute.restaurant.domain.Restaurant;
import com.stackroute.restaurant.repositories.RestaurantRepository;
import com.stackroute.restaurant.services.RestaurantServiceMySQLImpl;

public class RestaurantServiceLayerTest {
    /**
	 * mocking the MovieRepository
	 */
	@Mock
	private transient RestaurantRepository restaurantRepo;

	/**
	 * mocking Movie Object
	 */
	private transient Restaurant restaurant;

	/**
	 * injecting mocks in MovieServiceImpl object
	 */
	@InjectMocks
	private transient RestaurantServiceMySQLImpl restaurantServiceImpl;

	/**
	 * variable to hold user defined movies list
	 */
	transient Optional<Restaurant> options;

	/**
	 * Initializing the object declarations
	 */
	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		restaurant = new Restaurant(1, "POC", "good Movie", "www.abc.com");
		options = Optional.of(restaurant);

	}

	/**
	 * testing mock creation
	 */
	@Test
	public void testMockCreation() {
		assertNotNull("jpaRepository creation fails: use @injectMocks on movieServicempl", restaurantRepo);
	}


	/**
	 * testing the save method
	 * 
	 * @throws MovieAlreadyExistsException
	 */
	@Test
	public void testAddRestaurantSuccess() throws Exception{
		Mockito.when(restaurantRepo.save(restaurant)).thenReturn(restaurant);
		final boolean flag = restaurantServiceImpl.addRestaurant(restaurant);
		assertTrue("saving movie failed , the call to movieDAOImpl is returning false ,check this method", flag);
		verify(restaurantRepo, times(1)).save(restaurant);
		
   
}
	@Test
	public void testDeleteRestaurantSuccess() throws Exception{
		int id = 1;
		Mockito.doNothing().when(restaurantRepo).deleteById(1);
		final boolean flag = restaurantServiceImpl.deleteRestaurantById(id);
		assertTrue("saving movie failed , the call to movieDAOImpl is returning false ,check this method", flag);
		verify(restaurantRepo, times(1)).deleteById(1);
		
   
}
	@Test
	public void testUpdateRestaurantByIdSuccess() throws Exception{
		int id = 1;
		Restaurant restaurant1 = new Restaurant(1, "POC", "dubai", "www.abc.com");
		
		Mockito.when(restaurantRepo.save(restaurant)).thenReturn(restaurant);
		restaurant.setId(id);
		final boolean flag = restaurantServiceImpl.updateRestaurantById(restaurant1);
		assertTrue("saving movie failed , the call to movieDAOImpl is returning false ,check this method", flag);
		verify(restaurantRepo, times(1)).save(restaurant1);
		
   
}
	@Test
	public void testGetAllRestaurants() throws Exception{
		
		Restaurant restaurant1 = new Restaurant(2, "POC", "dubai", "www.abc.com");
		Restaurant restaurant2 = new Restaurant(3, "Hi", "I is ", "Shatyaki");
		List<Restaurant> convertedList = new ArrayList<>();
		convertedList.add(restaurant1);
		convertedList.add(restaurant2);
		Mockito.when(restaurantRepo.findAll()).thenReturn(convertedList);
		Iterable <Restaurant> resultList = restaurantServiceImpl.getAllRestaurants();
		assertEquals("saving movie failed ", convertedList, resultList);
		verify(restaurantRepo, times(1)).findAll();
	
	
}
	@Test
	public void testGetRestaurantById() throws Exception{
		
		Mockito.when(restaurantRepo.findById(1)).thenReturn(options);
		Optional<Restaurant> resultList = restaurantServiceImpl. findRestaurantById(1);
		assertEquals("saving movie failed ", options, resultList);
		verify(restaurantRepo, times(1)).findById(1);
	
	
}
	
}
