<%@ page language="java" import="com.pmarlen.web.common.view.messages.Messages" %>

<jsp:useBean id="versionInfo" class="com.pmarlen.web.common.view.messages.VersionInfo"  scope="session"/>

<%
    Messages messages = new Messages("com.pmarlen.web.common.view.properties.UIMessages");
    String acceptLanguage = request.getHeader("Accept-Language");
    System.err.println("Header[Accept-Language]" + acceptLanguage);
%>
<html>
    <head>
        <title> <%=messages.getHTMLString("MAIN_SYSTEM_ACCESS")%> </title>

        <link   href="<%= request.getContextPath()%>/css/login.css"          rel="stylesheet"     type="text/css" />
        <style type="text/css">
            body{
                background-image : url(<%= request.getContextPath()%>/images/bg_1.jpg);
                background-repeat: no-repeat;
                background-position:right top;
                background-color:#ffffff;
            }
            .rich-panel-header{
                background-image: url('<%= request.getContextPath()%>/images/title.PNG');
            }	
        </style>

        <link 	href="<%= request.getContextPath()%>/images/Logo_icono_2.png" 	 rel="shortcut icon"  type="image/x-icon"/>
        <link	href="<%= request.getContextPath()%>/images/Logo_icono_2.png"   rel="icon"           type="image/x-icon"/>

        <script language="javascript" type="text/javascript">
            function init(){
                timedMsg();
                document.forms[0].j_username.focus();
            }
            function timedMsg() {

                var t = setTimeout("gotToLogout();",<%=((request.getSession().getMaxInactiveInterval() - 3) * 1000)%>);
            }
            function gotToLogout() {
                window.location = "<%=request.getContextPath()%>/pages/logout.jsf";
            }

        </script>
    </head>

    <body onload="init();">	

        <table cellpadding="0" cellspacing="0" border="0" width="500" align="center">
            <tr>
                <td align="center"  valign="middle">
                    <img src="<%= request.getContextPath()%>/images/GPO_ITES_Logo_Original_1.png"/>
                </td>				
            </tr>
        </table>

        <br/>

        <form action="j_security_check" method="post">
            <table width="400" align="center" align="center">
                <tr>
                    <td>
                        <div class="rich-panel " id="j_id353">
                            <div class="rich-panel-header " id="j_id353_header">
                                <%=messages.getHTMLString("MAIN_SYSTEM_ACCESS")%>
                            </div>
                            <div class="rich-panel-body " id="j_id353_body">								
                                <table>
                                    <tr>
                                        <td width="2%" rowspan="9" align="left" valign="top">
                                            <img src="<%= request.getContextPath()%>/images/key.png" />
                                        </td>
                                    </tr>
                                    <tr><td colspan="2">&nbsp;</td></tr>
                                    <tr><td colspan="2">&nbsp;</td></tr>
                                    <tr>
                                        <td width="10%" align="right">
                                            <span style="font-size : 10px;"><%=messages.getHTMLString("LOGIN_USER")%> :</span>
                                        </td>
                                        <td width="10%" align="left">
                                            <input type="text" name="j_username" value="" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">
                                            <span style="font-size : 10px;"><%=messages.getHTMLString("LOGIN_PASSWORD")%> :</span>
                                        </td>
                                        <td align="left">
                                            <input type="password" name="j_password" value="" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td colspan="2">
                                            <%
                                                if (request.getParameter("error") != null) {
                                            %>									
                                            <div style="color: red;vertical-align: middle;">
                                                <br/>
                                                <img src="<%= request.getContextPath()%>/images/error16x16.gif" />&nbsp; 
                                                <span style="font-size : 10px;"><%=messages.getHTMLString("LOGIN_ERROR_AUTHENTICATE")%></span>
                                                <br/>
                                                <br/>
                                            </div>
                                            <%
                                            } else {
                                            %>
                                            <br/>
                                            <br/>
                                            <%    }
                                            %>									
                                        </td>
                                    </tr>												

                                    <tr>
                                        <td colspan="2" width="100%" align="center">										
                                            <input type="submit" value="<%=messages.getHTMLString("COMMON_OK")%>"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>								
                    </td>
                </tr>
            </table>
        </form>

        <table cellpadding="0" cellspacing="0" border="0" align="center">
            <tr>
                <td align="center" height="20">
                    <a href="http://google.com" style="color:#000; display:block" class="footer-text">[<%=messages.getHTMLString("ENTERPRISE_INFO")%>]</a>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <div style="color:#A0A0A0; display:block" class="footer-text">
                        <a href="http://xpressosystems.com">XPRESSO SYSTEMS</a>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <div style="color:#A0A0A0; display:block" class="footer-text">
                        Version: <jsp:getProperty name="versionInfo" property="version" /> | Last build  : <jsp:getProperty name="versionInfo" property="maven_build_timestamp" />						
                    </div>
                </td>
            </tr>
        </table>

    </body>	
</html>
