
const fakeRackList = [{ rackId: '1', rackName: 'AA' },
{ rackId: '2', rackName: 'AB' },
{ rackId: '3', rackName: 'BA' },
{ rackId: '4', rackName: 'BB' },
{ rackId: '5', rackName: 'CA' },
{ rackId: '6', rackName: 'CB' },
{ rackId: '7', rackName: 'DA' },
{ rackId: '8', rackName: 'DB' },
{ rackId: '9', rackName: 'EA' },
{ rackId: '10', rackName: 'EB' }];
const fakeBinList = [{ binId: '1', binName: 'AA' },
{ binId: '2', binName: 'AB' },
{ binId: '3', binName: 'BA' },
{ binId: '4', binName: 'BB' },
{ binId: '5', binName: 'CA' },
{ binId: '6', binName: 'CB' },
{ binId: '7', binName: 'DA' },
{ binId: '8', binName: 'DB' },
{ binId: '9', binName: 'EA' },
{ binId: '10', binName: 'EB' }];


$("#searchRefreshBtn").click(function () {
  $("#purchaseOrderSearch").val("");
  $("#styleNoSearch").val("");
  $("#itemNameSearch").val("");
  $("#accessoriesItemSearch").val("");
  $("#colorSearch").val("");
})

$("#itemSearchBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesPurchaseOrderIndentList',
    data: {},
    success: function (data) {
      drawPurchaseOrderListTable(data.purchaseOrderList);
    }
  });
});

$("#newTransactionBtn").click(function () {

  $("#transactionId").val("-- New Transaction --");
  $("#btnSubmit").prop("disabled", false);
  $("#btnEdit").prop("disabled", true);
});

$("#findTransactionBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesReceiveList',
    data: {},
    success: function (data) {
      $("#accessoriesReceiveList").empty();
      $("#accessoriesReceiveList").append(drawAccessoriesReceiveListTable(data.accessoriesReceiveList));

    }
  });
});

$("#accessoriesAddBtn").click(() => {

  
  let accessoriesList = "";
  $("#purchaseOrderList tr").filter(function () {
    const id = this.id.slice(4);

    if ($("#check-" + id).prop('checked')) {
      const purchaseOrder = this.getAttribute("data-purchase-order");
      const styleId = this.getAttribute("data-style-id");
      const styleNo = $("#styleName-"+id).text();
      const itemId = this.getAttribute("data-item-id");
      const itemName  = $("#itemName-"+id).text();
      const itemColorId = this.getAttribute("data-item-color-id");
      const itemColor  = $("#itemColor-"+id).text();
      const accessoriesId = this.getAttribute("data-accessories-id");
      const accessoriesName  = $("#accessoriesName-"+id).text();
      const accessoriesColorId = this.getAttribute("data-accessories-color-id");
      const accessoriesColor = $("#accessoriesColor-"+id).text();
      accessoriesList += `purchaseOrder : ${purchaseOrder},styleId : ${styleId},styleNo : ${styleNo},itemId : ${itemId},itemName : ${itemName},itemColorId : ${itemColorId},itemColorName : ${itemColor},accessoriesId : ${accessoriesId},accessoriesName : ${accessoriesName},accessoriesColorId : ${accessoriesColorId},accessoriesColorName : ${accessoriesColor} #`;
    }
  });

  

  if(accessoriesList.length>0){
    accessoriesList = accessoriesList.slice(0, -1);

    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getAccessoriesSizeList',
      data: {
        accessoriesList: accessoriesList
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Item Name..This Item Name Already Exist")
        } else {
          //console.log(data.accessoriesSizeList);
          drawAccessoriesSizeListTable(data.accessoriesSizeList);
        }
      }
    });
  }else{
    alert("Please Select/Check any Accessories from list");
  }
  

  //$("#accessoriesSizeList").html(rows);
  $('#itemSearchModal').modal('hide');

})

document.getElementById("checkAll").addEventListener("click", function () {
  if ($(this).prop('checked')) {
    $(".check").prop('checked', true);
  } else {
    $(".check").prop('checked', false);
  }
});

