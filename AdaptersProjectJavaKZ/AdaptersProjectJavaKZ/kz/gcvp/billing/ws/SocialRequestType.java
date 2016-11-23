
package kz.gcvp.billing.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for socialRequestType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="socialRequestType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SOCIAL6"/>
 *     &lt;enumeration value="SOCIAL12"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "socialRequestType")
@XmlEnum
public enum SocialRequestType {

    @XmlEnumValue("SOCIAL6")
    SOCIAL_6("SOCIAL6"),
    @XmlEnumValue("SOCIAL12")
    SOCIAL_12("SOCIAL12");
    private final String value;

    SocialRequestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SocialRequestType fromValue(String v) {
        for (SocialRequestType c: SocialRequestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
