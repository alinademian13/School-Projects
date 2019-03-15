package repository.paging;

import domain.Entity;
import repository.Repository;

import java.io.Serializable;

public interface PagingRepository<ID extends Serializable,
        T extends Entity<ID>>
        extends Repository<ID, T> {

    Page<T> findAll(Pageable pageable);
}