function submitAction() {
  const rowList = $("tr .newSizeRow");
  const length = rowList.length;
  const transactionId = $("#transactionId").val();
  const grnNo = $("#grnNo").val();
  const grnDate = $("#grnDate").val();
  const location = $("#location").val();
  const supplier = $("#supplier").val();
  const challanNo = $("#challanNo").val();
  const challanDate = $("#challanDate").val();
  const remarks = $("#remarks").val();
  const preparedBy = $("#preparedBy").val();
  const departmentId = 1;
  const userId = $("#userId").val();

  let sizeList = ""

  for (let i = 0; i < length; i++) {

    const row = rowList[i];
    const id = row.id.slice(6);
    const parentRow = row.getAttribute('data-parent-row');
    const purchaseOrder = row.getAttribute('data-purchase-order');
    const styleId = row.getAttribute('data-style-id');
    const itemId = row.getAttribute('data-item-id');
    const itemColorId = row.getAttribute('data-item-color-id');
    const accessoriesId = row.getAttribute('data-accessories-id');
    const accessoriesColorId = row.getAttribute('data-accessories-color-id');
    const unitId = row.getAttribute('data-unit-id');
    const accessoriesName = $("accessoriesName-" + parentRow).text();
    const accessoriesColor = $("accessoriesColor-" + parentRow).text();
    const sizeId = row.getAttribute('data-size-id');
    const sizeName = $("#sizeName-" + id).text();
    const unitQty = $("#unitQty-" + id).val().trim() == '' ? "0" : $("#unitQty-" + id).val();
    const qcPassedQty = 0;
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    const qcPassedType = 0;
    sizeList += `purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},accessoriesId : ${accessoriesId},accessoriesName : ${accessoriesName},accessoriesColorId : ${accessoriesColorId},accessoriesColorName : ${accessoriesColor},sizeId : ${sizeId},sizeName : ${sizeName},unitId : ${unitId},unitQty : ${unitQty},qcPassedQty : ${qcPassedQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType} #`;
  }

  sizeList = sizeList.slice(0, -1);

  if (length > 0) {
    if (transactionId != '') {
      if (grnNo != '') {
        if (supplier != '0') {

          if (confirm("Are you sure to submit this Accessories Receive...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './submitAccessoriesReceive',
              data: {
                transactionId: transactionId,
                grnNo: grnNo,
                grnDate: grnDate,
                location: location,
                sizeList: sizeList,
                supplierId: supplier,
                challanNo: challanNo,
                challanDate: challanDate,
                remarks: remarks,
                departmentId: departmentId,
                preparedBy: preparedBy,
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
          warningAlert("Please Select Supplier..");
          $("#supplier").focus();
        }
      } else {
        warningAlert("Please Enter GRN No")
        $("#grnNo").focus();
      }
    } else {
      warningAlert("Please Get a transaction Id")
      $("#transactionId").focus();
    }
  } else {
    warningAlert("Please Enter Accessories Size");
  }
}


function editAction() {
  const rowList = $("tr .newSizeRow");
  const length = rowList.length;
  const transactionId = $("#transactionId").val();
  const grnNo = $("#grnNo").val();
  const grnDate = $("#grnDate").val();
  const location = $("#location").val();
  const supplier = $("#supplier").val();
  const challanNo = $("#challanNo").val();
  const challanDate = $("#challanDate").val();
  const remarks = $("#remarks").val();
  const preparedBy = $("#preparedBy").val();
  const userId = $("#userId").val();

  let sizeList = ""

  for (let i = 0; i < length; i++) {

    const row = rowList[i];
    const id = row.id.slice(6);
    const parentRow = row.getAttribute('data-parent-row');
    const purchaseOrder = row.getAttribute('data-purchase-order');
    const styleId = row.getAttribute('data-style-id');
    const itemId = row.getAttribute('data-item-id');
    const itemColorId = row.getAttribute('data-item-color-id');
    const accessoriesId = row.getAttribute('data-accessories-id');
    const accessoriesColorId = row.getAttribute('data-accessories-color-id');
    const unitId = row.getAttribute('data-unit-id');
    const accessoriesName = $("accessoriesName-" + parentRow).text();
    const accessoriesColor = $("accessoriesColor-" + parentRow).text();
    const sizeId = row.getAttribute('data-size-id');
    const sizeName = $("#sizeId-" + id).text();
    const unitQty = $("#unitQty-" + id).val().trim() == '' ? "0" : $("#unitQty-" + id).val();
    const qcPassedQty = 0;
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    const qcPassedType = 0;

    sizeList += `purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},accessoriesId : ${accessoriesId},accessoriesName : ${accessoriesName},accessoriesColorId : ${accessoriesColorId},accessoriesColorName : ${accessoriesColor},sizeId : ${sizeId},sizeName : ${sizeName},unitId : ${unitId},unitQty : ${unitQty},qcPassedQty : ${qcPassedQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType} #`;
  }

  sizeList = sizeList.slice(0, -1);


  if (transactionId != '') {
    if (grnNo != '') {
      if (supplier != '0') {

        if (confirm("Are you sure to submit this Accessories Receive...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './editAccessoriesReceive',
            data: {
              transactionId: transactionId,
              grnNo: grnNo,
              grnDate: grnDate,
              location: location,
              sizeList: sizeList,
              supplierId: supplier,
              challanNo: challanNo,
              challanDate: challanDate,
              remarks: remarks,
              preparedBy: preparedBy,
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
        warningAlert("Please Select Supplier..");
        $("#supplier").focus();
      }
    } else {
      warningAlert("Please Enter GRN No")
      $("#grnNo").focus();
    }

  } else {
    warningAlert("Please Get a transaction Id")
    $("#transactionId").focus();
  }

}

function refreshAction() {
  location.reload();

}


function setData(unitId) {


  document.getElementById("unitId").value = unitId;
  document.getElementById("unitName").value = document.getElementById("unitName" + unitId).innerHTML;
  document.getElementById("unitValue").value = document.getElementById("unitValue" + unitId).innerHTML;
  document.getElementById("btnSave").disabled = true;
  document.getElementById("btnEdit").disabled = false;

}

function setAccessoriesInfo(autoId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesIndentInfo',
    data: {
      autoId: autoId
    },
    success: function (data) {
      //console.log(data.accessoriesInfo);
      const accessoriesInfo = data.accessoriesInfo;
      $("#purchaseOrder").text(accessoriesInfo.purchaseOrder);
      $("#styleId").val(accessoriesInfo.styleId);
      $("#styleNo").text(accessoriesInfo.styleName);
      $("#styleItemId").val(accessoriesInfo.itemId);
      $("#itemName").text(accessoriesInfo.itemName);
      $("#itemColorId").val(accessoriesInfo.itemColorId);
      $("#itemColor").text(accessoriesInfo.itemColorName);
      $("#accessoriesId").val(accessoriesInfo.accessoriesId);
      $("#accessoriesItem").text(accessoriesInfo.accessoriesName);
      $("#accessoriesColorId").val(accessoriesInfo.accessoriesColorId);
      $("#accessoriesColor").text(accessoriesInfo.accessoriesColor);
      $("#unitId").val(accessoriesInfo.unitId);
      $("#unit").text(accessoriesInfo.unit);
      $("#totalPoQty").text(accessoriesInfo.grandQty);
      $("#previousReceive").text(accessoriesInfo.previousReceiveQty);
      $('#itemSearchModal').modal('hide');
    }
  });
}

function editItemInDatabase(autoId) {
  const receiveQty = $("#unitQty-" + autoId).val();
  const tr = $("#rowId-" + autoId);
  const sizeId = tr.attr("data-size-id");
  const sizeName = $("#sizeName-" + autoId).text();
  //console.log(sizeName);
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './editReceiveSizeInTransaction',
    data: {
      autoId: autoId,
      unitQty: receiveQty,
      sizeId: sizeId,
      sizeName: sizeName
    },
    success: function (data) {
      if (data.result == "Successful") {
        alert("Edit Successfully...");
      } else if ("Used") {
        alert("Something Wrong..");
      }

    }
  });
}
function deleteItemFromDatabase(autoId) {
  const tr = $("#rowId-" + autoId);
  const sizeId = tr.attr("data-size-id");

  if (confirm("Are You Sure To Delete...")) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './deleteReceiveSizeFromTransaction',
      data: {
        autoId: autoId,
        sizeId: sizeId
      },
      success: function (data) {
        if (data.result == "Successful") {
          const parentRowId = $("#rowId-" + autoId).attr('data-parent-row');
          $("#rowId-" + autoId).remove();

          if ($(".rowGroup-" + parentRowId).length == 0) {
            $(".parentRowGroup-" + parentRowId).remove();
          }
        } else {

        }

      }
    });
  }

}

