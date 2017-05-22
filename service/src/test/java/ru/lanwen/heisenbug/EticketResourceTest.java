package ru.lanwen.heisenbug;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.builder.RequestSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.heisenbug.WiremockAddressResolver.Uri;
import ru.lanwen.heisenbug.WiremockResolver.Server;
import ru.lanwen.heisenbug.api.ApiTickets;
import ru.lanwen.heisenbug.app.TicketEndpoint;
import ru.lanwen.heisenbug.beans.Airline;
import ru.lanwen.heisenbug.beans.Airport;
import ru.lanwen.heisenbug.beans.Eticket;
import ru.lanwen.heisenbug.beans.EticketMeta;
import ru.lanwen.heisenbug.beans.Flight;
import ru.lanwen.heisenbug.beans.Region;
import ru.lanwen.heisenbug.beans.SchemaVersion;
import ru.lanwen.heisenbug.consts.JavapoetTestMethodConsts;

import java.time.ZonedDateTime;
import java.util.Collections;

import static java.util.function.Function.identity;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static ru.lanwen.heisenbug.api.ApiTickets.Config.ticketsConfig;
import static ru.lanwen.heisenbug.api.ApiTickets.tickets;
import static ru.lanwen.heisenbug.app.TicketEndpoint.X_TICKET_ID_HEADER;
import static ru.lanwen.heisenbug.beans.AirportMatchers.withIata;
import static ru.lanwen.heisenbug.beans.AirportMatchers.withScheduled;
import static ru.lanwen.heisenbug.beans.FlightMatchers.withArrival;
import static ru.lanwen.heisenbug.beans.FlightMatchers.withDeparture;
import static ru.lanwen.heisenbug.consts.JavapoetTestMethodConsts.TEST_METHOD_shouldCreateTicket;
import static ru.lanwen.heisenbug.consts.JavapoetTestMethodConsts.TEST_METHOD_shouldPassTicketAssertion;
import static ru.lanwen.heisenbug.consts.JavapoetTestMethodConsts.TEST_METHOD_shouldSaveTicketProps;
import static ru.lanwen.heisenbug.json.ZonedDateTimeJaxbAdapter.parseDate;

/**
 * @author lanwen (Merkushev Kirill)
 */
@ExtendWith({
        WiremockResolver.class,
        WiremockAddressResolver.class
})
@Slf4j
class EticketResourceTest {

    @Test
    void shouldCreateTicket(@Server(customizer = TicketEndpoint.class) WireMockServer server, @Uri String uri) {
        log.debug(TEST_METHOD_shouldCreateTicket);

        String id = api(uri).ticket()
                .withReq(spec -> spec.setBody(new Eticket()))
                .upload(identity())
                .then().assertThat().header(X_TICKET_ID_HEADER, not(isEmptyOrNullString()))
                .extract().header(X_TICKET_ID_HEADER);

        Eticket ticket = api(uri).ticket().uuid().withUuid(id).fetch(identity()).as(Eticket.class);
        assertThat(ticket, is(notNullValue()));
    }

    /**
     * Что бывает при использовании не тестовых клиентов
     */
    @Test
    void shouldPassTicketAssertion(@Server(customizer = TicketEndpoint.class) WireMockServer server, @Uri String uri) {
        log.debug(TEST_METHOD_shouldPassTicketAssertion);

        api(uri).ticket().uuid().withUuid("unknown")
                .fetch(identity())
                .then().assertThat().statusCode(is(200))
                .extract().as(Eticket.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldSaveTicketProps(@Server(customizer = TicketEndpoint.class) WireMockServer server, @Uri String uri) {
        log.debug(TEST_METHOD_shouldSaveTicketProps);

        Eticket original = new Eticket()
                .withMeta(new EticketMeta().withSchemaVersion(SchemaVersion.V_1))
                .withFlights(Collections.singletonList(
                        new Flight()
                                .withNumber("S7 232")
                                .withAirline(
                                        new Airline()
                                                .withIata("S7")
                                                .withName("S7 Airlines")
                                )
                                .withDeparture(
                                        new Airport()
                                                .withName("Чертовицкое")
                                                .withIata("VOZ")
                                                .withScheduled(parseDate("2017-04-29T10:25:00+03:00"))
                                                .withCity(
                                                        new Region()
                                                                .withName("Воронеж")
                                                                .withLatitude(51.661535)
                                                                .withLongitude(39.200287)
                                                )
                                                .withCountry(
                                                        new Region()
                                                                .withName("Россия")
                                                                .withLatitude(61.698653)
                                                                .withLongitude(99.505405)
                                                )
                                                .withTz("Europe/Moscow")
                                )
                                .withArrival(
                                        new Airport()
                                                .withName("Домодедово")
                                                .withIata("DME")
                                                .withScheduled(parseDate("2017-04-29T11:40:00+03:00"))
                                                .withCity(
                                                        new Region()
                                                                .withName("Москва")
                                                                .withLatitude(55.75396)
                                                                .withLongitude(37.620393)
                                                )
                                                .withCountry(
                                                        new Region()
                                                                .withName("Россия")
                                                                .withLatitude(61.698653)
                                                                .withLongitude(99.505405)
                                                )
                                )
                ));


        String id = api(uri).ticket()
                .withReq(spec -> spec.setBody(original))
                .upload(identity()).header(X_TICKET_ID_HEADER);
        Eticket ticket = api(uri).ticket().uuid().withUuid(id).fetch(identity()).as(Eticket.class);

        assertThat(ticket.getFlights(), hasItem(
                allOf(
                        withDeparture(withScheduled((Matcher) lessThanOrEqualTo(ZonedDateTime.now()))),
                        withArrival(withIata(containsString("DME")))
                )
        ));
    }

    private static ApiTickets api(@Uri String uri) {
        return tickets(
                ticketsConfig()
                        .withReqSpecSupplier(
                                () -> new RequestSpecBuilder().setBaseUri(uri)
                        )
        );
    }
}
