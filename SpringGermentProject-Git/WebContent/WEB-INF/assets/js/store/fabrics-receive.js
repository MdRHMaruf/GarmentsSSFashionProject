
$("#searchRefreshBtn").click(function(){
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

$("#newTransectionBtn").click(function () {

  $("#transectionId").val("-- New Transection --");
  $("#btnSubmit").prop("disabled", false);
  $("#btnEdit").prop("disabled", true);
});

$("#findTransectionBtn").click(function () {
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
  const rowList = $("#rollList tr");
  const length = rowList.length;
  const transectionId = $("#transectionId").val();
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
    const id = row.id;
    const purchaseOrder = row.getAttribute('data-purchase-order');
    const styleId = row.getAttribute('data-style-id');
    const itemId = row.getAttribute('data-item-id');
    const itemColorId = row.getAttribute('data-item-color-id');
    const fabricsId = row.getAttribute('data-fabrics-id');
    const rollId = $("#rollId-" + id).text();
    const unitQty = $("#unitQty-" + id).text().trim() == '' ? "0" : $("#unitQty-" + id).text();
    const rackName = $("#rackName-" + id).text();
    const binName = $("#binName-" + id).text();
    console.log(id,purchaseOrder,styleId,itemId,itemColorId,fabricsId,rollId,unitQty);
    rollList += `rollId : ${rollId},unitId : ${unitId},rollQty : ${rollQty},qcPassedQty : ${qcPassedQty},issueQty : ${issueQty},balanceQty : ${balanceQty},rate : ${rate},totalAmount : ${totalAmount},remarks : ${remarks},rackName : ${rackName},binName : ${binName} #`;
  }

  rollList = rollList.slice(0, -1);

  if (length > 0) {
    if (transectionId != '') {
      if(grnNo != ''){
        if (fabricsId != '') {

          if (confirm("Are you sure to submit this Fabrics Receive...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './submitFabricsReceive',
              data: {
                transectionId: transectionId,
                grnNo: grnNo,
                grnDate: grnDate,
                location: location,
                indentId: indentId,
                fabricsId: fabricsId,
                receiveQty: receiveQty,
                noOfRoll: noOfRoll,
                unitId : unitId,
                rollList: rollList,
                supplierId: supplier,
                buyer: buyer,
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
                  alert("Successfully Submit...");
                  refreshAction();
                }
              }
            });
          }
        } else {
          warningAlert("Please Select Fabrics..");
          $("#fabricsItem").focus();
        }
      }else{
        warningAlert("Please Enter GRN No")
        $("#grnNo").focus();
      }
    } else {
      warningAlert("Please Get a transection Id")
      $("#transectionId").focus();
    }
  } else {
    warningAlert("Please Enter Fabrics Roll");
  }
}


function editAction() {
  const rowList = $("#rollList tr");
  const length = rowList.length;

  

  const transectionId = $("#transectionId").val();
  const grnNo = $("#grnNo").val();
  const grnDate = $("#grnDate").val();
  const location = $("#location").val();
  const indentId = $("#indentId").val();
  const fabricsId = $("#fabricsId").val();
  const receiveQty = $("#receiveQty").val();
  const noOfRoll = $("#noOfRoll").val();
  const unitId = $("#unit").val();
  const unit = $( "#unit option:selected" ).text()
  const supplier = $("#supplier").val();
  const buyer = $("#buyer").val();
  const challanNo = $("#challanNo").val();
  const challanDate = $("#challanDate").val();
  const remarks = $("#remarks").val();
  const preparedBy = $("#preparedBy").val();
  const userId = $("#userId").val();

  let rollList = ""

  for (let i = 0; i < length; i++) {

    const id = rowList[i].id.slice(4);
    const rollId = $("#rollId-" + id).text();
    const supplierRollId = $("#supplierRollId-" + id).text();
    const rollQty = $("#rollQty-" + id).text().trim() == '' ? "0" : $("#rollQty-" + id).text();
    const qcPassedQty = $("#qcPassedQty-" + id).text().trim() == '' ? "0" : $("#qcPassedQty-" + id).text();
    const issueQty = $("#issueQty-" + id).text().trim() == '' ? "0" : $("#issueQty-" + id).text();
    const balanceQty = $("#amount-" + id).text().trim() == '' ? "0" : $("#amount-" + id).text();
    const rate = $("#rate-" + id).text().trim() == '' ? "0" : $("#rate-" + id).text();
    const totalAmount = $("#totalAmount-" + id).text().trim() == '' ? "0" : $("#totalAmount-" + id).text();
    const remarks = $("#remarks-" + id).text();
    const rackName = $("#rackName-" + id).text();
    const binName = $("#binName-" + id).text();

    rollList += `rollId : ${rollId},supplierRollId : ${supplierRollId},unitId : ${unitId},unit : ${unit},rollQty : ${rollQty},qcPassedQty : ${qcPassedQty},issueQty : ${issueQty},balanceQty : ${balanceQty},rate : ${rate},totalAmount : ${totalAmount},remarks : ${remarks},rackName : ${rackName},binName : ${binName} #`;
  }

  rollList = rollList.slice(0, -1);

  

  if (length > 0) {
    if (transectionId != '') {
      if(grnNo != ''){
        if (fabricsId != '') {

          if (confirm("Are you sure to submit this Fabrics Receive...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './editFabricsReceive',
              data: {
                transectionId: transectionId,
                grnNo: grnNo,
                grnDate: grnDate,
                location: location,
                indentId: indentId,
                fabricsId: fabricsId,
                receiveQty: receiveQty,
                noOfRoll: noOfRoll,
                unitId : unitId,
                rollList: rollList,
                supplierId: supplier,
                buyer: buyer,
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
                  alert("Successfully Submit...");
                  refreshAction();
                }
              }
            });
          }
        } else {
          warningAlert("Please Select Fabrics..");
          $("#fabricsItem").focus();
        }
      }else{
        warningAlert("Please Enter GRN No")
        $("#grnNo").focus();
      }
      
    } else {
      warningAlert("Please Get a transection Id")
      $("#transectionId").focus();
    }
  } else {
    warningAlert("Please Enter Fabrics Roll");
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
      autoId : autoId
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
      $("#unit").text(fabricsInfo.unit);
      $("#totalPoQty").text(fabricsInfo.grandQty);     
      $('#itemSearchModal').modal('hide');
    }
  });
}

