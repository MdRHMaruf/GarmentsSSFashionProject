
window.onload = ()=>{
	document.title = "Fabrics Create";	
} 


function saveAction() {
  const fabricsItemName = $("#fabricsItemName").val();
  const reference = $("#reference").val();
  const unitId = $("#unit").val();
  const userId = $("#userId").val();

  if (fabricsItemName != '') {
    if (unitId != '0') {
      if(confirm("Are you sure to Save this Item?")){
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './saveFabricsItem',
          data: {
            fabricsItemId: "0",
            fabricsItemName: fabricsItemName,
            reference: reference,
            unitId: unitId,
            userId: userId
          },
          success: function (data) {
            if (data.result == "Something Wrong") {
              dangerAlert("Something went wrong");
            } else if (data.result == "duplicate") {
              dangerAlert("Duplicate Fabrics Item Name..This Fabrics Item Name Allreary Exist")
            } else {
              successAlert("Fabrics Item Name Save Successfully");
  
              $("#dataList").empty();
              $("#dataList").append(drawDataTable(data.result));
  
            }
          }
        });
      }
      
    } else {
      warningAlert("Unit Not Selected... Please Select Unit");
      $("#unit").focus();
    }
  } else {
    warningAlert("Empty Fabrics Item Name... Please Enter Fabrics Item Name");
    $("#fabricsItemName").focus();
  }
}


function editAction() {
  const fabricsItemId = $("#fabricsItemId").val();
  const fabricsItemName = $("#fabricsItemName").val();
  const reference = $("#reference").val();
  const unitId = $("#unit").val();
  const userId = $("#userId").val();

  if (fabricsItemName != '') {
    if (unitId != '0') {
      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './editFabricsItem',
        data: {
          fabricsItemId: fabricsItemId,
          fabricsItemName: fabricsItemName,
          reference: reference,
          unitId: unitId,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Fabrics Item Name..This Fabrics Item Name Allreary Exist")
          } else {
            successAlert("Fabrics Item Name Edit Successfully");

            $("#dataList").empty();
            $("#dataList").append(drawDataTable(data.result));

          }
        }
      });
    } else {
      warningAlert("Unit Not Selected... Please Select Unit");
      $("#unit").focus();
    }
  } else {
    warningAlert("Empty Fabrics Item Name... Please Enter Fabrics Item Name");
    $("#fabricsItemName").focus();
  }
}


function refreshAction() {
  location.reload();
  /*let element = $(".alert");
  element.hide();
  document.getElementById("fabricsItemId").value = "0";
  document.getElementById("fabricsItemName").value = "";
  document.getElementById("reference").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}

function unitAddAction() {
  const unitQty = $("#unitQty").val();
  const fabricsItemId = $("#fabricsItemId").val();
  const itemType = $("#itemType").val();
  const unitId = $("#unit").val();
  const userId = $("#userId").val();

  if (fabricsItemId != '') {
    if (unitId != '0') {
      if (unitQty != '' && Number(unitQty) > 0) {
        if(confirm("Are you sure to Add this Unit with Quantity '"+ unitQty +"'?")){
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './addItemUnits',
            data: {
              unitId : unitId,
              unitQty : unitQty,
              userId : userId,
              itemId : fabricsItemId,
              itemType : itemType
            },
            success: function (data) {
              if (data.result == "Something Wrong") {
                dangerAlert("Something went wrong");
              } else if (data.result == "duplicate") {
                dangerAlert("Duplicate Fabrics Item Name..This Fabrics Item Name Allreary Exist")
              } else {
                drawUnitTable(data.result);
              }
            }
          });
        }
        
      } else {
        warningAlert("Invalid Minimum Qty... Please Enter Valid Minimum Qty");
        $("#unitQty").focus();
      }
    }
    else {
      warningAlert("Unit Not Selected... Please Select Unit");
      $("#unit").focus();
    }
  } else {
    warningAlert("Empty Fabrics Item Name... Please Select Fabrics Item Name");
    $("#fabricsItemName").focus();
  }

}
function setData(fabricsItemId) {


  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: './getFabricsItem',
    data: {
      fabricsItemId: fabricsItemId,
    },
    success: function (data) {
      if (data.result == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.result == "duplicate") {
        dangerAlert("Duplicate Fabrics Item Name..This Fabrics Item Name Allreary Exist")
      } else {
        const fabricsItem = data.result;
        $("#fabricsItemId").val(fabricsItem.fabricsItemId);
        $("#fabricsItemName").val(fabricsItem.fabricsItemName);
        $("#reference").val(fabricsItem.reference);
        $("#unit").val(fabricsItem.unitId).change();
        drawUnitTable(fabricsItem.unitList);
        document.getElementById("btnSave").disabled = true;
        document.getElementById("btnEdit").disabled = false;
      }
    }
  });

  // document.getElementById("fabricsItemId").value = fabricsItemId;
  // document.getElementById("fabricsItemName").value = document.getElementById("fabricsItemName" + fabricsItemId).innerHTML;
  // document.getElementById("reference").value = document.getElementById("reference" + fabricsItemId).innerHTML;


}

function drawUnitTable(unitList) {
  const length = unitList.length;
  let rowList = "";
  for (let i = 0; i < length; i++) {
    rowList += '<tr>'
      + '<td>' + unitList[i].unitName + '</td>'
      + '<td>' + unitList[i].unitQty + '</td>'
      + '</tr>';
  }

  $("#unitList").html(rowList);
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
  row.append($("<td>" + rowData.fabricsItemId + "</td>"));
  row.append($("<td id='fabricsItemName" + rowData.fabricsItemId + "'>" + rowData.fabricsItemName + "</td>"));
  row.append($("<td id='reference" + rowData.fabricsItemId + "'>" + rowData.reference + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.fabricsItemId + ")\" style='cursor:pointer'> </i></td>"));

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

