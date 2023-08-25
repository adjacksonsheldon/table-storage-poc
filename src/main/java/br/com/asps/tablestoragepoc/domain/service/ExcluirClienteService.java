package br.com.asps.tablestoragepoc.domain.service;

import br.com.asps.tablestoragepoc.core.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class ExcluirClienteService {
    private final AppConfig appConfig;
    private final TableStorageClientWithApiRest tableStorageClientWithApiRest;
    private static final String AUTHORIZATION_HEADER_FORMAT = "SharedKeyLite %s:%s";
    private static final String CANONICALIZED_RESOURCE_FORMAT = "/%s/%s(PartitionKey='%s',RowKey='%s')";

    public void excluir(String partitionKey, String rowKey){
        final var dataHora = getDataHora();
        final var authorizationHeader = gerarAuthorizationHeader(dataHora, partitionKey, rowKey);

        tableStorageClientWithApiRest.excluir(dataHora, partitionKey, rowKey, authorizationHeader);
    }

    private String gerarAuthorizationHeader(String dateHeader, String partitionKey, String rowKey) {
        String signature = computeHmac256(appConfig.getStorageAccountKey(),
                buildStringToSign(dateHeader, partitionKey, rowKey));

        return String.format(AUTHORIZATION_HEADER_FORMAT, appConfig.getStorageAccountName(), signature);
    }

    private String buildStringToSign(String dateHeader, String partitionKey, String rowKey) {
        return String.join("\n", dateHeader, getCanonicalizedResource(partitionKey, rowKey));
    }

    private String getCanonicalizedResource(String partitionKey, String rowKey) {
        return String.format(CANONICALIZED_RESOURCE_FORMAT, appConfig.getStorageAccountName(), appConfig.getTableStorageName(), partitionKey, rowKey);
    }

    private String computeHmac256(final String base64Key, final String stringToSign) {
        try {
            byte[] key = Base64.getDecoder().decode(base64Key);
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            hmacSHA256.init(new SecretKeySpec(key, "HmacSHA256"));
            byte[] utf8Bytes = stringToSign.getBytes(StandardCharsets.UTF_8);

            return Base64.getEncoder().encodeToString(hmacSHA256.doFinal(utf8Bytes));
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getDataHora() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH));

        return formattedDateTime;
    }
}
