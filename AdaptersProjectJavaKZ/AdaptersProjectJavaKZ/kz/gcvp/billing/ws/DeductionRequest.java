
package kz.gcvp.billing.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deductionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deductionRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.billing.gcvp.kz/}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="requestType" type="{http://ws.billing.gcvp.kz/}deductionRequestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deductionRequest", propOrder = {
    "requestType"
})
public class DeductionRequest
    extends BaseRequest
{

    protected DeductionRequestType requestType;

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link DeductionRequestType }
     *     
     */
    public DeductionRequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeductionRequestType }
     *     
     */
    public void setRequestType(DeductionRequestType value) {
        this.requestType = value;
    }

}
