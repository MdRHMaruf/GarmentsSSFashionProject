
let styleIdForSet = "0";
let itemIdForSet = "0";
let poIdForSet = "0";
let unitList = {};

window.onload = () => {
  document.title = "Master L/C";

  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getUnitList',
    data: {},
    success: function (data) {
      unitList = {};
      data.unitList.forEach(unit => {
        unitList[unit.unitId] = unit;
      });
    }
  });
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
        $("#importStyleNo").html(options);
        $("#exportStyleNo").html(options);
        $("#billStyleNo").html(options);
        $('#masterStyleNo').selectpicker('refresh');
        $('#importStyleNo').selectpicker('refresh');
        $('#billStyleNo').selectpicker('refresh');
        $('#exportStyleNo').selectpicker('refresh');
        $('#masterStyleNo').val(styleIdForSet).change();
        styleIdForSet = 0;

      }
    });
  } else {
    let options = "<option value='0' selected>Select Style</option>";
    $("#masterStyleNo").html(options);
    $("#importStyleNo").html(options);
    $("#billStyleNo").html(options);
    $("#exportStyleNo").html(options);
    $('#masterStyleNo').selectpicker('refresh');
    $('#importStyleNo').selectpicker('refresh');
    $('#billStyleNo').selectpicker('refresh');
    $('#exportStyleNo').selectpicker('refresh');
    $('#masterStyleNo').val("0").change();
  }

}

function styleWiseBuyerPOLoad(styleInput) {
  let styleId = styleInput.value;

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
        if (styleInput.id == "masterStyleNo") {
          $("#masterPurchaseOrder").html(options);
          $('#masterPurchaseOrder').selectpicker('refresh');
          $("#masterPurchaseOrder").val(poIdForSet).change();
        } else if (styleInput.id == "importStyleNo") {
          $("#importPurchaseOrder").html(options);
          $('#importPurchaseOrder').selectpicker('refresh');
          $("#importPurchaseOrder").val(poIdForSet).change();
        } else if (styleInput.id == "billStyleNo") {
          $("#billPurchaseOrder").html(options);
          $('#billPurchaseOrder').selectpicker('refresh');
          $("#billPurchaseOrder").val(poIdForSet).change();
        } else if (styleInput.id == "exportStyleNo") {
          $("#exportPurchaseOrder").html(options);
          $('#exportPurchaseOrder').selectpicker('refresh');
          $("#exportPurchaseOrder").val(poIdForSet).change();
        }

        poIdForSet = 0;
      }
    });
  } else {
    let options = "<option value='0' selected>Select Buyer PO</option>";
    if (styleInput.id == "masterStyleNo") {
      $("#masterPurchaseOrder").html(options);
      $('#masterPurchaseOrder').selectpicker('refresh');
      $("#masterPurchaseOrder").val(poIdForSet).change();
    } else if (styleInput.id == "importStyleNo") {
      $("#importPurchaseOrder").html(options);
      $('#importPurchaseOrder').selectpicker('refresh');
      $("#importPurchaseOrder").val(poIdForSet).change();
    } else if (styleInput.id == "billStyleNo") {
      $("#billPurchaseOrder").html(options);
      $('#billPurchaseOrder').selectpicker('refresh');
      $("#billPurchaseOrder").val(poIdForSet).change();
    } else if (styleInput.id == "exportStyleNo") {
      $("#exportPurchaseOrder").html(options);
      $('#exportPurchaseOrder').selectpicker('refresh');
      $("#exportPurchaseOrder").val(poIdForSet).change();
    }
    poIdForSet = 0;
  }
}


function styleWiseItemLoad(styleInput) {
  let styleId = styleInput.value;

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
        if (styleInput.id == "masterStyleNo") {
          $("#masterItemName").html(options);
          $('#masterItemName').selectpicker('refresh');
          $("#masterItemName").prop("selectedIndex", 1).change();
        } else if (styleInput.id == "exportStyleNo") {
          $("#exportItemName").html(options);
          $('#exportItemName').selectpicker('refresh');
          $("#exportItemName").prop("selectedIndex", 1).change();
        }
        itemIdForSet = 0;
      }
    });
  } else {
    let options = "<option value='0' selected>Select Item Type</option>";
    if (styleInput.id == "masterStyleNo") {
      $("#masterItemName").html(options);
      $('#masterItemName').selectpicker('refresh');
      $("#masterItemName").prop("selectedIndex", 1).change();
    } else if (styleInput.id == "exportStyleNo") {
      $("#exportItemName").html(options);
      $('#exportItemName').selectpicker('refresh');
      $("#exportItemName").prop("selectedIndex", 1).change();
    }
    itemIdForSet = 0;
  }

}

function itemTypeChangeAction() {
  let type = $("#importItemType").val();
  if (type != 0) {

    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getTypeWiseItems',
      data: {
        type: type
      },
      success: function (data) {
        let itemList = data.result;
        let options = "<option value='0' selected>Select Item Name</option>";
        let length = itemList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
        };
        $("#importFabricsAccessoriesItem").html(options);
        $('#importFabricsAccessoriesItem').selectpicker('refresh');
        $("#importFabricsAccessoriesItem").prop("selectedIndex", 0).change();
        itemIdForSet = 0;

      }
    });
  } else {
    let options = "<option value='0' selected>Select Item Type</option>";
    $("#importFabricsAccessoriesItem").html(options);
    $('#importFabricsAccessoriesItem').selectpicker('refresh');
    $('#importFabricsAccessoriesItem').val(itemIdForSet).change();
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

                row = `<tr id='masterUDRow-${id}' class='masterUDNewStyle' data-type='newStyle' data-master-lc-no='${masterLCNo}' data-buyer-id='${buyerId}' data-style-id='${styleId}' data-item-id='${itemId}' data-purchase-order-id='${purchaseOrderId}' >
                            <td id='masterUDStyleNo-${id}'>${styleNo}</td>
                            <td id='masterUDPurchaseOrder-${id}'>${purchaseOrder}</td>
                            <td><input type="number" id='masterUDQuantity-${id}' class="form-control-sm max-width-100" onfocusout="setUDAmount(${id})" value="${quantity}"/></td>
                            <td><input type="number" id='masterUDUnitPrice-${id}' class="form-control-sm max-width-100" onfocusout="setUDAmount(${id})" value="${unitPrice}"/></td>
                            <td id='masterUDAmount-${id}'>${amount}</td>
                            <td ><i class='fa fa-trash' onclick="deleteMasterStyle('${id}','new')" style="cursor:pointer;" title="Delete"></i></td>
                          </tr>`;

                $("#masterUDStyleList").append(row);

                totalValueCount();

                sessionObject = {
                  "buyerId": buyerId,
                  "masterLCNo": masterLCNo,
                  "itemList": itemList
                }

                sessionStorage.setItem("pendingMasterLcStyleItem", JSON.stringify(sessionObject));
              } else {
                alert("Empty Unit Price ... Please Select Unit Price");
                $("#masterUnitPrice").focus();
              }
            } else {
              alert("Empty Quantity ... Please Enter Quantity");
              $("#masterQuantity").focus();
            }
          } else {
            alert("Purchase Order selected... Please Select Purchase Order");
            $("#masterPurchaseOrder").focus();
          }
        } else {
          alert("Item Name not selected... Please Select Item Name");
          $("#masterItemName").focus();
        }
      } else {
        alert("Style No not selected... Please Select Style No");
        $("#masterStyleNo").focus();
      }

    } else {
      alert("Buyer not selected... Please Select Buyer Name");
      $("#masterBuyerName").focus();
    }
  } else {
    alert("Master LC No not entered... Please Enter Master LC No");
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

      $("#masterUDRow-" + autoId).remove();
    } else {
      $("#loader").show();
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
          $("#loader").hide();
        }
      });
    }
    totalValueCount();
  }

}


function deleteExportStyle(autoId, rowType) {
  if (confirm("Are you sure to Delete this Style?")) {
    if (rowType == 'new') {
      const buyerId = $("#exportRow-" + autoId).attr("data-buyer-id");
      const exportLCNo = $("#exportRow-" + autoId).attr("data-export-lc-no");
      const pendingExportLcStyleItem = JSON.parse(sessionStorage.getItem("pendingExportLcStyleItem"));
      const newItemList = pendingExportLcStyleItem.itemList.filter(item => item.buyerId != buyerId && item.exportLCNo != exportLCNo);
      pendingExportLcStyleItem.itemList = newItemList;
      sessionStorage.setItem("pendingExportLcStyleItem", JSON.stringify(pendingExportLcStyleItem));

      $("#exportRow-" + autoId).remove();
    } else {
      $("#loader").show();
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './deleteExportLcStyle',
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
            $("#exportStyleList").empty();
            drawItemDataTable(costingList);
            if (sessionStorage.getItem("pendingExportLcStyleItem")) {
              const pendingExportLcStyleItem = JSON.parse(sessionStorage.getItem("pendingExportLcStyleItem"));
              if (styleId == pendingExportLcStyleItem.styleId && itemId == pendingExportLcStyleItem.itemId) {
                drawSessionDataTable(pendingExportLcStyleItem.itemList);
              }
            }
          }
          $("#loader").hide();
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
    let udNo = $("#masterUDNo").val();
    let udDate = $("#masterUdDate").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    let udStyleItems = {};
    udStyleItems['list'] = [];

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

              let udRowList = $("#masterUDStyleList tr");
              length = udRowList.length;
              for (let i = 0; i < length; i++) {
                const newRow = udRowList[i];
                const id = newRow.id.slice(12);

                const item = {
                  buyerId: newRow.getAttribute('data-buyer-id'),
                  styleId: newRow.getAttribute('data-style-id'),
                  itemId: newRow.getAttribute('data-item-id'),
                  purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                  quantity: $("#masterUDQuantity-" + id).val(),
                  unitPrice: $("#masterUDUnitPrice-" + id).val(),
                  amount: $("#masterUDAmount-" + id).text(),
                  userId: userId
                }

                udStyleItems.list.push(item);
              }
              if (confirm("Are you sure to confirm..")) {
                $("#loader").show();
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
                    udNo: udNo,
                    udDate: udDate,
                    remarks: remarks,
                    userId: userId,
                    amendmentNo: '0',
                    amendmentDate: date,
                    styleList: JSON.stringify(styleItems),
                    udStyleList: JSON.stringify(udStyleItems),
                  },
                  success: function (data) {
                    if (data.result == 'success') {
                      alert("Successfully Submitted");
                      $("#masterAmendmentList").empty();
                      drawMasterLCAmendmentList(data.amendmentList);
                    } else {
                      alert("Master LC Insertion Failed")
                    }
                    $("#loader").hide();
                  }
                });

              }
            } else {
              alert("Please Select Currency...");
              $("#masterCurrency").focus();
            }
          } else {
            alert("Please Select Receive Bank Name...");
            $("#masterReceiveBankName").focus();
          }
        } else {
          alert("Please Select Send Bank Name...");
          $("#masterSendBankName").focus();
        }
      } else {
        alert("Please Select Buyer Name...");
        $("#masterBuyerName").focus();
      }
    } else {
      alert("Please Enter Master LC No...");
      $("#masterLCNo").focus();
    }
  } else {
    alert("Please Enter Any Style Item...");
  }

}

