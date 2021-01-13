
window.onload = ()=>{
  document.title = "Style Size Create";
  
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './sizeGroupLoad',
    data: {},
    success: function (obj) {
      sizeGroup = [];
      sizeGroup = obj.result;
      $("#groupTableList").empty();
      $("#groupTableList").append(drawGroupTable(sizeGroup));

      $(function () {
        $("#sizeGroupName").autocomplete({
          source: sizeGroup,
          select: function (event, ui) {
            document.getElementById("sizeGroupId").value = ui.item.id;
          }
        });
      });
    }
  });
} 
let sizeGroup = [];

function saveAction() {
  let sizeGroupId = $("#sizeGroupId").val().trim();
  let sizeGrouName = $("#sizeGroupName").val().trim();
  let sizeName = $("#sizeName").val().trim();
  let sorting = $("#sorting").val().trim() == "" ? "0" : $("#sorting").val().trim();
  let userId = $("#userId").val();

  if (sizeGroupId != '0') {
    if (sizeName != '') {
      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './saveSize',
        data: {
          sizeId: "0",
          groupId: sizeGroupId,
          groupName: sizeGrouName,
          sizeName: sizeName,
          sizeSorting: sorting,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Size Name..This Size Name Allreary Exist")
          } else {
            successAlert("Size Name Save Successfully");

            $("#dataList").empty();
            $("#dataList").append(drawDataTable(data.result));

          }
        }
      });
    } else {
      warningAlert("Empty Size Name... Please Enter Size Name");
    }
  } else {
    warningAlert("Invalid Size Group Name... Please Select a Size Group");
  }
}

function editAction() {
  let sizeId = $("#sizeId").val().trim();
  let sizeGroupId = $("#sizeGroupId").val().trim();
  let sizeGrouName = $("#sizeGroupName").val().trim();
  let sizeName = $("#sizeName").val().trim();
  let sorting = $("#sorting").val().trim() == "" ? "0" : $("#sorting").val().trim();
  let userId = $("#userId").val();

  if (sizeGroupId != '0') {
    if (sizeName != '') {

      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './editSize',
        data: {
          sizeId: sizeId,
          groupId: sizeGroupId,
          groupName: sizeGrouName,
          sizeName: sizeName,
          sizeSorting: sorting,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Size Name..This Size Name Allreary Exist")
          } else {
            successAlert("Size Name Edit Successfully");

            $("#dataList").empty();
            $("#dataList").append(drawDataTable(data.result));

          }
        }
      });
    } else {
      warningAlert("Empty Size Name... Please Enter Size Name");
    }
  } else {
    warningAlert("Invalid Size Group Name... Please Select a Size Group");
  }
}

function groupSaveAction() {

  let groupName = $("#groupName").val().trim();
  let userId = $("#userId").val();

  if (groupName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveSizeGroup',
      data: {
        groupId: "0",
        groupName: groupName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          alert("Something went wrong");
          //dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          alert("Duplicate Group Name..... \nThis Group Name Already Exist..");
          //dangerAlert("Duplicate Group Name..This Group Name Allreary Exist")
        } else {
          alert("Group Name Save Successfully...")
          //successAlert("Group Name Save Successfully");

          $("#groupTableList").empty();
          $("#groupTableList").append(drawGroupTable(data.result));

        }
      }
    });
  } else {
    //warningAlert("Empty Group Name... Please Enter a Group Name");
    alert("Empty Group Name ... please Enter a group name")
  }
}


