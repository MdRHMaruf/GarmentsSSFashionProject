
let styleIdForSet = "0";
let itemIdForSet = "0";

window.onload = () => {
  document.title = "Master L/C";
}


function buyerWiseStyleLoad() {
  var buyerId = $("#buyerName").val();

  // alert("buyerId "+buyerId);
  if (buyerId != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getBuyerWiseStylesItem',
      data: {
        buyerId: buyerId
      },
      success: function (data) {

        var styleList = data.styleList;
        var options = "<option value='0' selected>Select Style</option>";
        var length = styleList.length;
        for (var i = 0; i < length; i++) {
          options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
        };
        $("#styleNo").html(options);
        $('.selectpicker').selectpicker('refresh');
        $('#styleNo').val(styleIdForSet).change();
        styleIdForSet = 0;

      }
    });
  } else {
    var options = "<option value='0' selected>Select Style</option>";
    $("#styleNo").html(options);
    $('#styleNo').selectpicker('refresh');
    $('#styleNo').val("0").change();
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
        var options = "<option value='0' selected>Select Item Name</option>";
        var length = itemList.length;
        for (var i = 0; i < length; i++) {
          options += "<option value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
        };
        document.getElementById("itemName").innerHTML = options;
        $('#itemName').selectpicker('refresh');
        $("#itemName").prop("selectedIndex", 1).change();
        itemIdForSet = 0;
      }
    });
  } else {
    var options = "<option value='0' selected>Select Item Type</option>";
    $("#itemName").html(options);
    $('#itemName').selectpicker('refresh');
    $('#itemName').val(itemIdForSet).change();
    itemIdForSet = 0;
  }

}



function styleAddAction() {

  const rowList = $("#dataList tr");
  const length = rowList.length;

  let buyerName = $("#buyerName option:selected").text();
  let buyerId = $("#buyerName").val();
  let styleNo = $("#styleNo option:selected").text();
  let styleId = $("#styleNo").val();
  let itemName = $("#itemName option:selected").text();
  let itemId = $("#itemName").val();
  let quantity = $("#quantity").val() == "" ? 0 : $("#quantity").val();
  let unitPrice = $("#unitPrice").val() == "" ? 0 : $("#unitPrice").val();
  let amount = parseFloat(quantity) * parseFloat(unitPrice);


  let sessionObject = JSON.parse(sessionStorage.getItem("pendingMasterLcStyleItem") ? sessionStorage.getItem("pendingMasterLcStyleItem") : "{}");
  let itemList = sessionObject.itemList ? sessionObject.itemList : [];

  if (buyerId != 0) {
    if (styleId != 0) {
      if (itemId != 0) {
        if (quantity != 0) {
          if (unitPrice != 0) {
            const id = length;
            itemList.push({
              "autoId": id,
              "buyerId": buyerId,
              "buyerName": buyerName,
              "styleId": styleId,
              "styleNo": styleNo,
              "itemId": itemId,
              "itemName": itemName,
              "quantity": quantity
            });
            let row = `<tr id='row-${id}' class='newStyle' data-type='newStyle' data-buyer-id='${buyerId}' data-style-id='${styleId}' data-item-id='${itemId}' >
													<td id='styleNo-${id}'>${styleNo}</td>
                          <td><input type="number" id='quantity-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${quantity}"/></td>
                          <td><input type="number" id='unitPrice-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${unitPrice}"/></td>
                          <td id='amount-${id}'>${amount}</td>
                          <td ><i class='fa fa-trash' onclick="deleteStyle('${id}','new')" style="cursor:pointer;"></i></td>
                          <td ><i class='fas fa-dolly-flatbed' onclick="shippedAction('${id}')" style="cursor:pointer;"></i></td>
												</tr>`;

            $("#dataList").append(row);

            totalValueCount();

            sessionObject = {
              "buyerId": buyerId,
              "itemList": itemList
            }

            sessionStorage.setItem("pendingMasterLcStyleItem", JSON.stringify(sessionObject));
          } else {
            warningAlert("Empty Unit Price ... Please Select Unit Price");
            $("#unitPrice").focus();
          }
        } else {
          warningAlert("Empty Quantity ... Please Enter Quantity");
          $("#quantity").focus();
        }

      } else {
        warningAlert("Item Name not selected... Please Select Item Name");
        $("#itemName").focus();
      }
    } else {
      warningAlert("Style No not selected... Please Select Style No");
      $("#styleNo").focus();
    }

  } else {
    warningAlert("Buyer not selected... Please Select Buyer Name");
    $("#buyerName").focus();
  }
}


