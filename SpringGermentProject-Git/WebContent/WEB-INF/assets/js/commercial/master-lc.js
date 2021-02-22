
let styleIdForSet = "0";
let itemIdForSet = "0";
let poIdForSet = "0";

window.onload = () => {
  document.title = "Master L/C";
}

// Master LC Part
function buyerWiseStyleLoad() {
  let buyerId = $("#masterBuyerName").val();

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

        let styleList = data.styleList;
        let options = "<option value='0' selected>Select Style</option>";
        let length = styleList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
        };
        $("#masterStyleNo").html(options);
        $('#masterStyleNo').selectpicker('refresh');
        $('#masterStyleNo').val(styleIdForSet).change();
        styleIdForSet = 0;

      }
    });
  } else {
    let options = "<option value='0' selected>Select Style</option>";
    $("#masterStyleNo").html(options);
    $('#masterStyleNo').selectpicker('refresh');
    $('#masterStyleNo').val("0").change();
  }

}

function styleWiseBuyerPOLoad() {
  let styleId = $("#masterStyleNo").val();

  if (styleId != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getStyleWiseBuyerPO',
      data: {
        styleId: styleId
      },
      success: function (data) {

        let poList = data.poList;
        let options = "<option value='0' selected>Select Buyer PO</option>";
        let length = poList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + poList[i].id + "'>" + poList[i].name + "</option>";
        };
        document.getElementById("masterPurchaseOrder").innerHTML = options;
        $('#masterPurchaseOrder').selectpicker('refresh');
        $("#masterPurchaseOrder").val(poIdForSet).change();
        poIdForSet = 0;
      }
    });
  } else {
    let options = "<option value='0' selected>Select Buyer PO</option>";
    $("#masterPurchaseOrder").html(options);
    $('#masterPurchaseOrder').selectpicker('refresh');
    $('#masterPurchaseOrder').val(poIdForSet).change();
    poIdForSet = 0;
  }
}


function styleWiseItemLoad() {
  let styleId = $("#masterStyleNo").val();

  if (styleId != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getStyleWiseItem',
      data: {
        styleId: styleId
      },
      success: function (data) {

        let itemList = data.itemList;
        let options = "<option value='0' selected>Select Item Name</option>";
        let length = itemList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
        };
        document.getElementById("masterItemName").innerHTML = options;
        $('#masterItemName').selectpicker('refresh');
        $("#masterItemName").prop("selectedIndex", 1).change();
        itemIdForSet = 0;
      }
    });
  } else {
    let options = "<option value='0' selected>Select Item Type</option>";
    $("#masterItemName").html(options);
    $('#masterItemName').selectpicker('refresh');
    $('#masterItemName').val(itemIdForSet).change();
    itemIdForSet = 0;
  }

}



