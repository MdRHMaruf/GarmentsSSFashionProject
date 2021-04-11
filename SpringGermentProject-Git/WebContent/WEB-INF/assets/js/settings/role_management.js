
let permissionList=[];

function saveAction(){

	let roleName = $("#roleName").val();
	let userId = $("#userId").val();

	const rowlist=$("#roleList tr").length;
	var accesslist = [];
	let j=0;
	for (var i = 1; i <=rowlist; i++) {

		var rId = $("#R"+i).attr("data-id");

		if($("#check_"+rId).is(":checked")){

			let module=0,head=0,sub=0,add=0,edit=0,view=0,del=0;

			module = $("#moduleId_"+rId).text();
			head = $("#head_"+rId).text();
			sub = $("#id_"+rId).text();

			if($("#add_"+rId).is(":checked")){
				add=1;
			}else{
				add=0;
			}

			if($("#edit_"+rId).is(":checked")){
				edit=1;
			}else{
				edit=0;
			}

			if($("#view_"+rId).is(":checked")){
				view=1;
			}else{
				view=0;
			}

			if($("#delete_"+rId).is(":checked")){
				del=1;
			}else{
				del=0;
			}

			var value=module+":"+head+":"+sub+":"+add+":"+edit+":"+view+":"+del;
			accesslist[j++] = [value];
		}
	}

	var valuelist="["+accesslist+"]";

	if(roleName!=""){

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './saveRolePermission',
			data: {

				accesslist:valuelist,roleName:roleName,userId:userId,
			},
			success: function(data){
				if(data==true){
					alert("Save Successfully");
					refreshAction();
				}else{
					alert("Role Name Already Exist")
				}
			}
		});

	}else{
		alert("Empty Role Name");
	}

}

function loadSubInTable(){
//	$('#moduleName').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {
	let moduleId = $("#moduleName").selectpicker("val");
	if(moduleId!=0){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './getSubmenu/'+moduleId,
			data: {

			},
			success: function(data){
				$("#roleList").empty();
				setTableData(data);
				checkedPermissionWise(permissionList);
			}
		});
	}else{
		alert("Select Module");
		$("#roleList").empty();
	}

}
//);

var rowIdx=0;
function setTableData(data){
	for (var i = 0; i < data.length; i++) {
		$('#roleList').append(`<tr data-id=${data[i].subId} id="R${++rowIdx}">

				<td class="row-index text-center"> ${rowIdx} </td>

				<td class="row-index text-center" id='moduleName_${rowIdx}'>${data[i].moduleName}</td>
				<td class="row-index text-center" id='moduleId_${rowIdx}' hidden>${data[i].moduleId}</td>

				<td class="row-index text-center" id='head_${rowIdx}' hidden>${data[i].head}</td>
				<td class="row-index text-center" id='id_${rowIdx}' hidden>${data[i].subId}</td>

				<td class="row-index" id='sub_${rowIdx}' data-sub="${data[i].subId}"> ${data[i].subName} </td>

				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="add_${data[i].subId}" class="add" type="checkbox"> </td>
				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="edit_${data[i].subId}" class="edit" type="checkbox"> </td>	
				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="view_${data[i].subId}" class="view" type="checkbox"> </td>
				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="delete_${data[i].subId}" class="delete" type="checkbox"> </td>
				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="check_${data[i].subId}" class="check" type="checkbox"> </td>

		</tr>`);
	}
	rowIdx=0;
}

function editAction(){

	let roleId = $("#roleId").val();
	let roleName = $("#roleName").val();
	let userId = $("#userId").val();

	const rowlist=$("#roleList tr").length;
	var accesslist = [];
	let j=0;
	for (var i = 1; i <=rowlist; i++) {

		var rId = $("#R"+i).attr("data-id");

		if($("#check_"+rId).is(":checked")){

			let module=0,head=0,sub=0,add=0,edit=0,view=0,del=0;

			module = $("#moduleId_"+rId).text();
			head = $("#head_"+rId).text();
			sub = $("#id_"+rId).text();

			if($("#add_"+rId).is(":checked")){
				add=1;
			}else{
				add=0;
			}

			if($("#edit_"+rId).is(":checked")){
				edit=1;
			}else{
				edit=0;
			}

			if($("#view_"+rId).is(":checked")){
				view=1;
			}else{
				view=0;
			}

			if($("#delete_"+rId).is(":checked")){
				del=1;
			}else{
				del=0;
			}

			var value=module+":"+head+":"+sub+":"+add+":"+edit+":"+view+":"+del;
			accesslist[j++] = [value];
		}
	}

	var valuelist="["+accesslist+"]";

	if(roleName!=""){

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './editRolePermission',
			data: {

				accesslist:valuelist,roleId:roleId,
				roleName:roleName,userId:userId,
			},
			success: function(data){
				alert("Role Edit Successfully");
				refreshAction();
			}
		});

	}else{
		alert("Empty Role Name");
	}

}

