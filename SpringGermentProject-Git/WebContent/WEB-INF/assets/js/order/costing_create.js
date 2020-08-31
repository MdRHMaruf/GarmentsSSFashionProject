var itemIdForSet = 0;
var particularItemIdForSet = 0;
function styleWiseItemLoad() {
  var styleId = $("#styleName").val();

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

function typeWiseParticularLoad() {
  var type = $("#particularType").val();

  if (type != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './typeWiseParticularLoad',
      data: {
        type: type
      },
      success: function (data) {

        var particularList = data.particularList;
        var options = "<option id='particularName' value='0' selected>Select Particular Name</option>";
        var length = particularList.length;
        for (var i = 0; i < length; i++) {
          options += "<option id='particularName' value='" + particularList[i].particularItemId + "'>" + particularList[i].particularItemName + "</option>";
        };
        document.getElementById("particularName").innerHTML = options;
        $('.selectpicker').selectpicker('refresh');
        $('#particularName').val(particularItemIdForSet).change();
        particularItemIdForSet = 0;
      }
    });
  } else {
    var options = "<option id='particularName' value='0' selected>Select Particular Name</option>";
    $("#particularName").html(options);
    $('#particularName').selectpicker('refresh');
    $('#particularName').val(0).change();
  }

}

function cloneButtonAction() {
  var styleId = $("#styleName").val();
  var itemId = $("#itemName").val();
  if (styleId != 0) {
    if (itemId != 0) {
      $('#cloneModal').modal('show');
      var element = $(".alert");
      element.hide();
    } else {
      warningAlert("Item Type not selected... Please Select Item Type");
      $("#itemName").focus();
    }
  } else {
    warningAlert("Style No not selected... Please Select Style No");
    $("#styleName").focus();
  }
}

function cloningCosting(oldStyleId, oldItemId) {
  var newStyleId = $("#styleName").val();
  var newItemId = $("#itemName").val();
  var userId = $("#userId").val();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './cloningCosting',
    data: {
      oldStyleId : oldStyleId,
      oldItemId : oldItemId,
      newStyleId : newStyleId,
      newItemId : newItemId,
      userId : userId
    },
    success: function (data) {

      if (data.result == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.result == "duplicate") {
        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
      } else {
        console.log(data.result);
        $("#dataList").empty();
        $("#dataList").append(drawDataTable(data.result));
        successAlert("Costing Cloning Successfully");
      }
    }
  });
}

function saveAction() {

  var autoId = $("#itemAutoId").val();
  var styleId = $("#styleName").val();
  var itemId = $("#itemName").val();
  var particularType = $("#particularType").val();
  var particularId = $("#particularName").val();
  var unitId = $("#unit").val();
  var commission = $("#commission").val() == "" ? 0 : $("#commission").val();
  var submissionDate = $("#submissionDate").val();
  var width = $("#width").val() == "" ? 0 : $("#width").val();
  var yard = $("#yard").val() == "" ? "0" : $("#yard").val();
  var gsm = $("#gsm").val() == "" ? "0" : $("#gsm").val();
  var consumption = $("#consumption").val() == "" ? "0" : $("#consumption").val();
  var unitPrice = $("#unitPrice").val() == "" ? "0" : $("#unitPrice").val();
  var amount = Number(consumption) * Number(unitPrice);
  var userId = $("#userId").val();


  if (styleId != 0) {
    if (itemId != 0) {
      if (particularId != 0) {
        if (unitId != 0) {
          if (commission != 0) {
            if (consumption != 0) {
              if (unitPrice != 0) {
                $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: './saveCosting',
                  data: {
                    autoId: autoId,
                    styleId: styleId,
                    itemId: itemId,
                    particularType: particularType,
                    particularId: particularId,
                    size: "",
                    unitId: unitId,
                    width: width,
                    yard: yard,
                    gsm: gsm,
                    consumption: consumption,
                    unitPrice: unitPrice,
                    amount: amount,
                    commission: commission,
                    date: submissionDate,
                    userId: userId
                  },
                  success: function (data) {
                    if (data.result == "Something Wrong") {
                      dangerAlert("Something went wrong");
                    } else if (data.result == "duplicate") {
                      dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                    } else {
                      console.log(data.result);
                      $("#dataList").empty();
                      $("#dataList").append(drawDataTable(data.result));
                      successAlert("Costing Item Save Successfully");
                    }
                  }
                });
              } else {
                warningAlert("Unit Price is empty ... Please Enter Unit Price");
                $("#unitPrice").focus();
              }
            } else {
              warningAlert("Consumption is empty ... Please Enter Consumption");
              $("#consumption").focus();
            }
          } else {
            warningAlert("Commission is empty ... Please Enter Commission");
            $("#commission").focus();
          }
        } else {
          warningAlert("Unit Not Selected... Please Select Unit");
          $("#unit").focus();
        }
      } else {
        warningAlert("Particular not selected... Please Select Particular Name");
        $("#particularName").focus();
      }
    } else {
      warningAlert("Item Type not selected... Please Select Item Type");
      $("#itemName").focus();
    }
  } else {
    warningAlert("Style No not selected... Please Select Style No");
    $("#styleName").focus();
  }

}