function deleteStyle(autoId, rowType) {
  if (confirm("Are you sure to Delete this Style?")) {
    if (rowType == 'new') {
      const buyerId = $("#row-" + autoId).attr("data-buyer-id");
      console.log(buyerId);
      const pendingMasterLcStyleItem = JSON.parse(sessionStorage.getItem("pendingMasterLcStyleItem"));
      const newItemList = pendingMasterLcStyleItem.itemList.filter(item => item.buyerId != buyerId);
      pendingMasterLcStyleItem.itemList = newItemList;
      sessionStorage.setItem("pendingMasterLcStyleItem", JSON.stringify(pendingMasterLcStyleItem));

      $("#row-" + autoId).remove();
    } else {
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './deleteMasterLcStyle',
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
            // if (costingList.size > 0) {
            //   itemIdForSet = costingList[0].itemId;
            //   $("#styleName").val(costingList[0].styleId).change();
            // }
            $("#dataList").empty();
            drawItemDataTable(costingList);
            if (sessionStorage.getItem("pendingMasterLcStyleItem")) {
              const pendingMasterLcStyleItem = JSON.parse(sessionStorage.getItem("pendingMasterLcStyleItem"));
              if (styleId == pendingMasterLcStyleItem.styleId && itemId == pendingMasterLcStyleItem.itemId) {
                drawSessionDataTable(pendingMasterLcStyleItem.itemList);
              }
            }
          }
        }
      });
    }
    totalValueCount();
  }

}

function shippedAction(styleAutoId) {

  const rowList = $("#shippingStyleList tr");
  const id = rowList.length + 1;
  const styleId = $("#row-" + styleAutoId).attr("data-style-id");
  let existId = 0;
  for (let i = 0; i < id - 1; i++) {
    if (rowList[i].getAttribute('data-style-id') == styleId) {
      existId = rowList[i].id.slice(rowList[i].id.indexOf('-') + 1);
      break;
    }
  }

  if (existId != 0) {
    console.log("existId-", existId);
    $("#shippedStyleNo-" + existId).text($("#styleNo-" + styleAutoId).text());
    $("#shippedQuantity-" + existId).val($("#quantity-" + styleAutoId).val());
  } else {
    let row = `<tr id='shippingStyleRow-${id}' class='newShippingStyle' data-type='newShippingStyle' data-style-id='${styleId}' >
    <td id='shippedStyleNo-${id}'>${$("#styleNo-" + styleAutoId).text()}</td>
    <td><input type="number" id='shippedQuantity-${id}' class="form-control-sm max-width-100" value="${$("#quantity-" + styleAutoId).val()}"/></td>
    <td ><i class='fa fa-trash' onclick="deleteShippedStyle('${id}','new')" style="cursor:pointer;"></i></td>
    </tr>`;
    $("#shippingStyleList").append(row);
  }
}


