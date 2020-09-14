

$("#newFabricsReturnBtn").click(function () {

  $("#returnTransectionId").val("-- New Transection --");
  $("#btnSubmit").prop("disabled", false);
  $("#btnEdit").prop("disabled", true);
});

$("#findFabricsReturnBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsReturnList',
    data: {},
    success: function (data) {
      drawFabricsReturnListTable(data.fabricsReturnList);
    }
  });
});

$("#grnSearchBtn").click(function () {

  const supplierId = $("#supplier").val();
  
  if(supplierId != '0'){
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getFabricsRollList',
      data: {
        supplierId : supplierId
      },
      success: function (data) {
        drawFabricsRollListSearchTable(data.fabricsRollList)
        $("#rollSearchModal").modal('show');
      }
    });
  }else{
    warningAlert("Please Select a supplier name first");
    $("#supplier").focus();
  }
  
});

document.getElementById("checkAll").addEventListener("click", function () {
  if ($(this).prop('checked')) {
    $(".check").prop('checked', true);
  } else {
    $(".check").prop('checked', false);
  }
});

$("#rollAddBtn").click(function(){
  let rows = "";
  $("#fabricsRollSearchList tr").filter(function () {
    const id = this.id.slice(4);
    
    const fabricsName = $("#fabricsName-"+id).text();
    const fabricsColor = $("#fabricsColor-"+id).text();
    const rollId = $("#rollId-"+id).text();
    const balanceQty = $("#balanceQty-"+id).text();
   

    const purchaseOrder = this.getAttribute("data-purchase-order");
    const styleId = this.getAttribute("data-style-id");
    const itemId = this.getAttribute("data-item-id");
    const itemColorId = this.getAttribute("data-item-color-id");
    const fabricsId = this.getAttribute("data-fabrics-id");
    const fabricsColorId = this.getAttribute("data-fabrics-color-id");
    const unitId = this.getAttribute("data-unit-id");
    const unit = this.getAttribute("data-unit");
    const rackName = this.getAttribute("data-rack-name");
    const binName = this.getAttribute("data-bin-name");

    console.log("check=",$("#check-"+id).prop('checked'));
    if($("#check-"+id).prop('checked')){
      rows += "<tr id='rowId-" + id + "' data-purchase-order='" + purchaseOrder + "' data-style-id='" + styleId + "' data-item-id='" + itemId + "' data-item-color-id='" + itemColorId + "' data-fabrics-id='" + fabricsId + "' data-fabrics-color-id='" + fabricsColorId + "' data-unit-id='" + unitId + "' data-unit='"+unit+"' data-rack-name='"+rackName+"' data-bin-name='"+binName+"'>"
                +"<td id='rollFabricsName-"+id+"'>" + fabricsName + "</td>"
                +"<td id='rollFabricsColor-"+id+"'>" + fabricsColor + "</td>"
                +"<td id='listRollId-"+id+"'>" + rollId + "</td>"
                +"<td id='rollUnit-"+id+"'>" + unit + "</td>"
                +"<td id='balanceQty-"+id+"'>" + balanceQty + "</td>"
                +"<td><input type='number' class='form-control-sm max-width-100' id='returnQty-"+id+"' value='"+balanceQty+"'></td>"
                +"<td id='rackName-"+id+"'>" + rackName+"</td>"
                +"<td id='binName-"+id+"'>" + binName+"</td>"
              +"</tr>";
    }
  });
  console.log("rows= ",rows);
  $("#rollList").html(rows);
  $('#rollSearchModal').modal('hide');

});


function setFabricsReceiveInfo(transectionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsReceiveInfoForReturn',
    data: {
      transectionId: transectionId
    },
    success: function (data) {

      const fabricsReceive = data.fabricsReceive;
      //$("#returnTransectionId").val(fabricsReceive.transectionId);
      $("#fabricsId").val(fabricsReceive.fabricsId);
      $("#fabrics").val(fabricsReceive.fabricsName);
      $("#grnNo").val(fabricsReceive.grnNo);
      let date = fabricsReceive.grnDate.split("/");
      $("#receiveDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      
      $("#remarks").val(fabricsReceive.remarks);
      $("#supplier").val(fabricsReceive.supplierId).change();
      $('#grnSearchModal').modal('hide');
      drawFabricsRollListTable(fabricsReceive.fabricsRollList);

    }
  });
}