function editAction() {

  var autoId = $("#itemAutoId").val();
  var styleId = $("#styleName").val();
  var itemId = $("#itemName").val();
  var particularType = $("#particularType").val();
  var particularId = $("#particularName").val();
  var unitId = $("#unit").val();
  var commission = $("#commission").val() == "" ? 0 : $("#commission").val();
  var submissionDate = $("#submissionDate").val();
  var width = $("#width").val() == "" ? 0 : $("#width").val();
  var yard = $("#yard").val() == "" ? "0" : $("#yard").val();
  var gsm = $("#gsm").val() == "" ? "0" : $("#gsm").val();
  var consumption = $("#consumption").val() == "" ? "0" : $("#consumption").val();
  var unitPrice = $("#unitPrice").val() == "" ? "0" : $("#unitPrice").val();
  var amount = Number(consumption) * Number(unitPrice);
  var userId = $("#userId").val();


  if (styleId != 0) {
    if (itemId != 0) {
      if (particularId != 0) {
        if (unitId != 0) {
          if (commission != 0) {
            if (consumption != 0) {
              if (unitPrice != 0) {
                $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: './editCosting',
                  data: {
                    autoId: autoId,
                    styleId: styleId,
                    itemId: itemId,
                    particularType: particularType,
                    particularId: particularId,
                    size: "",
                    unitId: unitId,
                    width: width,
                    yard: yard,
                    gsm: gsm,
                    consumption: consumption,
                    unitPrice: unitPrice,
                    amount: amount,
                    commission: commission,
                    date: submissionDate,
                    userId: userId
                  },
                  success: function (data) {
                    if (data.result == "Something Wrong") {
                      dangerAlert("Something went wrong");
                    } else if (data.result == "duplicate") {
                      dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                    } else {
                      $("#btnSave").prop("disabled", false);
                      $("#btnEdit").prop("disabled", true);
                      $("#dataList").empty();
                      $("#dataList").append(drawDataTable(data.result));
                      successAlert("Costing Item Edit Successfully");
                    }
                  }
                });
              } else {
                warningAlert("Unit Price is empty ... Please Enter Unit Price");
                $("#unitPrice").focus();
              }
            } else {
              warningAlert("Consumption is empty ... Please Enter Consumption");
              $("#consumption").focus();
            }
          } else {
            warningAlert("Commission is empty ... Please Enter Commission");
            $("#commission").focus();
          }
        } else {
          warningAlert("Unit Not Selected... Please Select Unit");
          $("#unit").focus();
        }
      } else {
        warningAlert("Particular not selected... Please Select Particular Name");
        $("#particularName").focus();
      }
    } else {
      warningAlert("Item Type not selected... Please Select Item Type");
      $("#itemName").focus();
    }
  } else {
    warningAlert("Style No not selected... Please Select Style No");
    $("#styleName").focus();
  }

}

