<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">
</head>

<body>
	<div class="container">
		<div class="starter-template" style="margin:1em">
			<form method="POST" action="" enctype="multipart/form-data" id="form">
				<div class="form-group">
					<label id="label" for="file">Upload file</label>
					<input type="file" name="file" class="form-control" id="file" required> 
					<input type="hidden" name="name" value="" id="name"> 
					<input type="hidden" name="due_date_string" value="" id="due_date_string">
					<input type="hidden" name="author_zid" value="" id="author_zid">
					<input type="hidden" name="type" value="" id="type">
				</div>
				<div class="form-group">
					<input type="submit" class="btn btn-primary" id="button">
				</div>
			</form>
		</div>
	</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
		integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
		integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
		crossorigin="anonymous"></script>
	<script>
		$(function() {
			var getUrlParameter = function getUrlParameter(sParam) {
				var sPageURL = decodeURIComponent(window.location.search
						.substring(1)), sURLVariables = sPageURL.split('&'), sParameterName, i;

				for (i = 0; i < sURLVariables.length; i++) {
					sParameterName = sURLVariables[i].split('=');

					if (sParameterName[0] === sParam) {
						return sParameterName[1] === undefined ? true
								: sParameterName[1];
					}
				}
			};
			var author_zid = getUrlParameter("author_zid");
			var due_date_string = getUrlParameter("due_date_string");
			var name = getUrlParameter("assignment_title");
			var type = getUrlParameter("type")
			$("#author_zid").val(author_zid);
			$("#name").val(name);
			$("#due_date_string").val(due_date_string);
			$("#type").val(type);
			if (type === "add") {
				$("#label").append(" for adding assignment material of "+ name);			
				$("#form").attr("action", "/resource/assignment/add");
			}else if(type === "update") {
				console.log("update");
				$("#label").append(" for updating assignment material of "+ name);
				$("#form").attr("action", "/resource/assignment/update");
			}
		})
	</script>
</body>

</html>