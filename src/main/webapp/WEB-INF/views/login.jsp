<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>管理系统</title>
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<meta name="viewport" content="width=1200" />
        <link rel="shortcut icon" href="${baseUrl}/img/favicon.png">
        <link rel="stylesheet" href="${baseUrl}/bower_components/font-awesome/css/font-awesome.css">
		<link rel="stylesheet" href="${baseUrl}/css/main.css">
        <script src="${baseUrl}/js/jquery.min.js"></script>
	</head>
	<body style="background:#fff">
		<div class="header">
			<div class="container">
				<input type="hidden" value="${baseUrl}" id="baseurl"/>
				
				<a href="${baseUrl}"> 管理平台</a>
			</div>
		</div>		
		<div style="background:#f4f8fc;">	
		<br />	
		<div style="padding-top:4% ;"></div>
		<div class="container box">
			<div class="box-w60">
				<div></div>
			</div>
			<div class="box-w40">
				<br />
				<br />
				<div class="shadow">
					<ul class="login-way">
						<li data-class="psw-form" class="left-bg active"><span>密码登录</span></li>
						<li data-class="ca-form" class="right-bg"><span></span></li>
					</ul>
					<div class="login-box">
						<form class="psw-form" method="post" action="?">
							<input type="hidden" class="redirectUrl" name="redirectUrl" value="${redirectUrl}"/>
							<div class="tips-alert">
								<span class="close pull-right"><i class="fa fa-times"></i></span>
								<i class="fa fa-exclamation-circle"></i> 
								<span class="info">message</span>
							</div>
							<div class="login-group">
								<input class="login-name" name="account" type="text" placeholder="用户名"/>
								<span class="icon-box"><i class="fa fa-user"></i></span>								
								<div class="check-info"> </div>
							</div>
							<div class="login-group">
								<input class="login-pwd" name="password" type="password"  placeholder="请输入密码"/>
								<span class="icon-box"><i class="fa fa-unlock-alt"></i></span>								
								<div class="check-info"> </div>
							</div>
							
							
							<br />
							<div class="text-center">
								<button class="btn login-btn btn-block" id="login-btn">登录</button>
							</div>
						</form>
						
					</div>
				</div>
			</div>
		</div>		
		<div style="padding-top:4% ;"></div>
		</div>
	<div class="copyright">
		　　版权所有　　|　　
	</div>
	<script src="${baseUrl}/js/login.js?v2"></script>
	</body>
</html>