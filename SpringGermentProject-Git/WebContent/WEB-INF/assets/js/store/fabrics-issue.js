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

$("#newFabricsIssueBtn").click(function () {

  $("#issueTransactionId").val("-- New Transaction --");
  $("#btnSubmit").prop("disabled", false);
  $("#btnEdit").prop("disabled", true);
});

$("#findFabricsIssueBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsIssueList',
    data: {},
    success: function (data) {
      drawFabricsIssueListTable(data.fabricsIssueList);
    }
  });
});


$("#fabricsSearchBtn").click(function () {
  const departmentId = "1";
  
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAvailableFabricsRollList',
    data: {
      departmentId : departmentId
    },
    success: function (data) {
      drawFabricsRollListSearchTable(data.fabricsRollList)
      $("#rollSearchModal").modal('show');
    }
  });
  
});


function editItemInDatabase(autoId) {
  const issueQty = $("#rollIssueQty-" + autoId).val();

  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './editIssueRollInTransaction',
    data: {
      autoId: autoId,
      unitQty: issueQty
    },
    success: function (data) {
      if (data.result == "Successful") {
        alert("Edit Successfully...");
      } else if (data.result == "Issue Qty Exist") {
        alert("Issue Qty Exist...");
      }
    }
  });
}
function deleteItemFromDatabase(autoId) {
  if(confirm("Are You Sure To Delete...")){
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './deleteIssueRollFromTransaction',
      data: {
        autoId: autoId
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

function totalIssueQtyCount(id) {
  const elements = $(".rollIssueGroup-" + id);
  const length = elements.length;
  let total = 0;
  for (let i = 0; i < length; i++) {

    total += Number(elements[i].value);
  };
  $("#issueQty-" + id).text(total);
  $("#bottomTotalIssue-" + id).text(total);
}

document.getElementById("checkAll").addEventListener("click", function () {
  if ($(this).prop('checked')) {
    $(".check").prop('checked', true);
  } else {
    $(".check").prop('checked', false);
  }
});

$("#rollAddBtn").click(function(){
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);
  let rows = "",tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempFabricsId, tempFabricsColorId;
  let parentRowId = 0,tempTotalBalance=0;
  let balanceQtyList = [];
  let rackIdList = [];
  let binIdList = [];
  $("#fabricsRollSearchList tr").filter(function () {
    const id = this.id.slice(4);
    
    if($("#check-"+id).prop('checked')){
      const fabricsName = $("#fabricsName-"+id).text();
      const fabricsColorName = $("#fabricsColor-"+id).text();
      const rollId = this.getAttribute("data-roll-id");
      const supplierRollId = $("#rollId-"+id).text();
      const balanceQty = Number($("#balanceQty-"+id).text());
    

      const purchaseOrder = this.getAttribute("data-purchase-order");
      const styleId = this.getAttribute("data-style-id");
      const styleNo = $("#styleNo-"+id).text();
      const itemId = this.getAttribute("data-item-id");
      const itemName = $("#itemName-"+id).text();
      const itemColorId = this.getAttribute("data-item-color-id");
      const itemColor = $("#itemColor-"+id).text();
      const fabricsId = this.getAttribute("data-fabrics-id");
      const fabricsColorId = this.getAttribute("data-fabrics-color-id");
      const unitId = this.getAttribute("data-unit-id");
      const unit = this.getAttribute("data-unit");
      const rackName = this.getAttribute("data-rack-name");
      const binName = this.getAttribute("data-bin-name");
      const receiveQty = this.getAttribute("data-receive-qty");
      const issueQty = this.getAttribute("data-issue-qty");
      const returnQty = this.getAttribute("data-return-qty");

      if (!(fabricsColorId == tempFabricsColorId && fabricsId == tempFabricsId && itemColorId == tempItemColorId && itemId == tempItemId && styleId == tempStyleId && purchaseOrder == tempPurchaseOrder)) {
        if (!(tempPurchaseOrder === "")) {
          rows += `<tr>
                    <td colspan='2'>Total</td>
                    <td id='bottomTotalBalance-${parentRowId}'>${tempTotalBalance}</td>
                    <td id='bottomTotalIssue-${parentRowId}'>${tempTotalBalance}</td>         
                </tr>
              </tbody>
            </table>
          </td> 
          </tr>`;
          balanceQtyList.push(tempTotalBalance);
          parentRowId++;
          tempTotalBalance = 0;
        }
        tempFabricsColorId = fabricsColorId;
        tempFabricsId = fabricsId;
        tempItemColorId = itemColorId;
        tempItemId = itemId;
        tempStyleId = styleId;
        tempPurchaseOrder = purchaseOrder;
        rows += `<tr class='odd parentRowGroup-${parentRowId}'>
                  <td id='fabricsName-${parentRowId}'>${fabricsName}</td>
                  <td id='fabricsColor-${parentRowId}'>${fabricsColorName}</td>
                  <td>${unit}</td>
                  <td>${receiveQty}</td>
                  <td>${issueQty}</td>
                  <td id='returnQty-${parentRowId}'>${returnQty}</td>
                  <td id='balanceQty-${parentRowId}'>0</td>
                  <td id='issueQty-${parentRowId}'>0</td>
                  <td><div class="table-expandable-arrow"></div></td>
              </tr>
              <tr class='even parentRowGroup-${parentRowId}' style='display:none'>
                <td colspan="9">
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
                        <th>Balance Qty</th>
                        <th>Issue Qty</th>
                        <th>Rack Name</th>
                        <th>Bin Name</th>
                      </tr>
                    </thead>
                    <tbody>`;
      }

      const rackSelect = `<select id='rackId-${id}' class='selectRackGroup-${parentRowId} form-control-sm'>
                                     ${rackOptions}
                                   </select>`;
      const binSelect = `<select id='binId-${id}' class='selectBinGroup-${parentRowId} form-control-sm'>
                                        ${rackOptions}
                                  </select>`;

      rows += "<tr id='rowId-" + id + "'  class='newRollRow rollRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + purchaseOrder + "' data-style-id='" + styleId + "' data-item-id='" + itemId + "' data-item-color-id='" + itemColorId + "' data-fabrics-id='" + fabricsId + "' data-fabrics-color-id='" + fabricsColorId + "' data-roll-id='" + rollId + "' data-unit-id='" + unitId + "' data-unit='"+unit+"' data-rack-name='"+rackName+"' data-bin-name='"+binName+"'>"
                +"<td id='listRollId-"+id+"'>" + supplierRollId + "</td>"
                +"<td id='rollUnit-"+id+"'>" + unit + "</td>"
                +"<td id='rollBalanceQty-"+id+"'>" + balanceQty + "</td>"
                +"<td><input type='number' class='rollIssueGroup-" + parentRowId + " form-control-sm max-width-100' id='rollIssueQty-"+id+"' onblur='totalIssueQtyCount(" + parentRowId + ")' value='"+balanceQty+"'></td>"
                +"<td>" + rackSelect+"</td>"
                +"<td>" + binSelect+"</td>"
                + "<td><i class='fa fa-trash' onclick='deleteItemFromList(" + id + ")' style='cursor:pointer;'> </i></td>"
              +"</tr>";
      tempTotalBalance += balanceQty;
      rackIdList.push({
        "id": id,
         "rackId" : rackName
      });
      binIdList.push({
        "id": id,
         "binId" : binName
      });
    }
  });

  if(rows){
    rows += `<tr>
                <td colspan='2'>Total</td>
                <td id='bottomTotalBalance-${parentRowId}'>${tempTotalBalance}</td>
                <td id='bottomTotalIssue-${parentRowId}'>${tempTotalBalance}</td>

            </tr>
          </tbody>
        </table>
        </td>
    </tr>`;
    balanceQtyList.push(tempTotalBalance);
  }
  
  
  $("#rollList").html(rows);

  balanceQtyList.forEach((qty, index) => {
    $("#balanceQty-" + index).text(qty);
    $("#issueQty-" + index).text(qty);
  });
  
  rackIdList.forEach((rack,index)=>{
    $("#rackId-"+rack.id).val(rack.rackId);
  })
  binIdList.forEach((bin,index)=>{
    $("#binId-"+bin.id).val(bin.binId);
  })
  $('#rollSearchModal').modal('hide');

});


function setFabricsIssueInfo(transactionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsIssueInfo',
    data: {
      transactionId: transactionId
    },
    success: function (data) {

      const fabricsIssue = data.fabricsIssue;
      console.log(fabricsIssue)
      $("#issueTransactionId").val(fabricsIssue.transactionId);
      
      let date = fabricsIssue.issueDate.split("/");
      $("#issueDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      
      $("#remarks").val(fabricsIssue.remarks);
      $("#department").val(fabricsIssue.issuedTo).change();
      $("#receiveBy").val(fabricsIssue.receiveBy).change();
      drawFabricsRollListTable(fabricsIssue.fabricsRollList);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);
      $('#issueSearchModal').modal('hide');

    }
  });
}

