
package kz.gcvp.billing.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for socialRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="socialRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.billing.gcvp.kz/}baseRequest">
 *       &lt;sequence>
 *         &lt;element name="requestType" type="{http://ws.billing.gcvp.kz/}socialRequestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "socialRequest", propOrder = {
    "requestType"
})
public class SocialRequest
    extends BaseRequest
{

    protected SocialRequestType requestType;

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link SocialRequestType }
     *     
     */
    public SocialRequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SocialRequestType }
     *     
     */
    public void setRequestType(SocialRequestType value) {
        this.requestType = value;
    }

}
