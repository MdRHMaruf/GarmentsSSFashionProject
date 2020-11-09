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

$("#newAccessoriesIssueBtn").click(function () {

  $("#issueTransactionId").val("-- New Transaction --");
  $("#btnSubmit").prop("disabled", false);
  $("#btnEdit").prop("disabled", true);
});

$("#findAccessoriesIssueBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesIssueList',
    data: {},
    success: function (data) {
      drawAccessoriesIssueListTable(data.accessoriesIssueList);
    }
  });
});


$("#accessoriesSearchBtn").click(function () {
  const departmentId = $("#departmentId").val();
 
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAvailableAccessoriesSizeList',
    data: {
      departmentId : departmentId
    },
    success: function (data) {
      drawAccessoriesSizeListSearchTable(data.accessoriesSizeList)
      $("#sizeSearchModal").modal('show');
    }
  });
  
});


function searchAccessoriesRequisition(cuttingEntryId){
  const departmentId = $("#departmentId").val();;
  $("#cuttingEntryId").val(cuttingEntryId);
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getRequisitionAccessoriesList',
    data: {
      cuttingEntryId: cuttingEntryId,
      departmentId : departmentId
    },
    success: function (data) {
      drawRequisitionAccessoriesListSearchTable(data.accessoriesList)
      $("#requisitionNo").val(cuttingEntryId);
    }
  });
  
};