function deleteItemFromList(rowId) {
  const parentRowId = $("#rowId-" + rowId).attr('data-parent-row');
  $("#rowId-" + rowId).remove();

  if ($(".rowGroup-" + parentRowId).length == 0) {
    $(".parentRowGroup-" + parentRowId).remove();
  }
}




function setAccessoriesReceiveInfo(transactionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesReceiveInfo',
    data: {
      transactionId: transactionId
    },
    success: function (data) {
      const accessoriesReceive = data.accessoriesReceive;
      $("#transactionId").val(accessoriesReceive.transactionId);
      $("#grnNo").val(accessoriesReceive.grnNo);
      let date = accessoriesReceive.grnDate.split("/");
      $("#grnDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      $("#indentId").val(accessoriesReceive.indentId);
      $("#location").val(accessoriesReceive.location);
      $("#supplier").val(accessoriesReceive.supplierId).change();
      $("#challanNo").val(accessoriesReceive.challanNo);
      $("#challanDate").val(accessoriesReceive.challanDate);
      // $("#accessoriesItem").val(indent.accessoriesName);
      $("#remarks").val(accessoriesReceive.remarks);
      $("#preparedBy").val(accessoriesReceive.preparedBy).change();
      $('#searchModal').modal('hide');
      $("#accessoriesSizeList").empty();
      drawSearchAccessoriesSizeListTable(accessoriesReceive.accessoriesSizeList);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);

    }
  });
}



