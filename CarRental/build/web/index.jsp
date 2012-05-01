<%-- 
    Document   : index
    Created on : 30 квіт 2012, 22:18:47
    Author     : ivan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cars</title>
        
        <style>
            td, th
            {
                text-align: center;
                padding: 5px;
            }
        </style>        
    </head>
    <body>
        <h2>Rentals</h2>
                
        <jsp:useBean id="rentalmodel" class="carrental.model.CarRentalModel">
            <table>
                <tr>
                    <th>Customer</th>
                    <th>Car</th>
                    <th>Start date</th>
                    <th>Days</th>
                </tr>
                <c:forEach var="rental" items="${rentalmodel.rentals}">
                    <tr>
                        <td>${rental.customer.firstName}&nbsp;${rental.customer.lastName}</td>
                        <td>${rental.car.make}&nbsp;${rental.car.model}</td>                        
                        <td>${rental.formattedStartDate}</td>
                        <td>${rental.days}</td>
                        <td><a href="RemoveRental?id=${rental.id}">Remove</a></td>
                    </tr>
                </c:forEach>
            </table>              
        </jsp:useBean>
        
        <h3>Rent a car</h3>
        
        <form action="AddRental" method="post">
            <table>
            <tr>
            <td><label for="customer">Customer:</label></td>
            <td>
                <select name="customer" style="width: 100px">
                    <jsp:useBean id="customerSelectModel" class="carrental.model.CustomerModel">
                        <c:forEach var="customer" items="${customerSelectModel.customers}">
                            <option value="${customer.id}">${customer.firstName}&nbsp;${customer.lastName}</option>
                        </c:forEach>
                    </jsp:useBean>
                </select>                
            </td>
            </tr>
            
            <tr>
            <td><label for="car">Car:</label></td>
            <td>
                <select name="car" style="width: 100px">
                    <jsp:useBean id="carSelectModel" class="carrental.model.CarModel">
                        <c:forEach var="car" items="${carSelectModel.cars}">
                            <option value="${car.id}">${car.make}&nbsp;${car.model}</option>
                        </c:forEach>
                    </jsp:useBean>
                </select>                
            </td>
            </tr>
            
            <tr>
            <td><label for="days">Days:</label></td>
            <td><input type="text" name="days" style="width: 100px"/></td>
            </tr>
            
            <tr>
            <td colspan="2"><input type="submit" value="Rent"/></td>
            </tr>
            </table>
        </form>
        
        <h2>Our cars</h2>
        <jsp:useBean id="carmodel" class="carrental.model.CarModel">
            <table>
                <tr>
                    <th>Model</th>
                    <th>Year</th>
                    <th>Engine</th>
                </tr>
                <c:forEach var="car" items="${carmodel.cars}">
                    <tr>
                        <td>${car.make}&nbsp;${car.model}</td>
                        <td>${car.year}</td>
                        <td>${car.engineVolume}</td>
                        <td><a href="RemoveCar?id=${car.id}">Remove</a></td>
                    </tr>
                </c:forEach>
            </table>              
        </jsp:useBean>
        
        <h3>Add new car</h3>
        
        <form action="addcar.jsp" method="post">
            <table>
            <tr>
            <td><label for="make">Make:</label></td>
            <td><input type="text" name="make"/></td>
            </tr>
            
            <tr>
            <td><label for="model">Model:</label></td>
            <td><input type="text" name="model"/></td>
            </tr>
            
            <tr>
            <td><label for="year">Year:</label></td>           
            <td><input type="text" name="year"/></td>
            </tr>
            
            <tr>
            <td><label for="engineVolume">Engine:</label></td>
            <td><input type="text" name="engineVolume"/></td>
            </tr>
            
            <tr>
            <td colspan="2"><input type="submit" value="Add"/></td>
            </tr>
            </table>
        </form>
        
        <h2>Our customers</h2>
        <jsp:useBean id="customermodel" class="carrental.model.CustomerModel">
            <table>
                <tr>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>ID code</th>
                </tr>
                <c:forEach var="customer" items="${customermodel.customers}">
                    <tr>
                        <td>${customer.firstName}&nbsp;${car.model}</td>
                        <td>${customer.lastName}</td>
                        <td>${customer.idCode}</td>
                        <td>${customer.formattedRegisterDate}</td>
                        <td><a href="RemoveCustomer?id=${customer.id}">Remove</a></td>
                    </tr>
                </c:forEach>
            </table>              
        </jsp:useBean>
        
        <h3>Register new customer</h3>
        
        <form action="addcustomer.jsp" method="post">
            <table>
            <tr>
            <td><label for="make">First name:</label></td>
            <td><input type="text" name="firstName"/></td>
            </tr>
            
            <tr>
            <td><label for="model">Last name:</label></td>
            <td><input type="text" name="lastName"/></td>
            </tr>
            
            <tr>
            <td><label for="year">ID code:</label></td>           
            <td><input type="text" name="idCode"/></td>
            </tr>  
            
            <tr>
            <td colspan="2"><input type="submit" value="Register"/></td>
            </tr>
            </table>
        </form>
    </body>
</html>
