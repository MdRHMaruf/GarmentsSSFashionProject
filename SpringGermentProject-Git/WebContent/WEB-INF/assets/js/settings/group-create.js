function memberAddAction(){
    let memberId = $("#memberSelect").val();
    let memberName = $("#memberSelect option:selected").text();

    let rows = $("#memberList tr");
    let isExist = false;
    let length = rows.length;
    for(let i=0;i<length;i++){
        let row = rows[i];
        if(memberId == row.getAttribute('data-member-id')){
            isExist = true;
            break;
        }
    }
    if(memberId != 0){
        if(!isExist){
            $("#memberList").append(`<tr id='mRow-${length+1}' data-row-type='newRow' data-member-id='${memberId}'>
                                        <td>${length+1}</td>
                                        <td>${memberName}</td>
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

    let groupName = $("#groupName").val().trim();
    let rows = $("#memberList tr");
    let userId = $("#userId").val();
    let memberIds = '';
    rows.each((index,row)=>{
        memberIds += row.getAttribute('data-member-id')+",";
    })


    if(groupName != ''){
        if(rows.length>1){
            memberIds = memberIds.slice(0,-1);
            if(confirm("Are you sure to Save Group")){
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './saveGroupName',
                    data: {
                        group : JSON.stringify({
                            groupName: groupName,
                            members : memberIds,
                            userId : userId
                        })
                    },
                    success: function(data){
                        if(data.result == "success"){
                            alert("Group Save Successful");
                            $("#groupList").empty();
                            drawGroupList(data.groupList);
                        }
                    },
                    error: function(jqXHR, textStatus,errorThrown){
    
                    }
                });
            }
            
        }else{
            alert("Please Select More Than One Member Name...");
        }
    }else{
        alert("Please Enter Group Name")
    }
}


function editAction(){
    let groupId = $("#groupId").val();
    let groupName = $("#groupName").val().trim();
    let rows = $("#memberList tr");
    let userId = $("#userId").val();
    let memberIds = '';
    rows.each((index,row)=>{
        memberIds += row.getAttribute('data-member-id')+",";
    })


    if(groupName != ''){
        if(rows.length>1){
            memberIds = memberIds.slice(0,-1);

            if(confirm("Are you sure to Edit")){
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './editGroup',
                    data: {
                        group : JSON.stringify({
                            groupId : groupId,
                            groupName: groupName,
                            members : memberIds,
                            userId : userId
                        })
                    },
                    success: function(data){
                        if(data.result == "success"){
                            alert("Group Save Successful");
                            $("#groupList").empty();
                            drawGroupList(data.groupList);
                        }
                    },
                    error: function(jqXHR, textStatus,errorThrown){
    
                    }
                });
            }
            
        }else{
            alert("Please Select More Than One Member Name...");
        }
    }else{
        alert("Please Enter Group Name")
    }
}

function refreshAction(){
    location.reload();
}

function setGroup(groupId){
    $("#loader").show();
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
            $("#loader").hide();
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
        rows += `<tr id='mRow-${member.autoId}' data-row-type='oldRow' data-member-id='${member.memberId}'>
        <td>${i+1}</td>
        <td>${member.memberName}</td>
        <td><i class="fa fa-trash" onclick="deleteMember('${member.autoId}')" style="cursor : pointer;"> </i></td>
    </tr>`;
    }

    $("#memberList").append(rows);
}

function drawGroupList(data){
    let rows = '';
    for(let i=0;i<data.length;i++){
        let group = data[i];
        rows += `<tr>
        <td>${i+1}</td>
        <td id='groupName${group.groupId}'>${group.groupName}</td>
        <td><i class="fa fa-edit" style="cursor: pointer;"
            onclick="setGroup('${group.groupId}')"> </i></td>
    </tr>`;
    }

    $("#groupList").append(rows);
}



