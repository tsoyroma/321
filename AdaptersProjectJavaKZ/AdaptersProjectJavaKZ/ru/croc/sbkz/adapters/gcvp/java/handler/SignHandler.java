package ru.croc.sbkz.adapters.gcvp.java.handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.transform.TransformerException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import kz.iola.jce.provider.IolaProvider;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SignHandler implements SOAPHandler<SOAPMessageContext> {

	private String keyDir = null;

	public SignHandler() {
	}

	public SignHandler(String keyDir) {
		this.keyDir=keyDir;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		boolean out = (Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (out) {
			try {
				SOAPEnvelope env = context.getMessage().getSOAPPart().getEnvelope();

				String id = UUID.randomUUID().toString();

				context.getMessage().writeTo(new FileOutputStream("out_before.xml"));

				sign(env,id);

			} catch (SOAPException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMLSecurityException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}

		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	private void sign(SOAPEnvelope env, String id) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, XMLSecurityException, ParserConfigurationException, JAXBException, TransformerException, SOAPException {
		KeyStore ks = KeyStore.getInstance("PKCS12", IolaProvider.PROVIDER_NAME);
		ks.load(new FileInputStream(keyDir+"/GOSTKZ.p12"),"123456".toCharArray());
		String alias = ks.aliases().nextElement();
		PrivateKey key = (PrivateKey) ks.getKey(alias, "123456".toCharArray());

		X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);

		String signMethod = "http://www.w3.org/2001/04/xmldsig-more#gost34310-gost34311";
		String digestMethod = "http://www.w3.org/2001/04/xmldsig-more#gost34311";

		SOAPBody body = env.getBody();
		body.addAttribute(new QName("Id"), id);

		SOAPHeader header = env.getHeader();
		if (header == null) {
			header = env.addHeader();
		}

		Document doc = env.getOwnerDocument();

		XMLSignature signature = new XMLSignature(doc, "", signMethod);
		signature.setId("bank");
		Transforms transforms = new Transforms(doc);
		transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);
		transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
		signature.addDocument("#"+id,transforms,digestMethod);
		signature.addKeyInfo(certificate);

		NodeList childs = header.getChildNodes();
		if (childs.getLength()>0) {
			Node node = header.getChildNodes().item(0);
			header.insertBefore(signature.getElement(), node);
		} else {
			header.appendChild(signature.getElement());
		}

		signature.sign(key);
	}

}
