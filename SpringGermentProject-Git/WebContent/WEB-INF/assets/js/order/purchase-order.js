let styleIdForSet = 0;
let itemIdForSet = 0;
let indentIdForSet = 0;


window.onload = ()=>{
  document.title = "Purchase Order";
}
function poWiseStyleLoad() {
  const purchaseOrder = $("#purchaseOrder").val();

  if (purchaseOrder != "0") {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getPOWiseStyleLoad',
      data: {
        purchaseOrder: purchaseOrder
      },
      success: function (data) {

        const styleList = data.styleList;
        let options = "<option id='styleNo' value='0' selected>Select Style No</option>";
        const length = styleList.length;
        for (var i = 0; i < length; i++) {
          options += "<option id='styleNo' value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
        };
        document.getElementById("styleNo").innerHTML = options;
        $('#styleNo').selectpicker('refresh');
        $('#styleNo').val(styleIdForSet).change();
        styleIdForSet = 0;
      }
    });
  } else {
    let options = "<option id='styleNo' value='0' selected>Select Style No</option>";
    $("#styleNo").html(options);
    $('#styleNo').selectpicker('refresh');
    $('#styleNo').val(0).change();
    styleIdForSet = 0;
  }
}

function typeWiseIndentItemLoad() {
  const type = $("#type").val();
  const purchaseOrder = $("#purchaseOrder").val();
  const styleId = $("#styleNo").val();
  if (type != "0") {
    
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getTypeWiseIndentItems',
        data: {
          purchaseOrder: purchaseOrder,
          styleId: styleId,
          type: type
        },
        success: function (data) {

          const itemList = data.itemList;
          let options = "<option id='indentItem' value='0' selected>--Select Indent Item--</option>";
          const length = itemList.length;
          for (var i = 0; i < length; i++) {
            options += "<option id='indentItem' value='" + itemList[i].accessoriesItemId + "'>" + itemList[i].accessoriesItemName + "</option>";
          };
          document.getElementById("indentItem").innerHTML = options;
          $('#indentItem').selectpicker('refresh');
          $('#indentItem').val(indentIdForSet).change();
          indentIdForSet = 0;
        }
      });
    

  } else {
    var options = "<option id='indentItem' value='0' selected>--Select Indent Item--</option>";
    $("#indentItem").html(options);
    $('#indentItem').selectpicker('refresh');
    $('#indentItem').val(0).change();
    indentIdForSet = 0;
  }
}

function indentItemAdd() {

  const purchaseOrder = $("#purchaseOrder").val();
  const styleId = $("#styleNo").val();
  const type = $("#type").val();
  const itemId = $("#indentItem").val();
  const supplierId = $("#supplierName").val();
  const rate = $("#rate").val();
  const dollar = $("#dollar").val();
  const userId = $("#userId").val();


  if (purchaseOrder != 0) {
    if (styleId != 0) {
      if (type != 0) {
        if (type == 3 || itemId != 0) {
          if (supplierId != 0) {
            if (rate != '') {
              if (dollar != '') {
                $.ajax({
                  type: 'GET',
                  dataType: 'json',
                  url: './addIndentItem',
                  data: {
                    purchaseOrder: purchaseOrder,
                    styleId: styleId,
                    type: type,
                    indentItemId: itemId,
                    supplierId: supplierId,
                    rate: rate,
                    dollar: dollar,
                    userId: userId
                  },
                  success: function (data) {
                    if (data.result == "Something Wrong") {
                      dangerAlert("Something went wrong");
                    } else if (data.result == "duplicate") {
                      dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                    } else {
                      //$("#dataList").empty();
                      $("#dataList").append(drawDataTable(data.poItemList,"checked"));
                      $('.tableSelect').selectpicker('refresh');

                      setSupplierValue(data.poItemList);
                      //setCurrencyValue(data.poItemList);

                    }
                  }
                });

              } else {
                warningAlert("Dollar amount in empty... Please enter dollar amount");
                $("#dollar").focus();
              }
            } else {
              warningAlert("Rate is empty... Please enter Rate amount");
              $("#rate").focus();
            }
          } else {
            warningAlert("supplierName not selected... Please Select Supplier Name");
            $("#supplierName").focus();
          }
        } else {
          warningAlert("indent Item not selected... Please Select Indent Item");
          $("#indentItem").focus();
        }
      } else {
        warningAlert("Type not selected... Please Select Type");
        $("#type").focus();
      }
    } else {
      warningAlert("Style No not selected... Please Select Style No");
      $("#styleNo").focus();
    }
  } else {
    warningAlert("Purchase order not selected... Please Select Purchase order");
    $("#purchaseOrder").focus();
  }

}


