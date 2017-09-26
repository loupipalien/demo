<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<h2>登录</h2>
	<form action="login" method="post">
		<input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<table>
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="登录" />
					<input type="reset" value="重置" /></td>
			</tr>
		</table>
	 </form>
</body>
</html>