function deleteShippedStyle(autoId, rowType) {
  if (confirm("Are you sure to Delete this Shipped Style?")) {
    if (rowType == 'new') {
      $("#shippingStyleRow-" + autoId).remove();
    } else {
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './deleteMasterLcShippedStyle',
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
            // if (costingList.size > 0) {
            //   itemIdForSet = costingList[0].itemId;
            //   $("#styleName").val(costingList[0].styleId).change();
            // }
            $("#dataList").empty();
            drawItemDataTable(costingList);
            if (sessionStorage.getItem("pendingMasterLcStyleItem")) {
              const pendingMasterLcStyleItem = JSON.parse(sessionStorage.getItem("pendingMasterLcStyleItem"));
              if (styleId == pendingMasterLcStyleItem.styleId && itemId == pendingMasterLcStyleItem.itemId) {
                drawSessionDataTable(pendingMasterLcStyleItem.itemList);
              }
            }
          }
        }
      });
    }
    totalValueCount();
  }
}

function searchMasterLc(autoId) {

  const masterLc = fakeData.masterLcList[autoId];

  $("#buyerName").val(masterLc.buyerId).change();
  $("#sendBankName").val(masterLc.sendBankId).change();
  $("#receiveBankName").val(masterLc.receiveBankId).change();
  $("#beneficiaryBankName").val(masterLc.beneficiaryBankId).change();
  $("#throughBankName").val(masterLc.throughBankId).change();
  $("#date").val(masterLc.date);
  $("#totalValue").val(masterLc.totalValue);
  $("#maturityDate").val(masterLc.maturityDate);
  $("#remarks").val(masterLc.remarks);

  $("#amendmentList").empty();
  masterLc.amendmentList.forEach(amendment => {
    let row = `<tr>
                  <td style="cursor:pointer;" onclick='setAmendmentInfo(${amendment.amendmentNo})'>${amendment.amendmentNo}</td>
                  <td>${amendment.amendmentDate}</td>
                </tr>`
    $("#amendmentList").append(row);
  });

  $("#shipmentList").empty();
  masterLc.shippingList.forEach(shipping => {
    let row = `<tr>
                  <td style="cursor:pointer;" onclick='setShippingInfo(${shipping.shippingNo})'>${shipping.shippingMark}</td>
                  <td>${shipping.shippingDate}</td>
                </tr>`
    $("#shipmentList").append(row);
  })

  $("#searchModal").modal('hide');
}

function setShippingInfo(shippingNo) {
  let shipping = fakeData.shippingInfoList[shippingNo];
  $("#shippingDate").val(shipping.shippingDate);
  $("#shippingMark").val(shipping.shippingMark);
  $("#shippingStyleList").empty();
  shipping.styleList.forEach(style => {
    let row = `<tr>
                <td>${style.styleNo}</td>
                <td>${style.quantity}</td>
              </tr>`
    $("#shippingStyleList").append(row);
  })
}

function setAmendmentInfo(amendmentNo) {
  let amendment = fakeData.amendmentInfoList[amendmentNo];
  $("#buyerName").val(amendment.buyerId).change();
  $("#sendBankName").val(amendment.sendBankId).change();
  $("#receiveBankName").val(amendment.receiveBankId).change();
  $("#beneficiaryBankName").val(amendment.beneficiaryBankId).change();
  $("#throughBankName").val(amendment.throughBankId).change();
  $("#date").val(amendment.date);
  $("#totalValue").val(amendment.totalValue);
  $("#maturityDate").val(amendment.maturityDate);
  $("#remarks").val(amendment.remarks);

  $("#dataList").empty();
  amendment.styleList.forEach(style => {
    let id = style.styleNo;
    let row = `<tr id='row-${id}' class='oldStyle' data-type='oldStyle'  >
													<td id='styleNo-${id}'>${style.styleNo}</td>
                          <td><input type="number" id='quantity-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${style.quantity}"/></td>
                          <td><input type="number" id='unitPrice-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${style.unitPrice}"/></td>
                          <td id='amount-${id}'>${style.amount}</td>
                          <td ><i class='fa fa-trash' onclick="deleteStyle('${id}','new')" style="cursor:pointer;"></i></td>
                          <td ><i class='fas fa-dolly-flatbed' onclick="shippedAction('${id}')" style="cursor:pointer;"></i></td>
												</tr>`;
    $("#dataList").append(row);
  })
}