// function setFabricsInfo(autoId) {
//   $.ajax({
//     type: 'GET',
//     dataType: 'json',
//     url: './getFabricsIndentInfo',
//     data: {
//       autoId: autoId
//     },
//     success: function (data) {
//       const indent = data.fabricsInfo;
//       $("#indentId").val(indent.autoId);
//       $("#unit").val(indent.unitId).change();
//       $("#fabricsId").val(indent.fabricsId);
//       $("#fabricsItem").val(indent.fabricsName);
//       $("#totalPoQty").val(indent.grandQty.toFixed(2));
//       $("#balance").val(indent.grandQty.toFixed(2));
//       $("#balanceTotal").val(indent.grandQty.toFixed(2));
//       $("#fabricsRate").val(indent.rate.toFixed(2));
//       $('#fabricsModal').modal('hide');

//     }
//   });
// }

function setFabricsReceiveInfo(transectionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsReceiveInfo',
    data: {
      transectionId: transectionId
    },
    success: function (data) {
      const fabricsReceive = data.fabricsReceive;
      $("#transectionId").val(fabricsReceive.transectionId);
      $("#grnNo").val(fabricsReceive.grnNo);
      let date = fabricsReceive.grnDate.split("/");
      $("#grnDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      $("#indentId").val(fabricsReceive.indentId);
      $("#fabricsId").val(fabricsReceive.fabricsId);
      $("#location").val(fabricsReceive.location);
      // $("#fabricsItem").val(indent.fabricsName);
      $("#receiveQty").val(fabricsReceive.receiveQty);
      $("#noOfRoll").val(fabricsReceive.noOfRoll);
      $("#remarks").val(fabricsReceive.remarks);
      $("#preparedBy").val(fabricsReceive.preparedBy).change();
      $('#searchModal').modal('hide');
      $("#rollList").empty();
      $("#rollList").append(drawFabricsRollListTable(fabricsReceive.fabricsRollList));
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);

    }
  });
}

