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

$("#newAccessoriesIssueReturnBtn").click(function () {

  $("#issueReturnTransactionId").val("-- New Transaction --");
  $("#btnSubmit").prop("disabled", false);
  $("#btnEdit").prop("disabled", true);
});

$("#findAccessoriesIssueReturnBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesIssueReturnList',
    data: {},
    success: function (data) {
      drawAccessoriesIssueReturnListTable(data.accessoriesIssueReturnList);
    }
  });
});


$("#accessoriesSearchBtn").click(function () {
  const departmentId = "1";
  const returnDepartmentId = $("#department").val();
  
  if(returnDepartmentId > 0 ){
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getIssuedAccessoriesSizeList',
      data: {
        departmentId : departmentId,
        returnDepartmentId : returnDepartmentId
      },
      success: function (data) {
        drawAccessoriesSizeListSearchTable(data.accessoriesSizeList)
        $("#sizeSearchModal").modal('show');
      }
    });
  }else{
    warningAlert("Please Select a Department")
      $("#department").focus();
  }
  
  
});


function editItemInDatabase(autoId) {
  const issueReturnQty = $("#sizeIssueReturnQty-" + autoId).val();
  const rackId = $("#rackId-"+autoId).val();
  const binId = $("#binId-"+autoId).val();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './editIssueReturnSizeInTransaction',
    data: {
      autoId: autoId,
      unitQty: issueReturnQty,
      rackName : rackId,
      binName : binId
    },
    success: function (data) {
      if (data.result == "Successful") {
        alert("Edit Successfully...");
      } else if (data.result == "IssueReturn Qty Exist") {
        alert("IssueReturn Qty Exist...");
      }
    }
  });
}
function deleteItemFromDatabase(autoId) {
  if(confirm("Are You Sure To Delete...")){
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './deleteIssueReturnSizeFromTransaction',
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

function totalIssueReturnQtyCount(id) {
  const elements = $(".sizeIssueReturnGroup-" + id);
  const length = elements.length;
  let total = 0;
  for (let i = 0; i < length; i++) {

    total += Number(elements[i].value);
  };
  $("#returnQty-" + id).text(total);
  $("#bottomTotalIssueReturn-" + id).text(total);
}

document.getElementById("checkAll").addEventListener("click", function () {
  if ($(this).prop('checked')) {
    $(".check").prop('checked', true);
  } else {
    $(".check").prop('checked', false);
  }
});

$("#sizeAddBtn").click(function(){
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);
  let rows = "",tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempAccessoriesId, tempAccessoriesColorId;
  let parentRowId = 0,tempTotalIssue=0,tempTotalPreviousReturn = 0,tempTotalIssueReturn = 0;
  let issueQtyList = [],previousReturnQtyList = [],returnQtyList=[];
 
  $("#accessoriesSizeSearchList tr").filter(function () {
    const id = this.id.slice(4);
    
    if($("#check-"+id).prop('checked')){
      const accessoriesName = $("#accessoriesName-"+id).text();
      const accessoriesColorName = $("#accessoriesColor-"+id).text();
      const sizeId = this.getAttribute("data-size-id");
      const sizeName = $("#sizeName-"+id).text();
      const purchaseOrder = this.getAttribute("data-purchase-order");
      const styleId = this.getAttribute("data-style-id");
      const styleNo = $("#styleNo-"+id).text();
      const itemId = this.getAttribute("data-item-id");
      const itemName = $("#itemName-"+id).text();
      const itemColorId = this.getAttribute("data-item-color-id");
      const itemColor = $("#itemColor-"+id).text();
      const accessoriesId = this.getAttribute("data-accessories-id");
      const accessoriesColorId = this.getAttribute("data-accessories-color-id");
      const unitId = this.getAttribute("data-unit-id");
      const unit = this.getAttribute("data-unit");
      const issuedQty = $("#issuedQty-"+id).text();
      const previousReturnQty = this.getAttribute("data-previous-return-qty");
      

      if (!(accessoriesColorId == tempAccessoriesColorId && accessoriesId == tempAccessoriesId && itemColorId == tempItemColorId && itemId == tempItemId && styleId == tempStyleId && purchaseOrder == tempPurchaseOrder)) {
        if (!(tempPurchaseOrder === "")) {
          rows += `<tr>
                    <td colspan='2'>Total</td>
                    <td id='bottomTotalIssued-${parentRowId}'>${tempTotalIssue}</td>
                    <td id='bottomTotalPreviousReturn-${parentRowId}'>${tempTotalPreviousReturn}</td>
                    <td id='bottomTotalIssueReturn-${parentRowId}'>${tempTotalIssueReturn}</td>         
                </tr>
              </tbody>
            </table>
          </td> 
          </tr>`;
          issueQtyList.push(tempTotalIssue);
          previousReturnQtyList.push(tempTotalPreviousReturn);
          returnQtyList.push(tempTotalIssueReturn);
          parentRowId++;
          tempTotalPreviousReturn = 0;
          tempTotalIssueReturn = 0;
          tempTotalIssue = 0;
        }
        tempAccessoriesColorId = accessoriesColorId;
        tempAccessoriesId = accessoriesId;
        tempItemColorId = itemColorId;
        tempItemId = itemId;
        tempStyleId = styleId;
        tempPurchaseOrder = purchaseOrder;
        rows += `<tr class='odd parentRowGroup-${parentRowId}'>
                  <td id='accessoriesName-${parentRowId}'>${accessoriesName}</td>
                  <td id='accessoriesColor-${parentRowId}'>${accessoriesColorName}</td>
                  <td>${unit}</td>
                  <td id='issuedQty-${parentRowId}'>0</td>
                  <td id='previousReturnQty-${parentRowId}'>0</td>
                  <td id='returnQty-${parentRowId}'>0</td>
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
                        <th>Size Id</th>
                        <th>Unit</th>
                        <th>Issued Qty</th>
                        <th>Prev. Return Qty</th>
                        <th>Return Qty</th>
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

      rows += "<tr id='rowId-" + id + "'  class='newSizeRow sizeRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + purchaseOrder + "' data-style-id='" + styleId + "' data-item-id='" + itemId + "' data-item-color-id='" + itemColorId + "' data-accessories-id='" + accessoriesId + "' data-accessories-color-id='" + accessoriesColorId + "' data-size-id='" + sizeId + "' data-unit-id='" + unitId + "' data-unit='"+unit+"' >"
                +"<td id='listSizeName-"+id+"'>" + sizeName + "</td>"
                +"<td id='sizeUnit-"+id+"'>" + unit + "</td>"
                +"<td id='sizeIssuedQty-"+id+"'>" + issuedQty + "</td>"
                +"<td id='sizePreviousReturnQty-"+id+"'>" + previousReturnQty + "</td>"
                +"<td><input type='number' class='sizeIssueReturnGroup-" + parentRowId + " form-control-sm max-width-100' id='sizeIssueReturnQty-"+id+"' onblur='totalIssueReturnQtyCount(" + parentRowId + ")' value='"+(issuedQty - previousReturnQty)+"'></td>"
                +"<td>" + rackSelect+"</td>"
                +"<td>" + binSelect+"</td>"
                + "<td><i class='fa fa-trash' onclick='deleteItemFromList(" + id + ")' style='cursor:pointer;'> </i></td>"
              +"</tr>";
      tempTotalIssue += Number(issuedQty) ;
      tempTotalPreviousReturn += Number(previousReturnQty);
      tempTotalIssueReturn += Number(issuedQty - previousReturnQty);
    }
  });

  if(rows){
    rows += `<tr>
                <td colspan='2'>Total</td>
                <td id='bottomTotalIssued-${parentRowId}'>${tempTotalIssue}</td>
                <td id='bottomTotalPreviousReturn-${parentRowId}'>${tempTotalPreviousReturn}</td>
                <td id='bottomTotalIssueReturn-${parentRowId}'>${tempTotalIssueReturn}</td>        

            </tr>
          </tbody>
        </table>
        </td>
    </tr>`;
    issueQtyList.push(tempTotalIssue);
    previousReturnQtyList.push(tempTotalPreviousReturn);
    returnQtyList.push(tempTotalIssueReturn);
  }
  
  
  $("#sizeList").html(rows);
  const length = issueQtyList.length;
  for(let i = 0;i<length;i++){
    $("#issuedQty-" + i).text(issueQtyList[i]);
    $("#previousReturnQty-" + i).text(previousReturnQtyList[i]);
    $("#returnQty-"+i).text(returnQtyList[i]);
  }
  
  
  $('#sizeSearchModal').modal('hide');

});


function setAccessoriesIssueReturnInfo(transactionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesIssueReturnInfo',
    data: {
      transactionId: transactionId
    },
    success: function (data) {

      const accessoriesIssueReturn = data.accessoriesIssueReturn;
      console.log(accessoriesIssueReturn)
      $("#issueReturnTransactionId").val(accessoriesIssueReturn.transactionId);
      
      let date = accessoriesIssueReturn.issueReturnDate.split("/");
      $("#issueReturnDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      
      $("#remarks").val(accessoriesIssueReturn.remarks);
      $("#department").val(accessoriesIssueReturn.issueReturnFrom).change();
      $("#receiveFrom").val(accessoriesIssueReturn.receiveFrom).change();
      drawAccessoriesSizeListTable(accessoriesIssueReturn.accessoriesSizeList);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);
      $('#issueReturnSearchModal').modal('hide');

    }
  });
}

function submitAction() {
  const rowList = $("tr .newSizeRow");
  const length = rowList.length;
  const issueReturnTransactionId = $("#issueReturnTransactionId").val();
  const issueReturnDate = $("#issueReturnDate").val();
  const issueReturnDepartmentId = $("#department").val();
  const receiveFrom = $("#receiveFrom").val();
  const remarks = $("#remarks").val();
  const departmentId = 1;
  const userId = $("#userId").val();

  let sizeList = ""
  for(let i = 0 ;i<length;i++){
    const row = rowList[i];
    const id = row.id.slice(6);
    const parentRowId = row.getAttribute('data-parent-row');
    const purchaseOrder = row.getAttribute("data-purchase-order");
    const styleId = row.getAttribute("data-style-id");
    const itemId = row.getAttribute("data-item-id");
    const itemColorId = row.getAttribute("data-item-color-id");
    const accessoriesId = row.getAttribute("data-accessories-id");
    const accessoriesName = $("#accessoriesName-"+parentRowId).text();
    const accessoriesColorId = row.getAttribute("data-accessories-color-id");
    const accessoriesColorName = $("#accessoriesColor-"+parentRowId).text();
    const unitId = row.getAttribute("data-unit-id");
    const unit = $("#sizeUnit-"+id).text();
    const sizeId = row.getAttribute("data-size-id");
    const sizeName = $("#listSizeName-"+id).text();
    const issueReturnQty = $("#sizeIssueReturnQty-"+id).val();
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    
    const qcPassedType= 1;
    
    sizeList += `autoId : ${id},issueReturnTransactionId : ${issueReturnTransactionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},accessoriesId : ${accessoriesId},accessoriesName : ${accessoriesName},accessoriesColorId : ${accessoriesColorId},accessoriesColorName : ${accessoriesColorName},sizeId : ${sizeId},sizeName : ${sizeName},unitId : ${unitId},unit : ${unit},issueReturnQty : ${issueReturnQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  
  };
    

  sizeList = sizeList.slice(0, -1);
  
  if (length > 0) {
    if (issueReturnTransactionId != '') {   
      if(issueReturnDepartmentId != '0'){
        if (confirm("Are you sure to submit this Accessories IssueReturn...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './submitAccessoriesIssueReturn',
            data: {
              transactionId: issueReturnTransactionId,
              issueReturnDate : issueReturnDate,
              issueReturnFrom : issueReturnDepartmentId,
              receiveFrom : receiveFrom,
              remarks : remarks,
              departmentId : departmentId,
              sizeList : sizeList,
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
        warningAlert("Please Select a IssueReturnd To Department.")
        $("#department").focus();
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
  const issueReturnTransactionId = $("#issueReturnTransactionId").val();
  const issueReturnDate = $("#issueReturnDate").val();
  const issueReturnDepartmentId = $("#department").val();
  const receiveFrom = $("#receiveFrom").val();
  const remarks = $("#remarks").val();
  const departmentId = 1;
  const userId = $("#userId").val();

  let sizeList = ""
  for(let i = 0 ;i<length;i++){
    const row = rowList[i];
    const id = row.id.slice(6);
    const parentRowId = row.getAttribute('data-parent-row');
    const purchaseOrder = row.getAttribute("data-purchase-order");
    const styleId = row.getAttribute("data-style-id");
    const itemId = row.getAttribute("data-item-id");
    const itemColorId = row.getAttribute("data-item-color-id");
    const accessoriesId = row.getAttribute("data-accessories-id");
    const accessoriesName = $("#accessoriesName-"+parentRowId).text();
    const accessoriesColorId = row.getAttribute("data-accessories-color-id");
    const accessoriesColorName = $("#accessoriesColor-"+parentRowId).text();
    const unitId = row.getAttribute("data-unit-id");
    const unit = $("#sizeUnit-"+id).text();
    const sizeId = row.getAttribute("data-size-id");
    const sizeName = $("#listSizeName-"+id).text();
    const issueReturnQty = $("#sizeIssueReturnQty-"+id).val();
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    
    const qcPassedType= 1;
    
    sizeList += `autoId : ${id},issueReturnTransactionId : ${issueReturnTransactionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},accessoriesId : ${accessoriesId},accessoriesName : ${accessoriesName},accessoriesColorId : ${accessoriesColorId},accessoriesColorName : ${accessoriesColorName},sizeId : ${sizeId},sizeName : ${sizeName},unitId : ${unitId},unit : ${unit},issueReturnQty : ${issueReturnQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  
  };
    

  sizeList = sizeList.slice(0, -1);
  
  
    if (issueReturnTransactionId != '') {   
      if(issueReturnDepartmentId != '0'){
        if (confirm("Are you sure to Edit this Accessories IssueReturn...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './editAccessoriesIssueReturn',
            data: {
              transactionId: issueReturnTransactionId,
              issueReturnDate : issueReturnDate,
              issueReturnFrom : issueReturnDepartmentId,
              receiveFrom : receiveFrom,
              remarks : remarks,
              departmentId : departmentId,
              sizeList : sizeList,
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


function drawAccessoriesSizeListSearchTable(data) {
  const length = data.length;
  var tr_list="";
  $("#accessoriesSizeSearchList").empty();
  
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = i;
    tr_list=tr_list+"<tr id='row-" + id + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "' data-size-id='" + rowData.sizeId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"'  data-previous-return-qty='"+rowData.previousReturnQty+"' >"
              +"<td id='purchaseOrder-"+id+"'>" + rowData.purchaseOrder + "</td>"
              +"<td id='styleNo-"+id+"'>" + rowData.styleNo + "</td>"
              +"<td id='itemName-"+id+"'>" + rowData.itemName + "</td>"
              +"<td id='itemColor-"+id+"'>" + rowData.itemColor + "</td>"
              +"<td id='accessoriesName-"+id+"'>" + rowData.accessoriesName + "</td>"
              +"<td id='accessoriesColor-"+id+"'>" + rowData.accessoriesColorName + "</td>"
              +"<td id='sizeId-"+id+"'>" + rowData.sizeName + "</td>"
              +"<td id='issuedQty-"+id+"'>" + rowData.issueQty + "</td>"
              +"<td ><input class='check' type='checkbox' id='check-"+id+"'></td>"
            +"</tr>";
  }
  $("#accessoriesSizeSearchList").html(tr_list);
}

function drawAccessoriesSizeListTable(data){
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);
  let rows = "",tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempAccessoriesId, tempAccessoriesColorId;
  let parentRowId = 0,tempTotalIssue=0,tempTotalPreviousReturn = 0,tempTotalIssueReturn = 0;
  let issueQtyList = [],previousReturnQtyList = [],returnQtyList=[];
  let length = data.length;
    $("#sizeList").empty();
    
    for (var i = 0; i < length; i++) {   
      const rowData = data[i];
      const id = rowData.autoId;

      if (!(rowData.accessoriesColorId == tempAccessoriesColorId && rowData.accessoriesId == tempAccessoriesId && rowData.itemColorId == tempItemColorId && rowData.itemId == tempItemId && rowData.styleId == tempStyleId && rowData.purchaseOrder == tempPurchaseOrder)) {
        if (!(tempPurchaseOrder === "")) {
          rows += `<tr>
                    <td colspan='2'>Total</td>
                    <td id='bottomTotalIssued-${parentRowId}'>${tempTotalIssue}</td>
                    <td id='bottomTotalPreviousReturn-${parentRowId}'>${tempTotalPreviousReturn}</td>
                    <td id='bottomTotalIssueReturn-${parentRowId}'>${tempTotalIssueReturn}</td>         
                </tr>
              </tbody>
            </table>
          </td> 
          </tr>`;
          issueQtyList.push(tempTotalIssue);
          previousReturnQtyList.push(tempTotalPreviousReturn);
          returnQtyList.push(tempTotalIssueReturn);
          parentRowId++;
          tempTotalPreviousReturn = 0;
          tempTotalIssueReturn = 0;
          tempTotalIssue = 0;
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
                  <td id='issuedQty-${parentRowId}'>0</td>
                  <td id='previousReturnQty-${parentRowId}'>0</td>
                  <td id='returnQty-${parentRowId}'>0</td>
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
                        <th>Size Id</th>
                        <th>Unit</th>
                        <th>Issued Qty</th>
                        <th>Prev. Return Qty</th>
                        <th>Return Qty</th>
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
        
                rows += "<tr id='rowId-" + id + "'  class='sizeRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "' data-size-id='" + rowData.sizeId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"' >"
                +"<td id='listSizeName-"+id+"'>" + rowData.sizeName + "</td>"
                +"<td id='sizeUnit-"+id+"'>" + rowData.unit + "</td>"
                +"<td id='sizeIssuedQty-"+id+"'>" + rowData.issueQty + "</td>"
                +"<td id='sizePreviousReturnQty-"+id+"'>" + rowData.previousReturnQty + "</td>"
                +"<td><input type='number' class='sizeIssueReturnGroup-" + parentRowId + " form-control-sm max-width-100' id='sizeIssueReturnQty-"+id+"' onblur='totalIssueReturnQtyCount(" + parentRowId + ")' value='"+rowData.unitQty +"'></td>"
                +"<td>" + rackSelect+"</td>"
                +"<td>" + binSelect+"</td>"
                + "<td><i class='fa fa-edit' onclick='editItemInDatabase(" + id + ")' style='cursor:pointer;'> </i></td>"
                + "<td><i class='fa fa-trash' onclick='deleteItemFromDatabase(" + id + ")' style='cursor:pointer;'> </i></td>" 
              +"</tr>";
              
              
              tempTotalIssue += Number(rowData.issueQty) ;
              tempTotalPreviousReturn += Number(rowData.previousReturnQty);
              tempTotalIssueReturn += Number(rowData.unitQty);
        
    }
    if(rows){
      rows += `<tr>
                <td colspan='2'>Total</td>
                <td id='bottomTotalIssued-${parentRowId}'>${tempTotalIssue}</td>
                <td id='bottomTotalPreviousReturn-${parentRowId}'>${tempTotalPreviousReturn}</td>
                <td id='bottomTotalIssueReturn-${parentRowId}'>${tempTotalIssueReturn}</td>        

            </tr>
          </tbody>
        </table>
        </td>
      </tr>`;
      issueQtyList.push(tempTotalIssue);
      previousReturnQtyList.push(tempTotalPreviousReturn);
      returnQtyList.push(tempTotalIssueReturn);
    }

   
    $("#sizeList").html(rows);
    length = issueQtyList.length;
    for(let i = 0;i<length;i++){
      $("#issuedQty-" + i).text(issueQtyList[i]);
      $("#previousReturnQty-" + i).text(previousReturnQtyList[i]);
      $("#returnQty-"+i).text(returnQtyList[i]);
    }
    data.forEach((size,index)=>{
      $("#rackId-"+size.autoId).val(size.rackName);
      $("#binId-"+size.autoId).val(size.binName);
    })

    $('#accessoriesIssueReturnList').modal('hide');
}

function drawAccessoriesIssueReturnListTable(data){
  const length = data.length;
  var tr_list="";
  $("#accessoriesIssueReturnList").empty();
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    tr_list=tr_list+"<tr id='row-" + rowData.transactionId + "'>"
              +"<td>" + rowData.transactionId + "</td>"
              +"<td>" + rowData.issueReturnDate + "</td>"
              +"<td>" + rowData.issueReturnDepartmentName + "</td>"
              +"<td ><i class='fa fa-search' onclick=\"setAccessoriesIssueReturnInfo('" + rowData.transactionId + "')\" style='cursor:pointer;'> </i></td>"
            +"</tr>";
  }
  $("#accessoriesIssueReturnList").html(tr_list);
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
  $("#purchaseOrderSearch , #styleNoSearch, #itemNameSearch,#accessoriesItemSearch,#colorSearch,#sizeIdSearch").on("keyup", function () {
    const po = $("#purchaseOrderSearch").val().toLowerCase();
    const style = $("#styleNoSearch").val().toLowerCase();
    const item = $("#itemNameSearch").val().toLowerCase();
    const accessories = $("#accessoriesItemSearch").val().toLowerCase();
    const color = $("#colorSearch").val().toLowerCase();
    const sizeId = $("#sizeIdSearch").val().toLowerCase();

    $("#accessoriesSizeSearchList tr").filter(function () {
      const id = this.id.slice(4);
      
      if($("#check-"+id).prop('checked') || ( ( !po.length || $("#purchaseOrder-"+id).text().toLowerCase().indexOf(po) > -1 ) && 
        ( !style.length || $("#styleNo-"+id).text().toLowerCase().indexOf(style) > -1 ) &&
        ( !item.length || $("#itemName-"+id).text().toLowerCase().indexOf(item) > -1 ) &&
        ( !accessories.length || $("#accessoriesName-"+id).text().toLowerCase().indexOf(accessories) > -1 ) &&
        ( !color.length || $("#itemColor-"+id).text().toLowerCase().indexOf(color) > -1 || $("#accessoriesColor-"+id).text().toLowerCase().indexOf(color) > -1 )  &&
        ( !sizeId.length || $("#sizeName-"+id).text().toLowerCase().indexOf(sizeId) > -1 ) ) ){      
        $(this).show();
       }else{      
        $(this).hide();
       }
    });
  });
});


var today = new Date();
document.getElementById("issueReturnDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);


