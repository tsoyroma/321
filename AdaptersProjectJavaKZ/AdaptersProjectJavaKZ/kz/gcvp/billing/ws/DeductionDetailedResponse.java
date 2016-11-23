
package kz.gcvp.billing.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deductionDetailedResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deductionDetailedResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.billing.gcvp.kz/}deductionResponse">
 *       &lt;sequence>
 *         &lt;element name="deductions" type="{http://ws.billing.gcvp.kz/}deductionDetailed" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deductionDetailedResponse", propOrder = {
    "deductions"
})
public class DeductionDetailedResponse
    extends DeductionResponse
{

    @XmlElement(nillable = true)
    protected List<DeductionDetailed> deductions;

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
     * {@link DeductionDetailed }
     * 
     * 
     */
    public List<DeductionDetailed> getDeductions() {
        if (deductions == null) {
            deductions = new ArrayList<DeductionDetailed>();
        }
        return this.deductions;
    }

}
