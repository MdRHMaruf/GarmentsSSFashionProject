window.onload = () => {

    document.title = "User Profile Create";

    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './allEmployee',
        data: {
        },
        success: function (data) {
            let employeeCode = data.result.map(employee => employee.EmployeeCode)

            $("#employeeId").autocomplete({
                source: employeeCode
            });
        }
    });
};

function employeeSearch() {
    let employeeId = $("#employeeId").val();
    if (employeeId != '') {
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: './getEmployeeInfoByEmployeeCode',
            data: {
                employeeCode: employeeId
            },
            success: function (data) {
                console.log("employee =", data);
                let employeeInfo = data.employeeInfo;
                $("#name").val(employeeInfo.employeeName);
                $("#employeeAutoId").val(employeeInfo.autoId)
            }
        });
    } else {
        alert("Please Enter Employee ID");
    }
}


function loadRolePermissions() {
    //	$('#moduleName').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {
    //let userRoles = $("#userRole").selectpicker("val");
    let userRoles = '';
    $("#userRole").val().forEach(roleId => {
        userRoles += `'${roleId}',`;
    });


    if (userRoles != '') {
        userRoles = userRoles.slice(0, -1);

        console.log("User Roles=", userRoles);
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: './getRolePermissions',
            data: {
                roleIds: userRoles
            },
            success: function (data) {
                console.log(data);
                $("#permissionList").empty();
                drawRolePermissionTable(data.permissionList);
                //checkedPermissionWise(permissionList);
            }
        });
    } else {
        $("#permissionList").empty();
    }

}


function loadExtraPermissionInTable(){
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
                    $("#extraPermissionList").empty();
                    setTableData(data);
                    checkedPermissionWise(permissionList);
                }
            });
        }else{
            alert("Select Module");
            $("#roleList").empty();
        }
    
    }


function toggleExtraDiv() {

    $("#extraDiv").fadeToggle("slow");
}

function saveAction(){
    let employeeId = $("#employeeAutoId").val();
    let fullName = $("#name").val();
    let userName = $("#userName").val();
    let password = $("#password").val();
    let confirmPassword = $("#confirmPassword").val();
    let userRoles = '';
    let userId = $("#userId").val();
    $("#userRole").val().forEach(roleId => {
        userRoles += `'${roleId}',`;
    });
    let activeStatus = $("#activeStatus").val();


    const rowList=$("#extraPermissionList tr").length;
	let accessList = [];
	
	for (let i = 1; i <=rowList; i++) {

		let rId = $("#R"+i).attr("data-id");

		if($("#check_"+rId).is(":checked")){

			let moduleId=0,headId=0,subId=0,add=0,edit=0,view=0,del=0;

			moduleId = $("#moduleId_"+rId).text();
			headId = $("#head_"+rId).text();
			subId = $("#id_"+rId).text();

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

			//let value=moduleId+":"+headId+":"+subId+":"+add+":"+edit+":"+view+":"+del;
			accessList.push({
                moduleId: moduleId,
                headId: headId,
                subId: subId,
                add: add,
                edit: edit,
                view: view,
                delete: del
            });
		}
	}

	//let valueList="["+accessList+"]";

    if(employeeId != "0" && employeeId != ""){
        if(userName != ''){
            if(password != ''){
                if(userRoles != ''){
                    if(confirm("Are you sure to Save this User")){
                        $.ajax({
                            type: 'POST',
                            dataType: 'json',
                            url: './saveUserProfile',
                            data: {
                                userInfo: JSON.stringify({
                                    employeeId: employeeId,
                                    fullName: fullName,
                                    userName: userName,
                                    password: password,
                                    userRoles: userRoles,
                                    activeStatus: activeStatus,
                                    userId: userId,
                                    extraPermissionList: accessList
                                })
                            },
                            success: function(data){
                                if(data.result == "successful"){
                                    alert("User Save Successful");
                                    location.reload();
                                }else
                                alert(data.result);

                            }
                        });
                    }
                }else{
                    alert("Please Select Any Role");
                }
            }else{
                alert("Please Enter Password");
            }
        }else{
            alert("Please Enter User Name");
        }
    }else{
        alert("Please Select Employee");
    }
    

}

