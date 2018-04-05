package com.stackroute.restaurant.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.restaurant.domain.Restaurant;
import com.stackroute.restaurant.exceptions.DataBaseNotAvailableException;
import com.stackroute.restaurant.exceptions.InvalidInputException;
import com.stackroute.restaurant.exceptions.TableEmptyException;
import com.stackroute.restaurant.services.RestaurantService;

@RequestMapping("api/v1")

@RestController
public class RestaurantController {
	

	@Autowired
	RestaurantService restaurantService;

	@RequestMapping(value = "/restaurant", method = RequestMethod.POST)
	public ResponseEntity<String> addRestaurant(@RequestBody @Valid Restaurant restaurant, BindingResult result)
			throws InvalidInputException, DataBaseNotAvailableException {

		if (result.hasErrors()) {

			throw new InvalidInputException("You have entered an invalid input");
		}
		restaurantService.addRestaurant(restaurant);
		return new ResponseEntity<String>("Created",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public ResponseEntity<Iterable> showAllRestaurant(Restaurant restaurant)
			throws TableEmptyException, DataBaseNotAvailableException {

		Iterable<Restaurant> allRestaurants = restaurantService.getAllRestaurants();

		if (allRestaurants.iterator().hasNext() == true) {
			return new ResponseEntity<Iterable>(allRestaurants, HttpStatus.OK);

		}

		throw new TableEmptyException("The current database in empty");

	}

	@RequestMapping(value = "/getall/{id}", method = RequestMethod.GET)
	
	public ResponseEntity<Optional<Restaurant>> findRestaurantbyId(@PathVariable(value = "id") int id)
			throws DataBaseNotAvailableException, InvalidInputException {

		Optional<Restaurant> foundRestaurant = restaurantService.findRestaurantById(id);
		if (foundRestaurant == null) {
			throw new InvalidInputException("The id entered does not exist");
		}
		return new ResponseEntity<Optional<Restaurant>>(foundRestaurant,HttpStatus.OK);

	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Restaurant> updateRestaurantbyId(@PathVariable(value = "id") int id,
			@RequestBody Restaurant restaurant) {
		restaurant.setId(id);
		restaurantService.updateRestaurantById(restaurant);
		return new ResponseEntity<Restaurant>(HttpStatus.OK);

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public  ResponseEntity<Restaurant> deleteRestaurant(@PathVariable(value = "id") int id) {
		restaurantService.deleteRestaurantById(id);
		return new ResponseEntity<Restaurant>(HttpStatus.OK);
	}
}
