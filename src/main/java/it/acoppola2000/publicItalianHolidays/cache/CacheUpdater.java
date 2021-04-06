package it.acoppola2000.publicItalianHolidays.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLSocketFactory;
import java.time.LocalDate;
import java.util.List;

@EnableScheduling
@Component
class CacheUpdater {

    @Value("${clientkeystore.path}")
    String clientKeyStorePath;

    @Value("${clientkeystore.pass}")
    String clientKeyStorePass;

    @Value("${truststore.path}")
    String trustStorePath;

    @Value("${truststore.pass}")
    String trustStoreStorePass;

    @Autowired
    private HolidaysCache cache;

    private SSLSocketFactory customSSLSocketFactory;

    private ObjectMapper objectMapper;

    @PostConstruct
    void init() {
        //TODO replace System.out.println with log
        if (clientKeyStorePath.equals("${clientkeystore.path}") ||
                clientKeyStorePass.equals("${clientkeystore.pass}") ||
                trustStorePath.equals("${truststore.path}") ||
                trustStoreStorePass.equals("${truststore.pass}")) {
            throw new RuntimeException("application.properties not loaded, please check your CATALINA_OPTS, it should exists and reference application.properties filepath");
        }

        System.out.println("CacheUpdater init: begin");
        System.out.println("CacheUpdater clientKeyStorePath: " + clientKeyStorePath);
        System.out.println("CacheUpdater trustStorePath: " + trustStorePath);

        objectMapper = new ObjectMapper();
        customSSLSocketFactory = CustomSSLFactoryBuilder.buildSSLSocketFactory(clientKeyStorePath, clientKeyStorePass,
                                                                               trustStorePath, trustStoreStorePass);
        this.periodicUpdate();
        System.out.println("CacheUpdater init: end");
    }

    @Scheduled(fixedDelay = 300000)  //every five minutes, can be made configurable
    void periodicUpdate() {
        try {
            String url = "https://date.nager.at/api/v2/publicholidays/" + LocalDate.now().getYear() + "/IT";
            CustomHttpClient customHttpClient = new CustomHttpClient(url,customSSLSocketFactory);
            String responseFromServer = customHttpClient.getPublicItalianHolidaysResponse();
            List<CachedHoliday> list = null;
            //PublicHoliday[] asArray = objectMapper.readValue(response.toString(), PublicHoliday[].class);
            try {
                list = objectMapper.readValue(responseFromServer,
                                              new TypeReference<List<CachedHoliday>>() {});
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (list != null) {
                cache.update(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}