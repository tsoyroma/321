package ru.croc.sbrf.common.flow;

import java.util.List;

import ru.croc.sbrf.router.config.util.HashGenerator;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXPath;

public class RouteHashValidator {
	private static final String NS = "http://www.croc.ru/sbrf/router/config/xml/ControlMessage";

	/**
	 * Проверяет корректность полученных данных маршрутизации
	 * @param rootElement	- корневой элемент сообщения
	 * @param error			- буфер для хранения текста ошибки
	 * @return				- Boolean.TRUE, если сообщение успешно прошло проверку
	 * 						  Boolean.FALSE если сообщение не прошло проверку
	 */
	@SuppressWarnings("unchecked")
	public static Boolean isMsgCorrect(MbElement rootElement, String[] error) {
		try {
			MbXPath hashPath = new MbXPath("string(//ControlMessage/Hash)");
			hashPath.setDefaultNamespace(NS);
			String receivedHash = (String)rootElement.evaluateXPath(hashPath);

			MbXPath routesPath = new MbXPath("//ControlMessage/RoutingConfig/Route");
			routesPath.setDefaultNamespace(NS);
			List routeList = (List)rootElement.evaluateXPath(routesPath);
			StringBuilder buff = new StringBuilder();
			for(int routeInd = 0; routeInd < routeList.size(); routeInd++) {
				MbElement route = (MbElement)routeList.get(routeInd);

				MbXPath paramPath = new MbXPath("Param", route);
				paramPath.setDefaultNamespace(NS);
				List paramList = (List)route.evaluateXPath(paramPath);
				for(int paramInd = 0; paramInd < paramList.size(); paramInd++) {
					MbElement param = (MbElement)paramList.get(paramInd);
					buff.append(param.evaluateXPath("string(@name)"));
					buff.append(param.evaluateXPath("string(.)"));
				}
			}

			String calculatedHash = HashGenerator.generateHash(buff.toString());

			return receivedHash != null && receivedHash.equals(calculatedHash);
		} catch (MbException e) {
			error[0] = ExceptionProcessor.getExcText(e.toString());
		} catch (Exception e) {
			error[0] = ExceptionProcessor.getExcText(e.toString());
		} catch (Throwable th) {
			error[0] = ExceptionProcessor.getExcText(th.toString());
		}

		return false;
	}
}
