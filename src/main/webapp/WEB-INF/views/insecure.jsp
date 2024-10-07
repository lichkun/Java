<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body {
        font-family: Arial, sans-serif;
        text-align: center;
    }
    h1 {
        font-size: 48px;
        color: #ff4b4b;
    }
    p {
        font-size: 18px;
        color: #555;
    }
    a {
        color: #007bff;
        text-decoration: none;
        font-size: 16px;
    }
    a:hover {
        text-decoration: underline;
    }
</style>
<h1>Access Denied</h1>
<p>Your access to this resource is restricted due to an invalid signature.</p>
<a href="<%= request.getContextPath() %>/">Go Back to Homepage</a>
