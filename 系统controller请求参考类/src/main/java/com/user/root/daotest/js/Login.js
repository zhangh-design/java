$('#loginbtn').click(function() {
	    var param = {
	        username : $("#username").val(),
	        password : $("#password").val()
	    };
	    $.ajax({
	        type: "post",
	        url: "/login.html",
	        data: param, 
	        dataType: "json", 
	        success: function(data) {
	        	console.info('aaaaaaaaaaaa ',data);
	            if(data.success == false){
	                alert(data.errorMsg);
	            }else{
	                //登录成功
	                window.location.href = "/user/register.html";
	            }
	        },
	        error: function(data) { 
	        	console.info('bbb ',data);
	            alert("调用失败....");
	        }
	    });
	});