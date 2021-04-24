


window.onload = ()=>{
	document.title = "Color Create";
} 

function deleteColorCreate(itemId){
    if(confirm("Are you sure to delete this Item?")){
    	
   	 var userId = $("#userId").val();
   	 var linkName=$('#linkName').val();
   	  
       $("#loader").show();
       $.ajax({
         type: 'POST',
         dataType: 'json',
         url: './deleteColorCreate',
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
  let colorName = $("#colorName").val().trim();
  let colorCode = $("#colorCode").val().trim();
  let userId = $("#userId").val();

  if (colorName != '') {
    $("#loader").show();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveColor',
      data: {
        colorId: "0",
        colorName: colorName,
        colorCode: colorCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Color Name..This Color Name Allreary Exist")
        } else {
          successAlert("Color Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
        $("#loader").hide();
      }
    });
  } else {
    warningAlert("Empty Color Name... Please Enter Color Name");
  }
}


function editAction() {
  let colorId = $("#colorId").val();
  let colorName = $("#colorName").val().trim();
  let colorCode = $("#colorCode").val().trim();
  let userId = $("#userId").val();

  if (colorName != '') {
    $("#loader").show();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editColor',
      data: {
        colorId: colorId,
        colorName: colorName,
        colorCode: colorCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Color Name..This Color Name Allreary Exist")
        } else {
          successAlert("Color Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
        $("#loader").hide();
      }
    });
  } else {
    warningAlert("Empty Color Name... Please Enter Color Name");
  }
}


function refreshAction() {
  location.reload();
  /*let element = $(".alert");
  element.hide();
  document.getElementById("colorId").value = "0";
  document.getElementById("colorName").value = "";
  document.getElementById("colorCode").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(colorId) {


  document.getElementById("colorId").value = colorId;
  document.getElementById("colorName").value = document.getElementById("colorName" + colorId).innerHTML;
  document.getElementById("colorCode").value = document.getElementById("colorCode" + colorId).innerHTML;
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
  row.append($("<td id='colorName" + rowData.colorId + "'>" + rowData.colorName + "</td>"));
  row.append($("<td id='colorCode" + rowData.colorId + "'>" + rowData.colorCode + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.colorId + ")\"> </i></td>"));
  row.append($("<td ><i class='fa fa-trash' onclick=\"deleteColorCreate(" + rowData.colorId + ")\"> </i></td>"));
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
