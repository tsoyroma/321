
package kz.gcvp.billing.ws;

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
 *     &lt;enumeration value="MISSING_INPUT_PARAMETERS"/>
 *     &lt;enumeration value="DUPLICATE_REQUEST_NUMBER"/>
 *     &lt;enumeration value="IIN_NOT_FOUND"/>
 *     &lt;enumeration value="FIO_BD_DOCUMENT_NOT_CORRECT"/>
 *     &lt;enumeration value="ERROR1"/>
 *     &lt;enumeration value="ERROR2"/>
 *     &lt;enumeration value="SIGNATURE_NOT_VALID"/>
 *     &lt;enumeration value="ACCESS_DENIED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "responseCode")
@XmlEnum
public enum ResponseCode {

    FOUND("FOUND"),
    MISSING_INPUT_PARAMETERS("MISSING_INPUT_PARAMETERS"),
    DUPLICATE_REQUEST_NUMBER("DUPLICATE_REQUEST_NUMBER"),
    IIN_NOT_FOUND("IIN_NOT_FOUND"),
    FIO_BD_DOCUMENT_NOT_CORRECT("FIO_BD_DOCUMENT_NOT_CORRECT"),
    @XmlEnumValue("ERROR1")
    ERROR_1("ERROR1"),
    @XmlEnumValue("ERROR2")
    ERROR_2("ERROR2"),
    SIGNATURE_NOT_VALID("SIGNATURE_NOT_VALID"),
    ACCESS_DENIED("ACCESS_DENIED");
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