function masterStyleAddAction() {

  const rowList = $("#masterStyleList tr");
  const length = rowList.length;

  let masterLCNo = $("#masterLCNo").val();
  let buyerName = $("#masterBuyerName option:selected").text();
  let buyerId = $("#masterBuyerName").val();
  let styleNo = $("#masterStyleNo option:selected").text();
  let styleId = $("#masterStyleNo").val();
  let itemName = $("#masterItemName option:selected").text();
  let itemId = $("#masterItemName").val();
  let purchaseOrder = $("#masterPurchaseOrder option:selected").text();
  let purchaseOrderId = $("#masterPurchaseOrder").val();
  let quantity = $("#masterQuantity").val() == "" ? 0 : $("#masterQuantity").val();
  let unitPrice = $("#masterUnitPrice").val() == "" ? 0 : $("#masterUnitPrice").val();
  let amount = parseFloat(quantity) * parseFloat(unitPrice);


  let sessionObject = JSON.parse(sessionStorage.getItem("pendingMasterLcStyleItem") ? sessionStorage.getItem("pendingMasterLcStyleItem") : "{}");
  let itemList = sessionObject.itemList ? sessionObject.itemList : [];

  if (masterLCNo != '') {
    if (buyerId != 0) {
      if (styleId != 0) {
        if (itemId != 0) {
          if (purchaseOrderId != 0) {
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
                  "purchaseOrderId": purchaseOrderId,
                  "purchaseOrder": purchaseOrder,
                  "quantity": quantity,
                  "unitPrice": unitPrice,
                  "amount": amount
                });
                let row = `<tr id='masterRow-${id}' class='masterNewStyle' data-type='newStyle' data-master-lc-no='${masterLCNo}' data-buyer-id='${buyerId}' data-style-id='${styleId}' data-item-id='${itemId}' data-purchase-order-id='${purchaseOrderId}' >
                            <td id='masterStyleNo-${id}'>${styleNo}</td>
                            <td id='masterPurchaseOrder-${id}'>${purchaseOrder}</td>
                            <td><input type="number" id='masterQuantity-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${quantity}"/></td>
                            <td><input type="number" id='masterUnitPrice-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${unitPrice}"/></td>
                            <td id='masterAmount-${id}'>${amount}</td>
                            <td ><i class='fa fa-trash' onclick="deleteMasterStyle('${id}','new')" style="cursor:pointer;" title="Delete"></i></td>
                          </tr>`;

                $("#masterStyleList").append(row);

                totalValueCount();

                sessionObject = {
                  "buyerId": buyerId,
                  "masterLCNo": masterLCNo,
                  "itemList": itemList
                }

                sessionStorage.setItem("pendingMasterLcStyleItem", JSON.stringify(sessionObject));
              } else {
                warningAlert("Empty Unit Price ... Please Select Unit Price");
                $("#masterUnitPrice").focus();
              }
            } else {
              warningAlert("Empty Quantity ... Please Enter Quantity");
              $("#masterQuantity").focus();
            }
          } else {
            warningAlert("Purchase Order selected... Please Select Purchase Order");
            $("#masterPurchaseOrder").focus();
          }
        } else {
          warningAlert("Item Name not selected... Please Select Item Name");
          $("#masterItemName").focus();
        }
      } else {
        warningAlert("Style No not selected... Please Select Style No");
        $("#masterStyleNo").focus();
      }

    } else {
      warningAlert("Buyer not selected... Please Select Buyer Name");
      $("#masterBuyerName").focus();
    }
  } else {
    warningAlert("Master LC No not entered... Please Enter Master LC No");
    $("#masterLCNo").focus();
  }

}


