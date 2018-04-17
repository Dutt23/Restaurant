package com.stackroute.restaurant.services;

import java.util.Optional;

import com.stackroute.restaurant.domain.Restaurant;
import com.stackroute.restaurant.exceptions.DataBaseNotAvailableException;
import com.stackroute.restaurant.exceptions.InvalidInputException;

public interface RestaurantService {
	
	public Restaurant addRestaurant(Restaurant restaurant) throws DataBaseNotAvailableException, InvalidInputException;
	public Iterable<Restaurant> getAllRestaurants() throws DataBaseNotAvailableException;
	public Optional<Restaurant> findRestaurantById(int id);
	public Boolean deleteRestaurantById(int id);
	public Restaurant updateRestaurantById(Restaurant restaurant);

}
