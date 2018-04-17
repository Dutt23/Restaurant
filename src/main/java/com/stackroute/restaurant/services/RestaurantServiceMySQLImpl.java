package com.stackroute.restaurant.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.stackroute.restaurant.domain.Restaurant;
import com.stackroute.restaurant.exceptions.DataBaseNotAvailableException;
import com.stackroute.restaurant.exceptions.InvalidInputException;
import com.stackroute.restaurant.repositories.RestaurantRepository;

@Service
public class RestaurantServiceMySQLImpl implements RestaurantService {

	RestaurantRepository restaurantRepository;
	
	@Autowired
	public RestaurantServiceMySQLImpl(RestaurantRepository restaurantRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
	}

	@Override
	public Restaurant addRestaurant(Restaurant restaurant) throws DataBaseNotAvailableException, InvalidInputException{
		if(restaurantRepository.findById(restaurant.getId()).orElse(null) != null)
		{
			throw new InvalidInputException("Id already exists");
		}
		try {
			Restaurant restaurant1 = restaurantRepository.save(restaurant);
			return restaurant1;
		} 
		catch (Exception e) {
			
			throw  new DataBaseNotAvailableException("Database service isn't available");
		}
		
	}

	@Override
	public Iterable<Restaurant> getAllRestaurants() throws DataBaseNotAvailableException {
		try {
		Iterable<Restaurant> restaurantList = restaurantRepository.findAll();
		return restaurantList;
		}	
		catch(Exception e)
		{
			throw  new DataBaseNotAvailableException("Database service isn't available");
		}
		
		
	}
	
	@Override
	public Optional<Restaurant> findRestaurantById(int id) {
		Optional <Restaurant> foundRestaurant = restaurantRepository.findById(id);
		return foundRestaurant;
	}
	
	@Override
	public Restaurant updateRestaurantById(Restaurant restaurant) {
		restaurant = restaurantRepository.save(restaurant);
		return restaurant;
	}

	@Override
	public Boolean deleteRestaurantById(int id) {
		 restaurantRepository.deleteById(id);
		return true;
	}

	

	
}