function deleteMasterStyle(autoId, rowType) {
  if (confirm("Are you sure to Delete this Style?")) {
    if (rowType == 'new') {
      const buyerId = $("#masterRow-" + autoId).attr("data-buyer-id");
      const masterLCNo = $("#masterRow-" + autoId).attr("data-master-lc-no");
      const pendingMasterLcStyleItem = JSON.parse(sessionStorage.getItem("pendingMasterLcStyleItem"));
      const newItemList = pendingMasterLcStyleItem.itemList.filter(item => item.buyerId != buyerId && item.masterLCNo != masterLCNo);
      pendingMasterLcStyleItem.itemList = newItemList;
      sessionStorage.setItem("pendingMasterLcStyleItem", JSON.stringify(pendingMasterLcStyleItem));

      $("#masterRow-" + autoId).remove();
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

            let costingList = data.result;
            // if (costingList.size > 0) {
            //   itemIdForSet = costingList[0].itemId;
            //   $("#styleName").val(costingList[0].styleId).change();
            // }
            $("#masterStyleList").empty();
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

function masterSubmitAction() {
  let rowList = $("#masterStyleList tr");
  let length = rowList.length;

  if (length > 0) {
    rowList = $("tr.masterNewStyle");
    length = rowList.length;

    let styleList = '';

    let masterLCNo = $("#masterLCNo").val();
    let buyerId = $("#masterBuyerName").val();
    let sendBankId = $("#masterSendBankName").val();
    let receiveBankId = $("#masterReceiveBankName").val();
    let beneficiaryBankId = $("#beneficiaryBankName").val();
    let throughBankId = $("#throughBankName").val();
    let date = $("#masterDate").val();
    let totalValue = $("#masterTotalValue").val();
    let currencyId = $("#masterCurrency").val();
    let shipmentDate = $("#masterShipmentDate").val();
    let expiryDate = $("#masterExpiryDate").val();
    let remarks = $("#masterRemarks").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    if (masterLCNo != '') {
      if (buyerId != '0') {
        if (sendBankId != '0') {
          if (receiveBankId != '0') {
            if (currencyId != '0') {
              for (let i = 0; i < length; i++) {
                const newRow = rowList[i];
                const id = newRow.id.slice(10);

                const item = {
                  buyerId: newRow.getAttribute('data-buyer-id'),
                  styleId: newRow.getAttribute('data-style-id'),
                  itemId: newRow.getAttribute('data-item-id'),
                  purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                  quantity: $("#masterQuantity-" + id).val(),
                  unitPrice: $("#masterUnitPrice-" + id).val(),
                  amount: $("#masterAmount-" + id).text(),
                  userId: userId
                }

                styleItems.list.push(item);
              }
              if (confirm("Are you sure to confirm..")) {
                $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: './masterLCSubmit',
                  data: {
                    masterLCNo: masterLCNo,
                    buyerId: buyerId,
                    senderBankId: sendBankId,
                    receiverBankId: receiveBankId,
                    beneficiaryBankId: beneficiaryBankId,
                    throughBankId: throughBankId,
                    date: date,
                    totalValue: totalValue,
                    currencyId: currencyId,
                    shipmentDate: shipmentDate,
                    expiryDate: expiryDate,
                    remarks: remarks,
                    userId: userId,
                    amendmentNo: '0',
                    amendmentDate: date,
                    styleList: JSON.stringify(styleItems),
                  },
                  success: function (data) {
                    if (data.result == 'success') {
                      alert("Successfully Submitted");
                      drawMasterLCAmendmentList(data.amendmentList);
                    } else {
                      alert("Master LC Insertion Failed")
                    }
                  }
                });

              }
            } else {
              warningAlert("Please Select Currency...");
              $("#masterCurrency").focus();
            }
          } else {
            warningAlert("Please Select Receive Bank Name...");
            $("#masterReceiveBankName").focus();
          }
        } else {
          warningAlert("Please Select Send Bank Name...");
          $("#masterSendBankName").focus();
        }
      } else {
        warningAlert("Please Select Buyer Name...");
        $("#masterBuyerName").focus();
      }
    } else {
      warningAlert("Please Enter Master LC No...");
      $("#masterLCNo").focus();
    }
  } else {
    warningAlert("Please Enter Any Style Item...");
  }

}

function masterEditAction(){
  let rowList = $("#masterStyleList .edited-row");
  let length = rowList.length;

  if (length > 0) {
    rowList = $("tr.masterNewStyle");
    length = rowList.length;

    let styleList = '';

    let masterLCNo = $("#masterLCNo").val();
    let buyerId = $("#masterBuyerName").val();
    let sendBankId = $("#masterSendBankName").val();
    let receiveBankId = $("#masterReceiveBankName").val();
    let beneficiaryBankId = $("#beneficiaryBankName").val();
    let throughBankId = $("#throughBankName").val();
    let date = $("#masterDate").val();
    let totalValue = $("#masterTotalValue").val();
    let currencyId = $("#masterCurrency").val();
    let shipmentDate = $("#masterShipmentDate").val();
    let expiryDate = $("#masterExpiryDate").val();
    let remarks = $("#masterRemarks").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    if (masterLCNo != '') {
      if (buyerId != '0') {
        if (sendBankId != '0') {
          if (receiveBankId != '0') {
            if (currencyId != '0') {
              for (let i = 0; i < length; i++) {
                const newRow = rowList[i];
                const id = newRow.id.slice(10);

                const item = {
                  buyerId: newRow.getAttribute('data-buyer-id'),
                  styleId: newRow.getAttribute('data-style-id'),
                  itemId: newRow.getAttribute('data-item-id'),
                  purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                  quantity: $("#masterQuantity-" + id).val(),
                  unitPrice: $("#masterUnitPrice-" + id).val(),
                  amount: $("#masterAmount-" + id).text(),
                  userId: userId
                }

                styleItems.list.push(item);
              }
              if (confirm("Are you sure to confirm..")) {
                $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: './masterLCEdit',
                  data: {
                    masterLCNo: masterLCNo,
                    buyerId: buyerId,
                    senderBankId: sendBankId,
                    receiverBankId: receiveBankId,
                    beneficiaryBankId: beneficiaryBankId,
                    throughBankId: throughBankId,
                    date: date,
                    totalValue: totalValue,
                    currencyId: currencyId,
                    shipmentDate: shipmentDate,
                    expiryDate: expiryDate,
                    remarks: remarks,
                    userId: userId,
                    amendmentNo: '0',
                    amendmentDate: date,
                    styleList: JSON.stringify(styleItems),
                  },
                  success: function (data) {
                    if (data.result == 'success') {
                      alert("Successfully Submitted");
                      drawMasterLCAmendmentList(data.amendmentList);
                    } else {
                      alert("Master LC Insertion Failed")
                    }
                  }
                });

              }
            } else {
              warningAlert("Please Select Currency...");
              $("#masterCurrency").focus();
            }
          } else {
            warningAlert("Please Select Receive Bank Name...");
            $("#masterReceiveBankName").focus();
          }
        } else {
          warningAlert("Please Select Send Bank Name...");
          $("#masterSendBankName").focus();
        }
      } else {
        warningAlert("Please Select Buyer Name...");
        $("#masterBuyerName").focus();
      }
    } else {
      warningAlert("Please Enter Master LC No...");
      $("#masterLCNo").focus();
    }
  } else {
    warningAlert("Please Enter Any Style Item...");
  }
}