function groupEditAction() {
  let groupId = $("#groupId").val().trim();
  let groupName = $("#groupName").val().trim();
  let userId = $("#userId").val();

  if (groupId != '0') {
    if (groupName != '0') {
      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './editSizeGroup',
        data: {
          groupId: groupId,
          groupName: groupName,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            alert("Something went wrong");
            //dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            alert("Duplicate Group Name..... \nThis Group Name Already Exist..");
            //dangerAlert("Duplicate Group Name..This Group Name Allreary Exist")
          } else {
            alert("Group Name Edit Successfully...")
            //successAlert("Group Name Save Successfully");

            $("#groupTableList").empty();
            $("#groupTableList").append(drawGroupTable(data.result));
            $("#btnGroupSave").show();
  $("#btnGroupEdit").hide();

          }
        }
      });
    } else {
      warningAlert("Empty Group Name... Please Enter a Group Name");
    }
  } else {
    warningAlert("Please Select a Group Name");
  }
}

function groupModalCloseAction(){
  $("groupId").val("");
  $("#btnGroupSave").show();
  $("#btnGroupEdit").hide();
}

function refreshAction() {
  location.reload();
}

function loadDepartmentByFactory() {
  let factoryId = $("#factoryName").val().trim();

  let length = departmentsByFactoryId['factId' + factoryId].length;
  let options = "<option value='0'>Select Department</option>";

  for (let i = 0; i < length; i++) {
    options += "<option value='" + departmentsByFactoryId['factId' + factoryId][i].departmentId + "'>" + departmentsByFactoryId['factId' + factoryId][i].departmentName + "</option>"
  }
  document.getElementById("departmentName").innerHTML = options;
}

function setData(sizeId, groupId) {

  document.getElementById("sizeId").value = sizeId;
  document.getElementById("sizeGroupId").value = groupId;
  document.getElementById("sizeGroupName").value = document.getElementById("sizeGroup" + sizeId).innerHTML;
  document.getElementById("sizeName").value = document.getElementById("sizeName" + sizeId).innerHTML;
  document.getElementById("sorting").value = document.getElementById("sizeSorting" + sizeId).innerHTML;
  $("#btnSave").hide();
  $("#btnEdit").show();

}

function setGroupData(groupId) {
  document.getElementById("groupId").value = groupId;
  document.getElementById("groupName").value = document.getElementById("groupName" + groupId).innerHTML;
  $("#btnGroupSave").hide();
  $("#btnGroupEdit").show();
}

function drawDataTable(data) {
  let rows = [];
  let length = data.length;

  for (let i = 0; i < length; i++) {
    rows.push(drawRowDataTable(data[i], i));
  }

  return rows;
}

function drawRowDataTable(rowData, c) {

  let row = $("<tr />")
  row.append($("<td>" + c + "</td>"));
  row.append($("<td id='sizeGroup" + rowData.sizeId + "'>" + rowData.groupName + "</td>"));
  row.append($("<td id='sizeName" + rowData.sizeId + "'>" + rowData.sizeName + "</td>"));
  row.append($("<td >" + rowData.sorting + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.sizeId + "," + rowData.groupId + ")\"> </i></td>"));

  return row;
}

function drawGroupTable(data) {
  let rows = [];
  let length = data.length;

  for (let i = 0; i < length; i++) {
    rows.push(drawRowGroupTable(data[i], i));
  }

  return rows;
}

function drawRowGroupTable(rowData, c) {

  let row = $("<tr />")
  row.append($("<td id='groupName" + rowData.groupId + "'>" + rowData.groupName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setGroupData(" + rowData.groupId + ")\"> </i></td>"));
  return row;
}

function successAlert(message) {
  let element = $(".alert");
  element.hide();
  element = $(".alert-success");
  document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function warningAlert(message) {
  let element = $(".alert");
  element.hide();
  element = $(".alert-warning");
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function dangerAlert(message) {
  let element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

$(document).ready(function () {
  $("input:text").focus(function () { $(this).select(); });
});

$(document).ready(function () {
  $("#search").on("keyup", function () {
    let value = $(this).val().toLowerCase();
    $("#dataList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

$(document).ready(function () {
  $("#groupName").on("keyup", function () {
    let value = $(this).val().toLowerCase();
    $("#groupTableList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

function setSizeGroupId(groupId) {
  document.getElementById("sizeGroupId").value = groupId;
}