function amountCalculation(autoId) {
  const rate = $("#rate-" + autoId).val();
  const grandQty = $("#grandQty-" + autoId).text();
  const amount = rate * grandQty;
  $("#amount-" + autoId).text(amount);
}

function changeSelect(groupClassName, element) {
  $("." + groupClassName).val(element.value);
}

function totalSizeQtyCount(id) {
  const elements = $(".sizeReceiveGroup-" + id);
  const length = elements.length;
  let total = 0;
  for (let i = 0; i < length; i++) {

    total += Number(elements[i].value);
  };
  $("#accessoriesReceiveQty-" + id).text(total);
  $("#bottomTotal-" + id).text(total);
}

function drawAccessoriesSizeListTable(data) {
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);

  let rows = "", tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempAccessoriesId, tempAccessoriesColorId;
  
  const rowList = $("tr .newSizeRow");
  const newDataLength = rowList.length;
  const length = data.length;

  let receiveQtyList = [];
  let parentRowId = 0;
  let tempTotalReceive = 0;
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    //const id = rowData.autoId;
    const id = newDataLength + i;
    if (!(rowData.accessoriesColorId == tempAccessoriesColorId && rowData.accessoriesId == tempAccessoriesId && rowData.itemColorId == tempItemColorId && rowData.itemId == tempItemId && rowData.styleId == tempStyleId && rowData.purchaseOrder == tempPurchaseOrder)) {
      if (!(tempPurchaseOrder === "")) {
        rows += `<tr>
                  <td colspan='2'>Total</td>
                  <td id='bottomTotal-${parentRowId}'>${tempTotalReceive}</td>
            
              </tr>
            </tbody>
          </table>
        </td> 
        </tr>`;
        receiveQtyList.push(tempTotalReceive);
        parentRowId++;
        tempTotalReceive = 0;
      }
      tempAccessoriesColorId = rowData.accessoriesColorId;
      tempAccessoriesId = rowData.accessoriesId;
      tempItemColorId = rowData.itemColorId;
      tempItemId = rowData.itemId;
      tempStyleId = rowData.styleId;
      tempPurchaseOrder = rowData.purchaseOrder;
      rows += `<tr class='odd parentRowGroup-${parentRowId}'>
                <td id='accessoriesName-${parentRowId}'>${rowData.accessoriesName}</td>
                <td id='accessoriesColor-${parentRowId}'>${rowData.accessoriesColorName}</td>
                <td>${rowData.unit}</td>
                <td>${rowData.orderQty}</td>
                <td id='previousReceiveQty-${parentRowId}'>${rowData.previousReceiveQty}</td>
                <td id='accessoriesReceiveQty-${parentRowId}'>0</td>
                <td><div class="table-expandable-arrow"></div></td>
            </tr>
            <tr class='even parentRowGroup-${parentRowId}' style='display:none'>
              <td colspan="7">
                <div class="row px-5">
                  
                  <div class="col-md-2 px-1">
                      <label>Purchase Order:</label>
                  </div>
                  <div class="col-md-4 px-1">
                    <b><label>${rowData.purchaseOrder}</label></b>
                  </div>

                  <div class="col-md-1 px-1">
                      <label>Item Name:</label>
                  </div>
                  <div class="col-md-5 px-1">
                    <b><label>${rowData.itemName}</label></b>
                  </div>

                  <div class="col-md-2 px-1">
                      <label>Style No:</label>
                  </div>
                  <div class="col-md-4 px-1">
                    <b><label>${rowData.styleNo}</label></b>
                  </div>

                  <div class="col-md-1 px-1">
                      <label>Item Color:</label>
                  </div>
                  <div class="col-md-5 px-1">
                    <b><label>${rowData.itemColor}</label></b>
                  </div>
                </div>
                <table class='table table-hover table-bordered table-sm mb-0 small-font pl-5'>
                  <thead>
                    <tr>
                      <th>Size</th>
                      <th>Unit</th>
                      <th>Receive Qty</th>
                      <th>Rack Name</th>
                      <th>Bin Name</th>
                    </tr>
                  </thead>
                  <tbody>`;
    }

    const rackSelect = `<select id='rackId-${id}' ${1 == 0 ? 'onchange=changeSelect("selectRackGroup-' + parentRowId + '",this)' : ''}  class='selectRackGroup-${parentRowId} form-control-sm'>
                                     ${rackOptions}
                                   </select>`;
    const binSelect = `<select id='binId-${id}' ${1 == 0 ? 'onchange=changeSelect("selectBinGroup-' + parentRowId + '",this)' : ''} class='selectBinGroup-${parentRowId} form-control-sm'>
                                        ${rackOptions}
                                  </select>`;
    rows += "<tr id='rowId-" + id + "' class='newSizeRow sizeRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "' data-size-id='" + rowData.sizeId + "' data-unit-id='" + rowData.unitId + "'>"
      + "<td id='sizeName-" + id + "'>" + rowData.sizeName + "</td>"
      + "<td>" + rowData.unit + "</td>"
      + "<td><input id='unitQty-" + id + "' type='number' onblur='totalSizeQtyCount(" + parentRowId + ")' class='sizeReceiveGroup-" + parentRowId + " form-control-sm max-width-100' value='" + rowData.unitQty + "'></td>"
      + "<td>" + rackSelect + "</td>"
      + "<td>" + binSelect + "</td>"
      + (rowData.unitQty > (rowData.returnQty + rowData.issueQty) ? "<td><i class='fa fa-edit' onclick='editItemInDatabase(" + id + "," + (rowData.returnQty + rowData.issueQty) + ")' style='cursor:pointer;'> </i></td>" : "")
      + ((rowData.returnQty + rowData.issueQty) == 0 ? "<td><i class='fa fa-trash' onclick='deleteItemFromDatabase(" + id + ")' style='cursor:pointer;'> </i></td>" : "")
      + "</tr>";
    tempTotalReceive += rowData.unitQty;
  }
  rows += `<tr>
                  <td colspan='2'>Total</td>
                  <td id='bottomTotal-${parentRowId}'>${tempTotalReceive}</td>
            
              </tr>
            </tbody>
          </table>
        </td> 
        </tr>`;
  receiveQtyList.push(tempTotalReceive);

  $("#accessoriesSizeList").append(rows);

  receiveQtyList.forEach((qty, index) => {
    $("#accessoriesReceiveQty-" + index).text(qty);
  });

  data.forEach((size, index) => {
    $("#rackId-" + size.autoId).val(size.rackName);
    $("#binId-" + size.autoId).val(size.binName);
  })

}


