package com.praktyki.backend.app.data.repositories;

import com.praktyki.backend.app.data.entities.ConfigurationEntryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<ConfigurationEntryEntity, Integer> {

    @Query(value = "SELECT * FROM configuration WHERE group = :group", nativeQuery = true)
    Iterable<ConfigurationEntryEntity> findEntriesForGroup(String group);

}
