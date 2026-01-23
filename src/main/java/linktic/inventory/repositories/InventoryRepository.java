package linktic.inventory.repositories;

import linktic.inventory.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Integer> {
    Optional<InventoryEntity> findByProductId(Integer productId);
}
