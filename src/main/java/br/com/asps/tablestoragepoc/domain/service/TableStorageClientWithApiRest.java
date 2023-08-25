package br.com.asps.tablestoragepoc.domain.service;

import br.com.asps.tablestoragepoc.core.config.AppConfig;
import br.com.asps.tablestoragepoc.domain.model.Cliente;
import br.com.asps.tablestoragepoc.domain.model.TableStorageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class TableStorageClientWithApiRest {
    public static final String VERSION_HEADER = "2020-04-08";
    public static final String APPLICATION_JSON_ODATA_MINIMAL_META_DATA = "application/json;odata=minimalmetadata";
    private final AppConfig appConfig;

    public TableStorageClientWithApiRest(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public void excluir(String dateHeader, String partitionKey, String rowKey, String authorizationHeader) {

        String baseUrlFormat = "%s/%s(PartitionKey='%s',RowKey='%s')";

        var url = String.format(baseUrlFormat, appConfig.getTableStorageUrl(), appConfig.getTableStorageName(), partitionKey, rowKey);

        WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .defaultHeader("date", dateHeader)
                .defaultHeader(HttpHeaders.IF_MATCH, "*")
                .defaultHeader("x-ms-version", "2020-12-06")
                .defaultHeader("DataServiceVersion", "3.0").build()
                .method(HttpMethod.DELETE)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        System.out.println("Chamada REST DELETE concluída com sucesso.");
    }

    public List<Cliente> consultar(String dateHeader, String authorizationHeader) {
        String baseUrlFormat = "%s/%s";

        var url = String.format(baseUrlFormat, appConfig.getTableStorageUrl(), appConfig.getTableStorageName());

        return WebClient.builder()
                .baseUrl(url)
                .build()
                .get()
                .header("Authorization", authorizationHeader)
                .header("x-ms-date", dateHeader)
                .header("x-ms-version", VERSION_HEADER)
                .header("Accept", APPLICATION_JSON_ODATA_MINIMAL_META_DATA)
                .retrieve()
                .bodyToMono(TableStorageResponse.class).block()
                .getValue();
    }

    public Cliente salvar(String dataHora, String sharedKey, Cliente cliente) {
        String baseUrlFormat = "%s/%s";

        var url = String.format(baseUrlFormat, appConfig.getTableStorageUrl(), appConfig.getTableStorageName());

        return WebClient.builder()
                .baseUrl(url)
                .build()
                .post()
                .header("Authorization", sharedKey)
                .header("x-ms-date", dataHora)
                .header("x-ms-version", VERSION_HEADER)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE + ";odata=minimalmetadata")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(cliente))
                .retrieve()
                .bodyToMono(Cliente.class)
                .block();
    }
}