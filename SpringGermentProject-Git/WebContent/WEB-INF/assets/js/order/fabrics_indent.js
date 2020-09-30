var styleIdForSet = 0;
var itemIdForSet = 0;
var itemColorIdForSet = 0;
var isFind = false;

function poWiseStyleLoad() {
  var purchaseOrder = $("#purchaseOrder option:selected").text();

  if (purchaseOrder != "") {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getPOWiseStyleLoad',
      data: {
        purchaseOrder: purchaseOrder
      },
      success: function (data) {

        var styleList = data.styleList;
        var options = "<option id='styleNo' value='0' selected>Select Style No</option>";
        var length = styleList.length;
        for (var i = 0; i < length; i++) {
          options += "<option id='styleNo' value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
        };
        document.getElementById("styleNo").innerHTML = options;
        $('.selectpicker').selectpicker('refresh');
        $('#styleNo').val(styleIdForSet).change();
        styleIdForSet = 0;
      }
    });
  } else {
    var options = "<option id='styleNo' value='0' selected>Select Style No</option>";
    $("#styleNo").html(options);
    $('#styleNo').selectpicker('refresh');
    $('#styleNo').val(0).change();
    styleIdForSet = 0;
  }
}

function styleWiseItemLoad() {
  var styleId = $("#styleNo").val();

  if (styleId != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getStyleWiseItem',
      data: {
        styleId: styleId
      },
      success: function (data) {

        var itemList = data.itemList;
        var options = "<option id='itemName' value='0' selected>Select Item Name</option>";
        var length = itemList.length;
        for (var i = 0; i < length; i++) {
          options += "<option id='itemName' value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
        };
        document.getElementById("itemName").innerHTML = options;
        $('.selectpicker').selectpicker('refresh');
        $('#itemName').val(itemIdForSet).change();
        itemIdForSet = 0;
      }
    });
  } else {
    var options = "<option id='itemName' value='0' selected>Select Item Name</option>";
    $("#itemName").html(options);
    $('#itemName').selectpicker('refresh');
    $('#itemName').val(0).change();
    itemIdForSet = 0;
  }

}

function styleItemWiseColorLoad() {
  var styleId = $("#styleNo").val();
  var itemId = $("#itemName").val();

  if (styleId != 0 && itemId != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getStyleItemWiseColor',
      data: {
        styleId: styleId,
        itemId: itemId
      },
      success: function (data) {

        var colorList = data.colorList;
        var options = "<option id='itemColor' value='0' selected>Select Item Color</option>";
        var length = colorList.length;
        for (var i = 0; i < length; i++) {
          options += "<option id='itemColor' value='" + colorList[i].colorId + "'>" + colorList[i].colorName + "</option>";
        };
        document.getElementById("itemColor").innerHTML = options;
        $('.selectpicker').selectpicker('refresh');
        $('#itemColor').val(itemColorIdForSet).change();
        itemColorIdForSet = 0;
      }
    });
  } else {
    var options = "<option id='itemColor' value='0' selected>Select Item Color</option>";
    $("#itemColor").html(options);
    $('#itemColor').selectpicker('refresh');
    $('#itemColor').val(0).change();
    itemColorIdForSet = 0;
  }
}

function setOrderQtyByPOStyleItemColor() {
  var colorId = $("#itemColor").val();
  if (colorId != 0) {
    var purchaseOrder = $("#purchaseOrder option:selected").text();
    var styleId = $("#styleNo").val();
    var itemId = $("#itemName").val();

    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getOrderQtyByPOStyleItemAndColor',
      data: {
        purchaseOrder: purchaseOrder,
        styleId: styleId,
        itemId: itemId,
        colorId: colorId
      },
      success: function (data) {
        $("#quantity").val(data.orderQuantity);
        $("#dozen").val(data.dozenQuantity.toFixed(2));

      }
    });
  } else {
    $("#quantity").val("0");
    $("#dozen").val("0");
  }
  totalQuantityCalculate();
}

