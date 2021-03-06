
package kz.idsoftware.gcvp.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "BillingService", targetNamespace = "http://ws.gcvp.idsoftware.kz/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BillingService {


    /**
     * 
     * @param arg0
     * @return
     *     returns kz.idsoftware.gcvp.ws.Response
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findInfo", targetNamespace = "http://ws.gcvp.idsoftware.kz/", className = "kz.idsoftware.gcvp.ws.FindInfo")
    @ResponseWrapper(localName = "findInfoResponse", targetNamespace = "http://ws.gcvp.idsoftware.kz/", className = "kz.idsoftware.gcvp.ws.FindInfoResponse")
    public Response findInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        Request arg0);

}