function editItemInDatabase(autoId) {
  const issueQty = $("#sizeIssueQty-" + autoId).val();

  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './editIssueSizeInTransaction',
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
      url: './deleteIssueSizeFromTransaction',
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
  const elements = $(".sizeIssueGroup-" + id);
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

document.getElementById("requisitionCheckAll").addEventListener("click", function () {
  if ($(this).prop('checked')) {
    $(".requisitionCheck").prop('checked', true);
  } else {
    $(".requisitionCheck").prop('checked', false);
  }
});

$("#sizeAddBtn").click(function(){
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);
  let rows = "",tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempAccessoriesId, tempAccessoriesColorId;
  let parentRowId = 0,tempTotalBalance=0;
  let balanceQtyList = [];
  let rackIdList = [];
  let binIdList = [];
  $("#accessoriesSizeSearchList tr").filter(function () {
    const id = this.id.slice(4);
    
    if($("#check-"+id).prop('checked')){
      const accessoriesName = $("#accessoriesName-"+id).text();
      const accessoriesColorName = $("#accessoriesColor-"+id).text();
      const sizeId = this.getAttribute("data-size-id");
      const sizeName = $("#sizeName-"+id).text();
      const balanceQty = Number($("#balanceQty-"+id).text());
    

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
      const rackName = this.getAttribute("data-rack-name");
      const binName = this.getAttribute("data-bin-name");
      const receiveQty = this.getAttribute("data-receive-qty");
      const issueQty = this.getAttribute("data-issue-qty");
      const returnQty = this.getAttribute("data-return-qty");

      if (!(accessoriesColorId == tempAccessoriesColorId && accessoriesId == tempAccessoriesId && itemColorId == tempItemColorId && itemId == tempItemId && styleId == tempStyleId && purchaseOrder == tempPurchaseOrder)) {
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
                        <th>Size Id</th>
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

      rows += "<tr id='rowId-" + id + "'  class='newSizeRow sizeRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + purchaseOrder + "' data-style-id='" + styleId + "' data-item-id='" + itemId + "' data-item-color-id='" + itemColorId + "' data-accessories-id='" + accessoriesId + "' data-accessories-color-id='" + accessoriesColorId + "' data-size-id='" + sizeId + "' data-unit-id='" + unitId + "' data-unit='"+unit+"' data-rack-name='"+rackName+"' data-bin-name='"+binName+"'>"
                +"<td id='listSizeName-"+id+"'>" + sizeName + "</td>"
                +"<td id='sizeUnit-"+id+"'>" + unit + "</td>"
                +"<td id='sizeBalanceQty-"+id+"'>" + balanceQty + "</td>"
                +"<td><input type='number' class='sizeIssueGroup-" + parentRowId + " form-control-sm max-width-100' id='sizeIssueQty-"+id+"' onblur='totalIssueQtyCount(" + parentRowId + ")' value='"+balanceQty+"'></td>"
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
  
  
  $("#sizeList").html(rows);

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
  $("#requisitionNo").val("0");
  $('#sizeSearchModal').modal('hide');

});

$("#requisitionSizeAddBtn").click(function(){
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);
  let accessoriesIdList = "";
  let rackIdList = [];
  let binIdList = [];

  const departmentId = $("#departmentId").val();;
  const cuttingEntryId = $("#cuttingEntryId").val();
  $("#requisitionAccessoriesSearchList tr").filter(function () {
    const id = this.id.slice(15);
    
    if($("#requisitionCheck-"+id).prop('checked')){
      accessoriesIdList += id+",";
    }
  });

  accessoriesIdList = accessoriesIdList.slice(0,-1);
  console.log("accessories id list=",accessoriesIdList);

  if(accessoriesIdList.length > 0){
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getRequisitionAccessoriesSizeList',
      data: {
        cuttingEntryId: cuttingEntryId,
        accessoriesIdList: accessoriesIdList,
        departmentId : departmentId
      },
      success: function (data) {
        drawAccessoriesSizeListTable(data.accessoriesSizeList,'newSizeRow');
        $("#requisitionSearchModal").modal('hide');
      }
    });
  }
});

// $("#requisitionSizeAddBtn").click(function(){
//   const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);
//   let rows = "",tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempAccessoriesId, tempAccessoriesColorId;
//   let parentRowId = 0,tempTotalBalance=0;
//   let balanceQtyList = [];
//   let rackIdList = [];
//   let binIdList = [];
//   $("#requisitionAccessoriesSearchList tr").filter(function () {
//     const id = this.id.slice(15);
    
//     if($("#requisitionCheck-"+id).prop('checked')){
//       const accessoriesName = $("#requisitionAccessoriesName-"+id).text();
//       const accessoriesColorName = $("#requisitionAccessoriesColor-"+id).text();
//       const sizeId = this.getAttribute("data-size-id");
//       const sizeName = $("#requisitionSizeName-"+id).text();
//       const balanceQty = Number($("#requisitionBalanceQty-"+id).text());
    

//       const purchaseOrder = this.getAttribute("data-purchase-order");
//       const styleId = this.getAttribute("data-style-id");
//       const styleNo = $("#requisitionStyleNo-"+id).text();
//       const itemId = this.getAttribute("data-item-id");
//       const itemName = $("#requisitionItemName-"+id).text();
//       const itemColorId = this.getAttribute("data-item-color-id");
//       const itemColor = $("#requisitionItemColor-"+id).text();
//       const accessoriesId = this.getAttribute("data-accessories-id");
//       const accessoriesColorId = this.getAttribute("data-accessories-color-id");
//       const unitId = this.getAttribute("data-unit-id");
//       const unit = this.getAttribute("data-unit");
//       const rackName = this.getAttribute("data-rack-name");
//       const binName = this.getAttribute("data-bin-name");
//       const receiveQty = this.getAttribute("data-receive-qty");
//       const issueQty = this.getAttribute("data-issue-qty");
//       const returnQty = this.getAttribute("data-return-qty");

//       if (!(accessoriesColorId == tempAccessoriesColorId && accessoriesId == tempAccessoriesId && itemColorId == tempItemColorId && itemId == tempItemId && styleId == tempStyleId && purchaseOrder == tempPurchaseOrder)) {
//         if (!(tempPurchaseOrder === "")) {
//           rows += `<tr>
//                     <td colspan='2'>Total</td>
//                     <td id='bottomTotalBalance-${parentRowId}'>${tempTotalBalance}</td>
//                     <td id='bottomTotalIssue-${parentRowId}'>${tempTotalBalance}</td>         
//                 </tr>
//               </tbody>
//             </table>
//           </td> 
//           </tr>`;
//           balanceQtyList.push(tempTotalBalance);
//           parentRowId++;
//           tempTotalBalance = 0;
//         }
//         tempAccessoriesColorId = accessoriesColorId;
//         tempAccessoriesId = accessoriesId;
//         tempItemColorId = itemColorId;
//         tempItemId = itemId;
//         tempStyleId = styleId;
//         tempPurchaseOrder = purchaseOrder;
//         rows += `<tr class='odd parentRowGroup-${parentRowId}'>
//                   <td id='accessoriesName-${parentRowId}'>${accessoriesName}</td>
//                   <td id='accessoriesColor-${parentRowId}'>${accessoriesColorName}</td>
//                   <td>${unit}</td>
//                   <td>${receiveQty}</td>
//                   <td>${issueQty}</td>
//                   <td id='returnQty-${parentRowId}'>${returnQty}</td>
//                   <td id='balanceQty-${parentRowId}'>0</td>
//                   <td id='issueQty-${parentRowId}'>0</td>
//                   <td><div class="table-expandable-arrow"></div></td>
//               </tr>
//               <tr class='even parentRowGroup-${parentRowId}' style='display:none'>
//                 <td colspan="9">
//                   <div class="row px-5">
                    
//                     <div class="col-md-2 px-1">
//                         <label>Purchase Order:</label>
//                     </div>
//                     <div class="col-md-4 px-1">
//                       <b><label>${purchaseOrder}</label></b>
//                     </div>

//                     <div class="col-md-1 px-1">
//                         <label>Item Name:</label>
//                     </div>
//                     <div class="col-md-5 px-1">
//                       <b><label>${itemName}</label></b>
//                     </div>

//                     <div class="col-md-2 px-1">
//                         <label>Style No:</label>
//                     </div>
//                     <div class="col-md-4 px-1">
//                       <b><label>${styleNo}</label></b>
//                     </div>

//                     <div class="col-md-1 px-1">
//                         <label>Item Color:</label>
//                     </div>
//                     <div class="col-md-5 px-1">
//                       <b><label>${itemColor}</label></b>
//                     </div>
//                   </div>
//                   <table class='table table-hover table-bordered table-sm mb-0 small-font pl-5'>
//                     <thead>
//                       <tr>
//                         <th>Size Name</th>
//                         <th>Unit</th>
//                         <th>Balance Qty</th>
//                         <th>Issue Qty</th>
//                         <th>Rack Name</th>
//                         <th>Bin Name</th>
//                       </tr>
//                     </thead>
//                     <tbody>`;
//       }

//       const rackSelect = `<select id='rackId-${id}' class='selectRackGroup-${parentRowId} form-control-sm'>
//                                      ${rackOptions}
//                                    </select>`;
//       const binSelect = `<select id='binId-${id}' class='selectBinGroup-${parentRowId} form-control-sm'>
//                                         ${rackOptions}
//                                   </select>`;

//       rows += "<tr id='rowId-" + id + "'  class='newSizeRow sizeRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + purchaseOrder + "' data-style-id='" + styleId + "' data-item-id='" + itemId + "' data-item-color-id='" + itemColorId + "' data-accessories-id='" + accessoriesId + "' data-accessories-color-id='" + accessoriesColorId + "' data-size-id='" + sizeId + "' data-unit-id='" + unitId + "' data-unit='"+unit+"' data-rack-name='"+rackName+"' data-bin-name='"+binName+"'>"
//                 +"<td id='listSizeName-"+id+"'>" + sizeName + "</td>"
//                 +"<td id='sizeUnit-"+id+"'>" + unit + "</td>"
//                 +"<td id='sizeBalanceQty-"+id+"'>" + balanceQty + "</td>"
//                 +"<td><input type='number' class='sizeIssueGroup-" + parentRowId + " form-control-sm max-width-100' id='sizeIssueQty-"+id+"' onblur='totalIssueQtyCount(" + parentRowId + ")' value='"+balanceQty+"'></td>"
//                 +"<td>" + rackSelect+"</td>"
//                 +"<td>" + binSelect+"</td>"
//                 + "<td><i class='fa fa-trash' onclick='deleteItemFromList(" + id + ")' style='cursor:pointer;'> </i></td>"
//               +"</tr>";
//       tempTotalBalance += balanceQty;
//       rackIdList.push({
//         "id": id,
//          "rackId" : rackName
//       });
//       binIdList.push({
//         "id": id,
//          "binId" : binName
//       });
//     }
//   });

//   if(rows){
//     rows += `<tr>
//                 <td colspan='2'>Total</td>
//                 <td id='bottomTotalBalance-${parentRowId}'>${tempTotalBalance}</td>
//                 <td id='bottomTotalIssue-${parentRowId}'>${tempTotalBalance}</td>

//             </tr>
//           </tbody>
//         </table>
//         </td>
//     </tr>`;
//     balanceQtyList.push(tempTotalBalance);
//   }
  
  
//   $("#sizeList").html(rows);

//   balanceQtyList.forEach((qty, index) => {
//     $("#balanceQty-" + index).text(qty);
//     $("#issueQty-" + index).text(qty);
//   });
  
//   rackIdList.forEach((rack,index)=>{
//     $("#rackId-"+rack.id).val(rack.rackId);
//   })
//   binIdList.forEach((bin,index)=>{
//     $("#binId-"+bin.id).val(bin.binId);
//   })
//   $('#requisitionSearchModal').modal('hide');

// });

function setAccessoriesIssueInfo(transactionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesIssueInfo',
    data: {
      transactionId: transactionId
    },
    success: function (data) {

      const accessoriesIssue = data.accessoriesIssue;
      console.log(accessoriesIssue)
      $("#issueTransactionId").val(accessoriesIssue.transactionId);
      
      let date = accessoriesIssue.issueDate.split("/");
      $("#issueDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      
      $("#remarks").val(accessoriesIssue.remarks);
      $("#department").val(accessoriesIssue.issuedTo).change();
      $("#receiveBy").val(accessoriesIssue.receiveBy).change();
      drawAccessoriesSizeListTable(accessoriesIssue.accessoriesSizeList);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);
      $('#issueSearchModal').modal('hide');

    }
  });
}

