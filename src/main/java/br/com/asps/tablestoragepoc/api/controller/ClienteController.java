package br.com.asps.tablestoragepoc.api.controller;

import br.com.asps.tablestoragepoc.api.model.ClienteRequest;
import br.com.asps.tablestoragepoc.api.model.mapper.ClienteMapper;
import br.com.asps.tablestoragepoc.api.model.mapper.ClienteResponse;
import br.com.asps.tablestoragepoc.domain.service.ClienteService;
import com.azure.core.annotation.QueryParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse criar(@RequestBody ClienteRequest request){
        final var cliente = clienteService.cadastrar(ClienteMapper.INSTANCE.toCliente(request));

        return ClienteMapper.INSTANCE.toClienteResponse(cliente);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponse> findAll(){
        return clienteService.findAll()
                .stream()
                .map(cliente -> ClienteMapper.INSTANCE.toClienteResponse(cliente))
                .collect(Collectors.toList());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@QueryParam("partitionKey") String partitionKey, @QueryParam("rowKey") String rowKey){
        clienteService.excluir(partitionKey, rowKey);
    }
}