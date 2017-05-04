(function(){
	var check_right ='<i class="fa fa-check-circle" style="color:#73c117"></i>';
	var name = $('.name-input');
	var tel = $('.tel-input');
	var title = $('.title-input');
	var detail = $('.detail-input');
	var telreg = /^(1\d{10})$/;
	var baseurl =$('#baseurl').val();
	$(document).ready(function(){
		$('.reg-form').submit(function(e){
			e.preventDefault();
		})
		
		$('.pop-alert .close').on('click',function(){
			$('.pop-alert').hide();
			if(detail.val().length > 200){
				detail.focus();	
			}
			if(!telreg.test(tel.val())){
				tel.focus();
			}
			check_null($(this));
			
		})
		$('.submit-btn').on('click',function(){	
			if (check_null($(this))){
				if(!telreg.test(tel.val())){
					$('.pop-alert .info').html('<i class="fa fa-exclamation-triangle" style="color:red"></i> 手机号码格式不正确');
					$('.pop-alert').show();
					
				}else{
					var vurl = baseurl+'/feedBack/save.do';
					if(detail.val().length > 200){
						$('.pop-alert .info').html('<i class="fa fa-exclamation-triangle" style="color:red"></i> 字数不能超过200');
						$('.pop-alert').show();
					}else{
						$.ajax({
							type:"post",
							url:vurl,
							data:{
								phone:tel.val(),
								content:detail.val(),
								title:title.val(),
								createBy:name.val()
							},
							success: function(data) {				
								if(data.code != '200'){
									$('.pop-alert .info').html('<i class="fa fa-exclamation-triangle" style="color:red"></i> '+data.message);
									$('.pop-alert').show();
								}else{
									$('.section1').hide();
									$('.section2').show();
								}
							}
						});
					}
					
					
					
				}
			}else{
				$('.pop-alert').show();
			}
			
		})
		
		
	})
	
	function check_null(that){
		var flag =true;
		$('input:visible').each(function(){
			if($(this).parent().parent().find('.check-info').html() != check_right){
				$(this).parent().parent().find('.check-info').html('')
			}
			if($(this).val() == ''){
				var check_name =$(this).parent().prev().html();
				$('.pop-alert .info').html('<i class="fa fa-exclamation-triangle" style="color:red"></i> '+check_name+'不能为空');
				
				$(this).focus();
				flag =false;
				return false;
			}
		});
		
		$('textarea:visible').each(function(){
			if($(this).parent().parent().find('.check-info').html() != check_right){
				$(this).parent().parent().find('.check-info').html('')
			}
			if($(this).val() == ''){
				$(this).focus();
				var check_name =$(this).parent().prev().html();
				$('.pop-alert .info').html('<i class="fa fa-exclamation-triangle" style="color:red"></i> '+check_name+'不能为空');
				
				flag =false;
				return false;
			}
		});	
		if(flag){
			return true;
		};
			
		
		
	}
})()
