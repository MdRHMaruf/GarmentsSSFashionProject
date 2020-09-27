
var sizeGroup = [];
function loadData() {

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

window.onload = loadData;
function saveAction() {
  var sizeGroupId = $("#sizeGroupId").val().trim();
  var sizeGrouName = $("#sizeGroupName").val().trim();
  var sizeName = $("#sizeName").val().trim();
  var sorting = $("#sorting").val().trim() == "" ? "0" : $("#sorting").val().trim();
  var userId = $("#userId").val();

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
  var sizeId = $("#sizeId").val().trim();
  var sizeGroupId = $("#sizeGroupId").val().trim();
  var sizeGrouName = $("#sizeGroupName").val().trim();
  var sizeName = $("#sizeName").val().trim();
  var sorting = $("#sorting").val().trim() == "" ? "0" : $("#sorting").val().trim();
  var userId = $("#userId").val();

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

  var groupName = $("#groupName").val().trim();
  var userId = $("#userId").val();

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
  var groupId = $("#groupId").val().trim();
  var groupName = $("#groupName").val().trim();
  var userId = $("#userId").val();

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
            document.getElementById("btnGroupSave").disabled = false;
            document.getElementById("btnGroupEdit").disabled = true;

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


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("lineId").value = "0";
  document.getElementById("factoryName").value = "";
  document.getElementById("lineName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}

function loadDepartmentByFactory() {
  var factoryId = $("#factoryName").val().trim();

  var length = departmentsByFactoryId['factId' + factoryId].length;
  var options = "<option value='0'>Select Department</option>";

  for (var i = 0; i < length; i++) {
    options += "<option value='" + departmentsByFactoryId['factId' + factoryId][i].departmentId + "'>" + departmentsByFactoryId['factId' + factoryId][i].departmentName + "</option>"
  }

  document.getElementById("departmentName").innerHTML = options;
}

function setData(sizeId, groupId) {


  document.getElementById("sizeId").value = sizeId;
  document.getElementById("sizeGroupId").value = groupId;
  document.getElementById("sizeGroupName").value = document.getElementById("sizeGroup" + sizeId).innerHTML;
  document.getElementById("sizeName").value = document.getElementById("sizeName" + sizeId).innerHTML;
  document.getElementById("btnSave").disabled = true;
  document.getElementById("btnEdit").disabled = false;

}

function setGroupData(groupId) {
  document.getElementById("groupId").value = groupId;
  document.getElementById("groupName").value = document.getElementById("groupName" + groupId).innerHTML;
  document.getElementById("btnGroupSave").disabled = true;
  document.getElementById("btnGroupEdit").disabled = false;
}

function drawDataTable(data) {
  var rows = [];
  var length = data.length;

  for (var i = 0; i < length; i++) {
    rows.push(drawRowDataTable(data[i], i));
  }

  return rows;
}

function drawRowDataTable(rowData, c) {

  var row = $("<tr />")
  row.append($("<td>" + c + "</td>"));
  row.append($("<td id='sizeGroup" + rowData.sizeId + "'>" + rowData.groupName + "</td>"));
  row.append($("<td id='sizeName" + rowData.sizeId + "'>" + rowData.sizeName + "</td>"));
  row.append($("<td >" + rowData.sorting + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.sizeId + "," + rowData.groupId + ")\"> </i></td>"));

  return row;
}

function drawGroupTable(data) {
  var rows = [];
  var length = data.length;

  for (var i = 0; i < length; i++) {
    rows.push(drawRowGroupTable(data[i], i));
  }

  return rows;
}

function drawRowGroupTable(rowData, c) {

  var row = $("<tr />")
  row.append($("<td id='groupName" + rowData.groupId + "'>" + rowData.groupName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setGroupData(" + rowData.groupId + ")\"> </i></td>"));
  return row;
}

function successAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-success");
  document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
  element.show();
}

function warningAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-warning");
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
  element.show();
}

function dangerAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
  element.show();
}

$(document).ready(function () {
  $("input:text").focus(function () { $(this).select(); });
});

$(document).ready(function () {
  $("#search").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#dataList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

$(document).ready(function () {
  $("#groupName").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#groupTableList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

function setSizeGroupId(groupId) {
  document.getElementById("sizeGroupId").value = groupId;
}