function totalQuantityCalculate() {
  var dozenQuantity = $("#dozen").val() == "" ? 0 : Number($("#dozen").val());
  var consumption = $("#consumption").val() == "" ? 0 : Number($("#consumption").val());
  var inPercent = $("#inPercent").val() == "" ? 0 : Number($("#inPercent").val());

  let totalQuantity = dozenQuantity * consumption;
  var percentQuantity = (totalQuantity * inPercent) / 100;

  totalQuantity += percentQuantity;

  $("#percentQuantity").val(percentQuantity.toFixed(2));
  $("#total").val(totalQuantity.toFixed(2));
}

function saveAction() {

  var autoId = $("#fabricsIndentAutoId").val();

  var buyerOrderId = $("#purchaseOrder").val();
  
  var purchaseOrder = $("#purchaseOrder option:selected").text();

  
  
  var styleId = $("#styleNo").val();
  var itemId = $("#itemName").val();
  var itemColorId = $("#itemColor").val();
  var fabricsId = $("#fabricsItem").val();
  var consumption = $("#consumption").val() == "" ? "0" : $("#consumption").val();
  var quantity = $("#quantity").val() == "" ? 0 : $("#quantity").val();
  var dozenQuantity = $("#dozen").val() == "" ? 0 : $("#dozen").val();
  var inPercent = $("#inPercent").val() == "" ? 0 : $("#inPercent").val();
  var percentQuantity = $("#percentQuantity").val() == "" ? 0 : $("#percentQuantity").val();
  var totalQuantity = $("#total").val() == "" ? 0 : $("#total").val();
  var unitId = $("#unit").val();
  var width = $("#width").val() == "" ? 0 : $("#width").val();
  var yard = $("#yard").val() == "" ? "0" : $("#yard").val();
  var gsm = $("#gsm").val() == "" ? "0" : $("#gsm").val();
  var grandQuantity = $("#grandQuantity").val() == "" ? "0" : $("#grandQuantity").val();
  var fabricsColorId = $("#fabricsColor").val();
  var brandId = $("#brand").val();
  var userId = $("#userId").val();



  if (styleId != 0) {
    if (itemId != 0) {
      if (itemColorId != 0) {
        if (fabricsId != 0) {
          if (quantity != 0) {
            if (dozen != 0) {
              if (consumption != 0) {
                if(confirm("Are you Sure to Save this Fabrics Indent")){
                  $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './saveFabricsIndent',
                    data: {
                      autoId  : autoId,
                      purchaseOrder : purchaseOrder,
                      buyerOrderId:buyerOrderId,
                      styleId : styleId,
                      itemId  : itemId,
                      itemColorId : itemColorId,
                      fabricsId : fabricsId,
                      qty: quantity,
                      dozenQty : dozenQuantity,
                      consumption: consumption,
                      inPercent : inPercent,
                      percentQty : percentQuantity,
                      totalQty : totalQuantity,
                      unitId : unitId,
                      width : width,
                      yard  : yard,
                      gsm : gsm,
                      grandQty  : grandQuantity,
                      fabricsColorId  : fabricsColorId,
                      brandId: brandId,
                      userId: userId
                    },
                    success: function (data) {
                      if (data.result == "Something Wrong") {
                        dangerAlert("Something went wrong");
                      } else if (data.result == "duplicate") {
                        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                      } else {
                        
                        $("#dataList").empty();
                        $("#dataList").append(drawDataTable(data.result));
                        successAlert("Fabrics Indent Item Save Successfully");
                      }
                    }
                  });
  
                }
              } else {
            	  alert("Consumption is empty ... Please Enter Consumption");
                $("#consumption").focus();
              }
            } else {
            	alert("Dozen Quantity is empty ... Please Enter dozen quantity");
              $("#dozen").focus();
            }

          } else {
        	  alert("Quantity is empty ... Please Enter Quantity");
            $("#quantity").focus();
          }
        } else {
        	alert("Fabrics item Not Selected... Please Select any Fabrics item");
          $("#fabricsItem").focus();
        }
      } else {
    	  alert("Color not selected... Please Select Color Name");
        $("#itemColor").focus();
      }
    } else {
    	alert("Item Name not selected... Please Select Item Name");
      $("#itemName").focus();
    }
  } else {
	  alert("Style No not selected... Please Select Style No");
    $("#styleNo").focus();
  }

}


