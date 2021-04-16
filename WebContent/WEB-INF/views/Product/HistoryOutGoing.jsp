<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Quản Lý Kho Hàng</title>
<link href="assetss/css/styles.css" rel="stylesheet" />
<link
	href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" crossorigin="anonymous" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"
	crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
	<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
	<a class="navbar-brand" href="InComing/Index.htm">Quản Lý Kho Hàng</a>
	<button class="btn btn-link btn-sm order-1 order-lg-0"
		id="sidebarToggle" href="#">
		<i class="fas fa-bars"></i>
	</button>
	<!-- Navbar Search-->
	<form
		class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
	</form>
	<!-- Navbar-->
	<ul class="navbar-nav ml-auto ml-md-0">
		<li style="color: white">Username</li>
		<li class="nav-item dropdown"><a class="nav-link dropdown-toggle"
			id="userDropdown" href="#" role="button" data-toggle="dropdown"
			aria-haspopup="true" aria-expanded="false"><i
				class="fas fa-user fa-fw"></i></a>
			<div class="dropdown-menu dropdown-menu-right"
				aria-labelledby="userDropdown">
				<a class="dropdown-item" href="#">Settings</a> <a
					class="dropdown-item" href="#">Activity Log</a>

				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="account/login.htm">Logout</a>
			</div></li>
	</ul>
	</nav>
	<div id="layoutSidenav">
		<div id="layoutSidenav_nav">
			<nav class="sb-sidenav accordion sb-sidenav-dark"
				id="sidenavAccordion">

			<div class="sb-sidenav-menu">
				<div class="nav">
					<a class="nav-link" href="InComing/Index.htm">
						<div class="sb-nav-link-icon">
							<img src="assetss/assets/img/image.png" height="19" width="19">
						</div>Quản Lý Nhập Kho
					</a> <a class="nav-link" href="OutGoing/Index.htm">
						<div class="sb-nav-link-icon">
							<i class="fas fa-truck" style="color: white"></i>
						</div>Quản Lý Xuất Kho
					</a> </a> <a class="nav-link" href="Supplier/Index.htm">
						<div class="sb-nav-link-icon">
							<i class="fas fa-users" style="color: white"></i>
						</div>Quản Lý Nhà Cung Cấp
					</a> </a> <a class="nav-link" href="Customer/Index.htm">
						<div class="sb-nav-link-icon">
							<i class="fas fa-user-tie" style="color: white"></i>
						</div>Quản Lý Khách Hàng
					</a> </a> </a> <a class="nav-link" href="Product/Index.htm">
						<div class="sb-nav-link-icon">
							<i class="fab fa-product-hunt" style="color: white"></i>
						</div>Quản Lý Sản Phẩm
					</a>
				</div>
			</div>
		</div>
		<div id="layoutSidenav_content">
			<div class="card mb-4">
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-table mr-1"></i>
						<h3>Lịch Sử Xuất Kho</h3>	
						<a href="Product/InComing/${id}.htm"> Quay Lại</a>					
					</div>
					<div class="card-body">
						<div class="table-responsive">
							<table class="table table-bordered" id="dataTable" width="100%"
								cellspacing="0">
								<thead>
									<tr>
										<th>Loại</th>
										<th>Tên Sản Phẩm</th>
										<th>Ngày Xuất</th>
										<th>Số Lượng</th>
										<th>Giá Xuất</th>
										<th>Nhà Cung Cấp</th>
										<th>Trạng Thái</th>
										<th>Khách hàng</th>
										<th>Địa chỉ</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${Outgoing}" var="o">
										<tr>
											<td>${o.incoming.product.category.name}</td>
											<td>${o.incoming.product.name}</td>
											<td>${o.date}</td>
											<td>${o.amount}</td>
											<td>$ <fmt:formatNumber value="${o.total}" /> (+<fmt:formatNumber
													value="${o.freightCost}" /> -<fmt:formatNumber
													value="${o.discount}" />%)
											</td>
											<td>${o.incoming.product.supplier.name}</td>
											<td>${o.status}</td>
											<td>${o.customer.name}</td>
											<td>${o.customer.address}</td>
										</tr>										
									</c:forEach>									
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="assetss/js/scripts.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
		crossorigin="anonymous"></script>
	<script src="assetss/assets/demo/chart-area-demo.js"></script>
	<script src="assetss/assets/demo/chart-bar-demo.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"
		crossorigin="anonymous"></script>
	<script src="assetss/assets/demo/datatables-demo.js"></script>
</body>
</html>