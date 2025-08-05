package com.planetrush.planetrush.planet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planetrush.planetrush.planet.domain.image.DefaultPlanetImg;

public interface DefaultPlanetImgRepository extends JpaRepository<DefaultPlanetImg, Long> {
}
