package repository;

import domain.Entity;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.paging.Page;
import repository.paging.Pageable;
import repository.paging.PagingRepository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRepository<ID extends Serializable,
        T extends Entity<ID>> implements PagingRepository<ID, T> {

    protected Map<ID, T> entities;
    protected Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }

        return Optional.ofNullable(this.entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return this.entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null.");
        }

        this.validator.validate(entity);

        T newEntity = this.entities.putIfAbsent(entity.getId(), entity);

        return Optional.ofNullable(newEntity);
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }

        T removedEntity = this.entities.remove(id);

        return Optional.ofNullable(removedEntity);
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null.");
        }

        this.validator.validate(entity);

        T updatedEntity = this.entities.computeIfPresent(entity.getId(), (key, value) -> entity);

        return Optional.ofNullable(updatedEntity);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        //TODO: pagination logic should be delegated
        throw new RuntimeException("not yet implemented");
    }
}