function setSupplierValue(dataList) {
  const length = dataList.length;
  for (let i = 0; i < length; i++) {
    $('#supplier-' + dataList[i].autoId).val(dataList[i].supplierId).change();
  }
}

function setCurrencyValue(dataList) {
  const length = dataList.length;
  for (let i = 0; i < length; i++) {
    console.log("currency",dataList[i].currency);
    $('#currency-' + dataList[i].autoId).val(dataList[i].currency).change();
  }
}

function submitAction() {
  const rowList = $("#dataList tr");
  const length = rowList.length;

  let itemList = ""

  let isChecked = false;

  const poNo = $("#poNo").val();
  const orderDate = $("#orderDate").val();
  const deliveryDate = $("#deliveryDate").val();
  const deliveryTo = $("#deliveryTo").val();
  const orderBy = $("#orderBy").val();
  const billTo = $("#billTo").val();
  const manualPO = $("#manualPO").val();
  const paymentType = $("#paymentType").val();
  const currency = $("#currency").val();
  const note = $("#note").val();
  const subject = $("#subject").val();
  const userId = $("#userId").val();

  for (let i = 0; i < length; i++) {

    const id = rowList[i].id.slice(4);
    const type = rowList[i].getAttribute("data-type");
    const supplierId = $("#supplier-" + id).val();
    const rate = $("#rate-" + id).val();
    const dollar = $("#dollar-" + id).text();
    const amount = $("#amount-" + id).text();
    const checked = $("#check-" + id).prop('checked');
    if (checked) isChecked = checked;
    itemList += `autoId : ${id},type : ${type},supplierId : ${supplierId},rate : ${rate},dollar : ${dollar},currency : ${currency},amount : ${amount},checked : ${checked} #`;
  }

  itemList = itemList.slice(0, -1);


  if (length > 0) {
    if (orderDate) {
      if (paymentType != 0) {
        if (currency != 0) {
          if (isChecked) {
            if (confirm("Are you sure to submit this Purchase Order...")) {
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './submitPurchaseOrder',
                data: {
                  poNo: poNo,
                  orderDate: orderDate,
                  deliveryDate: deliveryDate,
                  deliveryTo: deliveryTo,
                  orderBy: orderBy,
                  billTo: billTo,
                  manualPO: manualPO,
                  paymentType: paymentType,
                  currency: currency,
                  note: note,
                  subject: subject,
                  itemListString: itemList,
                  userId: userId
                },
                success: function (data) {
                  if (data.result == "Something Wrong") {
                    dangerAlert("Something went wrong");
                  } else if (data.result == "duplicate") {
                    dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                  } else {
                    alert("Successfully Submit...");
                    refreshAction();
                  }
                }
              });
            }
          } else {
            warningAlert("Please Select Any Item Checked..");
          }

        } else {
          warningAlert("Please Select Currency..");
          $("#currency").focus();
        }
      } else {
        warningAlert("Please Select Payment Type..");
        $("#paymentType").focus();
      }
    } else {
      warningAlert("Please Select Order Date")
      $("#orderDate").focus();
    }
  } else {
    warningAlert("Please Enter any indent id");
  }
}


