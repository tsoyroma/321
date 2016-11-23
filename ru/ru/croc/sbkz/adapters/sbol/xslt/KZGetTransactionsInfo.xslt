<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE xsl:stylesheet  [
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
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"    
 extension-element-prefixes="ex" xmlns="http://www.w3.org/2000/svg" xmlns:banks="http://sberbank.ru/dem/organizations/banks/15" xmlns:ex="http://exslt.org/dates-and-times" xmlns:ifxca="http://sberbank.ru/dem/commonAggregates/15" xmlns:inds="http://sberbank.ru/dem/individual/15" xmlns:orgs="http://sberbank.ru/dem/organizations/15">
<xsl:output method="html" encoding="UTF-8" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
<xsl:decimal-format name="mf" decimal-separator="." grouping-separator="&nbsp;" NaN="0.00"/>
<xsl:template match="/">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Sberbank Account Statement</title>
<style type="text/css">
<xsl:comment>
.стиль2 {font-family: "Times New Roman", Times, serif}
body,td,th {
	font-family: Times New Roman, Times, serif;
	font-size: 14px;
}
txt1{position:fixed; top:15%; left:10%}
txt2{position:fixed; top:18%; left:10%}
txt3{position:fixed; top:18%; left:19%}
txt4{position:fixed; top:21%; left:10%}
.стиль4 {color: #006633}
#Layer1 {
	position:absolute;
	width:369px;
	height:68px;
	z-index:1;
	left: 0px;
	top: 1px;
}
#Layer3 {
	position:absolute;
	width:333px;
	height:77px;
	z-index:2;
	left: 519px;
	top: 136px;
}
#Layer2 {
	position:absolute;
	width:295px;
	height:79px;
	z-index:3;
	left: 100px;
	top: 49px;
}
#Layer4 {
	position:absolute;
	width:422px;
	height:34px;
	z-index:4;
	left: 5px;
	top: 257px;
}
#Layer5 {
	position:absolute;
	width:1169px;
	height:115px;
	z-index:5;
	left: 4px;
	top: 230px;
}
#Layer6 {
	position:absolute;
	width:169px;
	height:42px;
	z-index:6;
	left: 1066px;
	top: 260px;
}
#Layer7 {
	position:absolute;
	width:117px;
	height:30px;
	z-index:7;
	left: 1076px;
	top: 259px;
}
#Layer8 {
	position:absolute;
	width:200px;
	height:30px;
	z-index:6;
	left: 2px;
	top: 600px;
}
</xsl:comment>
</style>
</head>
<body>
<xsl:if test="KZGetTransactionsInfoRs/TransData/Lang='ru'">
<div id="Layer1">
 <img src="https://online-test.sberbank.kz/SberLogo.png" />
</div>
<div id="Layer2"> 
<br /> 
     <p><span class="стиль4">ДБ АО "СБЕРБАНК" <xsl:value-of select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/BankInfo/banks:BranchName"/><br />
        БИК SABRKZKA  ОКПО 28104567  <br />БИН  <xsl:value-of select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/BankInfo/banks:BankId"/>
   </span>
  </p>
</div>

<div id="Layer3"> <span class="стиль2"><strong><xsl:value-of select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/ClientInfo/PersonInfo/inds:FullName"/></strong><br />
  <strong>Выписка по счету: </strong><xsl:value-of select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/AcctId"/> (<xsl:value-of select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/AcctCur" />) <br />
  <xsl:for-each select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/ClientInfo/PersonInfo/PersonIdExt">
      <xsl:if test="Key='IIN'">
        <strong>ИИН </strong>
        <xsl:value-of select="Value"/> 
        </xsl:if>
      </xsl:for-each>
    <br /> 
    <strong>За период с </strong><xsl:value-of select="KZGetTransactionsInfoRs/TransData/StartDate"/> <strong> по </strong> <xsl:value-of select="KZGetTransactionsInfoRs/TransData/EndDate"/><br /> 
    <xsl:for-each select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/ClientInfo/PersonInfo/PersonIdExt">
      <xsl:if test="Key='MABPK'">
        <strong>Код клиента: </strong>
        <xsl:value-of select="Value"/> 
        </xsl:if>
      </xsl:for-each>
	</span></div>
