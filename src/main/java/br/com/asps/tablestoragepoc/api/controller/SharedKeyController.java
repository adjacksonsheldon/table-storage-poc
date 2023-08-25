package br.com.asps.tablestoragepoc.api.controller;

import br.com.asps.tablestoragepoc.api.model.ClienteRequest;
import br.com.asps.tablestoragepoc.api.model.mapper.ClienteMapper;
import br.com.asps.tablestoragepoc.api.model.mapper.ClienteResponse;
import br.com.asps.tablestoragepoc.domain.service.GeraSharedKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/shared-key")
@RequiredArgsConstructor
public class SharedKeyController {
    private final GeraSharedKeyService geraSharedKeyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map criar(){
        String dataHora = getDataHora();
        String sharedKey = geraSharedKeyService.gerar(dataHora);

        return Map.of("sharedKey", sharedKey, "dataHora", dataHora);
    }

    private String getDataHora() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH));
        return formattedDateTime;
    }
}
