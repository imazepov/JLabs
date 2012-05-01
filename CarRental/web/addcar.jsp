<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="data" class="carrental.model.Car" scope="request"/>
<jsp:setProperty name="data" property="*"/>
<jsp:forward page="AddCar"/>