<div id="Layer5">
  <table border="1" style="border-collapse:collapse; border: 1px solid black;"  width="100%">
  <tr bgcolor="#FFFFFF">
  <td COLSPAN="7" ALIGN="LEFT" bgcolor="#FFFFFF"><strong>Входящий остаток на <xsl:value-of select="KZGetTransactionsInfoRs/TransData/StartDate"/></strong></td>
  <td COLSPAN="2" ALIGN="Right">
  <xsl:for-each select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/AcctBalInfo/BalanceRec">
  <xsl:if test="BalType='STBAL'">
    <strong><xsl:value-of select="format-number(Amount,'###&nbsp;###&nbsp;##0.00','mf')"/></strong>
  </xsl:if>
  </xsl:for-each></td>
  </tr>
<tr bgcolor="#FFFFFF">
<th rowspan ="2" width="10%" align="center"  bgcolor="#B9FFB9"><strong>Дата<br />
  Время</strong></th>
<th rowspan ="2" width="7%" align="center"  bgcolor="#B9FFB9"><strong>№ док.</strong></th>
<th colspan ="2" width="23%" align="center"  bgcolor="#B9FFB9"><strong>Корреспондент</strong></th>
<th width="5%" align="center"  bgcolor="#B9FFB9"><strong>Дебет</strong></th>
<th width="5%" align="center"  bgcolor="#B9FFB9"><strong>Кредит</strong></th>
<th rowspan ="2" width="11%" align="center" bgcolor="#B9FFB9"><strong>Сумма комиссии</strong></th>
<th rowspan ="2" width="13%" align="center" bgcolor="#B9FFB9"><strong>Назначение платежа</strong></th>
<th width="5%" align="center"  bgcolor="#B9FFB9"><strong>Код</strong></th>
</tr>
<tr>
<th bgcolor="#B9FFB9">БИК<br />
  Наименование<br />банка</th>
<th bgcolor="#B9FFB9">Счет<br />ИИН<br />Наименование</th>
<th bgcolor="#B9FFB9">Сумма</th>
<th bgcolor="#B9FFB9">Сумма</th>
<th bgcolor="#B9FFB9">ЕКНП</th>
</tr>
<xsl:for-each select="KZGetTransactionsInfoRs/TransData/AcctTrans/TransactionInfo">
<tr bgcolor="#99FFFF">
<td align="left" bgcolor="#D7F1FF"><xsl:value-of select="substring-before(OperationDate, 'T')"/><br /><xsl:value-of select="OperationTime"/></td>
<td bgcolor="#D7F1FF"><xsl:value-of select="DocNum"/></td>
<td bgcolor="#D7F1FF"><xsl:if test="Type='D'"><xsl:value-of select="PayeeInfo/BankInfo/banks:BankId"/><br /><xsl:value-of select="PayeeInfo/BankInfo/banks:Name"/></xsl:if>
  <xsl:if test="Type='C'"><xsl:value-of select="PayerInfo/BankInfo/banks:BankId"/><br /><xsl:value-of select="PayerInfo/BankInfo/banks:Name"/></xsl:if></td>
<td bgcolor="#D7F1FF"><xsl:if test="Type='D'"><xsl:value-of select="PayeeInfo/AcctInfo/AcctId"/><br />
  <xsl:for-each select="PayeeInfo/OrgInfo/OrgIdExt">
    <xsl:if test="Key='IIN'">
      <xsl:value-of select="Value"/>
    </xsl:if>
  </xsl:for-each><br /><xsl:value-of select="PayeeInfo/OrgInfo/orgs:Name"/></xsl:if>
<xsl:if test="Type='C'">
  <xsl:value-of select="PayerInfo/AcctInfo/AcctId"/>
  <br />
  <xsl:for-each select="PayerInfo/OrgInfo/OrgIdExt">
    <xsl:if test="Key='IIN'">
      <xsl:value-of select="Value"/>
    </xsl:if>
  </xsl:for-each>
  <br />
  <xsl:value-of select="PayerInfo/OrgInfo/orgs:Name"/>