function editAction(){
    let userAutoId = $("#userAutoId").val();
    let employeeId = $("#employeeAutoId").val();
    let fullName = $("#name").val();
    let userName = $("#userName").val();
    let password = $("#password").val();
    let confirmPassword = $("#confirmPassword").val();
    let userRoles = '';
    let userId = $("#userId").val();
    $("#userRole").val().forEach(roleId => {
        userRoles += `'${roleId}',`;
    });
    let activeStatus = $("#activeStatus").val();


    const rowList=$("#extraPermissionList tr").length;
	let accessList = [];
	
	for (let i = 1; i <=rowList; i++) {

		let rId = $("#R"+i).attr("data-id");

		if($("#check_"+rId).is(":checked")){

			let moduleId=0,headId=0,subId=0,add=0,edit=0,view=0,del=0;

			moduleId = $("#moduleId_"+rId).text();
			headId = $("#head_"+rId).text();
			subId = $("#id_"+rId).text();

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

			//let value=moduleId+":"+headId+":"+subId+":"+add+":"+edit+":"+view+":"+del;
			accessList.push({
                moduleId: moduleId,
                headId: headId,
                subId: subId,
                add: add,
                edit: edit,
                view: view,
                delete: del
            });
		}
	}

	//let valueList="["+accessList+"]";

    if(employeeId != "0" && employeeId != ""){
        if(userName != ''){
            if(password != ''){
                if(userRoles != ''){
                    if(confirm("Are you sure to Edit this User")){
                        $.ajax({
                            type: 'POST',
                            dataType: 'json',
                            url: './editUserProfile',
                            data: {
                                userInfo: JSON.stringify({
                                    userAutoId: userAutoId,
                                    employeeId: employeeId,
                                    fullName: fullName,
                                    userName: userName,
                                    password: password,
                                    userRoles: userRoles,
                                    activeStatus: activeStatus,
                                    userId: userId,
                                    extraPermissionList: accessList
                                })
                            },
                            success: function(data){
                                if(data.result == "successful"){
                                    alert("User Edit  Successful");
                                    location.reload();
                                }else
                                alert(data.result);

                            }
                        });
                    }
                }else{
                    alert("Please Select Any Role");
                }
            }else{
                alert("Please Enter Password");
            }
        }else{
            alert("Please Enter User Name");
        }
    }else{
        alert("Please Select Employee");
    }
}

function searchUser(userId){
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getUserInfo',
        data: {
            userId : userId
        },
        success: function(data){
           console.log(data.result);
           let userInfo = data.result;

           $("#employeeAutoId").val(userInfo.employeeId);
           $("#userAutoId").val(userId);
           $("#employeeId").val(userInfo.employeeCode);
           $("#name").val(userInfo.fullName);
           $("#userName").val(userInfo.username);
           $("#password").val(userInfo.password);
           $("#confirmPassword").val(userInfo.password);
           let roleIds = userInfo.roleIds.split(',');
           $("#userRole").val(roleIds).change();
           $("#activeStatus").val(userInfo.activeStatus);
            $("#btnSave").hide();
            $("#btnEdit").show();
           $("#exampleModal").modal('hide');
        }
    });
}

function fieldRefresh(){
    $("#employeeAutoId").val("0");
    $("#employeeId").val("");
    $("#name").val("");
    $("#userName").val("");
    $("#password").val("");
    $("#confirmPassword").val("");
    $("#userRole").val("").change();

}

function refreshAction(){
    location.reload();
}

function drawRolePermissionTable(data) {
    let rows = ''
    for (let i = 0; i < data.length; i++) {
        let permission = data[i];
        rows += `<tr data-id=${permission.subMenuId} id="R${i}">

				<td class="row-index text-center"> ${i + 1} </td>

				<td class="row-index text-center" id='moduleName_${permission.subMenuId}'>${permission.moduleName}</td>
				<td class="row-index" id='sub_${permission.subMenuId}' data-sub="${permission.subMenuId}"> ${permission.subMenuName} </td>

				<td class="row-index text-center"> <input id="add_${permission.subMenuId}" class="add" type="checkbox" ${permission.enter == '0' ? '' : 'checked'}> </td>
				<td class="row-index text-center"> <input id="edit_${permission.subMenuId}" class="edit" type="checkbox" ${permission.edit == '0' ? '' : 'checked'}> </td>	
				<td class="row-index text-center"> <input id="view_${permission.subMenuId}" class="view" type="checkbox" ${permission.view == '0' ? '' : 'checked'}> </td>
				<td class="row-index text-center"> <input id="delete_${permission.subMenuId}" class="delete" type="checkbox" ${permission.delete == '0' ? '' : 'checked'}> </td>
		</tr>`;
    }
    $("#permissionList").append(rows);
}

function setTableData(data){
    let rowIdx = 0;
	for (let i = 0; i < data.length; i++) {
		$('#extraPermissionList').append(`<tr data-id=${data[i].subId} id="R${++rowIdx}">

				<td class="row-index text-center"> ${rowIdx} </td>

				<td class="row-index text-center" id='moduleName_${data[i].subId}'>${data[i].moduleName}</td>
				<td class="row-index text-center" id='moduleId_${data[i].subId}' hidden>${data[i].moduleId}</td>

				<td class="row-index text-center" id='head_${data[i].subId}' hidden>${data[i].head}</td>
				<td class="row-index text-center" id='id_${data[i].subId}' hidden>${data[i].subId}</td>

				<td class="row-index" id='sub_${data[i].subId}' data-sub="${data[i].subId}"> ${data[i].subName} </td>

				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="add_${data[i].subId}" class="add" type="checkbox"> </td>
				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="edit_${data[i].subId}" class="edit" type="checkbox"> </td>	
				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="view_${data[i].subId}" class="view" type="checkbox"> </td>
				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="delete_${data[i].subId}" class="delete" type="checkbox"> </td>
				<td class="row-index text-center"> <input data-sub="${data[i].subId}" id="check_${data[i].subId}" class="check" type="checkbox"> </td>

		</tr>`);
	}
	rowIdx=0;
}