function masterEditAction() {
  let rowList = $("#masterStyleList tr");
  let length = rowList.length;

  if (length > 0) {
    rowList = $("tr.masterNewStyle");
    length = rowList.length;
    let masterLCAutoId = $("#masterLCAutoId").val();
    let masterLCNo = $("#masterLCNo").val();
    let previousMasterLCNo = $("#previousMasterLCNo").val();
    let amendmentNo = $("#masterAmendmentNo").val();
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
    let previousUdNo = $("#previousUdNo").val();
    let udNo = $("#masterUDNo").val();
    let udDate = $("#masterUdDate").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    let editedStyleItems = {};
    editedStyleItems['list'] = [];

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

              let editedRowList = $("#masterStyleList tr.editedRow");
              length = editedRowList.length;

              for (let i = 0; i < length; i++) {
                const editedRow = editedRowList[i];
                const id = editedRow.id.slice(10);

                const item = {
                  autoId: editedRow.getAttribute('data-auto-id'),
                  buyerId: editedRow.getAttribute('data-buyer-id'),
                  styleId: editedRow.getAttribute('data-style-id'),
                  itemId: editedRow.getAttribute('data-item-id'),
                  purchaseOrderId: editedRow.getAttribute('data-purchase-order-id'),
                  quantity: $("#masterQuantity-" + id).val(),
                  unitPrice: $("#masterUnitPrice-" + id).val(),
                  amount: $("#masterAmount-" + id).text(),
                  userId: userId
                }

                editedStyleItems.list.push(item);
              }
              if (confirm("Are you sure to confirm..")) {
                $("#loader").show();
                $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: './masterLCEdit',
                  data: {
                    autoId: masterLCAutoId,
                    masterLCNo: masterLCNo,
                    previousMasterLCNo: previousMasterLCNo,
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
                    udNo: udNo,
                    previousUdNo: previousUdNo,
                    udDate: udDate,
                    userId: userId,
                    amendmentNo: amendmentNo,
                    amendmentDate: date,
                    styleList: JSON.stringify(styleItems),
                    editedStyleList: JSON.stringify(editedStyleItems)
                  },
                  success: function (data) {
                    if (data.result == 'success') {
                      alert("Successfully Submitted");
                      $("#masterAmendmentList").empty();
                      drawMasterLCAmendmentList(data.amendmentList);
                    } else {
                      alert("Master LC Insertion Failed")
                    }
                    $("#loader").hide();
                  }
                });

              }
            } else {
              alert("Please Select Currency...");
              $("#masterCurrency").focus();
            }
          } else {
            alert("Please Select Receive Bank Name...");
            $("#masterReceiveBankName").focus();
          }
        } else {
          alert("Please Select Send Bank Name...");
          $("#masterSendBankName").focus();
        }
      } else {
        alert("Please Select Buyer Name...");
        $("#masterBuyerName").focus();
      }
    } else {
      alert("Please Enter Master LC No...");
      $("#masterLCNo").focus();
    }
  } else {
    alert("Please Enter Any Style Item...");
  }
}


function masterUdEditAction() {
  let rowList = $("#masterUDStyleList tr");
  let length = rowList.length;

  if (length > 0) {
    rowList = $("tr.masterNewStyle");
    length = rowList.length;
    let masterLCAutoId = $("#masterLCAutoId").val();
    let masterUDAutoId = $("#masterUdAutoId").val();
    let masterLCNo = $("#masterLCNo").val();
    let amendmentNo = $("#masterAmendmentNo").val();
    let date = $("#masterDate").val();
    let udNo = $("#masterUDNo").val();
    let udDate = $("#masterUdDate").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    let editedStyleItems = {};
    editedStyleItems['list'] = [];

    if (masterLCNo != '') {

      for (let i = 0; i < length; i++) {
        const newRow = rowList[i];
        const id = newRow.id.slice(12);

        const item = {
          buyerId: newRow.getAttribute('data-buyer-id'),
          styleId: newRow.getAttribute('data-style-id'),
          itemId: newRow.getAttribute('data-item-id'),
          purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
          quantity: $("#masterUDQuantity-" + id).val(),
          unitPrice: $("#masterUDUnitPrice-" + id).val(),
          amount: $("#masterUDAmount-" + id).text(),
          userId: userId
        }

        styleItems.list.push(item);
      }

      let editedRowList = $("#masterUDStyleList tr.editedRow");
      length = editedRowList.length;

      for (let i = 0; i < length; i++) {
        const editedRow = editedRowList[i];
        const id = editedRow.id.slice(12);

        const item = {
          autoId: editedRow.getAttribute('data-auto-id'),
          buyerId: editedRow.getAttribute('data-buyer-id'),
          styleId: editedRow.getAttribute('data-style-id'),
          itemId: editedRow.getAttribute('data-item-id'),
          purchaseOrderId: editedRow.getAttribute('data-purchase-order-id'),
          quantity: $("#masterUDQuantity-" + id).val(),
          unitPrice: $("#masterUDUnitPrice-" + id).val(),
          amount: $("#masterUDAmount-" + id).text(),
          userId: userId
        }

        editedStyleItems.list.push(item);
      }
      if (confirm("Are you sure to confirm..")) {
        $("#loader").show();
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './masterUDEdit',
          data: {
            autoId: masterLCAutoId,
            udAutoId: masterUDAutoId,
            masterLCNo: masterLCNo,
            udNo: udNo,
            udDate: udDate,
            userId: userId,
            amendmentNo: amendmentNo,
            amendmentDate: date,
            styleList: JSON.stringify(styleItems),
            editedStyleList: JSON.stringify(editedStyleItems)
          },
          success: function (data) {
            if (data.result == 'success') {
              alert("Successfully Submitted");
              $("#masterAmendmentList").empty();
              drawMasterLCAmendmentList(data.amendmentList);
            } else {
              alert("Master LC Insertion Failed")
            }
            $("#loader").hide();
          }
        });

      }

    } else {
      alert("Please Enter Master LC No...");
      $("#masterLCNo").focus();
    }
  } else {
    alert("Please Enter Any Style Item...");
  }
}


function masterAmendmentAction() {
  let rowList = $("#masterStyleList tr");
  let length = rowList.length;

  if (length > 0) {
    let styleList = '';

    let masterLCNo = $("#masterLCNo").val();
    let previousMasterLCNo = $("#previousMasterLCNo").val();
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
                $("#loader").show();
                $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: './masterLCAmendment',
                  data: {
                    masterLCNo: masterLCNo,
                    previousMasterLCNo: previousMasterLCNo,
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
                      $("#masterAmendmentList").empty();
                      drawMasterLCAmendmentList(data.amendmentList);
                    } else {
                      alert("Master LC Insertion Failed")
                    }
                    $("#loader").hide();
                  }
                });

              }
            } else {
              alert("Please Select Currency...");
              $("#masterCurrency").focus();
            }
          } else {
            alert("Please Select Receive Bank Name...");
            $("#masterReceiveBankName").focus();
          }
        } else {
          alert("Please Select Send Bank Name...");
          $("#masterSendBankName").focus();
        }
      } else {
        alert("Please Select Buyer Name...");
        $("#masterBuyerName").focus();
      }
    } else {
      alert("Please Enter Master LC No...");
      $("#masterLCNo").focus();
    }
  } else {
    alert("Please Enter Any Style Item...");
  }

}


function masterUdAmendmentAction() {
  let rowList = $("#masterUDStyleList tr");
  let length = rowList.length;

  if (length > 0) {
    let styleList = '';

    let masterLCNo = $("#masterLCNo").val();
    let udNo = $("#masterUDNo").val();
    let userId = $("#userId").val();
    let date = $("#masterDate").val();
    let styleItems = {};
    styleItems['list'] = [];

    if (masterLCNo != '') {
      for (let i = 0; i < length; i++) {
        const newRow = rowList[i];
        const id = newRow.id.slice(12);

        const item = {
          buyerId: newRow.getAttribute('data-buyer-id'),
          styleId: newRow.getAttribute('data-style-id'),
          itemId: newRow.getAttribute('data-item-id'),
          purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
          quantity: $("#masterUDQuantity-" + id).val(),
          unitPrice: $("#masterUDUnitPrice-" + id).val(),
          amount: $("#masterUDAmount-" + id).text(),
          userId: userId
        }

        styleItems.list.push(item);
      }
      if (confirm("Are you sure to confirm..")) {
        $("#loader").show();
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './masterUDAmendment',
          data: {
            masterLCNo: masterLCNo,
            udNo: udNo,
            userId: userId,
            amendmentNo: '0',
            amendmentDate: date,
            udStyleList: JSON.stringify(styleItems),
          },
          success: function (data) {
            if (data.result == 'success') {
              alert("Successfully Submitted");
              $("#masterAmendmentList").empty();
              drawMasterLCAmendmentList(data.amendmentList);
            } else {
              alert("Master LC Insertion Failed")
            }
            $("#loader").hide();
          }
        });

      }

    } else {
      alert("Please Enter Master LC No...");
      $("#masterLCNo").focus();
    }
  } else {
    alert("Please Enter Any Style Item...");
  }

}

