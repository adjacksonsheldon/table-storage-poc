package br.com.asps.tablestoragepoc.domain.service;

import br.com.asps.tablestoragepoc.core.config.AppConfig;
import br.com.asps.tablestoragepoc.domain.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final GeraSharedKeyService geraSharedKeyService;
    private final TableStorageClientWithApiRest tableStorageClientWithApiRest;
    private final AppConfig appConfig;
    private final ExcluirClienteService excluirClienteService;
    public List<Cliente> findAll(){
        final var dataHora = this.getDataHora();
        final var sharekey = geraSharedKeyService.gerar(dataHora);

        return tableStorageClientWithApiRest.consultar(dataHora, sharekey);
    }

    public Cliente cadastrar(Cliente cliente){
        final var dataHora = this.getDataHora();
        final var sharedKey = geraSharedKeyService.gerar(dataHora);

        cliente.setPartitionKey(Cliente.class.getSimpleName());
        cliente.setRowKey(UUID.randomUUID().toString());

        return tableStorageClientWithApiRest.salvar(dataHora, sharedKey, cliente);
    }

    public void excluir(String partitionKey, String rowKey){
        excluirClienteService.excluir(partitionKey, rowKey);
    }


    private String getDataHora() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH));

        return formattedDateTime;
    }
}
