
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
  $("#fabricsItemSearch").val("");
  $("#colorSearch").val("");
})

$("#itemSearchBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsPurchaseOrderIndentList',
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
    url: './getFabricsReceiveList',
    data: {},
    success: function (data) {
      $("#fabricsReceiveList").empty();
      $("#fabricsReceiveList").append(drawFabricsReceiveListTable(data.fabricsReceiveList));

    }
  });
});

function submitAction() {
  const rowList = $("tr .newRollRow");
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
  const departmentId = $("#departmentId").val();
  const userId = $("#userId").val();

  let rollList = ""

  for (let i = 0; i < length; i++) {

    const row = rowList[i];
    const id = row.id.slice(6);
    const parentRow = row.getAttribute('data-parent-row');
    const purchaseOrder = row.getAttribute('data-purchase-order');
    const styleId = row.getAttribute('data-style-id');
    const itemId = row.getAttribute('data-item-id');
    const itemColorId = row.getAttribute('data-item-color-id');
    const fabricsId = row.getAttribute('data-fabrics-id');
    const fabricsColorId = row.getAttribute('data-fabrics-color-id');
    const unitId = row.getAttribute('data-unit-id');
    const fabricsName = $("fabricsName-" + parentRow).text();
    const fabricsColor = $("fabricsColor-" + parentRow).text();
    const rollId = row.getAttribute('data-roll-id');
    const supplierRollId = $("#rollId-" + id).text();
    const unitQty = $("#unitQty-" + id).val().trim() == '' ? "0" : $("#unitQty-" + id).val();
    const qcPassedQty = 0;
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    const qcPassedType = 0;
    rollList += `purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},fabricsId : ${fabricsId},fabricsName : ${fabricsName},fabricsColorId : ${fabricsColorId},fabricsColorName : ${fabricsColor},rollId : ${rollId},supplierRollId : ${supplierRollId},unitId : ${unitId},unitQty : ${unitQty},qcPassedQty : ${qcPassedQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType} #`;
  }

  rollList = rollList.slice(0, -1);

  if (length > 0) {
    if (transactionId != '') {
      if (grnNo != '') {
        if (supplier != '0') {

          if (confirm("Are you sure to submit this Fabrics Receive...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './submitFabricsReceive',
              data: {
                transactionId: transactionId,
                grnNo: grnNo,
                grnDate: grnDate,
                location: location,
                rollList: rollList,
                supplierId: supplier,
                challanNo: challanNo,
                challanDate: challanDate,
                remarks: remarks,
                departmentId : departmentId,
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
    warningAlert("Please Enter Fabrics Roll");
  }
}


function editAction() {
  const rowList = $("tr .newRollRow");
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

  let rollList = ""

  for (let i = 0; i < length; i++) {

    const row = rowList[i];
    const id = row.id.slice(6);
    const parentRow = row.getAttribute('data-parent-row');
    const purchaseOrder = row.getAttribute('data-purchase-order');
    const styleId = row.getAttribute('data-style-id');
    const itemId = row.getAttribute('data-item-id');
    const itemColorId = row.getAttribute('data-item-color-id');
    const fabricsId = row.getAttribute('data-fabrics-id');
    const fabricsColorId = row.getAttribute('data-fabrics-color-id');
    const unitId = row.getAttribute('data-unit-id');
    const fabricsName = $("fabricsName-" + parentRow).text();
    const fabricsColor = $("fabricsColor-" + parentRow).text();
    const rollId = row.getAttribute('data-roll-id');
    const supplierRollId = $("#rollId-" + id).text();
    const unitQty = $("#unitQty-" + id).val().trim() == '' ? "0" : $("#unitQty-" + id).val();
    const qcPassedQty = 0;
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    const qcPassedType = 0;
   
    rollList += `purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},fabricsId : ${fabricsId},fabricsName : ${fabricsName},fabricsColorId : ${fabricsColorId},fabricsColorName : ${fabricsColor},rollId : ${rollId},supplierRollId : ${supplierRollId},unitId : ${unitId},unitQty : ${unitQty},qcPassedQty : ${qcPassedQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType} #`;
  }

  rollList = rollList.slice(0, -1);

 
    if (transactionId != '') {
      if (grnNo != '') {
        if (supplier != '0') {

          if (confirm("Are you sure to submit this Fabrics Receive...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './editFabricsReceive',
              data: {
                transactionId: transactionId,
                grnNo: grnNo,
                grnDate: grnDate,
                location: location,
                rollList: rollList,
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

function setFabricsInfo(autoId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsIndentInfo',
    data: {
      autoId: autoId
    },
    success: function (data) {
      console.log(data.fabricsInfo);
      const fabricsInfo = data.fabricsInfo;
      $("#purchaseOrder").text(fabricsInfo.purchaseOrder);
      $("#styleId").val(fabricsInfo.styleId);
      $("#styleNo").text(fabricsInfo.styleName);
      $("#styleItemId").val(fabricsInfo.itemId);
      $("#itemName").text(fabricsInfo.itemName);
      $("#itemColorId").val(fabricsInfo.itemColorId);
      $("#itemColor").text(fabricsInfo.itemColorName);
      $("#fabricsId").val(fabricsInfo.fabricsId);
      $("#fabricsItem").text(fabricsInfo.fabricsName);
      $("#fabricsColorId").val(fabricsInfo.fabricsColorId);
      $("#fabricsColor").text(fabricsInfo.fabricsColor);
      $("#unitId").val(fabricsInfo.unitId);
      $("#unit").text(fabricsInfo.unit);
      $("#totalPoQty").text(fabricsInfo.grandQty);
      $("#previousReceive").text(fabricsInfo.previousReceiveQty);
      $('#itemSearchModal').modal('hide');
    }
  });
}

function editItemInDatabase(autoId) {
  const receiveQty = $("#unitQty-" + autoId).val();
  const tr = $("#rowId-"+autoId);
  const rollId = tr.attr("data-roll-id");
  const supplierRollId = $("#rollId-"+autoId).text();
  console.log(supplierRollId);
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './editReceiveRollInTransaction',
    data: {
      autoId: autoId,
      unitQty: receiveQty,
      rollId : rollId,
      supplierRollId : supplierRollId
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
  const tr = $("#rowId-"+autoId);
  const rollId = tr.attr("data-roll-id");
  
  if(confirm("Are You Sure To Delete...")){
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './deleteReceiveRollFromTransaction',
      data: {
        autoId: autoId,
        rollId : rollId
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




function setFabricsReceiveInfo(transactionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsReceiveInfo',
    data: {
      transactionId: transactionId
    },
    success: function (data) {
      const fabricsReceive = data.fabricsReceive;
      $("#transactionId").val(fabricsReceive.transactionId);
      $("#grnNo").val(fabricsReceive.grnNo);
      let date = fabricsReceive.grnDate.split("/");
      $("#grnDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      $("#indentId").val(fabricsReceive.indentId);
      $("#location").val(fabricsReceive.location);
      $("#supplier").val(fabricsReceive.supplierId).change();
      $("#challanNo").val(fabricsReceive.challanNo);
      $("#challanDate").val(fabricsReceive.challanDate);
      // $("#fabricsItem").val(indent.fabricsName);
      $("#remarks").val(fabricsReceive.remarks);
      $("#preparedBy").val(fabricsReceive.preparedBy).change();
      $('#searchModal').modal('hide');
      $("#rollList").empty();
      drawFabricsRollListTable(fabricsReceive.fabricsRollList);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);

    }
  });
}



$("#noOfRoll").on('keyup', function (e) {
  if (e.key === 'Enter' || e.keyCode === 13) {
    length = $("#rollList tr").length;
    const noOfRoll = $("#noOfRoll").val();
    const orderQty = $("#totalPoQty").text();
    const previousRecQty = $("#previousReceive").text();
    const receiveQty = $("#receiveQty").val();
    const purchaseOrder = $("#purchaseOrder").text();
    const styleId = $("#styleId").val();
    const styleNo = $("#styleNo").text();
    const itemId = $("#styleItemId").val();
    const itemName = $("#itemName").text();
    const itemColor = $("#itemColor").text();
    const itemColorId = $("#itemColorId").val();
    const fabricsId = $("#fabricsId").val();
    const fabricsName = $("#fabricsItem").text();
    const fabricsColorId = $("#fabricsColorId").val();
    const fabricsColor = $("#fabricsColor").text();
    const fabricsRate = $("#fabricsRate").val();
    const unitId = $("#unitId").val();
    const unit = $("#unit").text();
    let tempTotalReceive = 0;

    if (noOfRoll != '') {
      if (receiveQty != '') {
        if (fabricsId != '' && fabricsName != '') {
          const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);



          const qty = receiveQty / noOfRoll;
          let rows = `<tr class='odd parentRowGroup-${length}'>
                    <td id='fabricsName-${length}'>${fabricsName}</td>
                    <td id='fabricsColor-${length}'>${fabricsColor}</td>
                    <td>${unit}</td>
                    <td>${orderQty}</td>
                    <td id='previousReceiveQty-${length}'>${previousRecQty}</td>
                    <td id='fabricsReceiveQty-${length}'>0</td>
                    <td><div class="table-expandable-arrow"></div></td>
                </tr>
                <tr class='even parentRowGroup-${length}' style='display:none'>
                  <td colspan="7">
                    <div class="row px-5">
                      
                      <div class="col-md-2 px-1">
                          <label>Purchase Order:</label>
                      </div>
                      <div class="col-md-4 px-1">
                        <b><label>${purchaseOrder}</label></b>
                      </div>

                      <div class="col-md-1 px-1">
                          <label>Item Name:</label>
                      </div>
                      <div class="col-md-5 px-1">
                        <b><label>${itemName}</label></b>
                      </div>

                      <div class="col-md-2 px-1">
                          <label>Style No:</label>
                      </div>
                      <div class="col-md-4 px-1">
                        <b><label>${styleNo}</label></b>
                      </div>

                      <div class="col-md-1 px-1">
                          <label>Item Color:</label>
                      </div>
                      <div class="col-md-5 px-1">
                        <b><label>${itemColor}</label></b>
                      </div>
                    </div>
                    <table class='table table-hover table-bordered table-sm mb-0 small-font pl-5'>
                      <thead>
                        <tr>
                          <th>Roll Id</th>
                          <th>Unit</th>
                          <th>Receive Qty</th>
                          <th>Rack Name</th>
                          <th>Bin Name</th>
                        </tr>
                      </thead>
                      <tbody>`;

          for (let i = 1; i <= noOfRoll; i++) {
            const id = length + i;
            const rackSelect = `<select id='rackId-${id}' ${i == 1 ? 'onchange=changeSelect("selectRackGroup-' + length + '",this)' : ''}  class='selectRackGroup-${length} form-control-sm'>
                                             ${rackOptions}
                                           </select>`;
            const binSelect = `<select id='binId-${id}' ${i == 1 ? 'onchange=changeSelect("selectBinGroup-' + length + '",this)' : ''} class='selectBinGroup-${length} form-control-sm'>
                                                ${rackOptions}
                                          </select>`;
            rows += "<tr id='rowId-" + id + "' class='newRollRow rollRowList rowGroup-" + length + "' data-parent-row='" + length + "' data-purchase-order='" + purchaseOrder + "' data-style-id='" + styleId + "' data-item-id='" + itemId + "' data-item-color-id='" + itemColorId + "' data-fabrics-id='" + fabricsId + "' data-fabrics-color-id='" + fabricsColorId + "' data-roll-id='" + i + "' data-unit-id='" + unitId + "'>"
              + "<td id='rollId-" + id + "' contenteditable = 'true'>" + i + "</td>"
              + "<td>" + unit + "</td>"
              + "<td><input id='unitQty-" + id + "' type='number' onblur='totalRollQtyCount(" + length + ")' class='rollReceiveGroup-" + length + " form-control-sm max-width-100' value='" + qty + "'></td>"
              + "<td>" + rackSelect + "</td>"
              + "<td>" + binSelect + "</td>"
              + "<td><i class='fa fa-trash' onclick='deleteItemFromList(" + id + ")' style='cursor:pointer;'> </i></td>"
              + "</tr>";
            tempTotalReceive += qty;
          }

          rows += `<tr>
                            <td colspan='2'>Total</td>
                            <td id='bottomTotal-${length}'>${tempTotalReceive}</td>

                        </tr>
                      </tbody>
                    </table>
                    </td>
                </tr>`;
          $("#rollList").append(rows);
          $("#fabricsReceiveQty-" + length).text(tempTotalReceive);
        } else {
          alert("Please Select Fabrics");
        }
      } else {
        alert("Please Enter GRN Qty");
      }
    } else {
      alert("Please Enter No Of Roll");
    }
  }
});

function amountCalculation(autoId) {
  const rate = $("#rate-" + autoId).val();
  const grandQty = $("#grandQty-" + autoId).text();
  const amount = rate * grandQty;
  $("#amount-" + autoId).text(amount);
}

function changeSelect(groupClassName, element) {
  $("." + groupClassName).val(element.value);
}

function totalRollQtyCount(id) {
  const elements = $(".rollReceiveGroup-" + id);
  const length = elements.length;
  let total = 0;
  for (let i = 0; i < length; i++) {

    total += Number(elements[i].value);
  };
  $("#fabricsReceiveQty-" + id).text(total);
  $("#bottomTotal-" + id).text(total);
}

function drawFabricsRollListTable(data) {
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);

  let rows = "", tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempFabricsId, tempFabricsColorId;
  const length = data.length;

  let receiveQtyList = [];
  let parentRowId = 0;
  let tempTotalReceive = 0;
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    if (!(rowData.fabricsColorId == tempFabricsColorId && rowData.fabricsId == tempFabricsId && rowData.itemColorId == tempItemColorId && rowData.itemId == tempItemId && rowData.styleId == tempStyleId && rowData.purchaseOrder == tempPurchaseOrder)) {
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
      tempFabricsColorId = rowData.fabricsColorId;
      tempFabricsId = rowData.fabricsId;
      tempItemColorId = rowData.itemColorId;
      tempItemId = rowData.itemId;
      tempStyleId = rowData.styleId;
      tempPurchaseOrder = rowData.purchaseOrder;
      rows += `<tr class='odd parentRowGroup-${parentRowId}'>
                <td id='fabricsName-${parentRowId}'>${rowData.fabricsName}</td>
                <td id='fabricsColor-${parentRowId}'>${rowData.fabricsColorName}</td>
                <td>${rowData.unit}</td>
                <td>${rowData.orderQty}</td>
                <td id='previousReceiveQty-${parentRowId}'>${rowData.previousReceiveQty}</td>
                <td id='fabricsReceiveQty-${parentRowId}'>0</td>
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
                      <th>Roll Id</th>
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
    rows += "<tr id='rowId-" + id + "' class='rollRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-fabrics-id='" + rowData.fabricsId + "' data-fabrics-color-id='" + rowData.fabricsColorId + "' data-roll-id='" + rowData.rollId + "' data-unit-id='" + rowData.unitId + "'>"
      + "<td id='rollId-" + id + "' contenteditable = 'true'>" + rowData.supplierRollId + "</td>"
      + "<td>" + rowData.unit + "</td>"
      + "<td><input id='unitQty-" + id + "' type='number' onblur='totalRollQtyCount(" + parentRowId + ")' class='rollReceiveGroup-" + parentRowId + " form-control-sm max-width-100' value='" + rowData.unitQty + "'></td>"
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

  $("#rollList").append(rows);

  receiveQtyList.forEach((qty, index) => {
    $("#fabricsReceiveQty-" + index).text(qty);
  });

  data.forEach((roll,index)=>{
    $("#rackId-"+roll.autoId).val(roll.rackName);
    $("#binId-"+roll.autoId).val(roll.binName);
  })

}

function drawFabricsReceiveListTable(data) {
  let rows = [];
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    let row = $("<tr/>")
    row.append($("<td>" + rowData.transactionId + "</td>"));
    row.append($("<td>" + rowData.grnNo + "</td>"));
    row.append($("<td>" + rowData.grnDate + "</td>"));
    row.append($("<td ><i class='fa fa-search' onclick=\"setFabricsReceiveInfo('" + rowData.transactionId + "')\" style='cursor:pointer;'> </i></td>"));

    rows.push(row);
  }

  return rows;
}


function drawFabricsListTable(data) {
  let rows = [];
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    let row = $("<tr/>")
    row.append($("<td>" + rowData.itemName + "</td>"));
    row.append($("<td>" + rowData.itemColorName + "</td>"));
    row.append($("<td>" + rowData.fabricsName + "</td>"));
    row.append($("<td ><i class='fa fa-search' onclick=\"setFabricsInfo('" + rowData.autoId + "')\" style='cursor:pointer;'> </i></td>"));

    rows.push(row);
  }
  return rows;
}

function drawPurchaseOrderListTable(data) {
  let rows = "";
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];

    rows += "<tr id='" + rowData.autoId + "'>"
      + "<td id='purchaseOrder-" + rowData.autoId + "'>" + rowData.purchaseOrder + "</td>"
      + "<td id='styleName-" + rowData.autoId + "'>" + rowData.styleName + "</td>"
      + "<td id='itemName-" + rowData.autoId + "'>" + rowData.itemName + "</td>"
      + "<td id='itemColor-" + rowData.autoId + "'>" + rowData.itemColorName + "</td>"
      + "<td id='fabricsName-" + rowData.autoId + "'>" + rowData.fabricsName + "</td>"
      + "<td id='fabricsColor-" + rowData.autoId + "'>" + rowData.fabricsColor + "</td>"
      + "<td><i class='fa fa-search' onclick=\"setFabricsInfo('" + rowData.autoId + "')\" style='cursor:pointer;'> </i></td>"
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
  $("#purchaseOrderSearch , #styleNoSearch, #itemNameSearch,#fabricsItemSearch,#colorSearch").on("keyup", function () {
    const po = $("#purchaseOrderSearch").val().toLowerCase();
    const style = $("#styleNoSearch").val().toLowerCase();
    const item = $("#itemNameSearch").val().toLowerCase();
    const fabrics = $("#fabricsItemSearch").val().toLowerCase();
    const color = $("#colorSearch").val().toLowerCase();

    $("#purchaseOrderList tr").filter(function () {
      const id = this.id;

      if ((!po.length || $("#purchaseOrder-" + id).text().toLowerCase().indexOf(po) > -1) &&
        (!style.length || $("#styleName-" + id).text().toLowerCase().indexOf(style) > -1) &&
        (!item.length || $("#itemName-" + id).text().toLowerCase().indexOf(item) > -1) &&
        (!fabrics.length || $("#fabricsName-" + id).text().toLowerCase().indexOf(fabrics) > -1) &&
        (!color.length || $("#itemColor-" + id).text().toLowerCase().indexOf(color) > -1 || $("#fabricsColor-" + id).text().toLowerCase().indexOf(color) > -1)) {
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

