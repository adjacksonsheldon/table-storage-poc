package br.com.asps.tablestoragepoc.domain.model;

import java.util.List;

public class TableStorageResponse {
    private List<Cliente> value;

    public TableStorageResponse() {
    }

    public List<Cliente> getValue() {
        return value;
    }

    public void setValue(List<Cliente> cliente) {
        this.value = cliente;
    }
}