function editAction() {

  var autoId = $("#fabricsIndentAutoId").val();

  var purchaseOrder = $("#purchaseOrder option:selected").text();
  var styleId = $("#styleNo").val();
  var itemId = $("#itemName").val();
  var itemColorId = $("#itemColor").val();
  var fabricsId = $("#fabricsItem").val();
  var consumption = $("#consumption").val() == "" ? "0" : $("#consumption").val();
  var quantity = $("#quantity").val() == "" ? 0 : $("#quantity").val();
  var dozenQuantity = $("#dozen").val() == "" ? 0 : $("#dozen").val();
  var inPercent = $("#inPercent").val() == "" ? 0 : $("#inPercent").val();
  var percentQuantity = $("#percentQuantity").val() == "" ? 0 : $("#percentQuantity").val();
  var totalQuantity = $("#total").val() == "" ? 0 : $("#total").val();
  var unitId = $("#unit").val();
  var width = $("#width").val() == "" ? 0 : $("#width").val();
  var yard = $("#yard").val() == "" ? "0" : $("#yard").val();
  var gsm = $("#gsm").val() == "" ? "0" : $("#gsm").val();
  var grandQuantity = $("#grandQuantity").val() == "" ? "0" : $("#grandQuantity").val();
  var fabricsColorId = $("#fabricsColor").val();
  var brandId = $("#brand").val();
  var userId = $("#userId").val();



  if (styleId != 0) {
    if (itemId != 0) {
      if (itemColorId != 0) {
        if (fabricsId != 0) {
          if (quantity != 0) {
            if (dozen != 0) {
              if (consumption != 0) {
                if(confirm("Are you Sure to Edit this Fabrics Indent")){
                  $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './editFabricsIndent',
                    data: {
                      autoId  : autoId,
                      purchaseOrder : purchaseOrder,
                      styleId : styleId,
                      itemId  : itemId,
                      itemColorId : itemColorId,
                      fabricsId : fabricsId,
                      qty: quantity,
                      dozenQty : dozenQuantity,
                      consumption: consumption,
                      inPercent : inPercent,
                      percentQty : percentQuantity,
                      totalQty : totalQuantity,
                      unitId : unitId,
                      width : width,
                      yard  : yard,
                      gsm : gsm,
                      grandQty  : grandQuantity,
                      fabricsColorId  : fabricsColorId,
                      brandId: brandId,
                      userId: userId
                    },
                    success: function (data) {
                      if (data.result == "Something Wrong") {
                        dangerAlert("Something went wrong");
                      } else if (data.result == "duplicate") {
                        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                      } else {
                        
                        $("#dataList").empty();
                        $("#dataList").append(drawDataTable(data.result));
                        successAlert("Costing Item Save Successfully");
                        $("#btnSave").prop("disabled", false);
                        $("#btnEdit").prop("disabled", true);
                      }
                    }
                  });
  
                }
              } else {
            	  alert("Consumption is empty ... Please Enter Consumption");
                $("#consumption").focus();
              }
            } else {
            	alert("Dozen Quantity is empty ... Please Enter dozen quantity");
              $("#dozen").focus();
            }

          } else {
        	  alert("Quantity is empty ... Please Enter Quantity");
            $("#quantity").focus();
          }
        } else {
          warningAlert("Fabrics item Not Selected... Please Select any Fabrics item");
          $("#fabricsItem").focus();
        }
      } else {
    	  alert("Color not selected... Please Select Color Name");
        $("#itemColor").focus();
      }
    } else {
    	alert("Item Name not selected... Please Select Item Name");
      $("#itemName").focus();
    }
  } else {
    alert("Style No not selected... Please Select Style No");
    $("#styleNo").focus();
  }
}

function refreshAction() {
  location.reload();

}


