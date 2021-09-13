package trader.rest.combat.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import trader.rest.combat.dao.CharacterInventoryDao;
import trader.rest.combat.dao.CharacterSheetDao;

import java.util.UUID;

@Repository
public interface CharacterInventoryDaoRepository extends CrudRepository<CharacterInventoryDao, UUID>, PagingAndSortingRepository<CharacterInventoryDao, UUID> {
    CharacterInventoryDao findByCharacterSheetUuid(UUID uuid);
}