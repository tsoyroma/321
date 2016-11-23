<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet  [
  <!ENTITY nbsp   "&#160;">
  <!ENTITY copy   "&#169;">
  <!ENTITY reg    "&#174;">
  <!ENTITY trade  "&#8482;">
  <!ENTITY mdash  "&#8212;">
  <!ENTITY ldquo  "&#8220;">
  <!ENTITY rdquo  "&#8221;">
  <!ENTITY pound  "&#163;">
  <!ENTITY yen    "&#165;">
  <!ENTITY euro   "&#8364;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:banks="http://sberbank.ru/dem/organizations/banks/15" xmlns:ifxca="http://sberbank.ru/dem/commonAggregates/15" xmlns:inds="http://sberbank.ru/dem/individual/15">
  <xsl:decimal-format name="mf" decimal-separator="." grouping-separator="&nbsp;" NaN="0.00"/>
  <xsl:output method="html" encoding="UTF-8"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>Loan amortization schedule</title>
				<style type="text/css">
					<xsl:comment>
.style1 {font-family: "Times New Roman", Times, serif}
.style3 {font-family: "Times New Roman", Times, serif; font-weight: bold; }
.style5 {font-family: "Times New Roman", Times, serif; font-size: 11px; }
.style4 {color: #006633}
.style6 {font-family: "Times New Roman", Times, serif; font-size: 16px; }
#Layer1 {
	position:absolute;
	width:289px;
	height:43px;
	z-index:1;
	left: 962px;
	top: 1px;
}
#Layer2 {
	position:absolute;
	width:373px;
	height:52px;
	z-index:2;
	left: 499px;
	top: 96px;
}
#Layer3 {
	position:absolute;
	width:1310px;
	height:49px;
	z-index:3;
	left: 3px;
	top: 223px;

}
#Layer4 {
	position:absolute;
	width:419px;
	height:96px;
	z-index:4;
	left: 0px;
	top: 1px;
}
#Layer5 {
	position:absolute;
	width:313px;
	height:61px;
	z-index:5;
	left: 102px;
	top: 42px;
}
</xsl:comment>
				</style>
			</head>
			<body>
				<xsl:if test="KZGetPrivateClientPayScheduleRs/Lang='ru'">
					<div id="Layer5">
						<br/>
						<p>
							<span class="style4">
								<xsl:value-of select="KZGetPrivateClientPayScheduleRs/AcctInfo/BankInfo/banks:BranchName"/>
								<br/>
        БИК SABRKZKA  ОКПО 28104567  <br/>БИН  <xsl:value-of select="KZGetPrivateClientPayScheduleRs/AcctInfo/BankInfo/banks:BankId"/>
							</span>
						</p>
					</div>
					<div id="Layer4">
						<img src="https://online-test.sberbank.kz/SberLogo.png" />
					</div>
					<div align="Left" class="style6" id="Layer2">
						<strong>График платежей</strong>
						<br/>
						<strong>ФИО: <xsl:value-of select="KZGetPrivateClientPayScheduleRs/AcctInfo/ClientInfo/PersonInfo/inds:FullName"/>
						</strong>
						<br/>
						<strong>Сумма займа: </strong>
						<xsl:value-of select="format-number(KZGetPrivateClientPayScheduleRs/AcctInfo/AcctExtAttr/LoanAttr/LoanAmount,'###&nbsp;###&nbsp;##0.00','mf')"/>
						<br/>
						<strong>Ставка: </strong>
						<xsl:value-of select="KZGetPrivateClientPayScheduleRs/AcctInfo/AcctExtAttr/LoanAttr/LoanRate"/>
						<strong>% годовых</strong>
						<br/>
						<strong>Валюта займа: </strong>
						<xsl:value-of select="KZGetPrivateClientPayScheduleRs/AcctInfo/AcctExtAttr/LoanAttr/LoanCur"/>
					</div>
					<div align="right" class="style5" id="Layer1">Приложение №1 <br/>
  к Договору банковского займа № <xsl:value-of select="KZGetPrivateClientPayScheduleRs/ScheduleInfo/AgrNum"/>
						<br/>
  от <xsl:value-of select="KZGetPrivateClientPayScheduleRs/ScheduleInfo/AgrDate"/>
					</div>
					<div id="Layer3">
						<table border="1" width="96%">
							<tr bgcolor="#33FF99">
								<td align="center" bgcolor="#CCCCCC">
									<strong>№ платежа</strong>
								</td>
								<td align="center" bgcolor="#CCCCCC">
									<strong>Дата погашения</strong>
								</td>
								<td align="center" bgcolor="#CCCCCC">
									<strong>Количество дней пользования кредитом</strong>
								</td>
								<td align="center" bgcolor="#CCCCCC">
									<strong>Сумма кредита на начало периода</strong>
								</td>
								<td align="center" bgcolor="#CCCCCC">
									<strong>Основной долг</strong>
								</td>
								<td align="center" bgcolor="#CCCCCC">
									<strong>Проценты</strong>
								</td>
								<td align="center" bgcolor="#CCCCCC">
									<strong>Платеж за период</strong>
								</td>
								<td align="center" bgcolor="#CCCCCC">
									<strong>Сумма кредита на конец периода</strong>
								</td>
							</tr>
							<xsl:for-each select="KZGetPrivateClientPayScheduleRs/ScheduleInfo/ScheduleRec">
								<tr bgcolor="#99FFFF">
									<td bgcolor="#FFFFFF">
										<xsl:value-of select="position()"/>
									</td>
									<td align="right" bgcolor="#FFFFFF">
										<xsl:value-of select="Date"/>
									</td>
									<td align="right" bgcolor="#FFFFFF">
										<xsl:value-of select="LoanUseDay"/>
									</td>
									<td align="right" bgcolor="#FFFFFF">
										<xsl:for-each select="PayInfo/PayRec">
											<xsl:if test="PayType='CBB'">
												<xsl:value-of select="format-number(Amount,'###&nbsp;###&nbsp;##0.00','mf')"/>
											</xsl:if>
										</xsl:for-each>
									</td>
									<td align="right" bgcolor="#FFFFFF">
										<xsl:for-each select="PayInfo/PayRec">
											<xsl:if test="PayType='CV'">
												<xsl:value-of select="format-number(Amount,'###&nbsp;###&nbsp;##0.00','mf')"/>
											</xsl:if>
										</xsl:for-each>
									</td>
									<td align="right" bgcolor="#FFFFFF">
										<xsl:for-each select="PayInfo/PayRec">
											<xsl:if test="PayType='CVP'">
												<xsl:value-of select="format-number(Amount,'###&nbsp;###&nbsp;##0.00','mf')"/>
											</xsl:if>
										</xsl:for-each>
									</td>
									<td align="right" bgcolor="#FFFFFF">
										<xsl:value-of select="format-number(PayInfo/PayRec[PayType='CV']/Amount+PayInfo/PayRec[PayType='CVP']/Amount,'###&nbsp;###&nbsp;##0.00','mf')"/>
									</td>
									<td align="right" bgcolor="#FFFFFF">
										<xsl:for-each select="PayInfo/PayRec">
											<xsl:if test="PayType='CBA'">
												<xsl:value-of select="format-number(Amount,'###&nbsp;###&nbsp;##0.00','mf')"/>
											</xsl:if>
										</xsl:for-each>
									</td>
								</tr>
							</xsl:for-each>
							<TR bgcolor="#33FF99" VALIGN="TOP">
								<TD COLSPAN="4" ALIGN="LEFT" bgcolor="#FFFFFF">
									<strong>Итого</strong>
								</TD>
								<TD ALIGN="RIGHT" bgcolor="#FFFFFF">
									<B>
										<xsl:value-of select="format-number(sum(KZGetPrivateClientPayScheduleRs/ScheduleInfo/ScheduleRec/PayInfo/PayRec[PayType='CV']/Amount),'###&nbsp;###&nbsp;##0.00','mf')"/>
									</B>
								</TD>
								<TD ALIGN="RIGHT" bgcolor="#FFFFFF">
									<B>
										<xsl:value-of select="format-number(sum(KZGetPrivateClientPayScheduleRs/ScheduleInfo/ScheduleRec/PayInfo/PayRec[PayType='CVP']/Amount),'###&nbsp;###&nbsp;##0.00','mf')"/>
									</B>
								</TD>
								<TD ALIGN="RIGHT" bgcolor="#FFFFFF">
									<B>
										<xsl:value-of select="format-number(sum(KZGetPrivateClientPayScheduleRs/ScheduleInfo/ScheduleRec/PayInfo/PayRec[PayType='CV']/Amount)+sum(KZGetPrivateClientPayScheduleRs/ScheduleInfo/ScheduleRec/PayInfo/PayRec[PayType='CVP']/Amount),'###&nbsp;###&nbsp;##0.00','mf')"/>
									</B>
								</TD>
								<TD ALIGN="RIGHT" bgcolor="#FFFFFF">
									<B>
									</B>
								</TD>
							</TR>
						</table>
						<br/>
						<table border="1" width="68%">
							<tr bgcolor="#33FF99">
								<td align="center" bgcolor="#CCCCCC" class="style1">
									<strong>Методы погашения Займа, предложенные Банком</strong>
								</td>
								<td align="center" bgcolor="#CCCCCC" class="style1">
									<strong>Выбор Заемщика</strong>
								</td>
							</tr>
							<tr bgcolor="#99FFFF">
								<td bgcolor="#FFFFFF" class="style1">1. аннуитетными платежами</td>
								<td align="center" bgcolor="#FFFFFF" class="style3">
									<xsl:if test="KZGetPrivateClientPayScheduleRs/AcctInfo/AcctExtAttr/LoanAttr/RepaymentMethod='Аннуитетная'">V</xsl:if>
								</td>
							</tr>
							<tr bgcolor="#99FFFF">
								<td bgcolor="#FFFFFF" class="style1">2. c погашением основного долга равными долями</td>
								<td align="center" bgcolor="#FFFFFF" class="style3">
									<xsl:if test="KZGetPrivateClientPayScheduleRs/AcctInfo/AcctExtAttr/LoanAttr/RepaymentMethod='Дифференцированная'">V</xsl:if>
								</td>
							</tr>
							<tr bgcolor="#99FFFF">
								<td bgcolor="#FFFFFF" class="style1">3. c погашением основного долга в конце срока</td>
								<td align="center" bgcolor="#FFFFFF" class="style3">
									<xsl:if test="KZGetPrivateClientPayScheduleRs/AcctInfo/AcctExtAttr/LoanAttr/RepaymentMethod='В конце срока'">V</xsl:if>
								</td>
							</tr>
						</table>
						<br/>
						<span class="style5">
							<strong>Примечание:</strong> Данный график является расчетным на момент подписания. Сумма вознаграждения начисляется на фактический остаток суммы займа, за фактическое время пользования им, при этом количество дней в году принимается 360 дней, в месяце – 30 дней.В случае если дата погашения суммы, причитающейся по Договору, выпадает на выходной, либо праздничный день, оплата указанной суммы платежа производится в следующий за ним рабочий день без уплаты неустойки и иных видов штрафных санкций. При наступлении вышеуказанных обстоятельств, сумма следующего платежа может быть скорректирована с учетом фактического времени пользования займом.</span>
					</div>
				</xsl:if>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
