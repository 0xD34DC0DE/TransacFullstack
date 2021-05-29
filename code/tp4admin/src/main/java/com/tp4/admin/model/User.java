package com.tp4.admin.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@MappedSuperclass
@DynamicUpdate
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    //@Column(unique = true, updatable = true)
    private String login;

    @NotNull
    private String password;

    public User(@NotNull String login, @NotNull String password) {
        this.login = login;
        this.password = password;
    }
}
