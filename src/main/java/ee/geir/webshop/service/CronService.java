package ee.geir.webshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CronService {

    // Et schedule töötaks, peab üle terve rakenduse olema @EnableScheduling annotatsioon (panime WebshopApplication faili)

    // * --> sekundid (0-59)
    // * * --> minutid (0-59)
    // * * * --> tunnid (0-23)
    // * * * * -> p2eva nr (1-31)
    // * * * * * --> kuu (1-12 or JAN-DEC)
    // * * * * * * --> day of the week (0-7, kus nii 0 kui 7 on pühapäev, või MON-SUN)

    //  nt (cron = "0 * 23 31 12 1-5")
    @Scheduled(cron = "* * * * * *")
    public void runEverySecond() {
//        System.out.println("Every second run");
    }

    @Scheduled(cron = "0 0 17 * * 5L")
    public void runAtLastFridayOfTheMonth() {
//        System.out.println("Every second run");
    }

    @Scheduled(cron = "0 0 17 LW * *")
    public void runAtLastWorkdayOfTheMonth() {
//        System.out.println("Every second run");
    }


    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${cron.5min.urls:}")
    private List<String> urls;

    @Scheduled(cron = "0 */5 * * * *")
    public void callUrlEveryFiveMinutes() {
        if (urls == null || urls.isEmpty()) {
            System.out.println("Cron job skipped: no URLs configured");
            return;
        }

        for (String url : urls) {
            try {
                restTemplate.getForObject(url, String.class);
                System.out.println(LocalDateTime.now() + " - Called " + url + " -> OK");
            } catch (Exception e) {
                System.err.println(LocalDateTime.now() + " - Failed to call " + url + ": " + e.getMessage());
            }
        }
    }


}