function masterAmendmentAction() {
  let rowList = $("#masterStyleList tr");
  let length = rowList.length;

  if (length > 0) {
    rowList = $("tr.masterNewStyle");
    length = rowList.length;

    let styleList = '';

    let masterLCNo = $("#masterLCNo").val();
    let buyerId = $("#masterBuyerName").val();
    let sendBankId = $("#masterSendBankName").val();
    let receiveBankId = $("#masterReceiveBankName").val();
    let beneficiaryBankId = $("#beneficiaryBankName").val();
    let throughBankId = $("#throughBankName").val();
    let date = $("#masterDate").val();
    let totalValue = $("#masterTotalValue").val();
    let currencyId = $("#masterCurrency").val();
    let shipmentDate = $("#masterShipmentDate").val();
    let expiryDate = $("#masterExpiryDate").val();
    let remarks = $("#masterRemarks").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    if (masterLCNo != '') {
      if (buyerId != '0') {
        if (sendBankId != '0') {
          if (receiveBankId != '0') {
            if (currencyId != '0') {
              for (let i = 0; i < length; i++) {
                const newRow = rowList[i];
                const id = newRow.id.slice(10);

                const item = {
                  buyerId: newRow.getAttribute('data-buyer-id'),
                  styleId: newRow.getAttribute('data-style-id'),
                  itemId: newRow.getAttribute('data-item-id'),
                  purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                  quantity: $("#masterQuantity-" + id).val(),
                  unitPrice: $("#masterUnitPrice-" + id).val(),
                  amount: $("#masterAmount-" + id).text(),
                  userId: userId
                }

                styleItems.list.push(item);
              }
              if (confirm("Are you sure to confirm..")) {
                $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: './masterLCAmendment',
                  data: {
                    masterLCNo: masterLCNo,
                    buyerId: buyerId,
                    senderBankId: sendBankId,
                    receiverBankId: receiveBankId,
                    beneficiaryBankId: beneficiaryBankId,
                    throughBankId: throughBankId,
                    date: date,
                    totalValue: totalValue,
                    currencyId: currencyId,
                    shipmentDate: shipmentDate,
                    expiryDate: expiryDate,
                    remarks: remarks,
                    userId: userId,
                    amendmentNo: '0',
                    amendmentDate: date,
                    styleList: JSON.stringify(styleItems),
                  },
                  success: function (data) {
                    if (data.result == 'success') {
                      alert("Successfully Submitted");
                      drawMasterLCAmendmentList(data.amendmentList);
                    } else {
                      alert("Master LC Insertion Failed")
                    }
                  }
                });

              }
            } else {
              warningAlert("Please Select Currency...");
              $("#masterCurrency").focus();
            }
          } else {
            warningAlert("Please Select Receive Bank Name...");
            $("#masterReceiveBankName").focus();
          }
        } else {
          warningAlert("Please Select Send Bank Name...");
          $("#masterSendBankName").focus();
        }
      } else {
        warningAlert("Please Select Buyer Name...");
        $("#masterBuyerName").focus();
      }
    } else {
      warningAlert("Please Enter Master LC No...");
      $("#masterLCNo").focus();
    }
  } else {
    warningAlert("Please Enter Any Style Item...");
  }

}