$(document).ready(function() {

	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './getAllRoleName',
		data: {},
		success: function(data){
			$("#roleNameList").empty();
			setroleNameListData(data);
		}
	});

});
let rowId=0;
function setroleNameListData(data){

	for (var i = 0; i < data.length; i++) {
		$('#roleNameList').append(`<tr id="R${++rowId}">

				<td class="row-index text-center">${rowId}</td>
				<td class="row-index text-center" id='roleId_${rowId}' hidden>${data[i].roleId}</td>
				<td class="row-index" id='roleName_${rowId}'>${data[i].roleName}</td>
				<td class="row-index text-center"> <i class="fa fa-edit" onclick="setForEdit(this)"></i> </td>

		</tr>`);
	}
	rowId=0;
}

function setForEdit(a){
	document.getElementById("btnSave").hidden=true;
	document.getElementById("btnEdit").hidden=false;
	let rowId = a.parentNode.parentNode.rowIndex;
	let id = $("#roleId_"+rowId).text();
	let name = $("#roleName_"+rowId).text();

	$("#roleId").val(id);
	$("#roleName").val(name);

	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getAllPermissions/'+id,
		data: {

		},
		success: function(data){
			$("#roleList").empty();
			let mId=[];
			permissionList  = data.permissionList;
			for (var i = 0; i < permissionList.length; i++) {
				mId.push(permissionList[i].moduleId);
			}
			$("#moduleName").val(mId).change();



		}
	});
}

function checkedPermissionWise(data){

	const rowL=$("#roleList tr").length;

	for (var i = 0; i < data.length; i++) {
		for (var j = 1; j <= rowL; j++) {

			var rId = $("#R"+j).attr("data-id");
			if(data[i].subId== rId){

				if(data[i].entry == 1){
					$("#add_"+data[i].subId).prop('checked', true);
				}else{
					$("#add_"+data[i].subId).prop('checked', false);
				}

				if(data[i].edit == 1){
					$("#edit_"+data[i].subId).prop('checked', true);
				}else{
					$("#edit_"+data[i].subId).prop('checked', false);
				}

				if(data[i].del == 1){
					$("#delete_"+data[i].subId).prop('checked', true);
				}else{
					$("#delete_"+data[i].subId).prop('checked', false);
				}

				if(data[i].view == 1){
					$("#view_"+data[i].subId).prop('checked', true);
				}else{
					$("#view_"+data[i].subId).prop('checked', false);
				}

				$("#check_"+data[i].subId).prop('checked', true);

			}
		}
	}

}

$("#checkAll").click(function () {
	if ($(this).is(":checked")) {
		$(this).closest('table').find('td input[class="check"]').prop('checked', this.checked);
	}else{
		$(this).closest('table').find('td input[class="check"]').prop('checked', false);
	}
	/*$('tbody tr td input[id="check"]').each(function(){
        $(this).prop('checked', this.checked);
    });*/
});

$("#checkAllAdd").click(function () {
	if ($(this).is(":checked")) {
		$(this).closest('table').find('td input[class="add"]').prop('checked', this.checked);
	}else{
		$(this).closest('table').find('td input[class="add"]').prop('checked', false);
	}
});

$("#checkAllEdit").click(function () {
	if ($(this).is(":checked")) {
		$(this).closest('table').find('td input[class="edit"]').prop('checked', this.checked);
	}else{
		$(this).closest('table').find('td input[class="edit"]').prop('checked', false);
	}
});

$("#checkAllView").click(function () {
	if ($(this).is(":checked")) {
		$(this).closest('table').find('td input[class="view"]').prop('checked', this.checked);
	}else{
		$(this).closest('table').find('td input[class="view"]').prop('checked', false);
	}
});

$("#checkAllDelete").click(function () {
	if ($(this).is(":checked")) {
		$(this).closest('table').find('td input[class="delete"]').prop('checked', this.checked);
	}else{
		$(this).closest('table').find('td input[class="delete"]').prop('checked', false);
	}
});

function refreshAction(){
	location.reload();
}