function searchMasterLc(masterLCNo, buyerId, amendmentNo) {

  //const masterLc = fakeData.masterLcList[autoId];
  $("#loader").show();
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

      const masterLCInfo = data.masterLCInfo;
      const masterLCStyles = data.masterLCStyles;
      $("#masterLCAutoId").val(masterLCInfo.autoId);
      $("#masterLCNo").val(masterLCInfo.masterLCNo);
      $("#masterLCNo").prop('readonly', true);
      $("#importMasterLcNo").val(masterLCInfo.masterLCNo);
      $("#exportMasterLcNo").val(masterLCInfo.masterLCNo);
      $("#previousMasterLCNo").val(masterLCInfo.masterLCNo);
      $("#masterAmendmentNo").val(masterLCStyles.amendmentNo);
      $("#masterBuyerName").val(masterLCInfo.buyerId).change();
      let buyerName = $("#masterBuyerName option:selected").text();
      $("#exportBuyerName").val(buyerName);
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

      $("#previousUdNo").val(masterLCInfo.udNo);
      $("#masterUDNo").val(masterLCInfo.udNo);
      $("#masterUdDate").val(masterLCInfo.udDate);
      $("#masterUdAmendmentList").empty();
      drawMasterUDAmendmentList(data.masterUDAmendmentList);
      loadImportUDSelect(data.masterUDAmendmentList);
      $("#exportNotifyTo").empty();
      loadNotifyer(data.notifyerList);
      $("#masterStyleList").empty();
      drawMasterLCStyleList(masterLCStyles);
      $("#masterAmendmentList").empty();
      drawMasterLCAmendmentList(data.amendmentList);
      $("#importInvoiceList").empty();
      drawImportInvoiceList(data.importInvoiceList);
      $("#exportShipmentList").empty();
      drawExportLCInvoiceList(data.exportInvoiceList);
      $("#masterSubmitBtn").hide();
      $("#masterAmendmentBtn").show();
      $("#masterEditBtn").show();
      $("#masterUdAmendmentBtn").show();
      $("#masterUdEditBtn").show();
      $("#masterPreviewBtn").show();
      $("#loader").hide();
    }
  });

  $("#searchModal").modal('hide');
}

function loadImportUDSelect(dataList) {

  let options = "<option value='0' selected>Select UD Amendment</option>";
  let length = dataList.length;
  for (let i = 0; i < length; i++) {
    options += "<option value='" + dataList[i].autoId + "'>" + dataList[i].udAmendmentNo + " Date:" + dataList[i].udAmendmentDate + "</option>";
  };
  $("#importUdAmendmentNo").html(options);
  $("#importUdAmendmentNo").selectpicker('refresh');
}


function setUdStyles(autoId) {

  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchMasterUD',
    data: {
      autoId: autoId
    },
    success: function (data) {

      let udInfo = data.udInfo;
      $("#masterUDStyleList").empty();
      drawMasterUDStyleList(data.udStyles);
      $("#loader").hide();
    }
  });

}


function loadNotifyer(data) {
  let options = '';
  data.forEach((notify, index) => {
    options += `<option value='${notify.id}'>${notify.name}</option>`;
  });
  $("#exportNotifyTo").append(options);
  $("#exportNotifyTo").selectpicker('refresh');
}

function refreshAction() {
  location.reload();
}


function exportStyleAddAction() {

  const rowList = $("#exportStyleList tr");
  const length = rowList.length;

  let masterLCNo = $("#masterLCNo").val();
  let buyerName = $("#masterBuyerName option:selected").text();
  let buyerId = $("#masterBuyerName").val();
  let styleNo = $("#exportStyleNo option:selected").text();
  let styleId = $("#exportStyleNo").val();
  let itemName = $("#exportItemName option:selected").text();
  let itemId = $("#exportItemName").val();
  let purchaseOrder = $("#exportPurchaseOrder option:selected").text();
  let purchaseOrderId = $("#exportPurchaseOrder").val();
  let quantity = $("#exportQuantity").val() == "" ? 0 : $("#exportQuantity").val();
  let unitPrice = $("#exportUnitPrice").val() == "" ? 0 : $("#exportUnitPrice").val();
  let amount = parseFloat(quantity) * parseFloat(unitPrice);
  let cartonQty = $("#exportCartonQty").val() == "" ? 0 : $("#exportCartonQty").val();


  let sessionObject = JSON.parse(sessionStorage.getItem("pendingExportLcStyleItem") ? sessionStorage.getItem("pendingExportLcStyleItem") : "{}");
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
                  "amount": amount,
                  "cartonQty": cartonQty
                });
                let row = `<tr id='exportRow-${id}' class='exportNewStyle' data-type='newStyle' data-master-lc-no='${masterLCNo}' data-buyer-id='${buyerId}' data-style-id='${styleId}' data-item-id='${itemId}' data-purchase-order-id='${purchaseOrderId}' >
                            <td id='exportStyleNo-${id}'>${styleNo}</td>
                            <td id='exportPurchaseOrder-${id}'>${purchaseOrder}</td>
                            <td><input type="number" id='exportQuantity-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${quantity}"/></td>
                            <td><input type="number" id='exportUnitPrice-${id}' class="form-control-sm max-width-100" onfocusout="setAmount(${id}),totalValueCount()" value="${unitPrice}"/></td>
                            <td id='exportAmount-${id}'>${amount}</td>
                            <td><input type="number" id='exportCartonQty-${id}' class="form-control-sm max-width-100"  value="${cartonQty}"/></td>
                            <td ><i class='fa fa-trash' onclick="deleteExportStyle('${id}','new')" style="cursor:pointer;" title="Delete"></i></td>
                          </tr>`;

                $("#exportStyleList").append(row);

                totalValueCount();

                sessionObject = {
                  "buyerId": buyerId,
                  "masterLCNo": masterLCNo,
                  "itemList": itemList
                }

                sessionStorage.setItem("pendingExportLcStyleItem", JSON.stringify(sessionObject));
              } else {
                alert("Empty Unit Price ... Please Select Unit Price");
                $("#exportUnitPrice").focus();
              }
            } else {
              alert("Empty Quantity ... Please Enter Quantity");
              $("#exportQuantity").focus();
            }
          } else {
            alert("Purchase Order selected... Please Select Purchase Order");
            $("#exportPurchaseOrder").focus();
          }
        } else {
          alert("Item Name not selected... Please Select Item Name");
          $("#exportItemName").focus();
        }
      } else {
        alert("Style No not selected... Please Select Style No");
        $("#exportStyleNo").focus();
      }

    } else {
      alert("Buyer not selected... Please Select Buyer Name");
      $("#exportBuyerName").focus();
    }
  } else {
    alert("Export LC No not entered... Please Enter Export LC No");
    $("#masterLCNo").focus();
  }

}

function exportSubmitAction() {
  let rowList = $("#exportStyleList tr");
  let length = rowList.length;

  if (length > 0) {
    rowList = $("tr.exportNewStyle");
    length = rowList.length;

    let styleList = '';

    let masterLCNo = $("#masterLCNo").val();
    let buyerId = $("#masterBuyerName").val();
    let notifyTo = $("#exportNotifyTo").val();
    let exportInvoiceNo = $("#exportInvoiceNo").val();
    let exportInvoiceDate = $("#exportInvoiceDate").val();
    let exportContractNo = $("#exportContractNo").val();
    let exportContractDate = $("#exportContractDate").val();
    let expNo = $("#exportExpNo").val();
    let expDate = $("#exportExpDate").val();
    let billEntryNo = $("#exportBillEntryNo").val();
    let billEntryDate = $("#exportBillEntryDate").val();
    let blNo = $("#exportBLNo").val();
    let blDate = $("#exportBLDate").val();
    let shippingMark = $("#exportShippingMark").val();
    let shippingDate = $("#exportShippingDate").val();

    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    if (masterLCNo != '') {
      if (buyerId != '0') {
        if (exportInvoiceNo != '') {
          if (exportInvoiceDate) {
            if (billEntryNo != '') {
              if (billEntryDate) {
                for (let i = 0; i < length; i++) {
                  const newRow = rowList[i];
                  const id = newRow.id.slice(10);

                  const item = {
                    buyerId: newRow.getAttribute('data-buyer-id'),
                    styleId: newRow.getAttribute('data-style-id'),
                    itemId: newRow.getAttribute('data-item-id'),
                    purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                    quantity: $("#exportQuantity-" + id).val(),
                    unitPrice: $("#exportUnitPrice-" + id).val(),
                    amount: $("#exportAmount-" + id).text(),
                    cartonQty: $("#exportCartonQty-" + id).val(),
                    userId: userId
                  }

                  styleItems.list.push(item);
                }
                if (confirm("Are you sure to confirm..")) {
                  $("#loader").show();
                  $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './exportLCSubmit',
                    data: {
                      masterLCNo: masterLCNo,
                      buyerId: buyerId,
                      notifyTo: notifyTo,
                      invoiceNo: exportInvoiceNo,
                      invoiceDate: exportInvoiceDate,
                      contractNo: exportContractNo,
                      contractDate: exportContractDate,
                      expNo: expNo,
                      expDate: expDate,
                      billEntryNo: billEntryNo,
                      billEntryDate: billEntryDate,
                      blNo: blNo,
                      blDate: blDate,
                      shippingMark: shippingMark,
                      shippingDate: shippingDate,
                      userId: userId,
                      styleList: JSON.stringify(styleItems),
                    },
                    success: function (data) {
                      if (data.result == 'success') {
                        alert("Successfully Submitted");
                        $("#exportShipmentList").empty();
                        drawExportLCInvoiceList(data.exportInvoiceList);
                      } else {
                        alert("Export LC Insertion Failed")
                      }
                      $("#loader").hide();
                    }
                  });

                }


              } else {
                alert("Please Select Bill Entry Date...");
                $("#exportBillEntryDate").focus();
              }
            } else {
              alert("Please Enter Bill Entry No...");
              $("#exportBillEntryNo").focus();
            }
          } else {
            alert("Please Select Export Invoice Date...");
            $("#exportInvoiceDate").focus();
          }
        } else {
          alert("Please Enter Invoice No...");
          $("#exportInvoiceNo").focus();
        }
      } else {
        alert("Please Select Buyer Name...");
        $("#exportBuyerName").focus();
      }
    } else {
      alert("Please Enter Export LC No...");
      $("#exportLCNo").focus();
    }
  } else {
    alert("Please Enter Any Style Item...");
  }

}


