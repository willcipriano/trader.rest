package trader.rest.combat.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import trader.rest.combat.dao.CharacterSheetDao;

import java.util.UUID;

@Repository
public interface CharacterSheetDaoRepository extends CrudRepository<CharacterSheetDao, UUID>, PagingAndSortingRepository<CharacterSheetDao, UUID> {
    CharacterSheetDao findByUuid(UUID uuid);
}