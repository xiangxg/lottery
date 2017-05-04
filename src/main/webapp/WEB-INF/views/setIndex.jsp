
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ page import="cn.hx.bat.sso.service.StartCrawlerService" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置管理</title>

<style type="text/css">
	body{
		margin: 0px;
		heigth:100%;
	}
	a{text-decoration:none}
</style>
<link rel="stylesheet" type="text/css" href="${baseUrl}/js/plugins/jquery-easyui/themes/color.css">
<link rel="stylesheet" type="text/css" href="${baseUrl}/js/plugins/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${baseUrl}/js/plugins/jquery-easyui/themes/icon.css">
<script src="${baseUrl}/js/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl}/js/plugins/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${baseUrl}/js/plugins/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${baseUrl}/js/plugins/jquery-easyui/EasyUI_validator.js"></script>
<style> 
.divcss5{text-align:center} 
</style> 
</head>
<body >

<div>
<span><span style="width:200px">&nbsp;
&nbsp;&nbsp;</span>
欢迎您 ${user} </span>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div style="display:inline;" class="divcss5">采集程序后台管理系统</div>

<span style="float:right"><a id="findPass" href="javascript:;"><font style="font-size:14px;text-decoration:none;" >修改密码 </font></a>&nbsp;&nbsp;<a href="./logout.do"><font style="font-size:14px;text-decoration:none;" >登出 </font></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
</div>
<div>&nbsp;</div>
<div>
&nbsp;<span style="font-size:14px;color:#777">采集开关设置</span>
</div>
<div>
&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size:13px;">彩票采集：</span>
<span id="lottery_txt" style="font-size:13px;">已启动</span>
<input id="lottery_btn"  type="button" value="关闭">
&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size:13px;">资讯采集：</span>
<span id="lotteryInfo_txt" style="font-size:13px;">已启动</span>
<input id="lotteryInfo_btn" type="button" value="关闭">
</div>
<div>&nbsp;</div>
<input type="hidden" id="lotteryIsStart" value="<%=StartCrawlerService.lotteryIsStart%>"></input>
<input type="hidden" id="lotteryInfoIsStart" value="<%=StartCrawlerService.lotteryInfoIsStart%>"></input>
<input type="hidden" id="userAccount" value="${user}"/>
<table id="tg" width="100%" height="100%"></table>

