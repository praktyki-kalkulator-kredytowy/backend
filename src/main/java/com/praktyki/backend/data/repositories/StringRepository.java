package com.praktyki.backend.data.repositories;

import com.praktyki.backend.data.entities.StringEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StringRepository extends CrudRepository<StringEntity, Integer> {}
