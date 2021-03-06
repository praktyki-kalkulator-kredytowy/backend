package com.praktyki.backend.app.data.repositories;

import com.praktyki.backend.app.data.entities.ConfigurationEntryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends CrudRepository<ConfigurationEntryEntity, Integer> {

    @Query(value = "SELECT * FROM configuration WHERE configuration_group = :group", nativeQuery = true)
    Iterable<ConfigurationEntryEntity> findEntriesForGroup(String group);

    @Query(value = "DELETE FROM configuration WHERE configuration_group = :group and configuration_key = :key",
            nativeQuery = true
    )
    void removeKey(String group, String key);

    @Query(value = "SELECT * FROM configuration WHERE configuration_group = :group and configuration_key = :key",
            nativeQuery = true
    )
    Optional<ConfigurationEntryEntity> find(String group, String key);



}
