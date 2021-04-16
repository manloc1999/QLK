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
<title>Quản lý sản phẩm</title>
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
	<a class="navbar-brand" href="InComing/Index.htm">Quản Lý Kho Hàng</a> <!-- Navbar Search-->

	<button class="btn btn-link btn-sm order-1 order-lg-0"
		id="sidebarToggle" href="#">
		<i class="fas fa-bars"></i>

	</button>
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
			<div class="card mb-1">
				<div class="card-header">
					<i class="fas fa-table mr-1"></i>
					<h3>Quản lý sản phẩm</h3>			
										
					<c:if test="${message != null }">
						<div class="alert alert-light">${message}</div>
					</c:if>
					<a class="btn btn-primary mb-3" style="float: right;"
						href="Product/Insert.htm">Thêm Sản Phẩm</a>
						
				 <div class="dropdown">
					  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					    Loại
					  </button>
					  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					    <a class="dropdown-item" href="#">LapTop</a>
					    <a class="dropdown-item" href="#">Ô Tô</a>
					 	<a class="dropdown-item">Máy Tính Bảng</a>
					    <a class="dropdown-item">Điện Thoại</a>
					   <a class="dropdown-item">Máy Giặt</a>
					    <a class="dropdown-item"">Đồng Hồ</a>
					   <a class="dropdown-item">Tai Nghe</a>
					  </div>
				  </div>
					  
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-bordered" id="dataTable" width="100%"
							cellspacing="0">
							<thead>
								<tr>
									<th>Loại</th>
									<th>Tên</th>
									<th>Giá Nhập</th>
									<th>Tên Nhà Cung Cấp</th>
									<th>Lịch Sử</th>
									<th>Tác Vụ</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${Products}" var="p">
									<tr>
										<td>${p.category.name}</td>
										<td><a href="Product/SeePhoto/${p.id}.htm">${p.name}</a></td>
										</td>
										<td>$ <fmt:formatNumber value="${p.sellingPrice}" />
										</td>
										<td>${p.supplier.name}</td>
										<td style="text-align:center;"><a class="btn btn-primary" href="Product/InComing/${p.id}.htm" >Lịch Sử</a></td>
										<td style="text-align:center;">
											<div>
												<a  class="btn btn-warning" href="Product/Update/${p.id}.htm">Sửa</a> 
												<a class="btn btn-danger" href="Product/Delete/${p.id}.htm">Xóa</a>
											</div>
										</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
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
		src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"
		crossorigin="anonymous"></script>
	<script src="assetss/assets/demo/datatables-demo.js"></script>
</body>
</html>