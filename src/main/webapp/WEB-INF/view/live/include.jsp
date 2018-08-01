<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored ="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="domi" uri="/WEB-INF/tld/domi.tld"%>
<%
	String path = request.getContextPath();
	String titleChar = "&nbsp;&nbsp;\\&nbsp;&nbsp;";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="直播" />

<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>

<script type="text/javascript" src="${ctx}/js/dropload.min.js"></script>

<!-- 自定义弹窗 -->
<script type="text/javascript" src="${ctx}/js/globalTip.js"></script>
<!-- 日历函数 -->
<script type="text/javascript" src="${ctx}/js/DatePicker/WdatePicker.js"></script>

 