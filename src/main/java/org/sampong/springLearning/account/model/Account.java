package org.sampong.springLearning.account.model;

import jakarta.persistence.*;
import lombok.*;
import org.sampong.springLearning.share.base.BaseEntity;
import org.sampong.springLearning.share.enumerate.Currency;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number", unique = true)
    private String accountNumber;
    @Column(name = "account_holder_name")
    private String accountHolderName;
    @Column(name = "acc_create_date")
    private LocalDateTime accCreateDate;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(name = "balance_amount")
    private Double balanceAmount;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account account)) return false;
        return Objects.equals(getId(), account.getId()) && Objects.equals(getAccountNumber(), account.getAccountNumber()) && Objects.equals(getAccountHolderName(), account.getAccountHolderName()) && Objects.equals(getAccCreateDate(), account.getAccCreateDate()) && getCurrency() == account.getCurrency() && Objects.equals(getBalanceAmount(), account.getBalanceAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountNumber(), getAccountHolderName(), getAccCreateDate(), getCurrency(), getBalanceAmount());
    }
}
