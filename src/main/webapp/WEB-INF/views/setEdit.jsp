<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑设置数据</title>
</head>
<body>
<form id="updateForm" action="update.do" method="post">
		   
			<input type="hidden" name="id" value="${setData.id}"/>
   <table cellpadding="5" id="searchWin">
             <tr>
                <td>名称:</td>
                <td><input class="easyui-textbox" type="text" name="name" value="${setData.name}" style="width:250px;" data-options="required:true"></input></td>
            </tr>
            <tr>
                <td>是否启动:</td>
                <td><input class="easyui-combobox" type="text" name="isStart" value="${setData.isStart}" style="width:250px;" data-options="valueField:'id',textField:'text',data:[{text:'是',id:'true'},{text:'否',id:'false'}]"></input></td>
            </tr>
            <tr>
                <td>备注:</td>
                  <td><textarea name="remark" style="width:250px;height:75px;">${setData.remark}</textarea>
            </tr>
     </table>
</form>
     <!-- 编辑表单窗口按钮 -->
	 <div id="btn" style="text-align:center;padding:5px">
	     <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editMarquee()">提交</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#addWin').dialog('close');">取消</a>
	 </div>
	 <!-- 编辑表单窗口按钮 -->

</body>
</html>