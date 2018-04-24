<html>
<head>
	<script type="text/javascript" src="https://code.jquery.com/jquery-1.4.2.js"></script>
	<title>Create Account Page</title>
</head>

<body onload='document.f.email.focus();InitCreateAccount();'>
	<h3>Create Account</h3>

	<%   
	    if ( null != request.getParameter("err_msg") ) {
	%>	
	<div style="margin-top:  25px ;position: relative; color: red; font:15px">
		<span style="font-weight:bold"><%= request.getParameter("err_msg") %></span>
	</div>
	<%   } %>
	
	<form name='f' 
		action='../createAccount'
		method='POST'>
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='email' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><input type='password' name='confirm_password' /></td>
			</tr>
			<tr>
				<td>Use Two step verification</td>
				<td><input type="checkbox" name='using2FA' value="true"/></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value='Create Account' /></td>
			</tr>
		</table>
	</form>
</body>
</html>