function searchExportLc(masterLCNo, invoiceNO) {

  //const exportLc = fakeData.exportLcList[autoId];
  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchExportLC',
    data: {
      masterLCNo: masterLCNo,
      invoiceNo: invoiceNO
    },
    success: function (data) {

      const exportLCInfo = data.exportLCInfo;
      const exportLCStyles = data.exportLCStyles;
      $("#exportLCAutoId").val(exportLCInfo.autoId);
      $("#exportInvoiceNo").val(exportLCInfo.invoiceNo);
      $("#exportInvoiceDate").val(exportLCInfo.invoiceDate);
      $("#exportContractNo").val(exportLCStyles.contractNo);
      $("#exportContractDate").val(exportLCStyles.contractDate);

      $("#exportExpNo").val(exportLCInfo.expNo);
      $("#exportExpDate").val(exportLCInfo.expDate);
      $("#exportBillEntryNo").val(exportLCInfo.billEntryNo);
      $("#exportBillEntryDate").val(exportLCInfo.billEntryDate);
      $("#exportBLNo").val(exportLCInfo.blNo);
      $("#exportBLDate").val(exportLCInfo.blDate);
      $("#exportShippingMark").val(exportLCInfo.shippingMark);
      $("#exportShippingDate").val(exportLCInfo.shippingDate);

      $("#exportStyleList").empty();
      drawExportLCStyleList(exportLCStyles);

      $("#exportSubmitBtn").hide();
      $("#exportAmendmentBtn").show();
      $("#exportEditBtn").show();
      $("#exportPreviewBtn").show();
      $("#loader").hide();
    }
  });

  $("#searchModal").modal('hide');
}

function exportEditAction() {
  let rowList = $("#exportStyleList tr");
  let length = rowList.length;

  if (length > 0) {
    rowList = $("tr.exportNewStyle");
    length = rowList.length;

    let styleList = '';
    let autoId = $("#exportLCAutoId").val();
    let masterLCNo = $("#masterLCNo").val();
    let buyerId = $("#masterBuyerName").val();
    let notifyTo = $("#exportNotifyTo").val();
    let exportInvoiceNo = $("#exportInvoiceNo").val();
    let exportInvoiceDate = $("#exportInvoiceDate").val();
    let exportContractNo = $("#exportContractNo").val();
    let exportContractDate = $("#exportContractDate").val();
    let expNo = $("#exportExpNo").val();
    let expDate = $("#exportExpDate").val();
    let billEntryNo = $("#exportBillEntryNo").val();
    let billEntryDate = $("#exportBillEntryDate").val();
    let blNo = $("#exportBLNo").val();
    let blDate = $("#exportBLDate").val();
    let shippingMark = $("#exportShippingMark").val();
    let shippingDate = $("#exportShippingDate").val();

    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    let editedStyleItems = {};
    editedStyleItems['list'] = [];

    if (masterLCNo != '') {
      if (buyerId != '0') {
        if (exportInvoiceNo != '') {
          if (exportInvoiceDate) {
            if (billEntryNo != '') {
              if (billEntryDate) {
                for (let i = 0; i < length; i++) {
                  const newRow = rowList[i];
                  const id = newRow.id.slice(10);

                  const item = {
                    buyerId: newRow.getAttribute('data-buyer-id'),
                    styleId: newRow.getAttribute('data-style-id'),
                    itemId: newRow.getAttribute('data-item-id'),
                    purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                    quantity: $("#exportQuantity-" + id).val(),
                    unitPrice: $("#exportUnitPrice-" + id).val(),
                    amount: $("#exportAmount-" + id).text(),
                    cartonQty: $("#exportCartonQty-" + id).text(),
                    userId: userId
                  }

                  styleItems.list.push(item);
                }

                let editedRowList = $("#exportStyleList tr.editedRow");
                length = editedRowList.length;

                for (let i = 0; i < length; i++) {
                  const editedRow = editedRowList[i];
                  const id = editedRow.id.slice(10);

                  const item = {
                    buyerId: newRow.getAttribute('data-buyer-id'),
                    styleId: newRow.getAttribute('data-style-id'),
                    itemId: newRow.getAttribute('data-item-id'),
                    purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                    quantity: $("#exportQuantity-" + id).val(),
                    unitPrice: $("#exportUnitPrice-" + id).val(),
                    amount: $("#exportAmount-" + id).text(),
                    cartonQty: $("#exportCartonQty-" + id).text(),
                    userId: userId
                  }

                  editedStyleItems.list.push(item);
                }
                if (confirm("Are you sure to confirm..")) {
                  $("#loader").show();
                  $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './exportLCEdit',
                    data: {
                      autoId: autoId,
                      masterLCNo: masterLCNo,
                      buyerId: buyerId,
                      notifyTo: notifyTo,
                      invoiceNo: exportInvoiceNo,
                      invoiceDate: exportInvoiceDate,
                      contractNo: exportContractNo,
                      contractDate: exportContractDate,
                      expNo: expNo,
                      expDate: expDate,
                      billEntryNo: billEntryNo,
                      billEntryDate: billEntryDate,
                      blNo: blNo,
                      blDate: blDate,
                      shippingMark: shippingMark,
                      shippingDate: shippingDate,
                      userId: userId,
                      styleList: JSON.stringify(styleItems),
                      editedStyleList: JSON.stringify(editedStyleItems)
                    },
                    success: function (data) {
                      if (data.result == 'success') {
                        alert("Successfully Edited");
                        //$("#exportShipmentList").empty();
                        //drawExportLCInvoiceList(data.exportInvoiceList);
                      } else {
                        alert("Export LC Insertion Failed")
                      }
                      $("#loader").hide();
                    }
                  });

                }
              } else {
                alert("Please Select Bill Entry Date...");
                $("#exportBillEntryDate").focus();
              }
            } else {
              alert("Please Enter Bill Entry No...");
              $("#exportBillEntryNo").focus();
            }
          } else {
            alert("Please Select Export Invoice Date...");
            $("#exportInvoiceDate").focus();
          }
        } else {
          alert("Please Enter Invoice No...");
          $("#exportInvoiceNo").focus();
        }
      } else {
        alert("Please Select Buyer Name...");
        $("#exportBuyerName").focus();
      }
    } else {
      alert("Please Enter Export LC No...");
      $("#exportLCNo").focus();
    }
  } else {
    alert("Please Enter Any Style Item...");
  }

}

function exportRefreshAction() {
  $("#exportNotifyTo").val(0);
  $("#exportInvoiceNo").val('');
  $("#exportContractNo").val('');
  $("#exportExpNo").val('');
  $("#exportBillEntryNo").val('');
  $("#exportBLNo").val('');
  $("#exportShippingMark").val('');
  $("#exportStyleList").empty();

  $("#exportSubmitBtn").show();
  $("#exportAmendmentBtn").hide();
  $("#exportEditBtn").hide();
  $("#exportPreviewBtn").hide();
}

function drawMasterLCAmendmentList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='masterAmendmentRow-${id}'  data-amendment-auto-id='${id}' onclick="searchMasterLc('${rowData.masterLCNo}','${rowData.buyerId}','${rowData.amendmentNo}')" style='cursor:pointer;'>
					<td id='masterAmendmentNo-${id}'>${rowData.amendmentNo}</td>
					<td id='masterAmendmentDate-${id}'>${rowData.amendmentDate}</td>
				</tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }

  $("#masterAmendmentList").append(rows);

}


function drawExportLCInvoiceList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='exportInvoiceRow-${id}'  data-amendment-auto-id='${id}' onclick="searchExportLc('${rowData.masterLCNo}','${rowData.invoiceNo}')" style='cursor:pointer;'>
					<td id='exportInvoiceNo-${id}'>${rowData.invoiceNo}</td>
					<td id='exportShippingMark-${id}'>${rowData.shippingMark}</td>
          <td id='exportShippingDate-${id}'>${rowData.shippingDate}</td>
				</tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }

  $("#exportShipmentList").append(rows);

}


function drawMasterLCStyleList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='masterRow-${id}' class='masterOldStyle' data-auto-id='${rowData.autoId}' data-type='oldStyle' data-master-lc-no='${rowData.masterLCNo}' data-buyer-id='${rowData.buyerId}' data-style-id='${rowData.styleId}' data-item-id='${rowData.itemId}' data-purchase-order-id='${rowData.purchaseOrderId}' >
              <td id='masterStyleNo-${id}'>${rowData.styleNo}</td>
              <td id='masterPurchaseOrder-${id}'>${rowData.purchaseOrder}</td>
              <td><input type="number" id='masterQuantity-${id}' class="form-control-sm max-width-100" onkeyup="editedMasterRow('${id}')" onfocusout="setAmount(${id}),totalValueCount()" value="${Number(rowData.quantity).toFixed(2)}"/></td>
              <td><input type="number" id='masterUnitPrice-${id}' class="form-control-sm max-width-100" onkeyup="editedMasterRow('${id}')" onfocusout="setAmount(${id}),totalValueCount()" value="${Number(rowData.unitPrice).toFixed(2)}"/></td>
              <td id='masterAmount-${id}'>${Number(rowData.amount).toFixed(2)}</td>
              <td ><i class='fa fa-trash' onclick="deleteMasterStyle('${id}','new')" style="cursor:pointer;" title="Delete"></i></td>
            </tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }
  $("#masterStyleList").append(rows);
}



function drawMasterUDStyleList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='masterUDRow-${id}' class='masterOldStyle' data-auto-id='${rowData.autoId}' data-type='oldStyle' data-master-lc-no='${rowData.masterLCNo}' data-buyer-id='${rowData.buyerId}' data-style-id='${rowData.styleId}' data-item-id='${rowData.itemId}' data-purchase-order-id='${rowData.purchaseOrderId}' >
              <td id='masterUDStyleNo-${id}'>${rowData.styleNo}</td>
              <td id='masterUDPurchaseOrder-${id}'>${rowData.purchaseOrder}</td>
              <td><input type="number" id='masterUDQuantity-${id}' class="form-control-sm max-width-100" onkeyup="editedMasterUDRow('${id}')" onfocusout="setUDAmount(${id})" value="${Number(rowData.quantity).toFixed(2)}"/></td>
              <td><input type="number" id='masterUDUnitPrice-${id}' class="form-control-sm max-width-100" onkeyup="editedMasterUDRow('${id}')" onfocusout="setUDAmount(${id})" value="${Number(rowData.unitPrice).toFixed(2)}"/></td>
              <td id='masterUDAmount-${id}'>${Number(rowData.amount).toFixed(2)}</td>
              <td ><i class='fa fa-trash' onclick="deleteMasterStyle('${id}','new')" style="cursor:pointer;" title="Delete"></i></td>
            </tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }
  $("#masterUDStyleList").append(rows);
}