function refreshAction(){
  location.reload();
}

function drawMasterLCAmendmentList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='masterAmendmentRow-${id}'  data-amendment-auto-id='${id}'>
					<td id='masterAmendmentNo-${id}'>${rowData.amendmentNo}</td>
					<td id='masterAmendmentDate-${id}'>${rowData.amendmentDate}</td>
				</tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }

  $("#masterAmendmentList").append(rows);

}

function drawMasterLCStyleList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='masterRow-${id}' class='masterOldStyle' data-type='oldStyle' data-master-lc-no='${rowData.masterLCNo}' data-buyer-id='${rowData.buyerId}' data-style-id='${rowData.styleId}' data-item-id='${rowData.itemId}' data-purchase-order-id='${rowData.purchaseOrderId}' >
              <td id='masterStyleNo-${id}'>${rowData.styleNo}</td>
              <td id='masterPurchaseOrder-${id}'>${rowData.purchaseOrder}</td>
              <td><input type="number" id='masterQuantity-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${rowData.quantity}"/></td>
              <td><input type="number" id='masterUnitPrice-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${rowData.unitPrice}"/></td>
              <td id='masterAmount-${id}'>${rowData.amount}</td>
              <td ><i class='fa fa-trash' onclick="deleteMasterStyle('${id}','new')" style="cursor:pointer;" title="Delete"></i></td>
            </tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }
  $("#masterStyleList").append(rows);
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

            let costingList = data.result;
            // if (costingList.size > 0) {
            //   itemIdForSet = costingList[0].itemId;
            //   $("#styleName").val(costingList[0].styleId).change();
            // }
            $("#masterStyleList").empty();
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

