package com.praktyki.backend.app.mocks.data.repositories;

import com.praktyki.backend.app.data.entities.ConfigurationEntryEntity;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MockupConfigurationRepositoryImpl implements ConfigurationRepository {
    private final List<ConfigurationEntryEntity> mStorage = new LinkedList<>();

    @Override
    public Iterable<ConfigurationEntryEntity> findEntriesForGroup(String group) {
        return mStorage.stream()
                .filter(e -> e.group.equals(group))
                .collect(Collectors.toList());
    }

    @Override
    public void removeKey(String group, String key) {
        mStorage.removeIf(e -> e.group.equals(group) && e.key.equals(key));
    }

    @Override
    public Optional<ConfigurationEntryEntity> find(String group, String key) {
        return mStorage.stream()
                .filter(e -> e.group.equals(group) && e.key.equals(key))
                .findFirst();
    }

    @Override
    public <S extends ConfigurationEntryEntity> S save(S entity) {
        mStorage.add(entity);
        return entity;
    }

    @Override
    public <S extends ConfigurationEntryEntity> Iterable<S> saveAll(Iterable<S> entities) {
        for(ConfigurationEntryEntity e : entities)
            mStorage.add(e);
        return entities;
    }

    @Override
    public Optional<ConfigurationEntryEntity> findById(Integer integer) {
        return mStorage.stream()
                .filter(e -> e.id == integer)
                .findFirst();
    }

    @Override
    public boolean existsById(Integer integer) {
        return mStorage.stream().anyMatch(e -> e.id == integer);
    }

    @Override
    public Iterable<ConfigurationEntryEntity> findAll() {
        return mStorage;
    }

    @Override
    public Iterable<ConfigurationEntryEntity> findAllById(Iterable<Integer> integers) {
        List<Integer> ints = StreamSupport.stream(integers.spliterator(), false).collect(Collectors.toList());
        return mStorage.stream()
                .filter(e -> ints.contains(e.id))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return mStorage.size();
    }

    @Override
    public void deleteById(Integer integer) {
        mStorage.removeIf(e -> e.id == integer);
    }

    @Override
    public void delete(ConfigurationEntryEntity entity) {
        mStorage.remove(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends ConfigurationEntryEntity> entities) {
        for(ConfigurationEntryEntity e : entities)
            mStorage.remove(e);
    }

    @Override
    public void deleteAll() {
        mStorage.clear();
    }
}
