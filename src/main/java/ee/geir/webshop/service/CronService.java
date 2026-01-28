package ee.geir.webshop.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
//    @Scheduled(cron = "* * * * * *")
//    public void runEverySecond() {
//        System.out.println("Every second run");
//    }
//
//    @Scheduled(cron = "0 0 17 * * 5L")
//    public void runAtLastFridayOfTheMonth() {
//        System.out.println("Every second run");
//    }
//
//    @Scheduled(cron = "0 0 17 LW * *")
//    public void runAtLastWorkdayOfTheMonth() {
//        System.out.println("Every second run");
//    }
}
