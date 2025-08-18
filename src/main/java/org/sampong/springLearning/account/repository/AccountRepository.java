package org.sampong.springLearning.account.repository;

import jakarta.transaction.Transactional;
import org.sampong.springLearning.account.model.Account;
import org.sampong.springLearning.share.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    Optional<Account> findByAccountHolderNameAndStatusTrue(String name);
    Boolean existsByAccountNumberAndStatusTrue(String accountNumber);

    @Transactional
    @Modifying
    @Query("update Account set status = false where id = :id and status = true")
    void delete(Long id);
}