function totalValueCount() {
  const tempList = $("#dataList tr");
  let totalAmount = 0;
  $.each(tempList, (i, tr) => {
    let id = tr.id.slice(4);
    totalAmount += (parseFloat($("#quantity-" + id).val()) * parseFloat($("#unitPrice-" + id).val()));
  });
  $("#totalValue").val(totalAmount);
}

function setAmount(id) {
  $("#amount-" + id).text(parseFloat($("#quantity-" + id).val()) * parseFloat($("#unitPrice-" + id).val()));
}


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

let today = new Date();
today = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
document.getElementById("date").value = today;
document.getElementById("shipmentDate").value = today;

let fakeData = {
  "masterLcList": [{
    "lcNo": "MLC-38394394",
    "buyerId": "1",
    "sendBankId": "1",
    "receiveBankId": "2",
    "beneficiaryBankId": "3",
    "throughBankId": "4",
    "date": "2020-05-04",
    "totalValue": "3000",
    "currency": "2",
    "maturityDate": "2021-04-02",
    "remarks": "remarks test",
    "amendmentList": [{
      "amendmentNo": "1",
      "amendmentDate": "2020-03-10"
    }, {
      "amendmentNo": "2",
      "amendmentDate": "2020-03-11"
    }],
    "shippingList": [{
      "shippingNo": "1",
      "shippingDate": "2020-03-10",
      "shippingMark": "TM-3"
    }, {
      "shippingNo": "2",
      "shippingDate": "2020-04-09",
      "shippingMark": "GH-23"
    }],
    "styleList": [{
      "autoId": "1",
      "styleId": "1",
      "styleNo": "A734M",
      "quantity": "4",
      "unitPrice": "10",
      "amount": "40"
    }, {
      "autoId": "2",
      "styleId": "3",
      "styleNo": "A7341W",
      "quantity": "70",
      "unitPrice": "40",
      "amount": "2800"
    }]
  }],
  "shippingInfoList": {
    "1": {
      "shippingDate": "2020-03-10",
      "shippingMark": "TM-3",
      "styleList": [{
        "styleId": "1",
        "styleNo": "A7340M",
        "quantity": "20"
      }, {
        "styleId": "2",
        "styleNo": "A7340J",
        "quantity": "30"
      }]
    },
    "2": {
      "shippingDate": "2020-04-09",
      "shippingMark": "GH-22",
      "styleList": [{
        "styleId": "3",
        "styleNo": "A7341W",
        "quantity": "80"
      }, {
        "styleId": "4",
        "styleNo": "A7341L",
        "quantity": "25"
      }]

    }
  },
  "amendmentInfoList": {
    "1": {
      "buyerId": "1",
      "sendBankId": "3",
      "receiveBankId": "1",
      "beneficiaryBankId": "4",
      "throughBankId": "2",
      "date": "2020-03-10",
      "totalValue": "3950",
      "currency": "1",
      "maturityDate": "2021-08-12",
      "remarks": "Amendment Remarks",
      "styleList": [{
        "styleId": "1",
        "styleNo": "A7340M",
        "quantity": "90",
        "unitPrice": "40",
        "amount": "3600"
      }, {
        "styleId": "2",
        "styleNo": "A7340J",
        "quantity": "7",
        "unitPrice": "50",
        "amount": "350"
      }]
    },
    "2": {
      "buyerId": "1",
      "sendBankId": "2",
      "receiveBankId": "4",
      "beneficiaryBankId": "3",
      "throughBankId": "1",
      "date": "2021-03-11",
      "totalValue": "8000",
      "currency": "1",
      "maturityDate": "2021-08-12",
      "remarks": "Amendment Remarks",
      "styleList": [{
        "styleId": "3",
        "styleNo": "A7341W",
        "quantity": "150",
        "unitPrice": "20",
        "amount": "3000"
      }, {
        "styleId": "4",
        "styleNo": "A7341L",
        "quantity": "200",
        "unitPrice": "25",
        "amount": "5000"
      }]
    }
  }
}
