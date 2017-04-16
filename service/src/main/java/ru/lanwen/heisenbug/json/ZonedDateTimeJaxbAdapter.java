package ru.lanwen.heisenbug.json;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.xml.bind.DatatypeConverter;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

/**
 * Simple adapter to generate beans from xsd with ZonedDateTime instead of default XMLGregorianCalendar...
 *
 * @author lanwen (Merkushev Kirill)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ZonedDateTimeJaxbAdapter {

    public static ZonedDateTime parseDate(String s) {
        return ZonedDateTime.parse(s, ISO_ZONED_DATE_TIME);
    }

    public static String printDate(ZonedDateTime dt) {
        return DatatypeConverter.printDateTime(GregorianCalendar.from(dt));
    }
}
