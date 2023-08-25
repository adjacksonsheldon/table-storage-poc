package br.com.asps.tablestoragepoc.api.model.mapper;

import br.com.asps.tablestoragepoc.api.model.ClienteRequest;
import br.com.asps.tablestoragepoc.domain.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);


    Cliente toCliente(ClienteRequest clienteRequest);

    ClienteResponse toClienteResponse(Cliente cliente);
}