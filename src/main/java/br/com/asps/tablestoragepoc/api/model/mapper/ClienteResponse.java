package br.com.asps.tablestoragepoc.api.model.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponse {
    private String nome;
    private String cpf;
    private String partitionKey;
    private String rowKey;
}