function drawSearchAccessoriesSizeListTable(data) {
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);

  let rows = "", tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempAccessoriesId, tempAccessoriesColorId;
  
  const rowList = $("tr .newSizeRow");
  const newDataLength = rowList.length;
  const length = data.length;

  let receiveQtyList = [];
  let parentRowId = 0;
  let tempTotalReceive = 0;
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    //const id = rowData.autoId;
    const id = rowData.autoId;
    if (!(rowData.accessoriesColorId == tempAccessoriesColorId && rowData.accessoriesId == tempAccessoriesId && rowData.itemColorId == tempItemColorId && rowData.itemId == tempItemId && rowData.styleId == tempStyleId && rowData.purchaseOrder == tempPurchaseOrder)) {
      if (!(tempPurchaseOrder === "")) {
        rows += `<tr>
                  <td colspan='2'>Total</td>
                  <td id='bottomTotal-${parentRowId}'>${tempTotalReceive}</td>
            
              </tr>
            </tbody>
          </table>
        </td> 
        </tr>`;
        receiveQtyList.push(tempTotalReceive);
        parentRowId++;
        tempTotalReceive = 0;
      }
      tempAccessoriesColorId = rowData.accessoriesColorId;
      tempAccessoriesId = rowData.accessoriesId;
      tempItemColorId = rowData.itemColorId;
      tempItemId = rowData.itemId;
      tempStyleId = rowData.styleId;
      tempPurchaseOrder = rowData.purchaseOrder;
      rows += `<tr class='odd parentRowGroup-${parentRowId}'>
                <td id='accessoriesName-${parentRowId}'>${rowData.accessoriesName}</td>
                <td id='accessoriesColor-${parentRowId}'>${rowData.accessoriesColorName}</td>
                <td>${rowData.unit}</td>
                <td>${rowData.orderQty}</td>
                <td id='previousReceiveQty-${parentRowId}'>${rowData.previousReceiveQty}</td>
                <td id='accessoriesReceiveQty-${parentRowId}'>0</td>
                <td><div class="table-expandable-arrow"></div></td>
            </tr>
            <tr class='even parentRowGroup-${parentRowId}' style='display:none'>
              <td colspan="7">
                <div class="row px-5">
                  
                  <div class="col-md-2 px-1">
                      <label>Purchase Order:</label>
                  </div>
                  <div class="col-md-4 px-1">
                    <b><label>${rowData.purchaseOrder}</label></b>
                  </div>

                  <div class="col-md-1 px-1">
                      <label>Item Name:</label>
                  </div>
                  <div class="col-md-5 px-1">
                    <b><label>${rowData.itemName}</label></b>
                  </div>

                  <div class="col-md-2 px-1">
                      <label>Style No:</label>
                  </div>
                  <div class="col-md-4 px-1">
                    <b><label>${rowData.styleNo}</label></b>
                  </div>

                  <div class="col-md-1 px-1">
                      <label>Item Color:</label>
                  </div>
                  <div class="col-md-5 px-1">
                    <b><label>${rowData.itemColor}</label></b>
                  </div>
                </div>
                <table class='table table-hover table-bordered table-sm mb-0 small-font pl-5'>
                  <thead>
                    <tr>
                      <th>Size</th>
                      <th>Unit</th>
                      <th>Receive Qty</th>
                      <th>Rack Name</th>
                      <th>Bin Name</th>
                    </tr>
                  </thead>
                  <tbody>`;
    }

    const rackSelect = `<select id='rackId-${id}' ${1 == 0 ? 'onchange=changeSelect("selectRackGroup-' + parentRowId + '",this)' : ''}  class='selectRackGroup-${parentRowId} form-control-sm'>
                                     ${rackOptions}
                                   </select>`;
    const binSelect = `<select id='binId-${id}' ${1 == 0 ? 'onchange=changeSelect("selectBinGroup-' + parentRowId + '",this)' : ''} class='selectBinGroup-${parentRowId} form-control-sm'>
                                        ${rackOptions}
                                  </select>`;
    rows += "<tr id='rowId-" + id + "' class='newSizeRow sizeRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "' data-size-id='" + rowData.sizeId + "' data-unit-id='" + rowData.unitId + "'>"
      + "<td id='sizeName-" + id + "'>" + rowData.sizeName + "</td>"
      + "<td>" + rowData.unit + "</td>"
      + "<td><input id='unitQty-" + id + "' type='number' onblur='totalSizeQtyCount(" + parentRowId + ")' class='sizeReceiveGroup-" + parentRowId + " form-control-sm max-width-100' value='" + rowData.unitQty + "'></td>"
      + "<td>" + rackSelect + "</td>"
      + "<td>" + binSelect + "</td>"
      + (rowData.unitQty > (rowData.returnQty + rowData.issueQty) ? "<td><i class='fa fa-edit' onclick='editItemInDatabase(" + id + "," + (rowData.returnQty + rowData.issueQty) + ")' style='cursor:pointer;'> </i></td>" : "")
      + ((rowData.returnQty + rowData.issueQty) == 0 ? "<td><i class='fa fa-trash' onclick='deleteItemFromDatabase(" + id + ")' style='cursor:pointer;'> </i></td>" : "")
      + "</tr>";
    tempTotalReceive += rowData.unitQty;
  }
  rows += `<tr>
                  <td colspan='2'>Total</td>
                  <td id='bottomTotal-${parentRowId}'>${tempTotalReceive}</td>
            
              </tr>
            </tbody>
          </table>
        </td> 
        </tr>`;
  receiveQtyList.push(tempTotalReceive);

  $("#accessoriesSizeList").append(rows);

  receiveQtyList.forEach((qty, index) => {
    $("#accessoriesReceiveQty-" + index).text(qty);
  });

  data.forEach((size, index) => {
    $("#rackId-" + size.autoId).val(size.rackName);
    $("#binId-" + size.autoId).val(size.binName);
  })

}

