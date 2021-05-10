package com.praktyki.backend.app.data.repositories;

import com.praktyki.backend.app.data.entities.StringEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StringRepository extends CrudRepository<StringEntity, Integer> {}
