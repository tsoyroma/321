
package kz.gcvp.billing.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for socialResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="socialResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.billing.gcvp.kz/}baseResponse">
 *       &lt;sequence>
 *         &lt;element name="assignmentDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="paymentAverage" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="paymentCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="request" type="{http://ws.billing.gcvp.kz/}socialRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "socialResponse", propOrder = {
    "assignmentDate",
    "paymentAverage",
    "paymentCount",
    "request"
})
public class SocialResponse
    extends BaseResponse
{

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar assignmentDate;
    protected Double paymentAverage;
    protected Integer paymentCount;
    protected SocialRequest request;

    /**
     * Gets the value of the assignmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssignmentDate() {
        return assignmentDate;
    }

    /**
     * Sets the value of the assignmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssignmentDate(XMLGregorianCalendar value) {
        this.assignmentDate = value;
    }

    /**
     * Gets the value of the paymentAverage property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPaymentAverage() {
        return paymentAverage;
    }

    /**
     * Sets the value of the paymentAverage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPaymentAverage(Double value) {
        this.paymentAverage = value;
    }

    /**
     * Gets the value of the paymentCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPaymentCount() {
        return paymentCount;
    }

    /**
     * Sets the value of the paymentCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPaymentCount(Integer value) {
        this.paymentCount = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link SocialRequest }
     *     
     */
    public SocialRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link SocialRequest }
     *     
     */
    public void setRequest(SocialRequest value) {
        this.request = value;
    }

}
