
package kz.idsoftware.gcvp.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responseCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="responseCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FOUND"/>
 *     &lt;enumeration value="IIN_NOT_FOUND"/>
 *     &lt;enumeration value="ERROR1"/>
 *     &lt;enumeration value="ERROR2"/>
 *     &lt;enumeration value="ACCESS_DENIED"/>
 *     &lt;enumeration value="SIGNATURE_NOT_VALID"/>
 *     &lt;enumeration value="MISSING_INPUT_PARAMETERS"/>
 *     &lt;enumeration value="WRONG_PERIOD"/>
 *     &lt;enumeration value="DUPLICATE_REQUEST_NUMBER"/>
 *     &lt;enumeration value="FIO_BD_INCORRECT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "responseCode")
@XmlEnum
public enum ResponseCode {

    FOUND("FOUND"),
    IIN_NOT_FOUND("IIN_NOT_FOUND"),
    @XmlEnumValue("ERROR1")
    ERROR_1("ERROR1"),
    @XmlEnumValue("ERROR2")
    ERROR_2("ERROR2"),
    ACCESS_DENIED("ACCESS_DENIED"),
    SIGNATURE_NOT_VALID("SIGNATURE_NOT_VALID"),
    MISSING_INPUT_PARAMETERS("MISSING_INPUT_PARAMETERS"),
    WRONG_PERIOD("WRONG_PERIOD"),
    DUPLICATE_REQUEST_NUMBER("DUPLICATE_REQUEST_NUMBER"),
    FIO_BD_INCORRECT("FIO_BD_INCORRECT");
    private final String value;

    ResponseCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ResponseCode fromValue(String v) {
        for (ResponseCode c: ResponseCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
