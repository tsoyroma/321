package ru.croc.sbrf.common.flow;

public class ExceptionProcessor {
	/**
	 * Обрезает треугольные скобки из текста исключения
	 * @param excText	- текст исключения
	 * @return String
	 */
	public static String getExcText(String excText) {
		if (excText.startsWith("<")) {
			excText = excText.substring(1);
		}
		if (excText.endsWith(">")) {
			excText = excText.substring(0, excText.length() - 1);
		}
		return excText;
	}
}
