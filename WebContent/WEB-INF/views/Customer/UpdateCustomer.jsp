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
<title>Quản lý khách hàng</title>
<link href="assetss/css/styles.css" rel="stylesheet" />
<link
	href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" crossorigin="anonymous" />
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
								<h3 class="text-center font-weight-light my-4">Sửa Khách
									Hàng</h3>
								<div class="card-body">
									<form:form action="Customer/Update/${id}.htm"
										modelAttribute="customer" method="POST">
										<div class="form-group">
											<label class="small mb-1">Tên Khách Hàng</label>
											<form:input class="form-control"
												placeholder="customer.name" path="name" />
											<form:errors class="redtext" path="name" />
										</div>
										<div class="form-group">
											<label class="small mb-1">Email</label>
											<form:input class="form-control" placeholder="customer.email"
												path="email" />
											<form:errors class="redtext" path="email" />
										</div>
										<div class="form-group">
											<label class="small mb-1">Số Điên Thoại</label>
											<form:input class="form-control"
												placeholder="customer.phoneNumber" path="phoneNumber" />
											<form:errors class="redtext" path="phoneNumber" />
										</div>
										<div class="form-group">
											<label class="small mb-1">Địa Chỉ</label>
											<form:input class="form-control" placeholder="customer.address"
												path="address" />
											<form:errors class="redtext" path="address" />
										</div>

										<div class="form-group mt-4 mb-0">
										<button class="btn btn-primary mb-3 btn-block" type="submit">Sửa</button>

											<c:if test="${message != null }">
												<div class="alert alert-light">${message}</div>
											</c:if>
										</div>
										<br>

										<div class="form-row">
											<a href="Customer/Index.htm">Quay Lại</a>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"
	crossorigin="anonymous"></script>
<script src="assetss/js/scripts.js"></script>
</body>
</html>