function drawExportLCStyleList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    console.log("row = ",rowData);
    const id = rowData.autoId;
    rows += `<tr id='exportRow-${id}' class='exportOldStyle' data-auto-id='${rowData.autoId}' data-type='oldStyle' data-export-lc-no='${rowData.exportLCNo}' data-buyer-id='${rowData.buyerId}' data-style-id='${rowData.styleId}' data-item-id='${rowData.itemId}' data-purchase-order-id='${rowData.purchaseOrderId}' >
              <td id='exportStyleNo-${id}'>${rowData.StyleNo}</td>
              <td id='exportPurchaseOrder-${id}'>${rowData.purchaseOrder}</td>
              <td><input type="number" id='exportQuantity-${id}' class="form-control-sm max-width-100" onkeyup="editedExportRow('${id}')" onfocusout="setAmount(${id}),totalValueCount()" value="${Number(rowData.quantity).toFixed(2)}"/></td>
              <td><input type="number" id='exportUnitPrice-${id}' class="form-control-sm max-width-100" onkeyup="editedExportRow('${id}')" onfocusout="setAmount(${id}),totalValueCount()" value="${Number(rowData.unitPrice).toFixed(2)}"/></td>
              <td id='exportAmount-${id}'>${Number(rowData.amount).toFixed(2)}</td>
              <td><input type="number" id='exportCartonQty-${id}' class="form-control-sm max-width-60" onfocusout="setAmount(${id}),totalValueCount()" value="${Number(rowData.cartonQty).toFixed(0)}"/></td>
              <td ><i class='fa fa-trash' onclick="deleteExportStyle('${id}','new')" style="cursor:pointer;" title="Delete"></i></td>
            </tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }
  $("#exportStyleList").append(rows);
}


function drawImportLCAmendmentList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='importAmendmentRow-${id}'  data-amendment-auto-id='${id}' onclick="searchImportInvoiceLc('${rowData.masterLCNo}','${rowData.invoiceNo}','${rowData.amendmentNo}')" style='cursor:pointer;'>
					<td id='importAmendmentNo-${id}'>${rowData.amendmentNo}</td>
					<td id='importAmendmentDate-${id}'>${rowData.amendmentDate}</td>
				</tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }

  $("#importAmendmentList").append(rows);

}

function drawMasterUDAmendmentList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='udRow-${id}' onclick="setUdStyles(${id})"  data-master-auto-id='${id}' style='cursor:pointer;'>
					<td id='masterUDNo-${id}'>${rowData.udAmendmentNo}</td>
					<td id='masterUDDate-${id}'>${rowData.udAmendmentDate}</td>
				</tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }

  $("#masterUdAmendmentList").append(rows);

}

function drawImportInvoiceList(data) {
  let rows = "";
  const length = data.length;
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = i;
    rows += `<tr id='importAmendmentRow-${id}'  data-amendment-auto-id='${id}' onclick="searchImportInvoiceLc('${rowData.masterLCNo}','${rowData.invoiceNo}','${rowData.amendmentNo}')" style='cursor:pointer;'>
					<td id='importAmendmentNo-${id}'>${rowData.invoiceNo}</td>
					<td id='importAmendmentDate-${id}'>${rowData.invoiceDate}</td>
				</tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }
  $("#importInvoiceList").append(rows);

}

function drawImportItemList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='importRow-${id}' data-style-id='${rowData.styleId}' data-purchase-order-id='${rowData.poNo}' data-item-type='${rowData.accessoriesItemType}' data-accessories-item-id='${rowData.accessoriesItemId}' data-color-id='${rowData.colorId}' data-unit-id='${rowData.unitId}'>
    <td id='importStyleNo-${id}'>${rowData.styleNo}</td>
    <td id='importPoNo-${id}'>${rowData.poNo}</td>
    <td id='importAccessoriesName-${id}'>${rowData.accessoriesName}</td>
    <td id='importColor-${id}'>${rowData.colorName}</td>
    <td id='importSize-${id}'>${rowData.size}</td>
    <td id='importUnit-${id}'>${rowData.unitName}</td>
    <td id='importWidth-${id}'>${Number(rowData.width).toFixed(2)}</td>
    <td id='importGsm-${id}'>${Number(rowData.gsm).toFixed(2)}</td>
    <td id='importTotalQty-${id}'>${Number(rowData.totalQty).toFixed(2)}</td>
    <td id='importPrice-${id}'>${Number(rowData.price).toFixed(2)}</td>
    <td id='importTotalValue-${id}'>${Number(rowData.totalValue).toFixed(2)}</td>
    <td><i class="fa fa-trash" onclick="deleteImportItem('${id}','old')" style="cursor:pointer;" title="Delete"></i></td>
    <td><i class="fa fa-sign-in" aria-hidden="true" style="cursor:pointer;" onclick="entryAction('${id}')"></i></td>
</tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }
  $("#importItemList").append(rows);

}

function drawBillItemList(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    rows += `<tr id='billItemRow-${id}' data-style-id='${rowData.styleId}' data-purchase-order-id='${rowData.poNo}' data-item-type='${rowData.accessoriesItemType}' data-accessories-item-id='${rowData.accessoriesItemId}' data-color-id='${rowData.colorId}' data-unit-id='${rowData.unitId}'>
                        <td id='billStyleNo-${id}'>${rowData.styleNo}</td>
                        <td id='billPoNo-${id}'>${rowData.poNo}</td>
                        <td id='billAccessoriesName-${id}'>${rowData.accessoriesName}</td>
                        <td id='billColor-${id}'>${rowData.colorName}</td>
                        <td id='billSize-${id}'>${rowData.size}</td>
                        <td id='billUnit-${id}'>${rowData.unitName}</td>
                        <td id='billWidth-${id}'>${rowData.width}</td>
                        <td id='billGsm-${id}'>${rowData.gsm}</td>
                        <td><input id='billTotalQty-${id}' type="number" class='from-control-sm max-width-60' value='${rowData.totalQty}'/></td>
                        <td><input id='billCartonQty-${id}' type="number" class='form-control-sm max-width-60' value='${rowData.price}'/></td>
                        <td><input id='billPrice-${id}' type="number" class='from-control-sm max-width-60' value='${rowData.price}'</td>
                        <td id='billTotalValue-${id}'>${rowData.totalValue}</td>
                        <td><i class="fa fa-trash" onclick="deleteImportItem('${id}','old')" style="cursor:pointer;" title="Delete"></i></td>
                        <td><i class="fa fa-sign-in" aria-hidden="true" style="cursor:pointer;" onclick="entryAction('${id}')"></i></td>
                    </tr>`;

    //rows.push(drawRowDataTable(data[i], i));
  }
  $("#billItemList").append(rows);

}

function drawBillEntryList(data) {
  let rows = "";
  const length = data.length;
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = i;
    rows += `<tr id='billRow-${id}'  data-bill-auto-id='${id}' onclick="searchBillOfEntry('${rowData.masterLCNo}','${rowData.invoiceNo}','${rowData.billEntryNo}')" style='cursor:pointer;'>
					<td id='billEntryNo-${id}'>${rowData.billEntryNo}</td>
					<td id='billEntryDate-${id}'>${rowData.billEntryDate}</td>
				</tr>`;
    //rows.push(drawRowDataTable(data[i], i));
  }
  $("#billOfEntryList").append(rows);
}



function editedMasterRow(rowId) {
  $("#masterRow-" + rowId).addClass('editedRow');
}

function editedMasterUDRow(rowId) {
  $("#masterUDRow-" + rowId).addClass('editedRow');
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

function entryAction(rowId) {
  const rowList = $("#billItemList tr");
  let id = rowList.length + 1;
  let accessoriesItemId = $("#importRow-" + rowId).attr("data-accessories-item-id");
  let existId = 0;
  for (let i = 0; i < id - 1; i++) {
    if (rowList[i].getAttribute('data-accessories-item-id') == accessoriesItemId) {
      existId = rowList[i].id.slice(rowList[i].id.indexOf('-') + 1);
      break;
    }
  }

  if (existId != 0) {

    $("#billTotalQty-" + existId).val($("#importTotalQty-" + rowId).val());
  } else {
    id = rowId;
    let importRow = $("#importRow-" + id);
    let styleId = importRow.attr('data-style-id');
    let poId = importRow.attr('data-purchase-order');
    let itemType = importRow.attr('data-item-type');
    let accessoriesItemId = importRow.attr('data-accessories-item-id');
    let colorId = importRow.attr('data-color-id');
    let unitId = importRow.attr('data-unit-id');
    let styleNo = $("#importStyleNo-" + id).text();
    let poNo = $("#importPoNo-" + id).text();
    let accessoriesName = $("#importAccessoriesName-" + id).text();
    let colorName = $("#importColor-" + id).text();
    let size = $("#importSize-" + id).text();
    let unit = $("#importUnit-" + id).text();
    let width = Number($("#importWidth-" + id).text().trim() == '' ? 0 : $("#importWidth-" + id).text().trim()).toFixed(2);
    let gsm = Number($("#importGsm-" + id).text().trim() == '' ? 0 : $("#importGsm-" + id).text().trim()).toFixed(2);
    let totalQty = Number($("#importTotalQty-" + id).text().trim() == '' ? 0 : $("#importTotalQty-" + id).text().trim()).toFixed(2);
    let price = Number($("#importPrice-" + id).text().trim() == '' ? 0 : $("#importPrice-" + id).text().trim()).toFixed(2);
    let totalValue = Number($("#importTotalValue-" + id).text().trim() == '' ? 0 : $("#importTotalValue-" + id).text().trim()).toFixed(2);

    let row = `<tr id='billItemRow-${id}' class='newRow' data-style-id='${styleId}' data-purchase-order-id='${poId}' data-item-type='${itemType}' data-accessories-item-id='${accessoriesItemId}' data-color-id='${colorId}' data-unit-id='${unitId}'>
                  <td>${styleNo}</td>
                  <td id='billPoNo-${id}'>${poNo}</td>
                  <td>${accessoriesName}</td>
                  <td>${colorName}</td>
                  <td id='billSize-${id}'>${size}</td>
                  <td id='billUnit-${id}'>${unit}</td>
                  <td id='billWidth-${id}'>${width}</td>
                  <td id='billGsm-${id}'>${gsm}</td>
                  <td><input id='billTotalQty-${id}' class='form-control-sm max-width-60' value = '${totalQty}' ></td>
                  <td><input id="billCartonQty-${id}" type='number' class='form-control-sm max-width-60'></td>
                  <td><input id='billPrice-${id}' class='form-control-sm max-width-60' value='${price}'></td>
                  <td id='billTotalValue-${id}'>${totalValue}</td>
                  <td><i class="fa fa-trash" onclick="deleteImportItem('${id}','old')" style="cursor:pointer;" title="Delete"></i></td>
              </tr>`;
    $("#billItemList").append(row);
  }
}


function deleteImportItem(autoId, rowType) {
  if (confirm("Are you sure to Delete this Shipped Style?")) {
    if (rowType == 'new') {
      $("#importRow-" + autoId).remove();
    } else {
      $("#loader").show();
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './deleteImportItem',
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
          $("#loader").hide();
        }
      });
    }
    totalValueCount();
  }
}