function searchMasterLc(masterLCNo, buyerId, amendmentNo) {

  //const masterLc = fakeData.masterLcList[autoId];
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchMasterLC',
    data: {
      masterLCNo: masterLCNo,
      buyerId: buyerId,
      amendmentNo: amendmentNo
    },
    success: function (data) {
      console.log(data)
      const masterLCInfo = data.masterLCInfo;
      const masterLCStyles = data.masterLCStyles;

      $("#masterLCNo").val(masterLCInfo.masterLCNo);
      $("#masterBuyerName").val(masterLCInfo.buyerId).change();
      $("#masterSendBankName").val(masterLCInfo.senderBankId).change();
      $("#masterReceiveBankName").val(masterLCInfo.receiverBankId).change();
      $("#beneficiaryBankName").val(masterLCInfo.beneficiaryBankId).change();
      $("#throughBankName").val(masterLCInfo.throughBankId).change();
      $("#masterDate").val(masterLCInfo.date);
      $("#masterTotalValue").val(masterLCInfo.totalValue);
      $("#masterCurrency").val(masterLCInfo.currencyId);
      $("#masterShipmentDate").val(masterLCInfo.shipmentDate);
      $("#masterExpiryDate").val(masterLCInfo.expiryDate);
      $("#remarks").val(masterLCInfo.remarks);
      drawMasterLCStyleList(masterLCStyles);
      drawMasterLCAmendmentList(data.amendmentList);

      $("#masterSubmitBtn").hide();
      $("#masterAmendmentBtn").show();
      $("#masterEditBtn").show();
      $("#masterPreviewBtn").show();
    }
  });



  // $("#buyerName").val(masterLc.buyerId).change();
  // $("#sendBankName").val(masterLc.sendBankId).change();
  // $("#receiveBankName").val(masterLc.receiveBankId).change();
  // $("#beneficiaryBankName").val(masterLc.beneficiaryBankId).change();
  // $("#throughBankName").val(masterLc.throughBankId).change();
  // $("#date").val(masterLc.date);
  // $("#totalValue").val(masterLc.totalValue);
  // $("#maturityDate").val(masterLc.maturityDate);
  // $("#remarks").val(masterLc.remarks);

  // $("#amendmentList").empty();
  // masterLc.amendmentList.forEach(amendment => {
  //   let row = `<tr>
  //                 <td style="cursor:pointer;" onclick='setAmendmentInfo(${amendment.amendmentNo})'>${amendment.amendmentNo}</td>
  //                 <td>${amendment.amendmentDate}</td>
  //               </tr>`
  //   $("#amendmentList").append(row);
  // });

  // $("#shipmentList").empty();
  // masterLc.shippingList.forEach(shipping => {
  //   let row = `<tr>
  //                 <td style="cursor:pointer;" onclick='setShippingInfo(${shipping.shippingNo})'>${shipping.shippingMark}</td>
  //                 <td>${shipping.exportShippingDate}</td>
  //               </tr>`
  //   $("#shipmentList").append(row);
  // })

  $("#searchModal").modal('hide');
}

function setShippingInfo(shippingNo) {
  //let shipping = fakeData.shippingInfoList[shippingNo];
  $("#exportShippingDate").val(shipping.exportShippingDate);
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
  //let amendment = fakeData.amendmentInfoList[amendmentNo];
  $("#buyerName").val(amendment.buyerId).change();
  $("#sendBankName").val(amendment.sendBankId).change();
  $("#receiveBankName").val(amendment.receiveBankId).change();
  $("#beneficiaryBankName").val(amendment.beneficiaryBankId).change();
  $("#throughBankName").val(amendment.throughBankId).change();
  $("#date").val(amendment.date);
  $("#totalValue").val(amendment.totalValue);
  $("#maturityDate").val(amendment.maturityDate);
  $("#remarks").val(amendment.remarks);

  $("#masterStyleList").empty();
  amendment.styleList.forEach(style => {
    let id = style.styleNo;
    let row = `<tr id='row-${id}' class='oldStyle' data-type='oldStyle'  >
													<td id='styleNo-${id}'>${style.styleNo}</td>
                          <td><input type="number" id='quantity-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${style.quantity}"/></td>
                          <td><input type="number" id='unitPrice-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${style.unitPrice}"/></td>
                          <td id='amount-${id}'>${style.amount}</td>
                          <td ><i class='fa fa-trash' onclick="deleteStyle('${id}','new')" style="cursor:pointer;"></i></td>
                          
												</tr>`;
    $("#masterStyleList").append(row);
  })
}

function totalValueCount() {
  const tempList = $("#masterStyleList tr");
  let totalAmount = 0;
  $.each(tempList, (i, tr) => {
    let id = tr.id.slice(10);
    totalAmount += (parseFloat($("#masterQuantity-" + id).val()) * parseFloat($("#masterUnitPrice-" + id).val()));
  });
  $("#masterTotalValue").val(totalAmount);
}

