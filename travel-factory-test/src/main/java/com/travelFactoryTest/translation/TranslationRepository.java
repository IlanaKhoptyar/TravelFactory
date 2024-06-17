package com.travelFactoryTest.translation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TranslationRepository extends CrudRepository<Translation, Long> {

	public List<Translation> findByApplicationId(Long AppId);
}