function refreshAction() {
  location.reload();

}


function inputSetByUnit() {
  var unit = $("#unit option:selected").text();

  if (unit.toLowerCase() == "kg") {
    $("#gsm").val("0");
    $("#yard").val("0");
    $("#yardDiv").show();
    $("#gsmDiv").show();
  } else {
    $("#yardDiv").hide();
    $("#gsmDiv").hide();
    $("#gsm").val("1");
    $("#yard").val("1");
  }
}

function searchCosting(styleId, itemId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchCosting',
    data: {
      styleId: styleId,
      itemId: itemId
    },
    success: function (data) {
      if (data.result == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.result == "duplicate") {
        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
      } else {
        $('#searchModal').modal('hide');
        var costingList = data.result;
        itemIdForSet = costingList[0].itemId;
        $("#styleName").val(costingList[0].styleId).change();
        $("#dataList").empty();
        $("#dataList").append(drawDataTable(costingList));
      }
    }
  });
}

function costingItemSet(autoId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchCostingItem',
    data: {
      autoId: autoId,
    },
    success: function (data) {
      if (data.result == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.result == "duplicate") {
        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
      } else {

        var costing = data.result;
        $("#itemAutoId").val(costing.autoId);
        itemIdForSet = costing.itemId;
        $("#styleName").val(costing.styleId).change();
        particularItemIdForSet = costing.particularId;
        $("#particularType").val(costing.particularType).change();
        $("#particularName").val(costing.particularId).change();
        $("#unit").val(costing.unitId).change();
        $("#commission").val(costing.commission);
        var date = costing.date.split("/");
        $("#submissionDate").val(date[2] + "-" + date[1] + "-" + date[0]);
        $("#width").val(costing.width);
        $("#yard").val(costing.yard);
        $("#gsm").val(costing.gsm);
        $("#consumption").val(costing.consumption);
        $("#unitPrice").val(costing.unitPrice);
        $("#btnSave").prop("disabled", true);
        $("#btnEdit").prop("disabled", false);
      }
    }
  });
}

function deleteCostingItem(autoId, styleId, itemId) {
  if (confirm("Are you sure to Delete this item?")) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './deleteCosting',
      data: {
        autoId: autoId,
        styleId: styleId,
        itemId: itemId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Item Name..This Item Name Already Exist")
        } else {

          var costingList = data.result;
          if (costingList.size > 0) {
            itemIdForSet = costingList[0].itemId;
            $("#styleName").val(costingList[0].styleId).change();
          }
          $("#dataList").empty();
          $("#dataList").append(drawDataTable(costingList));
        }
      }
    });
  }

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
  row.append($("<td>" + rowData.styleName + "</td>"));
  row.append($("<td>" + rowData.particularName + "</td>"));
  row.append($("<td>" + rowData.size + "</td>"));
  row.append($("<td>" + rowData.width + "</td>"));
  row.append($("<td>" + rowData.yard + "</td>"));
  row.append($("<td>" + rowData.gsm + "</td>"));
  row.append($("<td>" + rowData.consumption + "</td>"));
  row.append($("<td>" + rowData.unitPrice + "</td>"));
  row.append($("<td>" + rowData.amount + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"costingItemSet(" + rowData.autoId + ")\"> </i></td>"));
  row.append($("<td ><i class='fa fa-trash' onclick=\"deleteCostingItem(" + rowData.autoId + "," + rowData.styleId + "," + rowData.itemId + ")\"> </i></td>"));

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
  $("#searchCosting").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#costingListTable tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

$(document).ready(function () {
  $("#cloneCostingSearch").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#cloneCostingTable tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

var today = new Date();
document.getElementById("submissionDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);