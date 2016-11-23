
package kz.gcvp.billing.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deductionRequestType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="deductionRequestType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DEDUCTION3"/>
 *     &lt;enumeration value="DEDUCTION6"/>
 *     &lt;enumeration value="DEDUCTION12"/>
 *     &lt;enumeration value="DEDUCTION36"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "deductionRequestType")
@XmlEnum
public enum DeductionRequestType {

    @XmlEnumValue("DEDUCTION3")
    DEDUCTION_3("DEDUCTION3"),
    @XmlEnumValue("DEDUCTION6")
    DEDUCTION_6("DEDUCTION6"),
    @XmlEnumValue("DEDUCTION12")
    DEDUCTION_12("DEDUCTION12"),
    @XmlEnumValue("DEDUCTION36")
    DEDUCTION_36("DEDUCTION36");
    private final String value;

    DeductionRequestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DeductionRequestType fromValue(String v) {
        for (DeductionRequestType c: DeductionRequestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
