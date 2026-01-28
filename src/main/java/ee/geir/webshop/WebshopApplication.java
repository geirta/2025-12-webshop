package ee.geir.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class WebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}

    // 1. 02.12 - Spring algus: andmebasaide yhendamine, controller, entity, repository
    // 2. 04.12 - korralikud veateated, tabelite omavaheline yhendamine, @Service, validations
    // 3. 09.12 - rendipood, DTO
    // 4. 11.12 - 13.00 unit testid, integration tests
    // 5. 16.12 - 13.00 front-end, backendi päring
    // 6.N 18.12 - 13.00 front-end, react-router-dom, i18n, localStorage, .env
    // 7.E 22.12 - 9.00 context. array localStoragesse
    // 8.N 29.12 - 13.00 redux, URLi muutuja
    // 9.E 05.01 - 13.00 Pagination, @Autowired. custom hook. tellimus backendi
    //10.N 08.01 - 13.00 RestTemplate - teistelt rakendustelt andmeid (pakiautomaatid), Makse.
    //11.E 12.01 - 13.00 auth -> paneme API otspunktid kinni - auth tunnusega sisse
    //12.N 15.01 - 13.00 auth -> JWT token, MINU tellimused, profiil
    //13.E 19.01 - 13.00 auth -> front-endis kinni (ei saa teatud URL-dele ja nuppe näha),  rollid
    //14.N 22.01 - 13.00 rollid, auth parandused, aegumine
    //15.E 26.01 - 13.00 CRON, cache (Google Guava, Redis), pw crypting, emaili saamine
    //16.E 02.02 - 13.00 docker, serverisse ülespanek
	// render.com või AWS või Oracle trial
    //17.E 09.02 - 13.00 WebSocket
    //18.K 18.02 - 13.00-14.30  lõpuprojekti päev

}