function drawAccessoriesReceiveListTable(data) {
  let rows = [];
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    let row = $("<tr/>")
    row.append($("<td>" + rowData.transactionId + "</td>"));
    row.append($("<td>" + rowData.grnNo + "</td>"));
    row.append($("<td>" + rowData.grnDate + "</td>"));
    row.append($("<td ><i class='fa fa-search' onclick=\"setAccessoriesReceiveInfo('" + rowData.transactionId + "')\" style='cursor:pointer;'> </i></td>"));

    rows.push(row);
  }

  return rows;
}


function drawAccessoriesListTable(data) {
  let rows = [];
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    let row = $("<tr/>")
    row.append($("<td>" + rowData.itemName + "</td>"));
    row.append($("<td>" + rowData.itemColorName + "</td>"));
    row.append($("<td>" + rowData.accessoriesName + "</td>"));
    row.append($("<td ><i class='fa fa-search' onclick=\"setAccessoriesInfo('" + rowData.autoId + "')\" style='cursor:pointer;'> </i></td>"));

    rows.push(row);
  }
  return rows;
}

function drawPurchaseOrderListTable(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    ////console.log(rowData);
    rows += "<tr id='row-" + i + "' data-purchase-order='" + rowData.po + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "'>"
      + "<td id='purchaseOrder-" + i + "'>" + rowData.po + "</td>"
      + "<td id='styleName-" + i + "'>" + rowData.style + "</td>"
      + "<td id='itemName-" + i + "'>" + rowData.itemname + "</td>"
      + "<td id='itemColor-" + i + "'>" + rowData.itemcolor + "</td>"
      + "<td id='accessoriesName-" + i + "'>" + rowData.accessoriesName + "</td>"
      + "<td id='accessoriesColor-" + i + "'>" + rowData.accessoriescolor + "</td>"
      + "<td ><input class='check' type='checkbox' id='check-" + i + "'></td>"
      + "</tr>";

  }

  $("#purchaseOrderList").html(rows);
}

