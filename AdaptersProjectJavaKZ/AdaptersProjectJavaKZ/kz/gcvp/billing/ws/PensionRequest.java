
package kz.gcvp.billing.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pensionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pensionRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.billing.gcvp.kz/}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="requestType" type="{http://ws.billing.gcvp.kz/}pensionRequestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pensionRequest", propOrder = {
    "requestType"
})
public class PensionRequest
    extends BaseRequest
{

    protected PensionRequestType requestType;

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link PensionRequestType }
     *     
     */
    public PensionRequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PensionRequestType }
     *     
     */
    public void setRequestType(PensionRequestType value) {
        this.requestType = value;
    }

}
