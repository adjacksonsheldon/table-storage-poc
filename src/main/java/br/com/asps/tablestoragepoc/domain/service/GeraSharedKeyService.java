package br.com.asps.tablestoragepoc.domain.service;

import br.com.asps.tablestoragepoc.core.config.AppConfig;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class GeraSharedKeyService {

    private final AppConfig appConfig;

    public GeraSharedKeyService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String gerar(String dataHora) {
        String accountAndTable = "/" + appConfig.getStorageAccountName() + "/" + appConfig.getTableStorageName();

        String stringToSign = String.join("\n"
                , dataHora
                , accountAndTable);
        String base64Signature = computeHmac256(appConfig.getStorageAccountKey(), stringToSign);

        return "SharedKeyLite " + appConfig.getStorageAccountName() + ":" + base64Signature;
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
}