$(document).ready(function () {
  $('.table-expandable tbody').on("click", ".odd", function () {
    var element = $(this);
    element.next('tr').toggle(0);
    element.find(".table-expandable-arrow").toggleClass("up");
  })
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
  $("#purchaseOrderSearch , #styleNoSearch, #itemNameSearch,#accessoriesItemSearch,#colorSearch").on("keyup", function () {
    const po = $("#purchaseOrderSearch").val().toLowerCase();
    const style = $("#styleNoSearch").val().toLowerCase();
    const item = $("#itemNameSearch").val().toLowerCase();
    const accessories = $("#accessoriesItemSearch").val().toLowerCase();
    const color = $("#colorSearch").val().toLowerCase();

    $("#purchaseOrderList tr").filter(function () {
      const id = this.id.slice(4);

      if ((!po.length || $("#purchaseOrder-" + id).text().toLowerCase().indexOf(po) > -1) &&
        (!style.length || $("#styleName-" + id).text().toLowerCase().indexOf(style) > -1) &&
        (!item.length || $("#itemName-" + id).text().toLowerCase().indexOf(item) > -1) &&
        (!accessories.length || $("#accessoriesName-" + id).text().toLowerCase().indexOf(accessories) > -1) &&
        (!color.length || $("#itemColor-" + id).text().toLowerCase().indexOf(color) > -1 || $("#accessoriesColor-" + id).text().toLowerCase().indexOf(color) > -1)) {
        $(this).show();
      } else {
        $(this).hide();
      }
    });
  });
});

$(document).ready(function () {
  $("#searchEverything").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#purchaseOrderList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});


var today = new Date();
document.getElementById("grnDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);

