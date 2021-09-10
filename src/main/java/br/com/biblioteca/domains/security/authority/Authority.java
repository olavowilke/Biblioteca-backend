package br.com.biblioteca.domains.security.authority;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tb_authority", schema = "bibliotecaapi")
public class Authority {

    @Id
    private UUID id;
    private String code;
    private String description;

    public Authority() {
        this.id = UUID.randomUUID();
    }

}
