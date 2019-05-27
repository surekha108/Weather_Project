<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
      <h2>Student Information</h2>
      <form:form method = "POST" action = "/weatherReport/current" modelAttribute="city">
         <table>
            <tr>
            	        <td><form:label path="name">Name</form:label></td>
                        <td><form:input path="name" /></td>
            </tr>
            <tr>
               <td colspan = "2">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>
      </form:form>

</body>
</html>