function searchImportInvoiceLc(masterLCNo, invoiceNo, amendmentNo) {

  //const masterLc = fakeData.masterLcList[autoId];
  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchImportLCInvoice',
    data: {
      masterLCNo: masterLCNo,
      invoiceNo: invoiceNo,
      amendmentNo: amendmentNo
    },
    success: function (data) {
      const importLCInfo = data.importLCInfo;
      const importLCItems = data.masterLCStyles;
      $("#importLCAutoId").val(importLCInfo.autoId);
      $("#importInvoiceNo").val(importLCInfo.invoiceNo);
      $("#billOfEntryInvoiceNo").val(importLCInfo.invoiceNo);
      $("#billOfEntryInvoiceDate").val(importLCInfo.invoiceDate);
      $("#importLCType").val(importLCInfo.importLCType);
      $("#importInvoiceDate").val(importLCInfo.invoiceDate);
      $("#importSenderBankName").val(importLCInfo.senderBank).change();
      $("#importReceiverBankName").val(importLCInfo.receiverBank).change();
      $("#importSupplierName").val(importLCInfo.supplierId).change();
      $("#importDraftAt").val(importLCInfo.draftAt);
      $("#importMaturityDate").val(importLCInfo.maturityDate);
      $("#importProformaInvoiceNo").val(importLCInfo.proformaInvoiceNo);
      $("#importProformaInvoiceDate").val(importLCInfo.proformaInvoiceDate);

      $("#importAmendmentNo").val(amendmentNo);
      $("#importAmendmentList").empty();
      drawImportLCAmendmentList(data.amendmentList);
      //$("#importUDList").empty();
      //drawImportUDList(data.importUDList);
      $("#importItemList").empty();
      drawImportItemList(data.importItemList);
      $("#billOfEntryList").empty();
      drawBillEntryList(data.billEntryList);

      $("#importSubmitButton").hide();
      $("#importAmendmentButton").show();
      $("#importEditButton").show();
      $("#importPreviewBtn").show();
      $("#loader").hide();
    }
  });


}


function searchBillOfEntry(masterLCNo, invoiceNo, billNo) {

  //const masterLc = fakeData.masterLcList[autoId];
  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchBillOfEntry',
    data: {
      masterLCNo: masterLCNo,
      invoiceNo: invoiceNo,
      billNo: billNo
    },
    success: function (data) {

      let billOfEntry = data.billOfEntry;
      $("#billOfEntryAutoId").val(billOfEntry.autoId);
      $("#billOfEntryNo").val(billOfEntry.billEntryNo);
      $("#billOfEntryDate").val(billOfEntry.billEntryDate);
      $("#billBillNo").val(billOfEntry.billNo);
      $("#billShippedOnBoardDate").val(billOfEntry.shippedOnBoardDate);
      $("#billTelexReleaseDate").val(billOfEntry.telexReleaseDate);
      $("#billContainerNo").val(billOfEntry.containerNo);
      $("#billVesselNo").val(billOfEntry.vesselNo);
      $("#billDocumentReceiveDate").val(billOfEntry.documentReceiveDate);
      $("#billEtaDate").val(billOfEntry.etaDate);
      $("#billStuffingDate").val(billOfEntry.stuffingDate);
      $("#billClearingDate").val(billOfEntry.clearingDate);

      $("#billItemList").empty();
      drawBillItemList(data.itemList);

      $("#billSubmitButton").hide();
      $("#billEditButton").show();
      $("#billPreviewBtn").show();
      $("#loader").hide();
    }
  });


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
  $("#masterAmount-" + id).text((parseFloat($("#masterQuantity-" + id).val()) * parseFloat($("#masterUnitPrice-" + id).val())).toFixed(2));
}

function setUDAmount(id) {
  $("#masterUDAmount-" + id).text((parseFloat($("#masterUDQuantity-" + id).val()) * parseFloat($("#masterUDUnitPrice-" + id).val())).toFixed(2));
}

function importItemAddAction() {

  const rowList = $("#importItemList tr");
  const length = rowList.length;

  let styleNo = $("#importStyleNo option:selected").text();
  let styleId = $("#importStyleNo").val();
  let purchaseOrderId = $("#importPurchaseOrder").val();
  let purchaseOrder = $("#importPurchaseOrder option:selected").text();
  let itemType = $("#importItemType").val();
  let accessoriesItemId = $("#importFabricsAccessoriesItem").val();
  let accessoriesItemName = $("#importFabricsAccessoriesItem option:selected").text();
  let colorId = $("#importColor").val();
  let color = $("#importColor option:selected").text();
  let size = $("#importSize").val();
  let unitId = $("#importUnit").val();
  let unit = $("#importUnit option:selected").text();
  let consumption = $("#importConsumption").val();
  let width = $("#importWidth").val();
  let gsm = $("#importGSM").val();
  let totalQty = $("#importTotalQty").val();
  let price = $("#importPrice").val();
  let totalValue = $("#importTotalValue").val();
  const id = length;
  if (styleId != '0') {
    if (purchaseOrderId != '0') {
      if (accessoriesItemId != '0') {
        if (unitId != '0') {
          let row = `<tr id='importRow-${id}' class='newRow' data-style-id='${styleId}' data-purchase-order-id='${purchaseOrderId}' data-item-type='${itemType}' data-accessories-item-id='${accessoriesItemId}' data-color-id='${colorId}' data-unit-id='${unitId}' data-consumption='${consumption}'>
                        <td id='importStyleNo-${id}'>${styleNo}</td>
                        <td id='importPoNo-${id}'>${purchaseOrder}</td>
                        <td id='importAccessoriesName-${id}'>${accessoriesItemName}</td>
                        <td id='importColor-${id}'>${color}</td>
                        <td id='importSize-${id}'>${size}</td>
                        <td id='importUnit-${id}'>${unit}</td>
                        <td id='importWidth-${id}'>${width}</td>
                        <td id='importGsm-${id}'>${gsm}</td>
                        <td id='importTotalQty-${id}'>${totalQty}</td>
                        <td id='importPrice-${id}'>${price}</td>
                        <td id='importTotalValue-${id}'>${totalValue}</td>
                        <td ><i class='fa fa-trash' onclick="deleteImportItem('${id}','new')" style="cursor:pointer;" title="Delete"></i></td>
                    </tr>`;

          $("#importItemList").append(row);
        } else {
          alert("Please Select Unit");
          $("#importUnit").focus();
        }
      } else {
        alert("Please Select Fabrics/Accessories Item");
        $("#importFabricsAccessoriesItem").focus();
      }
    } else {
      alert("Please Select Purchase Order");
      $("#importPurchaseOrder").focus();
    }
  } else {
    alert("Please Select Style No");
    $("#importStyleNo").focus();
  }

}


function importSubmitAction() {


  let rowList = $("#importItemList tr");
  let length = rowList.length;

  if (length > 0) {
    let styleList = '';

    let masterLCNo = $("#importMasterLcNo").val();
    let lcType = $("#importLCType").val();
    let udAutoId = $("#importUdAmendmentNo").val();
    let invoiceNo = $("#importInvoiceNo").val();
    let date = $("#importInvoiceDate").val();
    let senderBankId = $("#importSenderBankName").val();
    let receiverBankId = $("#importReceiverBankName").val();
    let supplierId = $("#importSupplierName").val();
    let draftAt = $("#importDraftAt").val();
    let maturityDate = $("#importMaturityDate").val();
    let proformaInvoiceNo = $("#importProformaInvoiceNo").val();
    let proformaInvoiceDate = $("#importProformaInvoiceDate").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    if (masterLCNo != '') {
      if (invoiceNo != '') {
        if (senderBankId != '0') {
          if (receiverBankId != '0') {

            for (let i = 0; i < length; i++) {
              const newRow = rowList[i];
              const id = newRow.id.slice(10);

              const item = {
                styleId: newRow.getAttribute('data-style-id'),
                purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                poNo: $("#importPoNo-" + id).text(),
                accessoriesItemType: newRow.getAttribute('data-item-type'),
                accessoriesItemId: newRow.getAttribute('data-accessories-item-id'),
                colorId: newRow.getAttribute('data-color-id'),
                unitId: newRow.getAttribute('data-unit-id'),
                size: $("#importSize-" + id).text(),
                consumption: newRow.getAttribute('data-consumption'),
                width: $("#importWidth-" + id).text(),
                gsm: $("#importGSM-" + id).text(),
                totalQty: $("#importTotalQty-" + id).text(),
                price: $("#importPrice-" + id).text(),
                totalValue: $("#importTotalValue-" + id).text(),
                userId: userId
              }
              styleItems.list.push(item);
            }
            if (confirm("Are you sure to confirm..")) {
              $("#loader").show();
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './importLCSubmit',
                data: {
                  masterLCNo: masterLCNo,
                  importLCType: lcType,
                  udAutoId: udAutoId,
                  invoiceNo: invoiceNo,
                  invoiceDate: date,
                  senderBank: senderBankId,
                  receiverBank: receiverBankId,
                  supplierId: supplierId,
                  draftAt: draftAt,
                  maturityDate: maturityDate,
                  proformaInvoiceNo: proformaInvoiceNo,
                  proformaInvoiceDate: proformaInvoiceDate,
                  userId: userId,
                  amendmentNo: '0',
                  amendmentDate: date,
                  itemList: JSON.stringify(styleItems),
                },
                success: function (data) {
                  if (data.result == 'success') {
                    alert("Successfully Submitted");
                    $("#importInvoiceList").empty();
                    drawImportInvoiceList(data.importInvoiceList);
                    $("#importAmendmentList").empty();
                    drawImportLCAmendmentList(data.amendmentList);
                  } else {
                    alert("Import LC Insertion Failed")
                  }
                  $("#loader").hide();
                }
              });

            }

          } else {
            alert("Please Select Receiver Bank Name...");
            $("#importReceiverBankName").focus();
          }
        } else {
          alert("Please Select Sender Bank Name...");
          $("#importSenderBankName").focus();
        }
      } else {
        alert("Please Enter Invoice No...");
        $("#importInvoiceNo").focus();
      }
    } else {
      alert("Please Enter Master LC No...");
      $("#importMasterLCNo").focus();
    }
  } else {
    alert("Please Entry Any Accessories/Fabrics Item...");
    //alert("Please Enter Any Item...");
  }

}

