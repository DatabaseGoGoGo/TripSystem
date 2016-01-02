window.onload = function(){
	var bt_login = document.getElementById("login-button");
	var error_info = document.getElementById("error_info");
	if (error_info != null) {
		document.getElementById("summary").innerHTML = "<font color='red'>用户名或密码错误</font>";
	}

	bt_login.onclick = function(){
		var div = document.getElementById("summary");
		var u_user = document.getElementById("username").value;
		var u_pass = document.getElementById("password").value;
		if (u_user.length == 0 || u_pass.length ==0){
			div.innerHTML = "<font color='red'>输入不能为空</font>";
			return;
		}
		var login_form = document.getElementById("login_box");					
		login_form.submit();
	}
}