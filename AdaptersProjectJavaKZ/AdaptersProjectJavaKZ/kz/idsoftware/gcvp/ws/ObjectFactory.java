
package kz.idsoftware.gcvp.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the kz.idsoftware.gcvp.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Response_QNAME = new QName("http://ws.gcvp.idsoftware.kz/", "response");
    private final static QName _FindInfoResponse_QNAME = new QName("http://ws.gcvp.idsoftware.kz/", "findInfoResponse");
    private final static QName _FindInfo_QNAME = new QName("http://ws.gcvp.idsoftware.kz/", "findInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: kz.idsoftware.gcvp.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindInfoResponse }
     * 
     */
    public FindInfoResponse createFindInfoResponse() {
        return new FindInfoResponse();
    }

    /**
     * Create an instance of {@link PaymentPlaces }
     * 
     */
    public PaymentPlaces createPaymentPlaces() {
        return new PaymentPlaces();
    }

    /**
     * Create an instance of {@link Request }
     * 
     */
    public Request createRequest() {
        return new Request();
    }

    /**
     * Create an instance of {@link Organization }
     * 
     */
    public Organization createOrganization() {
        return new Organization();
    }

    /**
     * Create an instance of {@link FindInfo }
     * 
     */
    public FindInfo createFindInfo() {
        return new FindInfo();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gcvp.idsoftware.kz/", name = "response")
    public JAXBElement<Response> createResponse(Response value) {
        return new JAXBElement<Response>(_Response_QNAME, Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gcvp.idsoftware.kz/", name = "findInfoResponse")
    public JAXBElement<FindInfoResponse> createFindInfoResponse(FindInfoResponse value) {
        return new JAXBElement<FindInfoResponse>(_FindInfoResponse_QNAME, FindInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gcvp.idsoftware.kz/", name = "findInfo")
    public JAXBElement<FindInfo> createFindInfo(FindInfo value) {
        return new JAXBElement<FindInfo>(_FindInfo_QNAME, FindInfo.class, null, value);
    }

}
