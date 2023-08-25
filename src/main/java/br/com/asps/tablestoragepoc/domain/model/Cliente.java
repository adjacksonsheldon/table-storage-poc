package br.com.asps.tablestoragepoc.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {
    private String nome;
    private String cpf;
    @JsonProperty("PartitionKey")
    private String partitionKey;
    @JsonProperty("RowKey")
    private String rowKey;
}