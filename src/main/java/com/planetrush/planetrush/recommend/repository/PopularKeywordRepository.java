package com.planetrush.planetrush.recommend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planetrush.planetrush.planet.domain.Category;
import com.planetrush.planetrush.recommend.domain.PopularKeyword;

public interface PopularKeywordRepository extends JpaRepository<PopularKeyword, Long> {

	List<PopularKeyword> findByCategory(Category category);

}
