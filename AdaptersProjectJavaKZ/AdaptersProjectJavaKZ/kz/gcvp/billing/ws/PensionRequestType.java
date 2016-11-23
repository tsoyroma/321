
package kz.gcvp.billing.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pensionRequestType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="pensionRequestType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PENSION6"/>
 *     &lt;enumeration value="PENSION12"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "pensionRequestType")
@XmlEnum
public enum PensionRequestType {

    @XmlEnumValue("PENSION6")
    PENSION_6("PENSION6"),
    @XmlEnumValue("PENSION12")
    PENSION_12("PENSION12");
    private final String value;

    PensionRequestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PensionRequestType fromValue(String v) {
        for (PensionRequestType c: PensionRequestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
