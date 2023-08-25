package br.com.asps.tablestoragepoc.core.config;

import com.azure.core.credential.AzureNamedKeyCredential;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureConfig {
    @Value("${STORAGE_ACCOUNT_NAME}")
    private String storageAccountName;
    @Value("${STORAGE_ACCOUNT_KEY}")
    private String storageAccountKey;

    @Value("${TABLE_STORAGE_NAME}")
    private String tableStorageName;

    @Value("${TABLE_STORAGE_URL}")
    private String tableStorageUrl;

    @Bean
    public TableServiceClient getTableServiceClient() {
        final var credential = new AzureNamedKeyCredential(storageAccountName, storageAccountKey);
        return new TableServiceClientBuilder()
                .endpoint(tableStorageUrl)
                .credential(credential)
                .buildClient();
    }
}
