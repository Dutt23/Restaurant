package com.stackroute.restaurant.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.restaurant.domain.Restaurant;
import com.stackroute.restaurant.exceptions.DataBaseNotAvailableException;
import com.stackroute.restaurant.exceptions.InvalidInputException;
import com.stackroute.restaurant.exceptions.TableEmptyException;
import com.stackroute.restaurant.services.RestaurantService;
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1")
@RestController
public class RestaurantController {
	
	private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

	@Autowired
	RestaurantService restaurantService;

	@RequestMapping(value = "/restaurant", method = RequestMethod.POST)
	public ResponseEntity<Restaurant> addRestaurant(@RequestBody @Valid Restaurant restaurant, BindingResult result)
			
			throws InvalidInputException, DataBaseNotAvailableException {
		Restaurant restaurant1;
		if(restaurant.getId() == 0)
		{
			throw new InvalidInputException("You cannot enter a null id");
			
		}
		logger.info("Request for adding a new restaurant");
		if (result.hasFieldErrors()) {
             logger.error("Invalid input");
			throw new InvalidInputException("You have entered an invalid input");
		    
		}
		
		try 
		{
		restaurant1 = restaurantService.addRestaurant(restaurant);
		}catch(Exception e)
		{
			logger.error("Request for adding new restaurant failed , id already exists");
			throw new InvalidInputException("The id you entered already exists");
		}
		logger.info("Request for adding a new restaurant successfull");
		return new ResponseEntity<Restaurant>(restaurant1,HttpStatus.CREATED);	
	
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public ResponseEntity<Iterable> showAllRestaurant(Restaurant restaurant)
			throws TableEmptyException, DataBaseNotAvailableException {
		logger.info("Request for getting all restaurants");
		Iterable<Restaurant> allRestaurants = restaurantService.getAllRestaurants();

		if (allRestaurants.iterator().hasNext() == true) {
			logger.error("Request for getting all restaurants success");
			return new ResponseEntity<Iterable>(allRestaurants, HttpStatus.OK);

		}
		logger.info("Request for getting all restaurants failed");
		throw new TableEmptyException("The current database in empty");

	}

	@RequestMapping(value = "/getall/{id}", method = RequestMethod.GET)
	
	public ResponseEntity<Optional<Restaurant>> findRestaurantbyId(@PathVariable(value = "id") int id)
			throws DataBaseNotAvailableException, InvalidInputException {
		logger.info("Request for getting a restaurant by Id");
		Optional<Restaurant> foundRestaurant = restaurantService.findRestaurantById(id);
		
		if (foundRestaurant.orElse(null)== null) {
			logger.error("Request for getting a restaurant by Id failure");
			throw new InvalidInputException("The id entered does not exist");
		}
		logger.info("Request for getting a restaurant by Id success");
		return new ResponseEntity<Optional<Restaurant>>(foundRestaurant,HttpStatus.OK);

	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Restaurant> updateRestaurantbyId(@PathVariable(value = "id") int id,
			@RequestBody Restaurant restaurant) {
		logger.info("Request for updating a restaurant by Id");
		restaurant.setId(id);
		restaurant = restaurantService.updateRestaurantById(restaurant);
		logger.info("Request for updating a restaurant by id success");
		return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
		
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public  ResponseEntity<String> deleteRestaurant( @PathVariable(value = "id") int id ) throws InvalidInputException {
		logger.info("Request for deleting a restaurant by Id");
		if(restaurantService.findRestaurantById(id)== null)
		{
			logger.error("Request for deleting a restaurant by Id failure");
			throw new InvalidInputException("The id entered does not exist");
		}
		restaurantService.deleteRestaurantById(id);
		logger.info("Request for deleting a restaurant by id success");
		return new ResponseEntity<String>("Restaurant deleted",HttpStatus.OK);
	}
}
