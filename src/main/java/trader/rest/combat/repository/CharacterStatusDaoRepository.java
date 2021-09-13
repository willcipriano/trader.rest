package trader.rest.combat.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import trader.rest.combat.dao.CharacterStatusDao;

import java.util.UUID;

@Repository
public interface CharacterStatusDaoRepository extends CrudRepository<CharacterStatusDao, UUID>, PagingAndSortingRepository<CharacterStatusDao, UUID> {
    CharacterStatusDao findByCharacterSheetUuid(UUID uuid);
}