function setAmount(id) {
  $("#masterAmount-" + id).text(parseFloat($("#masterQuantity-" + id).val()) * parseFloat($("#masterUnitPrice-" + id).val()));
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

let today = new Date();
today = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
document.getElementById("masterDate").value = today;
document.getElementById("exportShippingDate").value = today;

// let fakeData = {
//   "masterLcList": [{
//     "lcNo": "MLC-38394394",
//     "buyerId": "1",
//     "sendBankId": "1",
//     "receiveBankId": "2",
//     "beneficiaryBankId": "3",
//     "throughBankId": "4",
//     "date": "2020-05-04",
//     "totalValue": "3000",
//     "currency": "2",
//     "maturityDate": "2021-04-02",
//     "remarks": "remarks test",
//     "amendmentList": [{
//       "amendmentNo": "1",
//       "amendmentDate": "2020-03-10"
//     }, {
//       "amendmentNo": "2",
//       "amendmentDate": "2020-03-11"
//     }],
//     "shippingList": [{
//       "shippingNo": "1",
//       "exportShippingDate": "2020-03-10",
//       "shippingMark": "TM-3"
//     }, {
//       "shippingNo": "2",
//       "exportShippingDate": "2020-04-09",
//       "shippingMark": "GH-23"
//     }],
//     "styleList": [{
//       "autoId": "1",
//       "styleId": "1",
//       "styleNo": "A734M",
//       "quantity": "4",
//       "unitPrice": "10",
//       "amount": "40"
//     }, {
//       "autoId": "2",
//       "styleId": "3",
//       "styleNo": "A7341W",
//       "quantity": "70",
//       "unitPrice": "40",
//       "amount": "2800"
//     }]
//   }],
//   "shippingInfoList": {
//     "1": {
//       "exportShippingDate": "2020-03-10",
//       "shippingMark": "TM-3",
//       "styleList": [{
//         "styleId": "1",
//         "styleNo": "A7340M",
//         "quantity": "20"
//       }, {
//         "styleId": "2",
//         "styleNo": "A7340J",
//         "quantity": "30"
//       }]
//     },
//     "2": {
//       "exportShippingDate": "2020-04-09",
//       "shippingMark": "GH-22",
//       "styleList": [{
//         "styleId": "3",
//         "styleNo": "A7341W",
//         "quantity": "80"
//       }, {
//         "styleId": "4",
//         "styleNo": "A7341L",
//         "quantity": "25"
//       }]

//     }
//   },
//   "amendmentInfoList": {
//     "1": {
//       "buyerId": "1",
//       "sendBankId": "3",
//       "receiveBankId": "1",
//       "beneficiaryBankId": "4",
//       "throughBankId": "2",
//       "date": "2020-03-10",
//       "totalValue": "3950",
//       "currency": "1",
//       "maturityDate": "2021-08-12",
//       "remarks": "Amendment Remarks",
//       "styleList": [{
//         "styleId": "1",
//         "styleNo": "A7340M",
//         "quantity": "90",
//         "unitPrice": "40",
//         "amount": "3600"
//       }, {
//         "styleId": "2",
//         "styleNo": "A7340J",
//         "quantity": "7",
//         "unitPrice": "50",
//         "amount": "350"
//       }]
//     },
//     "2": {
//       "buyerId": "1",
//       "sendBankId": "2",
//       "receiveBankId": "4",
//       "beneficiaryBankId": "3",
//       "throughBankId": "1",
//       "date": "2021-03-11",
//       "totalValue": "8000",
//       "currency": "1",
//       "maturityDate": "2021-08-12",
//       "remarks": "Amendment Remarks",
//       "styleList": [{
//         "styleId": "3",
//         "styleNo": "A7341W",
//         "quantity": "150",
//         "unitPrice": "20",
//         "amount": "3000"
//       }, {
//         "styleId": "4",
//         "styleNo": "A7341L",
//         "quantity": "200",
//         "unitPrice": "25",
//         "amount": "5000"
//       }]
//     }
//   }
// }
