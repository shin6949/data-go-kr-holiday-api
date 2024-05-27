package me.cocoblue.datagokrholidayapi.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.cocoblue.datagokrholidayapi.vo.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnniversaryDateService {

    private WebClient webClient;

    @Value("${service.key}")
    private String serviceKey;

    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.builder()
                .baseUrl("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public ResponseEntity<ApiResponse> getAnniversaryDate(Integer year, Integer month) {
        if (year == null) {
            year = LocalDateTime.now().getYear();
        }

        if (month == null) {
            month = LocalDateTime.now().getMonthValue();
        }

        final String yearString = String.format("%04d", year);
        final String monthString = String.format("%02d", month);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getAnniversaryInfo")
                        .queryParam("_type", "json")
                        .queryParam("solYear", yearString)
                        .queryParam("solMonth", monthString)
                        .queryParam("ServiceKey", serviceKey)
                        .build())
                .retrieve()
                .toEntity(ApiResponse.class)
                .block();
    }
}
