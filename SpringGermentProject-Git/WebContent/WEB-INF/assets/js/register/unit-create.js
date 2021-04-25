

window.onload = ()=>{
	document.title = "Unit Create";
} 

function deleteUnit(itemId){
    if(confirm("Are you sure to delete this Item?")){
    	
    	 var userId = $("#userId").val();
    	 var linkName=$('#linkName').val();
    	  
        $("#loader").show();
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './deleteUnit',
          data:{
        	  itemId:itemId,
        	  userId:userId,
        	  linkName:linkName
          },
          success: function (data) {
            if (data.result == "Something Wrong") {
              dangerAlert("Something went wrong");
            }
            else if(data.result =="You have no permission to delete this item"){
            	successAlert("You have no permission to delete this item");
            }
            else {
              
              $("#dataList").empty();
              $("#dataList").append(drawDataTable(data.result));
  
            }
            $("#loader").hide();
          }
        });
      }
}

function saveAction() {
  let unitName = $("#unitName").val().trim();
  let unitValue = $("#unitValue").val().trim();
  let userId = $("#userId").val();

  if (unitName != '') {
    $("#loader").show();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveUnit',
      data: {
        unitId: "0",
        unitName: unitName,
        unitValue: unitValue,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Unit Name..This Unit Name Allreary Exist")
        } else {
          successAlert("Unit Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));
        }
        $("#loader").hide();
      }
    });
  } else {
    warningAlert("Empty Unit Name... Please Enter Unit Name");
  }
}


function editAction() {
  let unitId = $("#unitId").val();
  let unitName = $("#unitName").val().trim();
  let unitValue = $("#unitValue").val().trim();
  let userId = $("#userId").val();

  if (unitName != '') {
    $("#loader").show();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editUnit',
      data: {
        unitId: unitId,
        unitName: unitName,
        unitValue: unitValue,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Unit Name..This Unit Name Allreary Exist")
        } else {
          successAlert("Unit Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));
        }
        $("#loader").hide();
      }
    });
  } else {
    warningAlert("Empty Unit Name... Please Enter Unit Name");
  }
}


function refreshAction() {
  location.reload();
  /*let element = $(".alert");
  element.hide();
  document.getElementById("unitId").value = "0";
  document.getElementById("unitName").value = "";
  document.getElementById("unitValue").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(unitId) {


  document.getElementById("unitId").value = unitId;
  document.getElementById("unitName").value = document.getElementById("unitName" + unitId).innerHTML;
  document.getElementById("unitValue").value = document.getElementById("unitValue" + unitId).innerHTML;
  $("#btnSave").hide();
  $("#btnEdit").show();

}

function drawDataTable(data) {
  let rows = [];
  let length = data.length;

  for (let i = 0; i < length; i++) {
    rows.push(drawRowDataTable(data[i], i+1));
  }

  return rows;
}

function drawRowDataTable(rowData, c) {

  let row = $("<tr />")
  row.append($("<td>" + c + "</td>"));
  row.append($("<td id='unitName" + rowData.unitId + "'>" + rowData.unitName + "</td>"));
  row.append($("<td id='unitValue" + rowData.unitId + "'>" + rowData.unitValue + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.unitId + ")\"> </i></td>"));
  row.append($("<td ><i class='fa fa-trash' onclick=\"deleteUnit(" + rowData.unitId + ")\"> </i></td>"));

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
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> "+message+"..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function dangerAlert(message) {
  let element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> "+message+"..";
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

$('.inputs').keyup(function (e) {
	if (e.which === 13) {
		var index = $('.inputs').index(this) + 1;
		$('.inputs').eq(index).focus();
	}
});