<div id="editDiv" class="easyui-dialog" style="padding-bottom:10px;" data-options="title:'修改密码',modal:true,width:260,height:200,closed:true,
	buttons:[{
				id:'saveBtn',
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					updatePass();
				}
			},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#editDiv').dialog('close');
			}
		}]">
  <div class="list">
    <form id="editForm" name="editForm">
      <input type="hidden" id="act" name="act" />
      <table cellpadding="5">
        <tr>
          <td nowrap>旧密码 :</td>
          <td><input  class="easyui-textbox" type="password" id="oldPassword" name="oldPassword" autocomplete="off"/></td>
        </tr>
        <tr nowrap>
          <td>新密码 :</td>
          <td><input  class="easyui-textbox" type="text" id="newPass" name="newPass" autocomplete="off"/>
            </td>
        </tr>
      </table>
    </form>
  </div>
  </div>
	<script type="text/javascript">
	var toolbar = [];
	</script>
	<script type="text/javascript">
		toolbar.push({
			 text:'新增',
			 iconCls:'icon-add',
			 handler:	function () {
				 $('#addWin').dialog({title:'新增设置数据',href:'./toAdd.do',width:440,height:320});
				}
		});
	</script>
	
	<script type="text/javascript">
		toolbar.push('-');
		toolbar.push({
			 text:'编辑',
			 iconCls:'icon-edit',
			 handler:	function () {
				 var row = $('#tg').datagrid('getSelected');
					if (!row) {
						$.messager.alert('提示','请选择要编辑的数据!');
						return;
					}
				 $('#addWin').dialog(
						 {title:'编辑设置数据',
						  href:'./toEdit.do',
						  queryParams:{id:row.id},
						  width:440,height:320
						  });
				}
		});
	</script>
	
	<script type="text/javascript">
		toolbar.push('-');
		toolbar.push({
			 text:'删除',
			 iconCls:'icon-cancel',
			 handler:function (){
				 if (editIndex != undefined){
	                    $('#tg').datagrid('select', editingId);
	                    $.messager.alert('提示','请先保存编辑中的数据!');
	                    return;
	                }
	                var row = $('#tg').datagrid('getSelected');
	                if(!row){
	                	 $.messager.alert('提示','请先选择要删除的数据!');
	                	 return;
	                }
	                $.messager.confirm('提示','确定要删除您所选择的数据吗？',function(v){
		    			if(v){
		    				$.ajax({
		                    	url:'delete.do',
		                    	dataType:'JSON',
		                    	type:'POST',
		                    	data:{id:row.id,name:row.name},
		                    	success:function(rs){
		                    		if(rs && rs.code == 200){
		                    			$('#tg').datagrid('reload');
		                    			$.messager.show({title:'提示',msg:'删除成功',showType:'show'});
		                    		}else{
		                    			$.messager.alert('提示',rs.message);
		                    		}
		                    	}
		                    });
		    			}
	                });
	                
				}
		});
	</script>
	
    <script type="text/javascript">
    var editingId = undefined;
	function onDblClickRow(index,row){
		if (editingId != undefined){
       		 $('#tg').datagrid('cancelEdit', editingId);
                editingId = undefined;
         }
       var row = $('#tg').datagrid('getSelected');
       if (row){
       	editingId = index;
           $('#tg').datagrid('beginEdit', editingId);
       };
	}
	// 表格行编辑结束事件
	function onAfterEdit(index,row,changes){
			var params = {
					'id':row.id,
					'isStart':row.isStart
					
				};
			$.post('update.do',params,function(data){
				if(data && data.code == '200'){
					 $.messager.show({title:'提示',msg:'更新成功',showType:'show'});
					$('#tg').datagrid('reload');
				}else{
					 $.messager.show({title:'提示',msg:'更新失败',showType:'show'});
				}
					
				},'json');
	}
	function onClickRow(index){
      	 if (editingId != undefined && editingId != index){
               $('#tg').datagrid('endEdit', editingId);
           }
		 }
		//更新主题内容
		function editMarquee() {
			$('#updateForm').form('submit', {
				onSubmit : function() {
					return $(this).form('enableValidation').form('validate');
				}
			});
			$('#updateForm').form({
				success : function(result) {
					result = $.parseJSON(result);
					if(result && result.code == '200'){
						$.messager.show({title:'提示',msg:'编辑成功',showType:'show'});
						$('#addWin').dialog('close');
						$('#tg').datagrid('reload');
    				}else{
    					$.messager.alert('提示','编辑失败!');
    				}
					
				}
			});
		}
		
		// 新增
		function addMarquee(){
			$('#addform').form('submit', {
				onSubmit : function() {
					return $(this).form('enableValidation').form('validate');
				}
			});
			$('#addform').form({
				success : function(result) {
					result = $.parseJSON(result);
					if (result && result.code == '200' ) {
						$.messager.alert('提示', "新增成功！");
						$('#addWin').dialog('close');
						$('#tg').datagrid('reload');
					}
				}
			});
		}
		
		$(function(){
			// 设置数据列表
			$('#tg').datagrid({
        	    idField:'id',
        	    title:'设置数据列表',
        	    url:'findSetData.do',
        	    border:false,
                rownumbers: true,
                animate: true,
                fit:true,
                cache:false,
                fitColumns: true,
                singleSelect:true,
                onDblClickCell: onDblClickRow,
                onClickRow:onClickRow,
                onAfterEdit:onAfterEdit,
                method: 'get',
				pagination : true,
				pageSize : 20,
                showFooter: true,
                loadFilter:function(result){
                	if(result && result.code == 200){
                		
                		var data = result.data;
                		return {rows:data.data,total:data.total};
                	}else{
                		$.messager.alert('提示','数据加载失败');
                	}
                },
                toolbar:toolbar,
                onBeforeLoad:function(){
                	editIndex = undefined;
                },
        	    columns:[[
					{field:'id',checkbox:true},
        	        {title:'名称',field:'name',width:200},
        	        {title:'是否启动',field:'isStart',width:50,
        	        	editor:{type:'combobox',options:{valueField:'id',textField:'text',data:[{text:'是',id:true},{text:'否',id:false}]}},
        	        	formatter:function(val,row){
        	        	return val==true||val=='true'?'是':'否';
        	        	}
        	        },
        	        {title:'备注',field:'remark',width:500}
        	    ]]
        	});
			var pager = $('#tg').datagrid('getPager');
			pager.pagination({
				 pageSize: 20,//每页显示的记录条数，默认为10 
			     pageList: [20,30,40],//可以设置每页记录条数的列表 
			     beforePageText: '第',//页数文本框前显示的汉字 
			     afterPageText: '页    共 {pages} 页', 
			     displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
			});	
			
		});