function purchaseOrderEdit() {
  const rowList = $("#dataList tr");
  const length = rowList.length;

  let itemList = "";
  let isChecked = false;

  for (let i = 0; i < length; i++) {

    const id = rowList[i].id.slice(4);
    const type = rowList[i].getAttribute("data-type");
    const supplierId = $("#supplier-" + id).val();
    const rate = $("#rate-" + id).val();
    const dollar = $("#dollar-" + id).text();
    const currency = $("#currency-" + id).val();
    const amount = $("#amount-" + id).text();
    const checked = $("#check-" + id).prop('checked');
    if (checked) isChecked = checked;
    itemList += `autoId : ${id},type : ${type},supplierId : ${supplierId},rate : ${rate},dollar : ${dollar},currency : ${currency},amount : ${amount},checked : ${checked} #`;
  }

  itemList = itemList.slice(0, -1);

  const poNo = $("#poNo").val();
  const orderDate = $("#orderDate").val();
  const deliveryDate = $("#deliveryDate").val();
  const deliveryTo = $("#deliveryTo").val();
  const orderBy = $("#orderBy").val();
  const billTo = $("#billTo").val();
  const manualPO = $("#manualPO").val();
  const paymentType = $("#paymentType").val();
  const currency = $("#currency").val();
  const note = $("#note").val();
  const subject = $("#subject").val();
  const userId = $("#userId").val();


  if (length > 0) {
    if (orderDate) {
      if (paymentType != 0) {
        if (currency != 0) {
          if (isChecked) {
            if (confirm("Are you sure to Edit this Purchase Order...")) {
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './editPurchaseOrder',
                data: {
                  poNo: poNo,
                  orderDate: orderDate,
                  deliveryDate: deliveryDate,
                  deliveryTo: deliveryTo,
                  orderBy: orderBy,
                  billTo: billTo,
                  manualPO: manualPO,
                  paymentType: paymentType,
                  currency: currency,
                  note: note,
                  subject: subject,
                  itemListString: itemList,
                  userId: userId
                },
                success: function (data) {
                  if (data.result == "Something Wrong") {
                    dangerAlert("Something went wrong");
                  } else if (data.result == "duplicate") {
                    dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                  } else {
                    alert("Successfully Edit...");
                    refreshAction();
                  }
                }
              });
            }
          } else {
            warningAlert("Please Select Any Item Checked..");
          }
        } else {
          warningAlert("Please Select Currency..");
          $("#currency").focus();
        }
      } else {
        warningAlert("Please Select Payment Type..");
        $("#paymentType").focus();
      }
    } else {
      warningAlert("Please Select Order Date")
      $("#orderDate").focus();
    }
  } else {
    warningAlert("Please Enter any indent id");
  }
}




function refreshAction() {
  location.reload();

}
function showPreview(poNo, supplierId, type) {

  var url = "getPurchaseOrderReport/" + poNo + "/" + supplierId + "/" + type;
  window.open(url, '_blank');



};

function getOptions(elementId) {
  let options = "";
  $("#" + elementId + " option").each(function () {
    options += "<option value='" + $(this).val() + "'>" + $(this).text() + "</option>"
  });
  return options;
};

function searchPurchaseOrder(poNo) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchPurchaseOrder',
    data: {
      poNo: poNo
    },
    success: function (data) {
      if (data.poInfo == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.poInfo == "duplicate") {
        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
      } else {
        //$("#dataList").empty();

        console.log(data.poInfo);
        const purchaseOrder = data.poInfo;

        $("#poNo").val(purchaseOrder.poNo);

        let date = purchaseOrder.orderDate.split("/");
        $("#orderDate").val(date[2] + "-" + date[1] + "-" + date[0]);

        date = purchaseOrder.deliveryDate.split("/");
        $("#deliveryDate").val(date[2] + "-" + date[1] + "-" + date[0]);

        $("#deliveryTo").val(purchaseOrder.deliveryTo).change();
        $("#orderBy").val(purchaseOrder.orderBy).change();
        $("#billTo").val(purchaseOrder.billTo).change();
        $("#manualPO").val(purchaseOrder.manualPO);
        $("#paymentType").val(purchaseOrder.deliveryTo);
        $("#note").val(purchaseOrder.note);
        $("#subject").val(purchaseOrder.subject);

        $("#dataList").empty();
        $("#dataList").append(drawDataTable(purchaseOrder.itemList,"checked"));
        $('.tableSelect').selectpicker('refresh');

        setSupplierValue(purchaseOrder.itemList);
        setCurrencyValue(purchaseOrder.itemList);
        $("#btnPOSubmit").prop("disabled", true);
        $("#btnPOEdit").prop("disabled", false);
        $("#btnPreview").prop("disabled", false);

        $('#searchModal').modal('hide');

      }
    }
  });
}

