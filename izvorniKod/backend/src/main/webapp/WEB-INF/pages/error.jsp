<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<!DOCTYPE html>
<html>
   <head>
      <title>Error</title>
   </head>
   <body>
   	 <p> Whoops! That is an error! It happens to the best of us 😓 </p> <br>
     <p> <% out.print(request.getAttribute("error")); %> </p> <br>
     <a href="index.html">Back to home page</a>
   </body>
</html>