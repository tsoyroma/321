
package kz.gcvp.billing.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the kz.gcvp.billing.ws package. 
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

    private final static QName _GetDeductionShortInfo_QNAME = new QName("http://ws.billing.gcvp.kz/", "getDeductionShortInfo");
    private final static QName _GetPensionInfo_QNAME = new QName("http://ws.billing.gcvp.kz/", "getPensionInfo");
    private final static QName _GetDeductionDetailedInfoResponse_QNAME = new QName("http://ws.billing.gcvp.kz/", "getDeductionDetailedInfoResponse");
    private final static QName _GetPensionInfoResponse_QNAME = new QName("http://ws.billing.gcvp.kz/", "getPensionInfoResponse");
    private final static QName _GetSocialInfo_QNAME = new QName("http://ws.billing.gcvp.kz/", "getSocialInfo");
    private final static QName _GetDeductionDetailedInfo_QNAME = new QName("http://ws.billing.gcvp.kz/", "getDeductionDetailedInfo");
    private final static QName _GetDeductionShortInfoResponse_QNAME = new QName("http://ws.billing.gcvp.kz/", "getDeductionShortInfoResponse");
    private final static QName _GetSocialInfoResponse_QNAME = new QName("http://ws.billing.gcvp.kz/", "getSocialInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: kz.gcvp.billing.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPensionInfo }
     * 
     */
    public GetPensionInfo createGetPensionInfo() {
        return new GetPensionInfo();
    }

    /**
     * Create an instance of {@link GetDeductionShortInfo }
     * 
     */
    public GetDeductionShortInfo createGetDeductionShortInfo() {
        return new GetDeductionShortInfo();
    }

    /**
     * Create an instance of {@link GetSocialInfo }
     * 
     */
    public GetSocialInfo createGetSocialInfo() {
        return new GetSocialInfo();
    }

    /**
     * Create an instance of {@link GetPensionInfoResponse }
     * 
     */
    public GetPensionInfoResponse createGetPensionInfoResponse() {
        return new GetPensionInfoResponse();
    }

    /**
     * Create an instance of {@link GetDeductionDetailedInfoResponse }
     * 
     */
    public GetDeductionDetailedInfoResponse createGetDeductionDetailedInfoResponse() {
        return new GetDeductionDetailedInfoResponse();
    }

    /**
     * Create an instance of {@link GetSocialInfoResponse }
     * 
     */
    public GetSocialInfoResponse createGetSocialInfoResponse() {
        return new GetSocialInfoResponse();
    }

    /**
     * Create an instance of {@link GetDeductionShortInfoResponse }
     * 
     */
    public GetDeductionShortInfoResponse createGetDeductionShortInfoResponse() {
        return new GetDeductionShortInfoResponse();
    }

    /**
     * Create an instance of {@link GetDeductionDetailedInfo }
     * 
     */
    public GetDeductionDetailedInfo createGetDeductionDetailedInfo() {
        return new GetDeductionDetailedInfo();
    }

    /**
     * Create an instance of {@link DeductionDetailedResponse }
     * 
     */
    public DeductionDetailedResponse createDeductionDetailedResponse() {
        return new DeductionDetailedResponse();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link BaseResponse }
     * 
     */
    public BaseResponse createBaseResponse() {
        return new BaseResponse();
    }

    /**
     * Create an instance of {@link SocialRequest }
     * 
     */
    public SocialRequest createSocialRequest() {
        return new SocialRequest();
    }

    /**
     * Create an instance of {@link BaseRequest }
     * 
     */
    public BaseRequest createBaseRequest() {
        return new BaseRequest();
    }

    /**
     * Create an instance of {@link DeductionShortResponse }
     * 
     */
    public DeductionShortResponse createDeductionShortResponse() {
        return new DeductionShortResponse();
    }

    /**
     * Create an instance of {@link Deduction }
     * 
     */
    public Deduction createDeduction() {
        return new Deduction();
    }

    /**
     * Create an instance of {@link DeductionDetailed }
     * 
     */
    public DeductionDetailed createDeductionDetailed() {
        return new DeductionDetailed();
    }

    /**
     * Create an instance of {@link DeductionResponse }
     * 
     */
    public DeductionResponse createDeductionResponse() {
        return new DeductionResponse();
    }

    /**
     * Create an instance of {@link PensionResponse }
     * 
     */
    public PensionResponse createPensionResponse() {
        return new PensionResponse();
    }

    /**
     * Create an instance of {@link DeductionRequest }
     * 
     */
    public DeductionRequest createDeductionRequest() {
        return new DeductionRequest();
    }

    /**
     * Create an instance of {@link PensionRequest }
     * 
     */
    public PensionRequest createPensionRequest() {
        return new PensionRequest();
    }

    /**
     * Create an instance of {@link SocialResponse }
     * 
     */
    public SocialResponse createSocialResponse() {
        return new SocialResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeductionShortInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.billing.gcvp.kz/", name = "getDeductionShortInfo")
    public JAXBElement<GetDeductionShortInfo> createGetDeductionShortInfo(GetDeductionShortInfo value) {
        return new JAXBElement<GetDeductionShortInfo>(_GetDeductionShortInfo_QNAME, GetDeductionShortInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPensionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.billing.gcvp.kz/", name = "getPensionInfo")
    public JAXBElement<GetPensionInfo> createGetPensionInfo(GetPensionInfo value) {
        return new JAXBElement<GetPensionInfo>(_GetPensionInfo_QNAME, GetPensionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeductionDetailedInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.billing.gcvp.kz/", name = "getDeductionDetailedInfoResponse")
    public JAXBElement<GetDeductionDetailedInfoResponse> createGetDeductionDetailedInfoResponse(GetDeductionDetailedInfoResponse value) {
        return new JAXBElement<GetDeductionDetailedInfoResponse>(_GetDeductionDetailedInfoResponse_QNAME, GetDeductionDetailedInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPensionInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.billing.gcvp.kz/", name = "getPensionInfoResponse")
    public JAXBElement<GetPensionInfoResponse> createGetPensionInfoResponse(GetPensionInfoResponse value) {
        return new JAXBElement<GetPensionInfoResponse>(_GetPensionInfoResponse_QNAME, GetPensionInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSocialInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.billing.gcvp.kz/", name = "getSocialInfo")
    public JAXBElement<GetSocialInfo> createGetSocialInfo(GetSocialInfo value) {
        return new JAXBElement<GetSocialInfo>(_GetSocialInfo_QNAME, GetSocialInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeductionDetailedInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.billing.gcvp.kz/", name = "getDeductionDetailedInfo")
    public JAXBElement<GetDeductionDetailedInfo> createGetDeductionDetailedInfo(GetDeductionDetailedInfo value) {
        return new JAXBElement<GetDeductionDetailedInfo>(_GetDeductionDetailedInfo_QNAME, GetDeductionDetailedInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeductionShortInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.billing.gcvp.kz/", name = "getDeductionShortInfoResponse")
    public JAXBElement<GetDeductionShortInfoResponse> createGetDeductionShortInfoResponse(GetDeductionShortInfoResponse value) {
        return new JAXBElement<GetDeductionShortInfoResponse>(_GetDeductionShortInfoResponse_QNAME, GetDeductionShortInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSocialInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.billing.gcvp.kz/", name = "getSocialInfoResponse")
    public JAXBElement<GetSocialInfoResponse> createGetSocialInfoResponse(GetSocialInfoResponse value) {
        return new JAXBElement<GetSocialInfoResponse>(_GetSocialInfoResponse_QNAME, GetSocialInfoResponse.class, null, value);
    }

}