function unitChangeAction() {
  var unit = $("#unit option:selected").text();
  var unitId = $("#unit").val();
  if(!isFind){
    if (unit.toLowerCase() == "kg") {
      $("#gsm").val("0");
      $("#yard").val("0");
      $("#width").val("0");
      $("#yard").prop("disabled", false);
      $("#gsm").prop("disabled", false);
      $("#width").prop("disabled", false);
    } else {
      $("#gsm").val("0");
      $("#yard").val("0");
      $("#width").val("0");
      $("#yard").prop("disabled", true);
      $("#gsm").prop("disabled", true);
      $("#width").prop("disabled", true);
    }
  
    if (unitId != "0") {
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getUnitValue',
        data: {
          unitId: unitId
        },
        success: function (data) {
          var totalQuantity = $("#total").val() == "" ? 0 : $("#total").val();
          var unitValue = isNaN(data.unitValue) ? 0 : data.unitValue;
          var grandQty = totalQuantity / unitValue;
          $("#grandQuantity").val(grandQty.toFixed(2));
        }
      });
    }
  }
  isFind = false;
}

function gsmCalculation() {
  var width = $("#width").val() == "" ? 0 : $("#width").val();
  var yard = $("#yard").val() == "" ? 0 : $("#yard").val();
  var gsm = $("#gsm").val() == "" ? 0 : $("#gsm").val();
  var grandQty = (width * yard * gsm * 36) / 1550000;
  $("#grandQuantity").val(grandQty.toFixed(2));
}

function viewFabricsIndent(autoId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsIndent',
    data: {
      autoId: autoId
    },
    success: function (data) {
      if (data.fabricsIndent == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.fabricsIndent == "duplicate") {
        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
      } else {
        isFind = true;
        var indent = data.fabricsIndent;
        styleIdForSet = indent.styleId;
        itemIdForSet = indent.itemId;
        itemColorIdForSet = indent.itemColorId;
        $("#fabricsIndentAutoId").val(indent.autoId);
        $("#fabricsItem").val(indent.fabricsId).change();
        $("#consumption").val(indent.consumption);
        $("#quantity").val(indent.qty);
        $("#dozen").val(indent.dozenQty);
        $("#inPercent").val(indent.inPercent);
        $("#percentQuantity").val(indent.percentQty);
        $("#total").val(indent.totalQty);
        $("#unit").val(indent.unitId).change();
        $("#width").val(indent.width);
        $("#yard").val(indent.yard);
        $("#gsm").val(indent.gsm);
        $("#grandQuantity").val(indent.grandQty.toFixed(2));
        $("#fabricsColor").val(indent.fabricsColorId).change();
        $("#brand").val(indent.brandId).change();
        $("#purchaseOrder").val(indent.purchaseOrder).change();
        $("#btnSave").prop("disabled", true);
        $("#btnEdit").prop("disabled", false);
      }
    }
  });
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
  row.append($("<td>" + rowData.purchaseOrder + "</td>"));
  row.append($("<td>" + rowData.styleName + "</td>"));
  row.append($("<td>" + rowData.itemName + "</td>"));
  row.append($("<td>" + rowData.itemColorName + "</td>"));
  row.append($("<td>" + rowData.fabricsName + "</td>"));
  row.append($("<td>" + rowData.dozenQty + "</td>"));
  row.append($("<td>" + rowData.consumption + "</td>"));
  row.append($("<td>" + rowData.percentQty + "</td>"));
  row.append($("<td>" + rowData.totalQty + "</td>"));
  row.append($("<td>" + rowData.unit + "</td>"));
  row.append($("<td ><i class='fa fa-info-circle' onclick='viewFabricsIndent(" + rowData.autoId + ")' style='cursor:pointer;'> </i></td>"));


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
  $("input").focus(function () { $(this).select(); });
});
$(document).ready(function () {
  $("#search").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#dataList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});


function FabricIndentReport(poID, Styleid, itemid) {
	
	console.log(" po "+poID)
	console.log(" style "+Styleid)
	console.log(" itemid "+itemid)
	
	
	  $.ajax({
	    type: 'POST',
	    dataType: 'json',
	    url: './fabricsIndentReport/'+poID+'/'+Styleid+'/'+itemid,
	    data: {
	    	
	    },
	    success: function (data) {
	    	if(data=='yes'){
				var url = "fabricsIndentReportView";
        		window.open(url, '_blank');
			}
	    }
	  });
	}


