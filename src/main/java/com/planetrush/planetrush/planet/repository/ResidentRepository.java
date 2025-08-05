package com.planetrush.planetrush.planet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planetrush.planetrush.planet.domain.Resident;

public interface ResidentRepository extends JpaRepository<Resident, Long> {

	Optional<Resident> findByMemberIdAndPlanetId(long memberId, long planetId);

	List<Resident> findByPlanetId(Long planetId);

}
