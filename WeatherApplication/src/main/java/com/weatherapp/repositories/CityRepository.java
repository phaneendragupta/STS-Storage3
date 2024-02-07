package com.weatherapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weatherapp.entities.City;
import com.weatherapp.entities.User;

@Repository

public interface CityRepository extends JpaRepository<City, Long> {

	@Query(value="SELECT * FROM City  c WHERE c.user = ?1 order by search_date desc limit=5",nativeQuery=true)

	List<User> findLastFiveCity();

	@Query(value="SELECT * FROM City  c WHERE c.user = ?1 and city_name= ?2 " ,nativeQuery=true)

	City findCityByUserAndCityName(Long id,String city);

}