$("#noOfRoll").on('keyup', function (e) {
  if (e.key === 'Enter' || e.keyCode === 13) {
    const noOfRoll = $("#noOfRoll").val();
    const receiveQty = $("#receiveQty").val();
    const purchaseOrder = $("#purchaseOrder").text();
    const styleId = $("#styleId").val();
    const itemId = $("#styleItemId").val();
    const itemColorId = $("#itemColorId").val();
    const fabricsId = $("#fabricsId").val();
    const fabricsName = $("#fabricsItem").text();
    const fabricsColorId = $("#fabricsColorId").val();
    const fabricsColor = $("#fabricsColor").text();
    const fabricsRate = $("#fabricsRate").val();
    const unitId = $("unitId").val();
    const unit = $("#unit").text();


    if (noOfRoll != '') {
      if (receiveQty != '') {
        if (fabricsId != '' && fabricsName != '') {
          const qty = receiveQty / noOfRoll;
         
          let rows = "";
          for (let i = 1; i <= noOfRoll; i++) {
            rows += "<tr id='"+i+"' data-purchase-order='"+purchaseOrder+"' data-style-id='"+styleId+"' data-item-id='"+itemId+"' data-item-color-id='"+itemColorId+"' data-fabrics-id='"+fabricsId+"' data-fabrics-color-id='"+fabricsColorId+"' data-unit-id='"+unitId+"'>"
            +"<td>"+fabricsName+"</td>"
            +"<td>"+fabricsColor+"</td>"
            +"<td id='rollId-" + i + "' contenteditable = 'true'>" + i + "</td>"
            +"<td>"+unit+"</td>"
            +"<td id='unitQty-" + i + "' contenteditable = 'true'>" + qty + "</td>"   
            +"<td id='qcPassedQty-" + i + "'> </td>"
            +"<td id='rackName-" + i + "' contenteditable = 'true'> </td>"
            +"<td id='binName-" + i + "' contenteditable = 'true'> </td>"
            +"<td><i class='fa fa-trash' onclick='deleteBuyerPoItem(" + 1 + ")' style='cursor:pointer;'> </i></td>"
            +"</tr>";
            
          }
          $("#rollList").append(rows);
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


function drawFabricsRollListTable(data) {
  let rows = [];
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    let row = $("<tr id='row-" + i + "'/>")
    row.append($("<td id='rollId-" + i + "' contenteditable = 'true'>" + rowData.rollId + "</td>"));
    row.append($("<td id='supplierRollId-" + i + "' contenteditable = 'true'>" + rowData.supplierRollId + "</td>"));
    row.append($("<td id='rollQty-" + i + "'>" + rowData.rollQty + "</td>"));
    row.append($("<td id='qcPassedQty-" + i + "'>"+rowData.qcPassedQty+"</td>"));
    row.append($("<td id='issueQty-" + i + "'>"+rowData.issueQty+"</td>"));
    row.append($("<td id='balanceQty-" + i + "'>"+rowData.balanceQty+"</td>"));
    row.append($("<td id='rate-" + i + "'> " + rowData.rate + "</td>"));
    row.append($("<td id='total-" + i + "'> " + rowData.totalAmount + "</td>"));
    row.append($("<td id='remarks-" + i + "' contenteditable = 'true'>"+rowData.remarks+"</td>"));
    row.append($("<td id='rackName-" + i + "' contenteditable = 'true'>"+rowData.rackName+"</td>"));
    row.append($("<td id='binName-" + i + "' contenteditable = 'true'>"+rowData.binName+"</td>"));
    
    rows.push(row);
  }

  return rows;
}

function drawFabricsReceiveListTable(data) {
  let rows = [];
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    let row = $("<tr/>")
    row.append($("<td>" + rowData.transectionId + "</td>"));
    row.append($("<td>" + rowData.grnNo + "</td>"));
    row.append($("<td>" + rowData.grnDate + "</td>"));
    row.append($("<td>" + rowData.receiveQty + "</td>"));
    row.append($("<td>" + rowData.noOfRoll + "</td>"));
    row.append($("<td ><i class='fa fa-search' onclick=\"setFabricsReceiveInfo('" + rowData.transectionId + "')\" style='cursor:pointer;'> </i></td>"));
    
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
    
    rows += "<tr id='"+rowData.autoId+"'>"
    +"<td id='purchaseOrder-"+rowData.autoId+"'>"+rowData.purchaseOrder+"</td>"
    +"<td id='styleName-"+rowData.autoId+"'>"+rowData.styleName+"</td>"
    +"<td id='itemName-"+rowData.autoId+"'>"+rowData.itemName+"</td>"
    +"<td id='itemColor-"+rowData.autoId+"'>"+rowData.itemColorName+"</td>"
    +"<td id='fabricsName-"+rowData.autoId+"'>"+rowData.fabricsName+"</td>"
    +"<td id='fabricsColor-"+rowData.autoId+"'>"+rowData.fabricsColor+"</td>"
    +"<td><i class='fa fa-search' onclick=\"setFabricsInfo('" + rowData.autoId + "')\" style='cursor:pointer;'> </i></td>"
    +"</tr>";
    
  }

  $("#purchaseOrderList").html(rows);
}


function successAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-success");
  document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
  element.show();
}

function warningAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-warning");
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
  element.show();
}

function dangerAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
  element.show();
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
      
      if( ( !po.length || $("#purchaseOrder-"+id).text().toLowerCase().indexOf(po) > -1 ) && 
      ( !style.length || $("#styleName-"+id).text().toLowerCase().indexOf(style) > -1 ) &&
       ( !item.length || $("#itemName-"+id).text().toLowerCase().indexOf(item) > -1 ) &&
       ( !fabrics.length || $("#fabricsName-"+id).text().toLowerCase().indexOf(fabrics) > -1 ) &&
       ( !color.length || $("#itemColor-"+id).text().toLowerCase().indexOf(color) > -1 || $("#fabricsColor-"+id).text().toLowerCase().indexOf(color) > -1 )){
        
        $(this).show();
       }else{
        
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

