function formSelectChangeAction(){
    let formId = $("#formName").val();
    let ownerId = $("#userId").val();
    let permittedUserId = $("#permittedUser").val();
    $("#loader").show();
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getPermitUserAndInvoiceNoList',
        data: {
            formId: formId,
            ownerId : ownerId,
            permittedUserId: permittedUserId
        },
        success: function(data){
            console.log(data.fileList);
            $("#fileList").empty();
            drawFileList(data.fileList);
            $("#permittedUserList").empty();
            drawPermittedUserList(data.permittedUser);
            $("#loader").hide();
        },
        error: function(jqXHR, textStatus,errorThrown){

        }
    });

}

function checkAllAction(){
    let isCheckAllSelect = $("#permitAll").prop('checked');
    if(isCheckAllSelect){
        $(".checkbox").prop('checked',true);
    }else{
        $(".checkbox").prop('checked',false);
    }
}

function memberAddAction(){
    let permittedUserId = $("#permittedUser").val();
    let permittedUserName = $("#permittedUser option:selected").text();

    let rows = $("#memberList tr");
    let isExist = false;
    let length = rows.length;
    for(let i=0;i<length;i++){
        let row = rows[i];
        if(permittedUserId == row.getAttribute('data-member-id')){
            isExist = true;
            break;
        }
    }
    if(permittedUserId != 0){
        if(!isExist){
            $("#memberList").append(`<tr id='mRow-${length+1}' data-row-type='newRow' data-member-id='${permittedUserId}'>
                                        <td>${length+1}</td>
                                        <td>${permittedUserName}</td>
                                        <td><i class="fa fa-trash" onclick="deleteMember('${length+1}')" style="cursor : pointer;"> </i></td>
                                    </tr>`)
        }
    }else{
        alert("Please Select Member....")
    }
    
}

function deleteMember(id){

    if(confirm("Are you sure to delete")){
        if($("#mRow-"+id).attr('data-row-type')=='newRow'){
            $("#mRow-"+id).remove();
        }else{
            $("#mRow-"+id).remove();
        }
        
    }
}


function saveAction(){

    let formId = $("#formName").val();
    let permittedUserId = $("#permittedUser").val();
    let rows = $("#fileList tr");
    let userId = $("#userId").val();
    let permittedUserIds = '';
    let permittedFileList = [];
    rows.each((index,row)=>{
        
        let id = row.id;

        if($("#permitCheck-"+id).prop('checked')){
            permittedFileList.push({
                resourceType : formId,
                resourceId : row.id,
                ownerId: userId,
                permittedUserId: permittedUserId,
                entryBy : userId 
            })
        }
        
    })


    if(formId != '0'){
        if(permittedUserId != '0'){
            
            if(confirm("Are you sure to Save Group")){
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './submitFileAccessPermit',
                    data: {
                        fileAccessPermit : JSON.stringify({
                            resourceType : formId,
                            ownerId: userId,
                            permittedUserId: permittedUserId,
                            permittedFileList: permittedFileList
                        })
                    },
                    success: function(data){
                        if(data.result == "success"){
                            alert("Access Permit Successful");
                           // $("#groupList").empty();
                           // drawGroupList(data.groupList);
                        }
                    },
                    error: function(jqXHR, textStatus,errorThrown){
    
                    }
                });
            }
            
        }else{
            alert("Please Select Permitted...");
        }
    }else{
        alert("Please Enter Group Name")
    }
}
function userListClick(permitUserId){
    $("#permittedUser").val(permitUserId).change();
}

function refreshAction(){
    location.reload();
}

function setGroup(groupId){
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getGroupInfo',
        data: {
            groupId : groupId
        },
        success: function(data){
            $("#memberList").empty();
            drawMemberList(data.memberList);
            $("#groupId").val(groupId);
            $("#btnSave").hide();
            $("#btnEdit").show();

        },
        error: function(jqXHR, textStatus,errorThrown){

        }
    });
}

function drawMemberList(data){
    let rows = '';
    $("#groupName").val(data[0].groupName);
    for(let i=0;i<data.length;i++){
        let member = data[i];
        rows += `<tr id='mRow-${member.autoId}' data-row-type='oldRow' data-member-id='${member.permittedUserId}'>
        <td>${i+1}</td>
        <td>${member.permittedUserName}</td>
        <td><i class="fa fa-trash" onclick="deleteMember('${member.autoId}')" style="cursor : pointer;"> </i></td>
    </tr>`;
    }

    $("#memberList").append(rows);
}

function drawFileList(data){
    let rows = '';
    for(let i=0;i<data.length;i++){
        let file = data[i];
        rows += `<tr id='${file.id}'>
        <td>${i+1}</td>
        <td>${file.formName}</td>
        <td>${file.fileNo}</td>
        <td><input type="checkbox" class='checkbox' id="permitCheck-${file.id}" ${file.permit=="0"?"":"checked"}></td>
    </tr>`;
    }

    $("#fileList").append(rows);
}

function drawPermittedUserList(data){
    let rows = '';
    for(let i=0;i<data.length;i++){
        let member = data[i];
        rows += `<tr id='permittedUser-${member.id}' style='cursor:pointer;' onclick='userListClick("${member.id}")'>
        <td>${i+1}</td>
        <td>${member.fullName}</td>
    </tr>`;
    }

    $("#permittedUserList").append(rows);
}