function submitAction() {
  const rowList = $("tr .newRollRow");
  const length = rowList.length;
  const issueTransactionId = $("#issueTransactionId").val();
  const issueDate = $("#issueDate").val();
  const issuedDepartmentId = $("#department").val();
  const receiveBy = $("#receiveBy").val();
  const remarks = $("#remarks").val();
  const departmentId = 1;
  const userId = $("#userId").val();

  let rollList = ""
  for(let i = 0 ;i<length;i++){
    const row = rowList[i];
    const id = row.id.slice(6);
    const parentRowId = row.getAttribute('data-parent-row');
    const purchaseOrder = row.getAttribute("data-purchase-order");
    const styleId = row.getAttribute("data-style-id");
    const itemId = row.getAttribute("data-item-id");
    const itemColorId = row.getAttribute("data-item-color-id");
    const fabricsId = row.getAttribute("data-fabrics-id");
    const fabricsName = $("#fabricsName-"+parentRowId).text();
    const fabricsColorId = row.getAttribute("data-fabrics-color-id");
    const fabricsColorName = $("#fabricsColor-"+parentRowId).text();
    const unitId = row.getAttribute("data-unit-id");
    const unit = $("#rollUnit-"+id).text();
    const rollId = row.getAttribute("data-roll-id");
    const supplierRollId = $("#listRollId-"+id).text();
    const issueQty = $("#rollIssueQty-"+id).val();
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    
    const qcPassedType= 1;
    
    rollList += `autoId : ${id},issueTransactionId : ${issueTransactionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},fabricsId : ${fabricsId},fabricsName : ${fabricsName},fabricsColorId : ${fabricsColorId},fabricsColorName : ${fabricsColorName},rollId : ${rollId},supplierRollId : ${supplierRollId},unitId : ${unitId},unit : ${unit},issueQty : ${issueQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  
  };
    

  rollList = rollList.slice(0, -1);
  
  if (length > 0) {
    if (issueTransactionId != '') {   
      if(issuedDepartmentId != '0'){
        if (confirm("Are you sure to submit this Fabrics Issue...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './submitFabricsIssue',
            data: {
              transactionId: issueTransactionId,
              issueDate : issueDate,
              issuedTo : issuedDepartmentId,
              receiveBy : receiveBy,
              remarks : remarks,
              departmentId : departmentId,
              rollList : rollList,
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
      }else{
        warningAlert("Please Select a Issued To Department.")
        $("#department").focus();
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
  const issueTransactionId = $("#issueTransactionId").val();
  const issueDate = $("#issueDate").val();
  const issuedDepartmentId = $("#department").val();
  const receiveBy = $("#receiveBy").val();
  const remarks = $("#remarks").val();
  const userId = $("#userId").val();

  let rollList = ""
  for(let i = 0 ;i<length;i++){
    const row = rowList[i];
    const id = row.id.slice(6);
    const parentRowId = row.getAttribute('data-parent-row');
    const purchaseOrder = row.getAttribute("data-purchase-order");
    const styleId = row.getAttribute("data-style-id");
    const itemId = row.getAttribute("data-item-id");
    const itemColorId = row.getAttribute("data-item-color-id");
    const fabricsId = row.getAttribute("data-fabrics-id");
    const fabricsName = $("#fabricsName-"+parentRowId).text();
    const fabricsColorId = row.getAttribute("data-fabrics-color-id");
    const fabricsColorName = $("#fabricsColor-"+parentRowId).text();
    const unitId = row.getAttribute("data-unit-id");
    const unit = $("#rollUnit-"+id).text();
    const rollId = row.getAttribute("data-roll-id");
    const supplierRollId = $("#listRollId-"+id).text();
    const issueQty = $("#rollIssueQty-"+id).val();
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    
    const qcPassedType= 1;
    
    rollList += `autoId : ${id},issueTransactionId : ${issueTransactionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},fabricsId : ${fabricsId},fabricsName : ${fabricsName},fabricsColorId : ${fabricsColorId},fabricsColorName : ${fabricsColorName},rollId : ${rollId},supplierRollId : ${supplierRollId},unitId : ${unitId},unit : ${unit},issueQty : ${issueQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  
  };
    

  rollList = rollList.slice(0, -1);
  
  
    if (issueTransactionId != '') {   
      if(issuedDepartmentId != '0'){
        if (confirm("Are you sure to Edit this Fabrics Issue...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './editFabricsIssue',
            data: {
              transactionId : issueTransactionId,
              issueDate : issueDate,
              issuedTo : issuedDepartmentId,
              receiveBy : receiveBy,
              remarks : remarks,
              rollList: rollList,
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
      }else{
        warningAlert("Please Select a Department")
        $("#department").focus();
      }       
    } else {
      warningAlert("Please Get a transaction Id")
      $("#transactionId").focus();
    }
  
  
}

function refreshAction() {
  location.reload();

}


function drawFabricsRollListSearchTable(data) {
  const length = data.length;
  var tr_list="";
  $("#fabricsRollSearchList").empty();
  
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    tr_list=tr_list+"<tr id='row-" + id + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-fabrics-id='" + rowData.fabricsId + "' data-fabrics-color-id='" + rowData.fabricsColorId + "' data-roll-id='" + rowData.rollId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"' data-rack-name='"+rowData.rackName+"' data-bin-name='"+rowData.binName+"' data-receive-qty='"+rowData.previousReceiveQty+"' data-issue-qty='"+rowData.issueQty+"' data-return-qty='"+rowData.returnQty+"'>"
              +"<td id='purchaseOrder-"+id+"'>" + rowData.purchaseOrder + "</td>"
              +"<td id='styleNo-"+id+"'>" + rowData.styleNo + "</td>"
              +"<td id='itemName-"+id+"'>" + rowData.itemName + "</td>"
              +"<td id='itemColor-"+id+"'>" + rowData.itemColor + "</td>"
              +"<td id='fabricsName-"+id+"'>" + rowData.fabricsName + "</td>"
              +"<td id='fabricsColor-"+id+"'>" + rowData.fabricsColorName + "</td>"
              +"<td id='rollId-"+id+"'>" + rowData.supplierRollId + "</td>"
              +"<td id='balanceQty-"+id+"'>" + rowData.balanceQty + "</td>"
              +"<td ><input class='check' type='checkbox' id='check-"+rowData.autoId+"'></td>"
            +"</tr>";
  }
  $("#fabricsRollSearchList").html(tr_list);
}

function drawFabricsRollListTable(data){
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);
  let rows = "", tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempFabricsId, tempFabricsColorId;
  
    const length = data.length;

    let parentRowId = 0,tempTotalBalanceQty=0,tempTotalIssueQty=0;
    let issueQtyList = [];
    let balanceQtyList = [];
    $("#rollList").empty();
    
    for (var i = 0; i < length; i++) {   
      const rowData = data[i];
      const id = rowData.autoId;

      if (!(rowData.fabricsColorId == tempFabricsColorId && rowData.fabricsId == tempFabricsId && rowData.itemColorId == tempItemColorId && rowData.itemId == tempItemId && rowData.styleId == tempStyleId && rowData.purchaseOrder == tempPurchaseOrder)) {
        if (!(tempPurchaseOrder === "")) {
          rows += `<tr>
                    <td colspan='2'>Total</td>
                    <td id='bottomTotalBalance-${parentRowId}'>${tempTotalBalanceQty}</td>
                    <td id='bottomTotalIssue-${parentRowId}'>${tempTotalIssueQty}</td>         
                </tr>
              </tbody>
            </table>
          </td> 
          </tr>`;
          issueQtyList.push(tempTotalIssueQty);
          balanceQtyList.push(tempTotalBalanceQty);
          parentRowId++;
          tempTotalIssueQty = 0;
          tempTotalBalanceQty = 0;
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
                  <td>${rowData.previousReceiveQty}</td>
                  <td>${rowData.issueQty}</td>
                  <td id='returnQty-${parentRowId}'>${rowData.returnQty}</td>
                  <td id='balanceQty-${parentRowId}'>${rowData.previousReceiveQty-rowData.issueQty-rowData.returnQty}</td>
                  <td id='issueQty-${parentRowId}'>0</td>
                  <td><div class="table-expandable-arrow"></div></td>
              </tr>
              <tr class='even parentRowGroup-${parentRowId}' style='display:none'>
                <td colspan="9">
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
                        <th>Balance Qty</th>
                        <th>Issue Qty</th>
                        <th>Rack Name</th>
                        <th>Bin Name</th>
                      </tr>
                    </thead>
                    <tbody>`;
      }

      const rackSelect = `<select id='rackId-${id}' class='selectRackGroup-${parentRowId} form-control-sm'>
                                     ${rackOptions}
                                   </select>`;
      const binSelect = `<select id='binId-${id}' class='selectBinGroup-${parentRowId} form-control-sm'>
                                        ${rackOptions}
                                  </select>`;

                                  rows += "<tr id='rowId-" + id + "'  class='rollRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-fabrics-id='" + rowData.fabricsId + "' data-fabrics-color-id='" + rowData.fabricsColorId + "' data-roll-id='" + rowData.rollId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"' data-rack-name='"+rowData.rackName+"' data-bin-name='"+rowData.binName+"'>"
                                  +"<td id='listRollId-"+id+"'>" + rowData.supplierRollId + "</td>"
                                  +"<td id='rollUnit-"+id+"'>" + rowData.unit + "</td>"
                                  +"<td id='rollBalanceQty-"+id+"'>" + rowData.balanceQty + "</td>"
                                  +"<td><input type='number' class='rollIssueGroup-" + parentRowId + " form-control-sm max-width-100' id='rollIssueQty-"+id+"' onblur='totalIssueQtyCount(" + parentRowId + ")' value='"+rowData.unitQty+"'></td>"
                                  +"<td>" + rackSelect+"</td>"
                                  +"<td>" + binSelect+"</td>"
                                  + "<td><i class='fa fa-edit' onclick='editItemInDatabase(" + id + ")' style='cursor:pointer;'> </i></td>"
                                  + "<td><i class='fa fa-trash' onclick='deleteItemFromDatabase(" + id + ")' style='cursor:pointer;'> </i></td>" 
                                +"</tr>";
                        tempTotalBalanceQty += rowData.balanceQty;
                        tempTotalIssueQty += rowData.unitQty;
      
    }
    if(rows){
      rows += `<tr>
                    <td colspan='2'>Total</td>
                    <td id='bottomTotalBalance-${parentRowId}'>${tempTotalBalanceQty}</td>
                    <td id='bottomTotalIssue-${parentRowId}'>${tempTotalIssueQty}</td>         
                </tr>
              </tbody>
            </table>
          </td> 
          </tr>`;
          issueQtyList.push(tempTotalIssueQty);
          balanceQtyList.push(tempTotalBalanceQty);
    }

    $("#rollList").html(rows);
    issueQtyList.forEach((qty, index) => {
      $("#issueQty-" + index).text(qty);

    });
    data.forEach((roll,index)=>{
      $("#rackId-"+roll.autoId).val(roll.rackName);
      $("#binId-"+roll.autoId).val(roll.binName);
    })

}

function drawFabricsIssueListTable(data){
  const length = data.length;
  var tr_list="";
  $("#fabricsIssueList").empty();
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    tr_list=tr_list+"<tr id='row-" + rowData.transactionId + "'>"
              +"<td>" + rowData.transactionId + "</td>"
              +"<td>" + rowData.issueDate + "</td>"
              +"<td>" + rowData.issuedDepartmentName + "</td>"
              +"<td ><i class='fa fa-search' onclick=\"setFabricsIssueInfo('" + rowData.transactionId + "')\" style='cursor:pointer;'> </i></td>"
            +"</tr>";
  }
  $("#fabricsIssueList").html(tr_list);
}

$(document).ready(function () {
  $('.table-expandable tbody').on("click", ".odd", function () {
    var element = $(this);
    element.next('tr').toggle(0);
    element.find(".table-expandable-arrow").toggleClass("up");
  })
});

function qcPassedChangeBackground(element){
  if(element.value==1){
    element.classList.remove('bg-danger');
    element.classList.add('bg-success');
  }else{
    element.classList.remove('bg-success');
    element.classList.add('bg-danger');
  }
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

$(document).ready(function () {
  $("input:text").focus(function () { $(this).select(); });
});
$(document).ready(function () {
  $("input").focus(function () { $(this).select(); });
});
$(document).ready(function () {
  $("#purchaseOrderSearch , #styleNoSearch, #itemNameSearch,#fabricsItemSearch,#colorSearch,#rollIdSearch").on("keyup", function () {
    const po = $("#purchaseOrderSearch").val().toLowerCase();
    const style = $("#styleNoSearch").val().toLowerCase();
    const item = $("#itemNameSearch").val().toLowerCase();
    const fabrics = $("#fabricsItemSearch").val().toLowerCase();
    const color = $("#colorSearch").val().toLowerCase();
    const rollId = $("#rollIdSearch").val().toLowerCase();

    $("#fabricsRollSearchList tr").filter(function () {
      const id = this.id.slice(4);
      
      if($("#check-"+id).prop('checked') || ( ( !po.length || $("#purchaseOrder-"+id).text().toLowerCase().indexOf(po) > -1 ) && 
        ( !style.length || $("#styleNo-"+id).text().toLowerCase().indexOf(style) > -1 ) &&
        ( !item.length || $("#itemName-"+id).text().toLowerCase().indexOf(item) > -1 ) &&
        ( !fabrics.length || $("#fabricsName-"+id).text().toLowerCase().indexOf(fabrics) > -1 ) &&
        ( !color.length || $("#itemColor-"+id).text().toLowerCase().indexOf(color) > -1 || $("#fabricsColor-"+id).text().toLowerCase().indexOf(color) > -1 )  &&
        ( !rollId.length || $("#rollId-"+id).text().toLowerCase().indexOf(rollId) > -1 ) ) ){      
        $(this).show();
       }else{      
        $(this).hide();
       }
    });
  });
});


var today = new Date();
document.getElementById("issueDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);