function submitAction() {
  const rowList = $("tr .newSizeRow");
  const length = rowList.length;
  const issueTransactionId = $("#issueTransactionId").val();
  const issueDate = $("#issueDate").val();
  const issuedDepartmentId = $("#department").val();
  const receiveBy = $("#receiveBy").val();
  const remarks = $("#remarks").val();
  const departmentId = $("#departmentId").val();;
  const userId = $("#userId").val();

  const requisitionNo = $("#requisitionNo").val();
  const requisitionStatus = "2";

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
    const issueQty = $("#sizeIssueQty-"+id).val();
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    
    const qcPassedType= 1;
    
    sizeList += `autoId : ${id},issueTransactionId : ${issueTransactionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},accessoriesId : ${accessoriesId},accessoriesName : ${accessoriesName},accessoriesColorId : ${accessoriesColorId},accessoriesColorName : ${accessoriesColorName},sizeId : ${sizeId},sizeName : ${sizeName},unitId : ${unitId},unit : ${unit},issueQty : ${issueQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  
  };
    

  sizeList = sizeList.slice(0, -1);
  
  if (length > 0) {
    if (issueTransactionId != '') {   
      if(issuedDepartmentId != '0'){
        if (confirm("Are you sure to submit this Accessories Issue...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './submitAccessoriesIssue',
            data: {
              transactionId: issueTransactionId,
              issueDate : issueDate,
              issuedTo : issuedDepartmentId,
              receiveBy : receiveBy,
              remarks : remarks,
              departmentId : departmentId,
              sizeList : sizeList,
              userId: userId,
              requisitionNo: requisitionNo,
              requisitionStatus: requisitionStatus
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
    warningAlert("Please Enter Accessories Size");
  }
}



function editAction() {

  const rowList = $("tr .newSizeRow");
  const length = rowList.length;
  const issueTransactionId = $("#issueTransactionId").val();
  const issueDate = $("#issueDate").val();
  const issuedDepartmentId = $("#department").val();
  const receiveBy = $("#receiveBy").val();
  const remarks = $("#remarks").val();
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
    const issueQty = $("#sizeIssueQty-"+id).val();
    const rackName = $("#rackId-" + id).val();
    const binName = $("#binId-" + id).val();
    
    const qcPassedType= 1;
    
    sizeList += `autoId : ${id},issueTransactionId : ${issueTransactionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},accessoriesId : ${accessoriesId},accessoriesName : ${accessoriesName},accessoriesColorId : ${accessoriesColorId},accessoriesColorName : ${accessoriesColorName},sizeId : ${sizeId},sizeName : ${sizeName},unitId : ${unitId},unit : ${unit},issueQty : ${issueQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  
  };
    

  sizeList = sizeList.slice(0, -1);
  
  
    if (issueTransactionId != '') {   
      if(issuedDepartmentId != '0'){
        if (confirm("Are you sure to Edit this Accessories Issue...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './editAccessoriesIssue',
            data: {
              transactionId : issueTransactionId,
              issueDate : issueDate,
              issuedTo : issuedDepartmentId,
              receiveBy : receiveBy,
              remarks : remarks,
              sizeList: sizeList,
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
    const id = rowData.autoId;
    tr_list=tr_list+"<tr id='row-" + id + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "' data-size-id='" + rowData.sizeId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"' data-rack-name='"+rowData.rackName+"' data-bin-name='"+rowData.binName+"' data-receive-qty='"+rowData.previousReceiveQty+"' data-issue-qty='"+rowData.issueQty+"' data-return-qty='"+rowData.returnQty+"'>"
              +"<td id='purchaseOrder-"+id+"'>" + rowData.purchaseOrder + "</td>"
              +"<td id='styleNo-"+id+"'>" + rowData.styleNo + "</td>"
              +"<td id='itemName-"+id+"'>" + rowData.itemName + "</td>"
              +"<td id='itemColor-"+id+"'>" + rowData.itemColor + "</td>"
              +"<td id='accessoriesName-"+id+"'>" + rowData.accessoriesName + "</td>"
              +"<td id='accessoriesColor-"+id+"'>" + rowData.accessoriesColorName + "</td>"
              +"<td id='sizeName-"+id+"'>" + rowData.sizeName + "</td>"
              +"<td id='balanceQty-"+id+"'>" + rowData.balanceQty + "</td>"
              +"<td ><input class='check' type='checkbox' id='check-"+rowData.autoId+"' style='cursor:pointer;'></td>"
            +"</tr>";
  }
  $("#accessoriesSizeSearchList").html(tr_list);
}

function drawRequisitionAccessoriesListSearchTable(data) {
  const length = data.length;
  var tr_list="";
  $("#requisitionAccessoriesSearchList").empty();
  
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.accessoriesId;
    tr_list=tr_list+"<tr id='requisitionRow-" + id + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "' data-size-id='" + rowData.sizeId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"' data-rack-name='"+rowData.rackName+"' data-bin-name='"+rowData.binName+"' data-receive-qty='"+rowData.previousReceiveQty+"' data-issue-qty='"+rowData.issueQty+"' data-return-qty='"+rowData.returnQty+"'>"
              +"<td id='requisitionPurchaseOrder-"+id+"'>" + rowData.purchaseOrder + "</td>"
              +"<td id='requisitionStyleNo-"+id+"'>" + rowData.styleNo + "</td>"
              +"<td id='requisitionItemName-"+id+"'>" + rowData.itemName + "</td>"
              +"<td id='requisitionItemColor-"+id+"'>" + rowData.itemColor + "</td>"
              +"<td id='requisitionAccessoriesName-"+id+"'>" + rowData.accessoriesName + "</td>"
              +"<td id='requisitionAccessoriesColor-"+id+"'>" + rowData.accessoriesColorName + "</td>"
              +"<td ><input class='requisitionCheck' type='checkbox' id='requisitionCheck-"+rowData.accessoriesId+"' style='cursor:pointer;'></td>"
            +"</tr>";
  }
  $("#requisitionAccessoriesSearchList").html(tr_list);
}

function drawAccessoriesSizeListTable(data,rowType = ''){
  const rackOptions = fakeRackList.map(rack => `<option value=${rack.rackId}>${rack.rackName}</option>`);
  let rows = "", tempPurchaseOrder = "", tempStyleId, tempItemId, tempItemColorId, tempAccessoriesId, tempAccessoriesColorId;
  
    const length = data.length;

    let parentRowId = 0,tempTotalBalanceQty=0,tempTotalIssueQty=0;
    let issueQtyList = [];
    let balanceQtyList = [];
    $("#sizeList").empty();
    
    for (var i = 0; i < length; i++) {   
      const rowData = data[i];
      const id = rowData.autoId;

      if (!(rowData.accessoriesColorId == tempAccessoriesColorId && rowData.accessoriesId == tempAccessoriesId && rowData.itemColorId == tempItemColorId && rowData.itemId == tempItemId && rowData.styleId == tempStyleId && rowData.purchaseOrder == tempPurchaseOrder)) {
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
                        <th>Size Id</th>
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

                                  rows += "<tr id='rowId-" + id + "'  class='"+rowType+" sizeRowList rowGroup-" + parentRowId + "' data-parent-row='" + parentRowId + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "' data-size-id='" + rowData.sizeId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"' data-rack-name='"+rowData.rackName+"' data-bin-name='"+rowData.binName+"'>"
                                  +"<td id='listSizeName-"+id+"'>" + rowData.sizeName + "</td>"
                                  +"<td id='sizeUnit-"+id+"'>" + rowData.unit + "</td>"
                                  +"<td id='sizeBalanceQty-"+id+"'>" + rowData.balanceQty + "</td>"
                                  +"<td><input type='number' class='sizeIssueGroup-" + parentRowId + " form-control-sm max-width-100' id='sizeIssueQty-"+id+"' onblur='totalIssueQtyCount(" + parentRowId + ")' value='"+rowData.unitQty+"'></td>"
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

    $("#sizeList").html(rows);
    issueQtyList.forEach((qty, index) => {
      $("#issueQty-" + index).text(qty);

    });
    data.forEach((size,index)=>{
      $("#rackId-"+size.autoId).val(size.rackName);
      $("#binId-"+size.autoId).val(size.binName);
    })

}

function drawAccessoriesIssueListTable(data){
  const length = data.length;
  var tr_list="";
  $("#accessoriesIssueList").empty();
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    tr_list=tr_list+"<tr id='row-" + rowData.transactionId + "'>"
              +"<td>" + rowData.transactionId + "</td>"
              +"<td>" + rowData.issueDate + "</td>"
              +"<td>" + rowData.issuedDepartmentName + "</td>"
              +"<td ><i class='fa fa-search' onclick=\"setAccessoriesIssueInfo('" + rowData.transactionId + "')\" style='cursor:pointer;'> </i></td>"
            +"</tr>";
  }
  $("#accessoriesIssueList").html(tr_list);
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
  $("#purchaseOrderSearch , #styleNoSearch, #itemNameSearch,#accessoriesItemSearch,#colorSearch,#sizeNameSearch").on("keyup", function () {
    const po = $("#purchaseOrderSearch").val().toLowerCase();
    const style = $("#styleNoSearch").val().toLowerCase();
    const item = $("#itemNameSearch").val().toLowerCase();
    const accessories = $("#accessoriesItemSearch").val().toLowerCase();
    const color = $("#colorSearch").val().toLowerCase();
    const sizeName = $("#sizeNameSearch").val().toLowerCase();

    $("#accessoriesSizeSearchList tr").filter(function () {
      const id = this.id.slice(4);
      
      if($("#check-"+id).prop('checked') || ( ( !po.length || $("#purchaseOrder-"+id).text().toLowerCase().indexOf(po) > -1 ) && 
        ( !style.length || $("#styleNo-"+id).text().toLowerCase().indexOf(style) > -1 ) &&
        ( !item.length || $("#itemName-"+id).text().toLowerCase().indexOf(item) > -1 ) &&
        ( !accessories.length || $("#accessoriesName-"+id).text().toLowerCase().indexOf(accessories) > -1 ) &&
        ( !color.length || $("#itemColor-"+id).text().toLowerCase().indexOf(color) > -1 || $("#accessoriesColor-"+id).text().toLowerCase().indexOf(color) > -1 )  &&
        ( !sizeName.length || $("#sizeName-"+id).text().toLowerCase().indexOf(sizeName) > -1 ) ) ){      
        $(this).show();
       }else{      
        $(this).hide();
       }
    });
  });
});

$(document).ready(function () {
  $("#requisitionPurchaseOrderSearch , #requisitionStyleNoSearch, #requisitionItemNameSearch,#requisitionAccessoriesItemSearch,#requisitionColorSearch,#requisitionSizeIdSearch").on("keyup", function () {
    const po = $("#requisitionPurchaseOrderSearch").val().toLowerCase();
    const style = $("#requisitionStyleNoSearch").val().toLowerCase();
    const item = $("#requisitionItemNameSearch").val().toLowerCase();
    const accessories = $("#requisitionAccessoriesItemSearch").val().toLowerCase();
    const color = $("#requisitionColorSearch").val().toLowerCase();
    const sizeName = $("#requisitionSizeNameSearch").val().toLowerCase();

    $("#requisitionAccessoriesSearchList tr").filter(function () {
      const id = this.id.slice(15);
      
      if($("#requisitionCheck-"+id).prop('checked') || ( ( !po.length || $("#requisitionPurchaseOrder-"+id).text().toLowerCase().indexOf(po) > -1 ) && 
        ( !style.length || $("#requisitionStyleNo-"+id).text().toLowerCase().indexOf(style) > -1 ) &&
        ( !item.length || $("#requisitionItemName-"+id).text().toLowerCase().indexOf(item) > -1 ) &&
        ( !accessories.length || $("#requisitionAccessoriesName-"+id).text().toLowerCase().indexOf(accessories) > -1 ) &&
        ( !color.length || $("#requisitionItemColor-"+id).text().toLowerCase().indexOf(color) > -1 || $("#requisitionAccessoriesColor-"+id).text().toLowerCase().indexOf(color) > -1 )  &&
        ( !sizeName.length || $("#requisitionSizeName-"+id).text().toLowerCase().indexOf(sizeName) > -1 ) ) ){      
        $(this).show();
       }else{      
        $(this).hide();
       }
    });
  });
});


var today = new Date();
document.getElementById("issueDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);