function importEditAction() {


  let rowList = $("#importItemList tr");
  let length = rowList.length;

  if (length > 0) {
    let styleList = '';

    let importLCAutoId = $("#importLCAutoId").val();
    let masterLCNo = $("#importMasterLcNo").val();
    let lcType = $("#importLCType").val();
    let udAutoId = $("#importUdAmendmentNo").val();
    let invoiceNo = $("#importInvoiceNo").val();
    let amendmentNo = $("#importAmendmentNo").val();
    let date = $("#importInvoiceDate").val();
    let senderBankId = $("#importSenderBankName").val();
    let receiverBankId = $("#importReceiverBankName").val();
    let supplierId = $("#importSupplierName").val();
    let draftAt = $("#importDraftAt").val();
    let maturityDate = $("#importMaturityDate").val();
    let proformaInvoiceNo = $("#importProformaInvoiceNo").val();
    let proformaInvoiceDate = $("#importProformaInvoiceDate").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];
    let editedItems = {};
    editedItems['list'] = [];

    if (masterLCNo != '') {
      if (invoiceNo != '') {
        if (senderBankId != '0') {
          if (receiverBankId != '0') {

            let rowList = $("#importItemList tr.newRow");
            let length = rowList.length;
            for (let i = 0; i < length; i++) {
              const newRow = rowList[i];
              const id = newRow.id.slice(10);

              const item = {
                styleId: newRow.getAttribute('data-style-id'),
                purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                poNo: $("#importPoNo-" + id).text(),
                accessoriesItemType: newRow.getAttribute('data-item-type'),
                accessoriesItemId: newRow.getAttribute('data-accessories-item-id'),
                colorId: newRow.getAttribute('data-color-id'),
                unitId: newRow.getAttribute('data-unit-id'),
                size: $("#importSize-" + id).text(),
                consumption: newRow.getAttribute('data-consumption'),
                width: $("#importWidth-" + id).text(),
                gsm: $("#importGSM-" + id).text(),
                totalQty: $("#importTotalQty-" + id).text(),
                price: $("#importPrice-" + id).text(),
                totalValue: $("#importTotalValue-" + id).text(),
                userId: userId
              }
              styleItems.list.push(item);
            }

            let editedRowList = $("#importItemList tr.editedRow");
            length = editedRowList.length;

            for (let i = 0; i < length; i++) {
              const editedRow = editedRowList[i];
              const id = editedRow.id.slice(10);

              const item = {
                autoId: editedRow.getAttribute('data-auto-id'),
                styleId: editedRow.getAttribute('data-style-id'),
                itemId: editedRow.getAttribute('data-accessories-item-id'),
                purchaseOrderId: editedRow.getAttribute('data-purchase-order-id'),
                quantity: $("#importTotalQty-" + id).val(),
                price: $("#importPrice-" + id).val(),
                totalValue: $("#importTotalValue-" + id).text(),
                userId: userId
              }

              editedItems.list.push(item);
            }

            if (confirm("Are you sure to confirm..")) {
              $("#loader").show();
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './importLCEdit',
                data: {
                  autoId: importLCAutoId,
                  masterLCNo: masterLCNo,
                  importLCType: lcType,
                  udAutoId: udAutoId,
                  invoiceNo: invoiceNo,
                  invoiceDate: date,
                  senderBank: senderBankId,
                  receiverBank: receiverBankId,
                  supplierId: supplierId,
                  draftAt: draftAt,
                  maturityDate: maturityDate,
                  proformaInvoiceNo: proformaInvoiceNo,
                  proformaInvoiceDate: proformaInvoiceDate,
                  userId: userId,
                  amendmentNo: amendmentNo,
                  amendmentDate: date,
                  itemList: JSON.stringify(styleItems),
                  editedItemList: JSON.stringify(editedItems)
                },
                success: function (data) {
                  if (data.result == 'success') {
                    alert("Successfully Submitted");
                    $("#importAmendmentList").empty();
                    drawImportLCAmendmentList(data.amendmentList);
                  } else {
                    alert("Import LC Insertion Failed")
                  }
                  $("#loader").hide();
                }
              });

            }

          } else {
            alert("Please Select Receiver Bank Name...");
            $("#importReceiverBankName").focus();
          }
        } else {
          alert("Please Select Sender Bank Name...");
          $("#importSenderBankName").focus();
        }
      } else {
        alert("Please Enter Invoice No...");
        $("#importInvoiceNo").focus();
      }
    } else {
      alert("Please Enter Master LC No...");
      $("#importMasterLCNo").focus();
    }
  } else {
    alert("Please Entry Any Accessories/Fabrics Item...");
    //alert("Please Enter Any Item...");
  }

}


function importAmendmentAction() {


  let rowList = $("#importItemList tr");
  let length = rowList.length;

  if (length > 0) {
    let styleList = '';

    let masterLCNo = $("#importMasterLcNo").val();
    let lcType = $("#importLCType").val();
    let udAutoId = $("#importUdAmendmentNo").val();
    let invoiceNo = $("#importInvoiceNo").val();
    let date = $("#importInvoiceDate").val();
    let senderBankId = $("#importSenderBankName").val();
    let receiverBankId = $("#importReceiverBankName").val();
    let supplierId = $("#importSupplierName").val();
    let draftAt = $("#importDraftAt").val();
    let maturityDate = $("#importMaturityDate").val();
    let proformaInvoiceNo = $("#importProformaInvoiceNo").val();
    let proformaInvoiceDate = $("#importProformaInvoiceDate").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    if (masterLCNo != '') {
      if (invoiceNo != '') {
        if (senderBankId != '0') {
          if (receiverBankId != '0') {

            for (let i = 0; i < length; i++) {
              const newRow = rowList[i];
              const id = newRow.id.slice(10);

              const item = {
                styleId: newRow.getAttribute('data-style-id'),
                purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                poNo: $("#importPoNo-" + id).text(),
                accessoriesItemType: newRow.getAttribute('data-item-type'),
                accessoriesItemId: newRow.getAttribute('data-accessories-item-id'),
                colorId: newRow.getAttribute('data-color-id'),
                unitId: newRow.getAttribute('data-unit-id'),
                size: $("#importSize-" + id).text(),
                consumption: newRow.getAttribute('data-consumption'),
                width: $("#importWidth-" + id).text(),
                gsm: $("#importGSM-" + id).text(),
                totalQty: $("#importTotalQty-" + id).text(),
                price: $("#importPrice-" + id).text(),
                totalValue: $("#importTotalValue-" + id).text(),
                userId: userId
              }
              styleItems.list.push(item);
            }

            if (confirm("Are you sure to confirm..")) {
              $("#loader").show();
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './importLCAmendment',
                data: {
                  masterLCNo: masterLCNo,
                  importLCType: lcType,
                  udAutoId: udAutoId,
                  invoiceNo: invoiceNo,
                  invoiceDate: date,
                  senderBank: senderBankId,
                  receiverBank: receiverBankId,
                  supplierId: supplierId,
                  draftAt: draftAt,
                  maturityDate: maturityDate,
                  proformaInvoiceNo: proformaInvoiceNo,
                  proformaInvoiceDate: proformaInvoiceDate,
                  userId: userId,
                  amendmentNo: '0',
                  amendmentDate: date,
                  itemList: JSON.stringify(styleItems),
                },
                success: function (data) {
                  if (data.result == 'success') {
                    alert("Successfully Submitted");
                    $("#importAmendmentList").empty();
                    drawImportLCAmendmentList(data.amendmentList);
                  } else {
                    alert("Import LC Insertion Failed")
                  }
                  $("#loader").hide();
                }
              });

            }
          } else {
            alert("Please Select Receiver Bank Name...");
            $("#importReceiverBankName").focus();
          }
        } else {
          alert("Please Select Sender Bank Name...");
          $("#importSenderBankName").focus();
        }
      } else {
        alert("Please Enter Invoice No...");
        $("#importInvoiceNo").focus();
      }
    } else {
      alert("Please Enter Master LC No...");
      $("#importMasterLCNo").focus();
    }
  } else {
    alert("Please Entry Any Accessories/Fabrics Item...");
    //alert("Please Enter Any Item...");
  }

}

function importUDAddAction() {

  let masterUDNo = $("#masterUDNo").val();
  let masterUdDate = $("#masterUdDate").val();
  let importInvoiceNo = $("#importInvoiceNo").val();
  let importLCAutoId = $("#importLCAutoId").val();
  let userId = $("#userId").val();
  if (masterUDNo != '') {
    if (masterUdDate != '') {
      if (importInvoiceNo != '') {
        $("#loader").show();
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './importInvoiceUDAdd',
          data: {
            udInfo: JSON.stringify({
              masterUDNo: masterUDNo,
              masterUdDate: masterUdDate,
              importInvoiceNo: importInvoiceNo,
              importLCAutoId: importLCAutoId,
              userId: userId
            })
          },
          success: function (data) {
            if (data.result == 'success') {
              alert("Successfully Submitted");
              $("#importUDList").empty();
              drawImportUDList(data.udList);
            } else {
              alert("Import LC Insertion Failed")
            }
            $("#loader").hide();
          }
        });
      } else {
        alert("Please Entry Invoice No...");
        $("#importInvoiceNo").focus();
      }
    } else {
      alert("Please Select UD Date...");
      $("#masterUdDate").focus();
    }
  } else {
    alert("Please Entry UD No...");
    $("#masterUDNo").focus();
  }
}

