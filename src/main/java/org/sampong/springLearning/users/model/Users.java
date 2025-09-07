package org.sampong.springLearning.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sampong.springLearning.share.base.BaseEntity;
import org.sampong.springLearning.share.enumerate.RoleStatus;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;
    @Column(name = "username")
    private String username = null;
    private String password = null;
    private String email = null;
    private String phone = null;
    private Boolean enabled = true;
    private Boolean accountNonExpired = true;
    private Boolean credentialsNonExpired = true;
    private Boolean accountNonLocked = true;
    private List<RoleStatus>  roleStatus = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "user")
    private UserDetail userDetail = null;
}
