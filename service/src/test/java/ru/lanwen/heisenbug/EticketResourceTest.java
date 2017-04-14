package ru.lanwen.heisenbug;

import com.github.tomakehurst.wiremock.WireMockServer;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.heisenbug.WiremockAddressResolver.Uri;
import ru.lanwen.heisenbug.WiremockResolver.Server;
import ru.lanwen.heisenbug.app.TicketEndpoint;
import ru.lanwen.heisenbug.beans.Airline;
import ru.lanwen.heisenbug.beans.Airport;
import ru.lanwen.heisenbug.beans.Eticket;
import ru.lanwen.heisenbug.beans.EticketMeta;
import ru.lanwen.heisenbug.beans.Flight;
import ru.lanwen.heisenbug.beans.Region;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.lanwen.heisenbug.app.TicketEndpoint.X_TICKET_ID_HEADER;

/**
 * @author lanwen (Merkushev Kirill)
 */
@ExtendWith({
        WiremockResolver.class,
        WiremockAddressResolver.class
})
public class EticketResourceTest {

    @Test
    void shouldCreateTicket(@Server(customizer = TicketEndpoint.class) WireMockServer server, @Uri String uri) {
        TicketApi api = TicketApi.connect(uri);

        Response response = api.create(new Eticket());
        Collection<String> ids = response.headers().get(X_TICKET_ID_HEADER);

        assertThat("ids", ids, hasSize(1));

        Eticket ticket = api.get(ids.iterator().next());

        assertThat(ticket, is(notNullValue()));
    }

    /**
     * Что бывает при использовании не тестовых клиентов
     */
    @Test
    void shouldPassTicketAssertion(@Server(customizer = TicketEndpoint.class) WireMockServer server, @Uri String uri) {
        Eticket ticket = TicketApi.connect(uri).get("unknown");

        // Ticket должен быть null!
        assertThrows(
                AssertionError.class,
                () -> assertThat(ticket, is(notNullValue()))
        );
    }

    @Test
    void shouldSaveTicketProps(@Server(customizer = TicketEndpoint.class) WireMockServer server, @Uri String uri) {
        Eticket original = new Eticket();
        original.setMeta(new EticketMeta());
        Flight flight = new Flight();
        Airline airline = new Airline();
        airline.setIata("S7");
        airline.setName("S7 Airlines");
        flight.setAirline(airline);
        Airport dep = new Airport();
        dep.setScheduled("2017-04-29T10:25:00+03:00");
        dep.setIata("VOZ");
        dep.setName("Чертовицкое");
        Region depCity = new Region();
        depCity.setName("Воронеж");
        depCity.setLatitude(51.661535);
        depCity.setLongitude(39.200287);
        dep.setCity(depCity);
        Region countryDep = new Region();
        countryDep.setName("Россия");
        countryDep.setLongitude(99.505405);
        countryDep.setLatitude(61.698653);
        dep.setCountry(countryDep);
        dep.setTz("Europe/Moscow");
        flight.setDeparture(dep);
        Airport arr = new Airport();
        arr.setName("Домодедово");
        arr.setIata("DME");
        arr.setScheduled("2017-04-29T11:40:00+03:00");
        Region cityArr = new Region();
        cityArr.setName("Москва");
        cityArr.setLatitude(55.75396);
        cityArr.setLongitude(37.620393);
        arr.setCity(cityArr);
        Region countryArr = new Region();
        countryArr.setName("Россия");
        countryArr.setLatitude(61.698653);
        countryArr.setLongitude(99.505405);
        arr.setCountry(countryArr);
        flight.setArrival(arr);
        flight.setNumber("S7 232");
        original.setFlights(Collections.singletonList(flight));

        TicketApi api = TicketApi.connect(uri);

        String id = api.create(original).headers().get(X_TICKET_ID_HEADER).iterator().next();
        Eticket ticket = api.get(id);

        assertThat(ticket, samePropertyValuesAs(original));
    }
}