function importItemTotalValueCalculate() {
  let totalQty = $("#importTotalQty").val() == '' ? 0 : $("#importTotalQty").val();
  let price = $("#importPrice").val() == '' ? 0 : $("#importPrice").val();
  let totalValue = totalQty * price;
  $("#importTotalValue").val(Number(totalValue).toFixed(2));
}


function importRefreshAction() {
  $("#importInvoiceNo").val();
  $("#importSenderBankName").val(0).change();
  $("#importReceiverBankName").val(0).change();
  $("#importSupplierName").val(0).change();
  $("#importDraftAt").val('');
  $("#importProformaInvoiceNo").val();
  $("#importItemList").empty();
  $("#importSubmitButton").show();
  $("#importAmendmentButton").show();
  $("#importEditButton").hide();
  $("#importPreviewBtn").hide();
}

function billSubmitAction() {


  let rowList = $("#billItemList tr");
  let length = rowList.length;

  if (length > 0) {
    let styleList = '';

    let masterLCNo = $("#importMasterLcNo").val();
    let invoiceNo = $("#importInvoiceNo").val();
    let billOfEntryNo = $("#billOfEntryNo").val();
    let billOfEntryDate = $("#billOfEntryDate").val();
    let billNo = $("#billBillNo").val();
    let shippedOnBoardDate = $("#billShippedOnBoardDate").val();
    let telexReleaseDate = $("#billTelexReleaseDate").val();
    let containerNo = $("#billContainerNo").val();
    let vesselNo = $("#billVesselNo").val();
    let documentReceiveDate = $("#billDocumentReceiveDate").val();
    let etaDate = $("#billEtaDate").val();
    let stuffingDate = $("#billStuffingDate").val();
    let clearingDate = $("#billClearingDate").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    if (masterLCNo != '') {
      if (invoiceNo != '') {
        if (billOfEntryNo) {
          if (billOfEntryDate) {

            for (let i = 0; i < length; i++) {
              const newRow = rowList[i];
              const id = newRow.id.slice(12);

              const item = {
                billEntryNo: billOfEntryNo,
                styleId: newRow.getAttribute('data-style-id'),
                purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                poNo: $("#billPoNo-" + id).text(),
                accessoriesItemType: newRow.getAttribute('data-item-type'),
                accessoriesItemId: newRow.getAttribute('data-accessories-item-id'),
                colorId: newRow.getAttribute('data-color-id'),
                unitId: newRow.getAttribute('data-unit-id'),
                size: $("#billSize-" + id).text(),
                width: $("#billWidth-" + id).text(),
                gsm: $("#billGSM-" + id).text(),
                totalQty: $("#billTotalQty-" + id).val(),
                cartonQty: $("#billCartonQty-" + id).val() == '' ? 0 : $("#billCartonQty-" + id).val(),
                price: $("#billPrice-" + id).val(),
                totalValue: $("#billTotalValue-" + id).text(),
                userId: userId
              }
              styleItems.list.push(item);

            }
            if (confirm("Are you sure to confirm..")) {
              $("#loader").show();
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './billOfEntrySubmit',
                data: {
                  masterLCNo: masterLCNo,
                  invoiceNo: invoiceNo,
                  billEntryNo: billOfEntryNo,
                  billEntryDate: billOfEntryDate,
                  billNo: billNo,
                  telexReleaseDate: telexReleaseDate,
                  vesselNo: vesselNo,
                  etaDate: etaDate,
                  clearingDate: clearingDate,
                  shippedOnBoardDate: shippedOnBoardDate,
                  containerNo: containerNo,
                  documentReceiveDate: documentReceiveDate,
                  stuffingDate: stuffingDate,
                  userId: userId,
                  itemList: JSON.stringify(styleItems),
                },
                success: function (data) {
                  if (data.result == 'success') {
                    alert("Successfully Submitted");
                    $("#billOfEntryList").empty();
                    drawBillEntryList(data.billEntryList);
                  } else {
                    alert("Bill Entry Insertion Failed")
                  }
                  $("#loader").hide();
                }
              });

            }

          } else {
            alert("Please Select Bill Of Entry Date...");
            $("#billOfEntryDate").focus();
          }
        } else {
          alert("Please Enter Bill Of Entry No...");
          $("#billOfEntryNo").focus();
        }
      } else {
        alert("Please Enter Invoice No...");
        $("#importInvoiceNo").focus();
      }
    } else {
      alert("Please Enter Master LC No...");
      $("#importMasterLCNo").focus();
    }
  } else {
    alert("Please Entry Any Accessories/Fabrics Item...");
    //alert("Please Enter Any Item...");
  }

}





function billEditAction() {


  let rowList = $("#billItemList tr");
  let length = rowList.length;

  if (length > 0) {
    let styleList = '';
    let billOfEntryAutoId = $("#billOfEntryAutoId").val();
    let masterLCNo = $("#importMasterLcNo").val();
    let invoiceNo = $("#importInvoiceNo").val();
    let billOfEntryNo = $("#billOfEntryNo").val();
    let billOfEntryDate = $("#billOfEntryDate").val();
    let billNo = $("#billBillNo").val();
    let shippedOnBoardDate = $("#billShippedOnBoardDate").val();
    let telexReleaseDate = $("#billTelexReleaseDate").val();
    let containerNo = $("#billContainerNo").val();
    let vesselNo = $("#billVesselNo").val();
    let documentReceiveDate = $("#billDocumentReceiveDate").val();
    let etaDate = $("#billEtaDate").val();
    let stuffingDate = $("#billStuffingDate").val();
    let clearingDate = $("#billClearingDate").val();
    let userId = $("#userId").val();
    let styleItems = {};
    styleItems['list'] = [];

    let editedItems = {};
    editedItems['list'] = [];

    if (masterLCNo != '') {
      if (invoiceNo != '') {
        if (billOfEntryNo) {
          if (billOfEntryDate) {

            let rowList = $("#billItemList tr.newRow");
            let length = rowList.length;
            for (let i = 0; i < length; i++) {
              const newRow = rowList[i];
              const id = newRow.id.slice(12);

              const item = {
                billEntryNo: billOfEntryNo,
                styleId: newRow.getAttribute('data-style-id'),
                purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
                poNo: $("#billPoNo-" + id).text(),
                accessoriesItemType: newRow.getAttribute('data-item-type'),
                accessoriesItemId: newRow.getAttribute('data-accessories-item-id'),
                colorId: newRow.getAttribute('data-color-id'),
                unitId: newRow.getAttribute('data-unit-id'),
                size: $("#billSize-" + id).text(),
                width: $("#billWidth-" + id).text(),
                gsm: $("#billGSM-" + id).text(),
                totalQty: $("#billTotalQty-" + id).val(),
                cartonQty: $("#billCartonQty-" + id).val() == '' ? 0 : $("#billCartonQty-" + id).val(),
                price: $("#billPrice-" + id).val(),
                totalValue: $("#billTotalValue-" + id).text(),
                userId: userId
              }
              styleItems.list.push(item);
            }

            let editedRowList = $("#billItemList tr.editedRow");
            length = editedRowList.length;

            for (let i = 0; i < length; i++) {
              const editedRow = editedRowList[i];
              const id = editedRow.id.slice(12);

              const item = {
                autoId: editedRow.getAttribute('data-auto-id'),
                styleId: editedRow.getAttribute('data-style-id'),
                itemId: editedRow.getAttribute('data-accessories-item-id'),
                purchaseOrderId: editedRow.getAttribute('data-purchase-order-id'),
                quantity: $("#billTotalQty-" + id).val(),
                price: $("#billPrice-" + id).val(),
                cartonQty: $("#billCartonQty-" + id).val(),
                totalValue: $("#billTotalValue-" + id).text(),
                userId: userId
              }
              editedItems.list.push(item);
            }

            if (confirm("Are you sure to confirm..")) {
              $("#loader").show();
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './billOfEntryEdit',
                data: {
                  autoId: billOfEntryAutoId,
                  masterLCNo: masterLCNo,
                  invoiceNo: invoiceNo,
                  billEntryNo: billOfEntryNo,
                  billEntryDate: billOfEntryDate,
                  billNo: billNo,
                  telexReleaseDate: telexReleaseDate,
                  vesselNo: vesselNo,
                  etaDate: etaDate,
                  clearingDate: clearingDate,
                  shippedOnBoardDate: shippedOnBoardDate,
                  containerNo: containerNo,
                  documentReceiveDate: documentReceiveDate,
                  stuffingDate: stuffingDate,
                  userId: userId,
                  itemList: JSON.stringify(styleItems),
                  editedItemList: JSON.stringify(editedItems)
                },
                success: function (data) {
                  if (data.result == 'success') {
                    alert("Successfully Submitted");
                    $("#billItemList").empty();
                    drawBillItemList(data.billItemList);
                  } else {
                    alert("Bill Entry Insertion Failed")
                  }
                  $("#loader").hide();
                }
              });

            }

          } else {
            alert("Please Select Bill Of Entry Date...");
            $("#billOfEntryDate").focus();
          }
        } else {
          alert("Please Enter Bill Of Entry No...");
          $("#billOfEntryNo").focus();
        }
      } else {
        alert("Please Enter Invoice No...");
        $("#importInvoiceNo").focus();
      }
    } else {
      alert("Please Enter Master LC No...");
      $("#importMasterLCNo").focus();
    }
  } else {
    alert("Please Entry Any Accessories/Fabrics Item...");
    //alert("Please Enter Any Item...");
  }

}

function billRefreshAction() {

  $("#billOfEntryNo").val('');
  $("#billBillNo").val('');
  $("#billContainerNo").val('');
  $("#billVesselNo").val('');
  $("#billItemList").empty();

  $("#billSubmitButton").show();
  $("#billEditButton").hide();
  $("#billPreviewBtn").hide();
  $("#loader").hide();

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
document.getElementById("billOfEntryDate").value = today;
document.getElementById("exportInvoiceDate").value = today;
document.getElementById("importInvoiceDate").value = today;
document.getElementById("exportBillEntryDate").value = today;



