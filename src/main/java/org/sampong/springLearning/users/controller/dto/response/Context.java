package org.sampong.springLearning.users.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sampong.springLearning.share.enumerate.RoleStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Context {
    private Long id;
    private String username;
    private List<RoleStatus> role;
}
