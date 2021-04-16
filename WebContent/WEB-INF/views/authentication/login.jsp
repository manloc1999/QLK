<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Login</title>
<link href="assetss/css/styles.css" rel="stylesheet" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"
	crossorigin="anonymous"></script>
</head>
<body class="bg-primary">
	<div id="layoutAuthentication">
		<div id="layoutAuthentication_content">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-lg-5">
						<div class="card shadow-lg border-0 rounded-lg mt-5">
							<div class="card-header">
								<h3 class="text-center font-weight-light my-4">Login</h3>
							</div>
							<div class="card-body">
								<form:form action="account/login.htm" modelAttribute="admin"
									method="post">
									<div class="form-group">
										<label class="small mb-1">UserName</label>
										<form:input class="form-control" path="username" type="text"
											name="username" placeholder="Username" />
										<form:errors class="redtext" path="username" />
									</div>
									<div class="form-group">
										<label class="small mb-1">PassWord</label>
										<form:input class="form-control" path="password"
											type="password" name="password" placeholder="Password" />
										<form:errors class="redtext" path="password" />
									</div>
									
									<div class="form-group mt-4 mb-0">
										<button class="btn btn-primary mb-3 btn-block">Login</button>
									</div>
									<br>
									
									<c:if test="${error != null}">
									<div class="alert alert-danger">${error}</div>
									</c:if>
									
									<div class="form-row">
										<a href="account/forgotpassword.htm">Forgor Password</a>
									</div>
								</form:form>
							</div>
							<div class="card-footer text-center">
								<div class="small">
									<a href="account/register.htm">New Acount</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</main>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="assetss/js/scripts.js"></script>
</body>
</html>