</xsl:if></td>
<td align="right" bgcolor="#D7F1FF"><xsl:if test="Type='D'"><xsl:value-of select="format-number(Amt,'###&nbsp;###&nbsp;##0.00','mf')"/></xsl:if></td>
<td align="right" bgcolor="#D7F1FF"><xsl:if test="Type='C'"><xsl:value-of select="format-number(Amt,'###&nbsp;###&nbsp;##0.00','mf')"/></xsl:if></td>
<td align="right" bgcolor="#D7F1FF"><xsl:value-of select="format-number(CommissionAmt,'###&nbsp;###&nbsp;##0.00','mf')"/></td>
<td bgcolor="#D7F1FF"><xsl:value-of select="Description"/></td>
<td bgcolor="#D7F1FF"><xsl:for-each select="PayerInfo/OrgInfo/OrgIdExt">
  <xsl:if test="Key='CODE'">
    <xsl:value-of select="Value"/>
  </xsl:if>
  </xsl:for-each>&nbsp;<xsl:for-each select="PayeeInfo/OrgInfo/OrgIdExt">
  <xsl:if test="Key='CODE'">
    <xsl:value-of select="Value"/>
  </xsl:if>
  </xsl:for-each><br /><xsl:value-of select="DescCode"/></td>
</tr>
</xsl:for-each>
<TR bgcolor="#33FF99" VALIGN="TOP">
<TD COLSPAN="4" ALIGN="LEFT" bgcolor="#B9FFB9"><strong>Итого обороты с <xsl:value-of select="KZGetTransactionsInfoRs/TransData/StartDate"/></strong> <strong> по </strong> <strong><xsl:value-of select="KZGetTransactionsInfoRs/TransData/EndDate"/></strong><br /></TD>
<TD ALIGN="RIGHT" bgcolor="#B9FFB9"><B><xsl:value-of select="format-number(sum(KZGetTransactionsInfoRs/TransData/AcctTrans/TransactionInfo[Type='D']/Amt),'###&nbsp;###&nbsp;##0.00','mf')"/></B></TD>
<TD ALIGN="RIGHT" bgcolor="#B9FFB9"><B><xsl:value-of select="format-number(sum(KZGetTransactionsInfoRs/TransData/AcctTrans/TransactionInfo[Type='C']/Amt),'###&nbsp;###&nbsp;##0.00','mf')"/></B></TD>
<TD ALIGN="RIGHT" bgcolor="#B9FFB9"><B><xsl:value-of select="format-number(sum(KZGetTransactionsInfoRs/TransData/AcctTrans/TransactionInfo/CommissionAmt),'###&nbsp;###&nbsp;##0.00','mf')"/></B></TD>
<TD colspan="3" bgcolor="#B9FFB9"></TD>
</TR>
<tr bgcolor="#FFFFFF" VALIGN="TOP" ALIGN="LEFT">
<TD COLSPAN="7"  ALIGN="LEFT" bgcolor="#B9FFB9">
   <strong>Исходящий остаток с <xsl:value-of select="KZGetTransactionsInfoRs/TransData/StartDate"/></strong> <strong> по </strong> <strong><xsl:value-of select="KZGetTransactionsInfoRs/TransData/EndDate"/></strong></TD>
  <TD COLSPAN="2" ALIGN="RIGHT" bgcolor="#B9FFB9"><xsl:for-each select="KZGetTransactionsInfoRs/TransData/AcctTrans/AcctInfo/AcctBalInfo/BalanceRec">
  <xsl:if test="BalType='ENDBAL'">
    <strong><xsl:value-of select="format-number(Amount,'###&nbsp;###&nbsp;##0.00','mf')"/></strong>
  </xsl:if>
  </xsl:for-each></TD>
</tr>
</table>
</div>
</xsl:if>
</body>
</html>

</xsl:template>
</xsl:stylesheet>