function setData(unitId) {


  document.getElementById("unitId").value = unitId;
  document.getElementById("unitName").value = document.getElementById("unitName" + unitId).innerHTML;
  document.getElementById("unitValue").value = document.getElementById("unitValue" + unitId).innerHTML;
  document.getElementById("btnSave").disabled = true;
  document.getElementById("btnEdit").disabled = false;

}

function amountCalculation(autoId) {
  const rate = $("#rate-" + autoId).val();
  const grandQty = $("#grandQty-" + autoId).text();
  const amount = rate * grandQty;
  $("#amount-" + autoId).text(amount);
}


function drawDataTable(data , isChecked = "") {
  let rows = "";
  const length = data.length;

  const supplierNameOptions = getOptions("supplierName");
  const currencyOptions = getOptions("currency");
  const currencyList = [];

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const autoId = rowData.autoId;
    rows += `<tr id='row-${autoId}' data-type='${rowData.type}'>
    <td>${rowData.styleNo}</td>
    <td>${rowData.indentItemName}</td>
    <td>${rowData.colorName}</td>
    <td>${rowData.size}</td>
    <td><select id='supplier-${autoId}' class='selectpicker tableSelect' data-live-search='true' data-style='btn-light btn-sm border-light-gray'>${supplierNameOptions}</select></td>
    <td>${rowData.qty}</td>
    <td id='grandQty-${autoId}'>${rowData.grandQty}</td>
    <td>${rowData.unit}</td>
    <td id='dollar-${autoId}'>${rowData.dollar}</td>
    <td><input id='rate-${autoId}' class='form-control-sm max-width-60' type='number' value=${rowData.rate} onkeyup='amountCalculation(${autoId})'></td>
    <td id='amount-${autoId}'>${(rowData.amount).toFixed(2)}</td>
    <td><input type='checkbox' class='check' id='check-${autoId}' ${isChecked}></td>
    </tr>`
   
  }

  return rows;
}

// function drawRowDataTable(rowData, c,supplierName,currency) {
//   const autoId = rowData.autoId;
//   supplierName.prop("id","supplier-"+autoId);
//   currency.prop("id","currency-"+autoId);

//   let row = $("<tr id='row-"+autoId+"' data-type='"+rowData.type+"'/>")
//   row.append($("<td>" + rowData.styleNo + "</td>"));
//   row.append($("<td>" + rowData.indentItemName + "</td>"));
//   row.append($("<td>" + rowData.colorName + "</td>"));
//   row.append($("<td>" + rowData.size + "</td>"));
//   row.append($("<td></td>").append(supplierName));
//   row.append($("<td>" + rowData.qty + "</td>"));
//   row.append($("<td id='grandQty-"+autoId+"'>" + rowData.grandQty + "</td>"));
//   row.append($("<td>" + rowData.unit + "</td>"));
//   row.append($("<td id='dollar-"+autoId+"'>" + rowData.dollar + "</td>"));
//   //row.append($("<td contenteditable='true'>" + rowData.rate + "</td>"));
//   row.append($("<td><input id='rate-"+autoId+"' class='form-control-sm' type='number' value="+ rowData.rate +" onkeyup='amountCalculation("+autoId+")'></td>"));
//   row.append($("<td></td>").append(currency));
//   row.append($("<td id='amount-"+autoId+"'>" + rowData.amount + "</td>"));
//   row.append($("<td ><input type='checkbox' class='check' id='check-"+autoId+"'></td>"));

//   return row;
// }



document.getElementById("allCheck").addEventListener("click", function () {
  if ($(this).prop('checked')) {
    $(".check").prop('checked', true);
  } else {
    $(".check").prop('checked', false);
  }

});

function successAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-success");
  document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function warningAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-warning");
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function dangerAlert(message) {
  var element = $(".alert");
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
  $("input").focus(function () { $(this).select(); });
});
$(document).ready(function () {
  $("#search").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#poList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});


var today = new Date();
document.getElementById("orderDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);