function setFabricsReturnInfo(returnTransectionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsReturnInfo',
    data: {
      returnTransectionId: returnTransectionId
    },
    success: function (data) {

      const fabricsReturn = data.fabricsReturn;
      console.log(fabricsReturn)
      $("#returnTransectionId").val(fabricsReturn.returnTransectionId);
      
      let date = fabricsReturn.returnDate.split("/");
      $("#returnDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      
      $("#remarks").val(fabricsReturn.remarks);
      $("#supplier").val(fabricsReturn.supplierId).change();
      drawFabricsRollListTable(fabricsReturn.fabricsRollList);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);
      $('#returnSearchModal').modal('hide');

    }
  });
}

function submitAction() {
  const rowList = $("#rollList tr");
  const length = rowList.length;
  const returnTransectionId = $("#returnTransectionId").val();
  const returnDate = $("#returnDate").val();
  const supplierId = $("#supplier").val();
  const remarks = $("#remarks").val();
  const userId = $("#userId").val();

  let rollList = ""
  for(let i = 0 ;i<length;i++){
    const row = rowList[i];
    const id = row.id.slice(6);
    const purchaseOrder = row.getAttribute("data-purchase-order");
    const styleId = row.getAttribute("data-style-id");
    const itemId = row.getAttribute("data-item-id");
    const itemColorId = row.getAttribute("data-item-color-id");
    const fabricsId = row.getAttribute("data-fabrics-id");
    const fabricsName = $("#rollFabricsName-"+id).text();
    const fabricsColorId = row.getAttribute("data-fabrics-color-id");
    const fabricsColorName = $("#rollFabricsColor-"+id).text();
    const unitId = row.getAttribute("data-unit-id");
    const unit = $("#rollUnit-"+id).text();
    const rackName = row.getAttribute("data-rack-name");
    const binName = row.getAttribute("data-bin-name");
    const rollId = $("#listRollId-"+id).text();
    const returnQty = $("#returnQty-"+id).val();
    const qcPassedType= 1;
    
    rollList += `autoId : ${id},returnTransectionId : ${returnTransectionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},fabricsId : ${fabricsId},fabricsName : ${fabricsName},fabricsColorId : ${fabricsColorId},fabricsColorName : ${fabricsColorName},rollId : ${rollId},unitId : ${unitId},unit : ${unit},returnQty : ${returnQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  
  };
    

  rollList = rollList.slice(0, -1);
  
  if (length > 0) {
    if (returnTransectionId != '') {   
          if (confirm("Are you sure to submit this Fabrics Return...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './submitFabricsReturn',
              data: {
                returnTransectionId: returnTransectionId,
                returnDate : returnDate,
                supplierId : supplierId,
                remarks : remarks,
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

  const returnTransectionId = $("#returnTransectionId").val();
  const returnDate = $("#returnDate").val();
  const grnNo = $("#grnNo").val();
  const remarks = $("#remarks").val();
  const userId = $("#userId").val();

  let rollList = ""

  for (let i = 0; i < length; i++) {

    const id = rowList[i].id.slice(4);
    const qcPassedQty = $("#qcPassedQty-" + id).text().trim() == '' ? "0" : $("#qcPassedQty-" + id).text();
    const qcPassedType = $("#qcPassed-" + id).val();
    const isReturn = $("#check-"+id).prop('checked');;

    rollList += `autoId : ${id},returnTransectionId : ${returnTransectionId},qcPassedQty : ${qcPassedQty},qcPassedType : ${qcPassedType},isReturn : ${isReturn},userId : ${userId} #`;
  }

  rollList = rollList.slice(0, -1);
  
  if (length > 0) {
    if (returnTransectionId != '') {
      if(grnNo != ''){
          if (confirm("Are you sure to Edit this Fabrics Return...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './editFabricsReturn',
              data: {
                returnTransectionId: returnTransectionId,
                returnDate : returnDate,
                grnNo: grnNo,
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


function drawFabricsRollListSearchTable(data) {
  const length = data.length;
  var tr_list="";
  $("#fabricsRollSearchList").empty();
  
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = rowData.autoId;
    tr_list=tr_list+"<tr id='row-" + id + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-fabrics-id='" + rowData.fabricsId + "' data-fabrics-color-id='" + rowData.fabricsColorId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"' data-rack-name='"+rowData.rackName+"' data-bin-name='"+rowData.binName+"'>"
              +"<td id='supplierName-"+id+"'>" + rowData.supplierName + "</td>"
              +"<td id='purchaseOrder-"+id+"'>" + rowData.purchaseOrder + "</td>"
              +"<td id='styleNo-"+id+"'>" + rowData.styleNo + "</td>"
              +"<td id='itemName-"+id+"'>" + rowData.itemName + "</td>"
              +"<td id='itemColor-"+id+"'>" + rowData.itemColor + "</td>"
              +"<td id='fabricsName-"+id+"'>" + rowData.fabricsName + "</td>"
              +"<td id='fabricsColor-"+id+"'>" + rowData.fabricsColorName + "</td>"
              +"<td id='rollId-"+id+"'>" + rowData.rollId + "</td>"
              +"<td id='balanceQty-"+id+"'>" + rowData.balanceQty + "</td>"
              +"<td ><input class='check' type='checkbox' id='check-"+rowData.autoId+"'></td>"
            +"</tr>";
  }
  $("#fabricsRollSearchList").html(tr_list);
}

function drawFabricsRollListTable(data){
    const length = data.length;
    let rows="";
   
    $("#rollList").empty();
    
    for (var i = 0; i < length; i++) {   
      const rowData = data[i];
      const id = rowData.autoId;
      rows += "<tr id='rowId-" + id + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-fabrics-id='" + rowData.fabricsId + "' data-fabrics-color-id='" + rowData.fabricsColorId + "' data-unit-id='" + rowData.unitId + "' data-unit='"+rowData.unit+"' data-rack-name='"+rowData.rackName+"' data-bin-name='"+rowData.binName+"'>"
      +"<td id='rollFabricsName-"+id+"'>" + rowData.fabricsName + "</td>"
      +"<td id='rollFabricsColor-"+id+"'>" + rowData.fabricsColorName + "</td>"
      +"<td id='listRollId-"+id+"'>" + rowData.rollId + "</td>"
      +"<td id='rollUnit-"+id+"'>" + rowData.unit + "</td>"
      +"<td id='balanceQty-"+id+"'>" + rowData.balanceQty + "</td>"
      +"<td><input type='number' class='form-control-sm max-width-100' id='returnQty-"+id+"' value='"+rowData.unitQty+"'></td>"
      +"<td id='rackName-"+id+"'>" + rowData.rackName+"</td>"
      +"<td id='binName-"+id+"'>" + rowData.binName+"</td>"
    +"</tr>";
    }
    $("#rollList").html(rows);

}

function drawFabricsReturnListTable(data){
  const length = data.length;
  var tr_list="";
  $("#fabricsReturnList").empty();
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    tr_list=tr_list+"<tr id='row-" + rowData.returnTransectionId + "'>"
              +"<td>" + rowData.returnTransectionId + "</td>"
              +"<td>" + rowData.returnDate + "</td>"
              +"<td>" + rowData.supplierName + "</td>"
              +"<td ><i class='fa fa-search' onclick=\"setFabricsReturnInfo('" + rowData.returnTransectionId + "')\" style='cursor:pointer;'> </i></td>"
            +"</tr>";
  }
  $("#fabricsReturnList").html(tr_list);
}

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
  $("#supplierNameSearch,#purchaseOrderSearch , #styleNoSearch, #itemNameSearch,#fabricsItemSearch,#colorSearch,#rollIdSearch").on("keyup", function () {
    const supplierName = $("#supplierNameSearch").val().toLowerCase();
    const po = $("#purchaseOrderSearch").val().toLowerCase();
    const style = $("#styleNoSearch").val().toLowerCase();
    const item = $("#itemNameSearch").val().toLowerCase();
    const fabrics = $("#fabricsItemSearch").val().toLowerCase();
    const color = $("#colorSearch").val().toLowerCase();
    const rollId = $("#rollIdSearch").val().toLowerCase();

    console.log(supplierName,po,style,item,fabrics,color,rollId)
    $("#fabricsRollSearchList tr").filter(function () {
      const id = this.id.slice(4);
      console.log(( !supplierName.length  ));
      console.log( $("#supplierName-"+id).text().toLowerCase().indexOf(supplierName) > -1);
     
      if($("#check-"+id).prop('checked') || ( ( !supplierName.length || $("#supplierName-"+id).text().toLowerCase().indexOf(supplierName) > -1 ) && 
        ( !po.length || $("#purchaseOrder-"+id).text().toLowerCase().indexOf(po) > -1 ) && 
        ( !style.length || $("#styleName-"+id).text().toLowerCase().indexOf(style) > -1 ) &&
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
document.getElementById("returnDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