</script>
	<!-- 新增表单 -->
    <div id="addWin"   data-options="modal:true,buttons:'#btn'" >
    </div>
    
   <script type="text/javascript">
   //修改密码
	function updatePass(){
		
		var oldPassword = $.trim( $("#oldPassword").val());
		var newPass =  $("#newPass").val();
		var userAccount = $("#userAccount").val();
		 
		
		if( ''== oldPassword ){
			$.messager.alert('提示','请输入旧密码');  
			return false;
		}
		
		if(''== newPass){
			$.messager.alert('提示','请输入新密码');
			return false;
		}

			$.ajax({
				url:'updatePassword.do',
				data:{ "newPassword": newPass, "oldPassword": oldPassword,"account":userAccount},
				dataType : "json",
				success: function (data){
					
					if(data.code == 200){
						window.location.href="toLogin.do";
					}else{
						$.messager.alert("提示",data.message); 
					}
					
				},
				error: function (data)//服务器响应失败处理函数
	            {
					$.messager.alert("提示", data.message); 
	            }
			});
	}
   $(document).ready(function(){ 
	   
	
	   
		//修改密码
		$("#findPass").click(function(){
			$('#editDiv .dialog-button').show();
			$('#editDiv').dialog('open');
		});
		
	   if($('#lotteryIsStart').val()==1){
		   $('#lottery_btn').attr("value","关闭");//lottery_txt
		   $('#lottery_txt').html("已启动");
	   }else{
		   $('#lottery_btn').attr("value","启动");
		   $('#lottery_txt').html("已关闭");
	   }
	   
	   if($('#lotteryInfoIsStart').val()==1){
		   $('#lotteryInfo_btn').attr("value","关闭");//lottery_txt
		   $('#lotteryInfo_txt').html("已启动");
	   }else{
		   $('#lotteryInfo_btn').attr("value","启动");
		   $('#lotteryInfo_txt').html("已关闭");
	   }
	   $('#lotteryInfo_btn').click(function(){
			  $(this).attr("disabled","disabled");
			  $.ajax({
	          	url:'lotteryInfoSwitch.do',
	          	dataType:'JSON',
	          	type:'POST',
	          	data:{},
	          	success:function(rs){
	          		if(rs && rs.code == 200){
	          			if(rs.data==1){
	          				$.messager.alert('提示',"启动成功");
	          				 $('#lotteryInfo_btn').attr("value","关闭");
	          				$('#lotteryInfo_txt').html("已启动");
	          			}else{
	          				$.messager.alert('提示',"关闭成功");
	          				 $('#lotteryInfo_btn').attr("value","启动");
	          				$('#lotteryInfo_txt').html("已关闭");
	          			}
	          			
	          		  
	          		}else{
	          			$.messager.alert('提示',rs.message);
	          		}
	          		 $('#lotteryInfo_btn').removeAttr("disabled");
	          	}
	          
	          });
			  
		   });
	
	   $('#lottery_btn').click(function(){
		  $(this).attr("disabled","disabled");
		  $.ajax({
          	url:'lotterySwitch.do',
          	dataType:'JSON',
          	type:'POST',
          	data:{},
          	success:function(rs){
          		if(rs && rs.code == 200){
          			if(rs.data==1){
          				$.messager.alert('提示',"启动成功");
          				 $('#lottery_btn').attr("value","关闭");
          				$('#lottery_txt').html("已启动");
          			}else{
          				$.messager.alert('提示',"关闭成功");
          				 $('#lottery_btn').attr("value","启动");
          				$('#lottery_txt').html("已关闭");
          			}
          			
          		  
          		}else{
          			$.messager.alert('提示',rs.message);
          		}
          		 $('#lottery_btn').removeAttr("disabled");
          	}
          
          });
		  
	   });
	});
   </script>
    
</body>
</html>