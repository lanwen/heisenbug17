package ru.lanwen.heisenbug;

import com.github.tomakehurst.wiremock.WireMockServer;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.heisenbug.WiremockAddressResolver.Uri;
import ru.lanwen.heisenbug.WiremockResolver.Server;
import ru.lanwen.heisenbug.app.TicketEndpoint;
import ru.lanwen.heisenbug.beans.Airline;
import ru.lanwen.heisenbug.beans.Arrival;
import ru.lanwen.heisenbug.beans.City;
import ru.lanwen.heisenbug.beans.City_;
import ru.lanwen.heisenbug.beans.Country;
import ru.lanwen.heisenbug.beans.Country_;
import ru.lanwen.heisenbug.beans.Departure;
import ru.lanwen.heisenbug.beans.Eticket;
import ru.lanwen.heisenbug.beans.Flight;
import ru.lanwen.heisenbug.beans.Meta;

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
        Eticket original = new Eticket()
                .withMeta(new Meta().withSchemaVersion("V1"))
                .withFlights(Collections.singletonList(
                        new Flight()
                                .withNumber("S7 232")
                                .withAirline(
                                        new Airline()
                                                .withIata("S7")
                                                .withName("S7 Airlines")
                                )
                                .withDeparture(
                                        new Departure()
                                                .withName("Чертовицкое")
                                                .withIata("VOZ")
                                                .withScheduled("2017-04-29T10:25:00+03:00")
                                                .withCity(
                                                        new City()
                                                                .withName("Воронеж")
                                                                .withLatitude(51.661535)
                                                                .withLongitude(39.200287)
                                                )
                                                .withCountry(
                                                        new Country()
                                                                .withName("Россия")
                                                                .withLatitude(61.698653)
                                                                .withLongitude(99.505405)
                                                )
                                                .withTz("Europe/Moscow")
                                )
                                .withArrival(
                                        new Arrival()
                                                .withName("Домодедово")
                                                .withIata("DME")
                                                .withScheduled("2017-04-29T11:40:00+03:00")
                                                .withCity(
                                                        new City_()
                                                                .withName("Москва")
                                                                .withLatitude(55.75396)
                                                                .withLongitude(37.620393)
                                                )
                                                .withCountry(new Country_()
                                                        .withName("Россия")
                                                        .withLatitude(61.698653)
                                                        .withLongitude(99.505405))
                                )
                ));

        TicketApi api = TicketApi.connect(uri);

        String id = api.create(original).headers().get(X_TICKET_ID_HEADER).iterator().next();
        Eticket ticket = api.get(id);

        assertThat(ticket, samePropertyValuesAs(original));
    }
}
