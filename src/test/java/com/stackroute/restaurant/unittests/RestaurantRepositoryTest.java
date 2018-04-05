package com.stackroute.restaurant.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.stackroute.restaurant.domain.Restaurant;
import com.stackroute.restaurant.repositories.RestaurantRepository;

@RunWith(SpringRunner.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
//@Transactional(rollbackFor =  RuntimeException.class)
public class RestaurantRepositoryTest{

@Autowired
RestaurantRepository restaurantRepository;

@Test
public void testAddRestaurant()
{
	Restaurant restaurant = new Restaurant(1,"3","4","5");
	Optional<Restaurant> expectedResult = Optional.of(restaurant);
	restaurantRepository.save(restaurant);
	assertEquals(expectedResult.get().getId(),restaurantRepository.findById(1).get().getId());
}
@Test
public void testDeleteRestaurantById()
{
	Restaurant restaurant = new Restaurant(1,"3","4","5");
	restaurantRepository.save(restaurant);
	restaurantRepository.deleteById(1);
	 assertNull(restaurantRepository.findById(1).orElse(null));
}

@Test
    public void testFindAllRestaurants() {
        restaurantRepository.deleteAll();
        Restaurant restaurant = new Restaurant(4,"teetam", "imageURLTeetam","bangalore");
        restaurantRepository.save(restaurant);
        Restaurant restaurant1 = new Restaurant(5,"hughh", "imageURLHugh","delhi");
        restaurantRepository.save(restaurant1);
        List <Restaurant>actualResult= (List<Restaurant>) restaurantRepository.findAll();
        List <Restaurant> expectedResult = new ArrayList<>();
        expectedResult.add(restaurant);
        expectedResult.add(restaurant1);
       assertEquals(expectedResult.size(),actualResult.size());          
       
}
@Test
public void testGetRestaurantById()
{
	Restaurant restaurant = new Restaurant(1,"3","4","5");
	Restaurant restaurant1 = new Restaurant(2,"3","4","5");
	restaurantRepository.save(restaurant);
	restaurantRepository.save(restaurant1);
	assertEquals(restaurant1.getId(),restaurantRepository.findById(2).get().getId());
}
@Test
public void testUpdateRestaurantById()
{
	Restaurant restaurant = new Restaurant(1,"SkoolRoom","Image1","Ulsoor");
	Restaurant restaurant1 = new Restaurant(1,"Despiwich","image2","OMBR");
	restaurantRepository.save(restaurant);
	restaurantRepository.save(restaurant1);
	assertEquals(restaurant1.getName(),restaurantRepository.findById(1).get().getName());
}

}
