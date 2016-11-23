
package kz.gcvp.billing.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deductionShortResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deductionShortResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.billing.gcvp.kz/}deductionResponse">
 *       &lt;sequence>
 *         &lt;element name="averageAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="deductions" type="{http://ws.billing.gcvp.kz/}deduction" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deductionShortResponse", propOrder = {
    "averageAmount",
    "deductions"
})
public class DeductionShortResponse
    extends DeductionResponse
{

    protected Double averageAmount;
    @XmlElement(nillable = true)
    protected List<Deduction> deductions;

    /**
     * Gets the value of the averageAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAverageAmount() {
        return averageAmount;
    }

    /**
     * Sets the value of the averageAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAverageAmount(Double value) {
        this.averageAmount = value;
    }

    /**
     * Gets the value of the deductions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deductions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeductions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Deduction }
     * 
     * 
     */
    public List<Deduction> getDeductions() {
        if (deductions == null) {
            deductions = new ArrayList<Deduction>();
        }
        return this.deductions;
    }

}
