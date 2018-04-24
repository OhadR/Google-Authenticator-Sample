<html>
<head>
	<title>Account Created Successfully</title>
</head>

<body>
	<h3>Account Created Successfully</h3>
	<div style="margin-top: 25px; position: relative; font: 15px">
		for user <span style="font-weight: bold"><%=request.getParameter("email")%></span><br>
	</div>

	<div id="qr">
		<p> Scan this Barcode using Google Authenticator app on
			your phone to use it later in login     
		</p>
		<img src=<%=request.getParameter("qr")%> />
	</div>
	<a href="/login" class="btn btn-primary">Go to login page</a>

</body>
</html>