(function(){
	
	$(document).ready(function(){
		
		$('.login-btn').on('click',function(){
			var pwd =$('.login-pwd');
			var name = $('.login-name');
			var baseurl =$('#baseurl').val();
			if(name.val() == '' || name.val() == undefined){
			 	$('.check-info').html('');
				name.parent().find('.check-info').html('<i class="fa fa-exclamation-circle"></i> 用户名不能为空');
				name.focus();
				return false;
			}else if(pwd.val() == '' || pwd.val() == undefined){
				$('.check-info').html('');
				pwd.parent().find('.check-info').html('<i class="fa fa-exclamation-circle"></i> 密码不能为空');
				pwd.focus();
				return false;
			}else{	
				$('.check-info').html('');
				var vurl = baseurl+'/web/login.do';
				
				$.ajax({
					type: "POST",
					url:vurl,
					data:{
						account:name.val(),
						password:pwd.val()
					},
					success: function(data) {
						if(data.code != '200'){
							$('.tips-alert .info').html(data.message);
							$('.tips-alert').show();
							
						}else{
							window.location.href=baseurl+'/web/setIndex.do';
						}
					}
				})
				
				return false;
			}
		})
		$('.login-way li').on('click',function(){
			showclass='.'+$(this).attr('data-class');
			$('.login-way li').removeClass('active');
			$(this).addClass('active');
			$('.login-box form').hide();
			$(showclass).show();
		})
		
		$('.tips-alert .close').on('click',function(){
			$('.tips-alert').slideUp();
		})
	})
})()
