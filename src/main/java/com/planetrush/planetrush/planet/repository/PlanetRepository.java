package com.planetrush.planetrush.planet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.planetrush.planetrush.planet.domain.Planet;

import jakarta.persistence.LockModeType;

public interface PlanetRepository extends JpaRepository<Planet, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Planet p where p.id = :id")
	Optional<Planet> findByIdForUpdate(Long id);
}
