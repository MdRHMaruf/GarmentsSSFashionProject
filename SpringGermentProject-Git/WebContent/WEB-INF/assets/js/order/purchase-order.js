let styleIdForSet = 0;
let itemIdForSet = 0;
let indentIdForSet = 0;


window.onload = () => {
  document.title = "Purchase Order";
}
function poWiseStyleLoad() {
  const purchaseOrder = $("#purchaseOrder").val();

  if (purchaseOrder != "0") {
    $("#loader").show();
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
        $("#loader").hide();
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
    $("#loader").show();
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
        $("#loader").hide();
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

  const indentId = $("#indentId").val();
  const styleId = $("#styleNo").val();
  const indentType = $("#indentType").val();
  const indentItemId = $("#indentItem").val();
  const rate = $("#rate").val();
  let dollar = $("#dollar").val().trim();
  if($("#currency").val()== "BDT" && dollar == ''){
    dollar = 1;
  }
  const userId = $("#userId").val();


  if (indentId != 0) {
    if (indentType != 0) {
      if (indentItemId != 0) {
        if (rate != '') {
          if (dollar != '') {
            $("#loader").show();
            $.ajax({
              type: 'GET',
              dataType: 'json',
              url: './addIndentItem',
              data: {
                aiNo: indentId,
                styleId: styleId,
                indentType: indentType,
                accessoriesId: indentItemId
              },
              success: function (data) {
                if (data.result == "Something Wrong") {
                  dangerAlert("Something went wrong");
                } else if (data.result == "duplicate") {
                  dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                } else {
                  //$("#dataList").empty();
                  $("#dataList").append(drawAddDataTable(data.poItemList, "checked"));
                  $('.tableSelect').selectpicker('refresh');

                  //setSupplierValue(data.poItemList);
                  //setCurrencyValue(data.poItemList);

                }
                $("#loader").hide();
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
        warningAlert("Indent Item not selected... Please Select a indent Item");
        $("#indentItem").focus();
      }

    } else {
      warningAlert("Type not selected... Please Search a indent ID..");
      $("#indentType").focus();
    }
  } else {
    warningAlert("Indent ID is not selected.... Please Search a indent ID...");
    $("#indentId").focus();
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
    console.log("currency", dataList[i].currency);
    $('#currency-' + dataList[i].autoId).val(dataList[i].currency).change();
  }
}

function submitAction() {
  const rowList = $("#dataList tr");
  const length = rowList.length;

  let itemList = ""

  let isChecked = false;

  const poNo = $("#poNo").val();
  const type = $("#indentType").val();
  const orderDate = $("#orderDate").val();
  const deliveryDate = $("#deliveryDate").val();
  const supplierId = $("#supplierName").val();
  const deliveryTo = $("#deliveryTo").val();
  //const orderBy = $("#orderBy").val();
  const billTo = $("#billTo").val();
  const manualPO = $("#manualPO").val();
  const paymentType = $("#paymentType").val();
  const currency = $("#currency").val();
  const subject = $("#subject").val();
  const body = $("#body").val();
  const note = $("#note").val();
  const userId = $("#userId").val();

  for (let i = 0; i < length; i++) {

    const id = rowList[i].id.slice(4);
    const type = rowList[i].getAttribute("data-type");
    const rate = $("#rate-" + id).val();
    const dollar = $("#dollar-" + id).text();
    const amount = $("#amount-" + id).text();
    const checked = $("#check-" + id).prop('checked');
    if (!isChecked) isChecked = checked;
    itemList += `autoId : ${id},type : ${type},supplierId : ${supplierId},rate : ${rate},dollar : ${dollar},currency : ${currency},amount : ${amount},checked : ${checked} #`;
  }

  itemList = itemList.slice(0, -1);

  if (supplierId != 0) {
    if (length > 0) {
      if (orderDate) {
        if (deliveryDate) {
          if (deliveryTo != 0) {
          if (paymentType != 0) {
            if (currency != 0) {
              if(body != ''){

              }else{
                warningAlert("Please Enter ..");
                $("#body").focus();
              }
              if (isChecked) {
                if (confirm("Are you sure to submit this Purchase Order...")) {
                  $("#loader").show();
                  $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './submitPurchaseOrder',
                    data: {
                      poNo: poNo,
                      type : type,
                      orderDate: orderDate,
                      deliveryDate: deliveryDate,
                      supplierId : supplierId,
                      deliveryTo: deliveryTo,
                      orderBy: userId,
                      billTo: billTo,
                      manualPO: manualPO,
                      paymentType: paymentType,
                      currency: currency,
                      note: note,
                      subject: subject,
                      body: body,
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
                      $("#loader").hide();
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
          warningAlert("Please Select Delivery To..");
          $("#deliveryTo").focus();
        }
        } else {
          warningAlert("Please Select Delivery Date..");
          $("#deliveryDate").focus();
        }
      } else {
        warningAlert("Please Select Order Date")
        $("#orderDate").focus();
      }
    } else {
      warningAlert("Please Enter any indent id");
    }
  } else {
    warningAlert("Please Select Supplier Name")
    $("#supplierName").focus();
  }

}


function purchaseOrderEdit() {
  const rowList = $("#dataList tr");
  const length = rowList.length;

  let itemList = "";
  let isChecked = false;

  

  const poNo = $("#poNo").val();
  const orderDate = $("#orderDate").val();
  const deliveryDate = $("#deliveryDate").val();
  const supplierId = $("#supplierName").val();
  const deliveryTo = $("#deliveryTo").val();
  const billTo = $("#billTo").val();
  const manualPO = $("#manualPO").val();
  const paymentType = $("#paymentType").val();
  const currency = $("#currency").val();
  const note = $("#note").val();
  const subject = $("#subject").val();
  const body = $("#body").val();
  const userId = $("#userId").val();
 
  for (let i = 0; i < length; i++) {

    const id = rowList[i].id.slice(4);
    const type = rowList[i].getAttribute("data-type");
    const rate = $("#rate-" + id).val();
    const dollar = $("#dollar-" + id).text();
    const currency = $("#currency-" + id).val();
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
            if (confirm("Are you sure to Edit this Purchase Order...")) {
              $("#loader").show();
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './editPurchaseOrder',
                data: {
                  poNo: poNo,
                  orderDate: orderDate,
                  deliveryDate: deliveryDate,
                  supplierId : supplierId,
                  deliveryTo: deliveryTo,
                  orderBy: userId,
                  billTo: billTo,
                  manualPO: manualPO,
                  paymentType: paymentType,
                  currency: currency,
                  note: note,
                  body: body,
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
                  $("#loader").hide();
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

function searchIndentItem(indentId, indentType) {

  $("#indentId").val(indentId);
  $("#indentType").val(indentType);
  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getIndentItems',
    data: {
      indentId: indentId,
      indentType: indentType
    },
    success: function (data) {
      const itemList = data.itemList;
      let length = itemList.length;
      let options = "<option value='0' selected>--Select Indent Item--</option>";
      for (var i = 0; i < length; i++) {
        options += "<option value='" + itemList[i].accessoriesItemId + "'>" + itemList[i].accessoriesItemName + "</option>";
      };
      document.getElementById("indentItem").innerHTML = options;
      $('#indentItem').selectpicker('refresh');
      $('#indentItem').val(indentIdForSet).change();
      indentIdForSet = 0;


      const styleList = data.styleList;
      length = styleList.length;
      options = "<option value='0'>--Select Style--</option>";
      for (var i = 0; i < length; i++) {
        options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
      };
      document.getElementById("styleNo").innerHTML = options;
      $('#styleNo').selectpicker('refresh');
      $('#styleNo').val(indentIdForSet).change();
      indentIdForSet = 0;
      $("#loader").hide();
    }
  });

  $("#indentSearchModal").modal('hide');
}


function refreshAction() {
  location.reload();

}

function previewAction(previewType = "general"){
  
  let poNo = $("#poNo").val();
  let supplierId = $("#supplierName").val();
  let poType = $("#poType").val();

  let data = $("#landscapeViewCheck").prop('checked')+"@"+$("#withBrandCheck").prop('checked')+"@"+$("#withSQNumberCheck").prop('checked')+"@"+$("#withSKUNumberCheck").prop('checked');

  var url = "getPurchaseOrderReport/" + poNo + "/" + supplierId + "/" + poType +"/"+ previewType + "/" +data;
  window.open(url, '_blank');
}

function generalPreviewAction(){
  let poNo = $("#poNo").val();
  let supplierId = $("#supplierName").val();
  let poType = $("#poType").val();
  var url = "getPurchaseOrderGeneralReport/" + poNo + "/" + supplierId + "/" + poType;
  window.open(url, '_blank');
}

function showPreview(poNo, supplierId, type,previewType='primary') {

	  var url = "getPurchaseOrderReport/" + poNo + "/" + supplierId + "/" + type +"/"+ previewType;
	  window.open(url, '_blank');

};


function getOptions(elementId) {
  let options = "";
  $("#" + elementId + " option").each(function () {
    options += "<option value='" + $(this).val() + "'>" + $(this).text() + "</option>"
  });
  return options;
};

function searchPurchaseOrder(poNo,poType) {
  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchPurchaseOrder',
    data: {
      poNo: poNo,
      poType : poType
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
        $("#poNoBadge").text(purchaseOrder.poNo);
        $("#poNo").val(purchaseOrder.poNo);
        $("#poType").val(purchaseOrder.type);
        let date = purchaseOrder.orderDate.split("/");
        $("#orderDate").val(date[2] + "-" + date[1] + "-" + date[0]).change();

        date = purchaseOrder.deliveryDate.split("/");
        $("#deliveryDate").val(date[2] + "-" + date[1] + "-" + date[0]).change();

        $("#supplierName").val(purchaseOrder.supplierId).change();
        $("#deliveryTo").val(purchaseOrder.deliveryTo).change();
        $("#orderBy").val(purchaseOrder.orderBy).change();
        $("#billTo").val(purchaseOrder.billTo).change();
        $("#manualPO").val(purchaseOrder.manualPO);
        $("#paymentType").val(purchaseOrder.paymentType);
        $("#currency").val(purchaseOrder.currency);
        $("#note").val(purchaseOrder.note);
        $("#subject").val(purchaseOrder.subject);
        $("#body").val(purchaseOrder.body);
        $("#dataList").empty();
        $("#dataList").append(drawDataTable(purchaseOrder.itemList, "checked"));
        $('.tableSelect').selectpicker('refresh');

        //setSupplierValue(purchaseOrder.itemList);
        //setCurrencyValue(purchaseOrder.itemList);
        $("#btnPOSubmit").hide();
        $("#btnPOEdit").show();
        $("#btnPreview").show();
        //$("#btnPreviewOption").show();

        $('#searchModal').modal('hide');
        $("#loader").hide();
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
  $("#amount-" + autoId).text(amount.toFixed(2));
}

function deleteIndentItem(rowId){
  if(confirm("Are you Sure to delete this indent item from purchase Order")){
    $("#row-"+rowId).remove();
  }
}

function drawDataTable(data, isChecked = "") {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const autoId = rowData.autoId;
    rows += `<tr id='row-${autoId}' data-type='${rowData.type}'>
    <td>${rowData.purchaseOrder}</td>
    <td>${rowData.styleNo}</td>
    <td>${rowData.indentItemName}</td>
    <td>${rowData.colorName}</td>
    <td>${rowData.size}</td>
    <td id='grandQty-${autoId}'>${rowData.grandQty}</td>
    <td>${rowData.unit}</td>
    <td id='dollar-${autoId}'>${parseFloat(rowData.dollar).toFixed(2)}</td>
    <td><input id='rate-${autoId}' class='form-control-sm max-width-60' type='number' value=${rowData.rate} onkeyup='amountCalculation(${autoId})'></td>
    <td id='amount-${autoId}'>${(rowData.amount).toFixed(2)}</td>
    <td><input type='checkbox' class='check' id='check-${autoId}' ${isChecked}></td>
    </tr>`

  }

  return rows;
}


function drawAddDataTable(data, isChecked = "") {
  let rows = "";
  const length = data.length;

  let rate = $("#rate").val();

  let dollar = $("#dollar").val().trim();
  if($("#currency").val()== "BDT" && dollar == ''){
    dollar = 1;
  }
  let amount = 0;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const autoId = rowData.autoId;
    amount = rate * rowData.grandQty;
    rows += `<tr id='row-${autoId}' data-type='${rowData.type}'>
    <td>${rowData.purchaseOrder}</td>
    <td>${rowData.styleNo}</td>
    <td>${rowData.indentItemName}</td>
    <td>${rowData.colorName}</td>
    <td>${rowData.size}</td>
    <td id='grandQty-${autoId}'>${rowData.grandQty}</td>
    <td>${rowData.unit}</td>
    <td id='dollar-${autoId}'>${dollar}</td>
    <td><input id='rate-${autoId}' class='form-control-sm max-width-60' type='number' value=${rate} onkeyup='amountCalculation(${autoId})'></td>
    <td id='amount-${autoId}'>${amount.toFixed(2)}</td>
    <td><input type='checkbox' class='check' id='check-${autoId}' ${isChecked}></td>
    <td><i class='fa fa-trash' onclick="deleteIndentItem('${autoId}')" style='cursor:pointer;'> </i></td>
    </tr>`;

  }

  return rows;
}




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
	$("#indentListSearch").on("keyup", function () {
		let value = $(this).val().toLowerCase();
		$("#dataList tr").filter(function () {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});



var today = new Date();
$("#orderDate").val(today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2)).change();

