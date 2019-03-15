package lab5.catalog.core.repository;

import lab5.catalog.core.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CatalogRepository<T extends BaseEntity<ID>,
        ID extends Serializable>
        extends JpaRepository<T, ID> {
}
