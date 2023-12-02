package dev.wilian.currencyconverter.infrastructure.service.fiscaldate;

import dev.wilian.currencyconverter.domain.service.FiscalDateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class FiscalDateServiceImpl implements FiscalDateService {
    @Override
    public ResponseApi fetchExchangeRates(String country) {
        RestTemplate restTemplate = new RestTemplate();
        String url = builderUrl(country);
        ResponseEntity<ResponseApi> response = restTemplate.getForEntity(url, ResponseApi.class);
        return response.getBody();
    }

    private static String builderUrl(String countries) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://api.fiscaldata.treasury.gov/services/api/fiscal_service");
        urlBuilder.append("/v1/accounting/od/rates_of_exchange");

        LocalDate currentDate = LocalDate.now();
        LocalDate sixMonthsAgo = currentDate.minusMonths(6);

        String startDate = sixMonthsAgo.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String endDate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        urlBuilder.append("?sort=-effective_date,country");

        urlBuilder.append("&filter=record_date:gte:").append(startDate).append(",record_date:lte:").append(endDate);
        appendCountryFilter(urlBuilder, countries);

        return urlBuilder.toString();
    }

    private static void appendCountryFilter(StringBuilder urlBuilder, String countries) {
        if (countries != null && !countries.isEmpty()) {
            urlBuilder.append(",country_currency_desc:in:(").append(countries).append(")");
        }
    }
}
