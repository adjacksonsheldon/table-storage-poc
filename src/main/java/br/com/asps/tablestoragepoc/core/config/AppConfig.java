package br.com.asps.tablestoragepoc.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${STORAGE_ACCOUNT_NAME}")
    private String storageAccountName;
    @Value("${STORAGE_ACCOUNT_KEY}")
    private String storageAccountKey;

    @Value("${TABLE_STORAGE_NAME}")
    private String tableStorageName;

    @Value("${TABLE_STORAGE_URL}")
    private String tableStorageUrl;

    public String getStorageAccountName() {
        return storageAccountName;
    }

    public void setStorageAccountName(String storageAccountName) {
        this.storageAccountName = storageAccountName;
    }

    public String getStorageAccountKey() {
        return storageAccountKey;
    }

    public void setStorageAccountKey(String storageAccountKey) {
        this.storageAccountKey = storageAccountKey;
    }

    public String getTableStorageName() {
        return tableStorageName;
    }

    public void setTableStorageName(String tableStorageName) {
        this.tableStorageName = tableStorageName;
    }

    public String getTableStorageUrl() {
        return tableStorageUrl;
    }

    public void setTableStorageUrl(String tableStorageUrl) {
        this.tableStorageUrl = tableStorageUrl;
    }
}
