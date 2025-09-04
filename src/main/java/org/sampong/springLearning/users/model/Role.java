package org.sampong.springLearning.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sampong.springLearning.share.base.BaseEntity;
import org.sampong.springLearning.share.enumerate.RoleStatus;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;
    private String role = null;
    private String description = null;
    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus = null;
}
