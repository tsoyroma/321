
package kz.gcvp.billing.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deductionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deductionResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.billing.gcvp.kz/}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://ws.billing.gcvp.kz/}deductionRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deductionResponse", propOrder = {
    "request"
})
@XmlSeeAlso({
    DeductionDetailedResponse.class,
    DeductionShortResponse.class
})
public class DeductionResponse
    extends BaseResponse
{

    protected DeductionRequest request;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link DeductionRequest }
     *     
     */
    public DeductionRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeductionRequest }
     *     
     */
    public void setRequest(DeductionRequest value) {
        this.request = value;
    }

}
