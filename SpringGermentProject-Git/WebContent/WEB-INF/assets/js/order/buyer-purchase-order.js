let styleIdForSet = 0;
let itemIdForSet = 0;
let sizeValueListForSet = [];
let sizesListByGroup = JSON;

window.onload = () =>{

  document.title = "Buyer Purchase Order";
  let userId = $("#userId").val();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './sizesLoadByGroup',
    data: {},
    success: function (obj) {
      sizesListByGroup = [];
      sizesListByGroup = obj.sizeList;
    }
  });

  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getBuyerPOItemsList',
    data: {
      buyerPoNo: "0",
      userId: userId
    },
    success: function (data) {
      if (data.result == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.result == "duplicate") {
        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
      } else {
        drawItemTable(data.result);
      }
    }
  });
};


function printBuyerPO(buyerPoId) {


  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './buyerIndentInfo',
    data: {
      buyerPoId: buyerPoId
    },
    success: function (data) {
      if (data == "Success") {
        let url = "printBuyerPoOrder";
        window.open(url, '_blank');

      }
    }
  });
}

function buyerWiseStyleLoad() {
  let buyerId = $("#buyerName").val();

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
        let options = "<option  value='0' selected>Select Style</option>";
        let length = styleList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
        };
        $("#styleNo").html(options);
        $('.selectpicker').selectpicker('refresh');
        $('#styleNo').val(styleIdForSet).change();
        styleIdForSet = 0;

      }
    });
  } else {
    let options = "<option value='0' selected>Select Style</option>";
    $("#styleNo").html(options);
    $('#styleNo').selectpicker('refresh');
    $('#styleNo').val("0").change();
  }

}
function styleWiseItemLoad() {
  let styleId = $("#styleNo").val();

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
        let options = "<option value='0' selected>Select Item Type</option>";
        let length = itemList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
        };
        document.getElementById("itemType").innerHTML = options;
        $('.selectpicker').selectpicker('refresh');
        $('#itemType').val(itemIdForSet).change();
        itemIdForSet = 0;
      }
    });
  } else {
    let options = "<option value='0' selected>Select Item Type</option>";
    $("#itemType").html(options);
    $('#itemType').selectpicker('refresh');
    $('#itemType').val(itemIdForSet).change();
    itemIdForSet = 0;
  }

}

function itemSizeAdd() {

  let buyerPOId = $("#buyerPOId").val();
  let buyerId = $("#buyerName").val();
  let styleId = $("#styleNo").val();
  let itemId = $("#itemType").val();
  let factoryId = $("#factory").val();
  let colorId = $("#color").val();
  let sizeGroupId = $("#sizeGroup").val();
  let customerOrder = $("#customerOrder").val();
  let purchaseOrder = $("#purchaseOrder").val();
  let shippingMark = $("#shippingMark").val();
  let userId = $("#userId").val();
  let totalUnit = 0;

  let rowList = $("tr.dataRow");
  let isExist = false;
  console.log("length=",rowList.length);
  for(let i=0;i<rowList.length;i++){
    row = rowList[i];
    if(row.getAttribute('data-style-id')==styleId && 
      row.getAttribute('data-item-id') == itemId && row.getAttribute('data-customer-order')==customerOrder && row.getAttribute('data-purchase-order')==purchaseOrder && row.getAttribute('data-color-id') == colorId && row.getAttribute('data-size-group-id') == sizeGroupId){
        isExist = true;
        break;
      }
  };
  if(!isExist){
    if (buyerId != 0) {
      if (styleId != 0) {
        if (itemId != 0) {
          if (factoryId != 0) {
            if (colorId != 0) {
              if (sizeGroupId != 0) {
                if (purchaseOrder != "") {
                  let sizeListLength = $(".sizeValue").length;
                  let sizeList = "";
                  for (let i = 0; i < sizeListLength; i++) {
                    let quantity = $("#sizeValue" + i).val().trim() == "" ? "0" : $("#sizeValue" + i).val().trim();
                    let id = $("#sizeId" + i).val().trim();
                    sizeList += "id=" + id + ",quantity=" + quantity + " ";
                    totalUnit += Number(quantity);
                  }
  
  
                  $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: './addItemToBuyerPO',
                    data: {
                      autoId: "0",
                      buyerPOId: buyerPOId,
                      buyerId: buyerId,
                      styleId: styleId,
                      itemId: itemId,
                      factoryId: factoryId,
                      colorId: colorId,
                      customerOrder: customerOrder,
                      purchaseOrder: purchaseOrder,
                      shippingMark: shippingMark,
                      sizeGroupId: sizeGroupId,
                      sizeListString: sizeList,
                      totalUnit: totalUnit,
                      unitCmt: 0,
                      totalPrice: 0,
                      unitFob: 0,
                      totalAmount: 0,
                      userId: userId
                    },
                    success: function (data) {
                      // warningAlert("");
                      if (data.result == "Something Wrong") {
                        dangerAlert("Something went wrong");
                      } else if (data.result == "duplicate") {
                        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                      } else {
                        drawItemTable(data.result);
                      }
                    }
                  });
                } else {
                  alert("Purchase Order Not Set... Please Select Purchase Order");
                  //warningAlert("Purchase Order Not Set... Please Select Purchase Order");
                  $("#purchaseOrder").focus();
                }
              } else {
                alert("Size Group not selected ... Please Select Size group");
                // warningAlert("Size Group not selected ... Please Select Size group");
                $("#sizeGroup").focus();
              }
            } else {
              alert("Color Not Selected... Please Select Color");
              $("#color").focus();
            }
          } else {
            alert("Factory not selected... Please Select Factory Name");
            $("#factoryId").focus();
          }
        } else {
          alert("Item Type not selected... Please Select Item Type");
          $("#itemType").focus();
        }
      } else {
        alert("Style No not selected... Please Select Style No");
        $("#styleNo").focus();
      }
    } else {
      alert("Buyer Name not selected... Please Select Buyer Name");
      $("#buyerName").focus();
    }
  }else{
    warningAlert("This Item Already Exist");
  }
  

}


function itemSizeEdit() {

  let buyerPOId = $("#buyerPOId").val();
  let itemAutoId = $("#itemAutoId").val();
  let buyerId = $("#buyerName").val();
  let styleId = $("#styleNo").val();
  let itemId = $("#itemType").val();
  let factoryId = $("#factory").val();
  let colorId = $("#color").val();
  let sizeGroupId = $("#sizeGroup").val();
  let customerOrder = $("#customerOrder").val();
  let purchaseOrder = $("#purchaseOrder").val();
  let shippingMark = $("#shippingMark").val();
  let unitCmt = $("#unitCmt" + itemAutoId).val();
  let unitFob = $("#unitFob" + itemAutoId).val();
  let userId = $("#userId").val();
  let totalUnit = 0;
  let totalPrice = 0;
  let totalAmount = 0;


  if (buyerId != 0) {
    if (styleId != 0) {
      if (itemId != 0) {
        if (factoryId != 0) {
          if (colorId != 0) {
            if (sizeGroupId != 0) {
              if (purchaseOrder != "") {
                let sizeListLength = $(".sizeValue").length;
                let sizeList = "";
                for (let i = 0; i < sizeListLength; i++) {
                  let quantity = $("#sizeValue" + i).val().trim() == "" ? "0" : $("#sizeValue" + i).val().trim();
                  let id = $("#sizeId" + i).val().trim();
                  sizeList += "id=" + id + ",quantity=" + quantity + " ";
                  totalUnit += Number(quantity);
                }
                totalPrice = totalUnit * unitCmt;
                totalAmount = totalUnit * unitFob;

                $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: './editBuyerPoItem',
                  data: {
                    autoId: itemAutoId,
                    buyerPOId: buyerPOId,
                    buyerId: buyerId,
                    styleId: styleId,
                    itemId: itemId,
                    factoryId: factoryId,
                    colorId: colorId,
                    customerOrder: customerOrder,
                    purchaseOrder: purchaseOrder,
                    shippingMark: shippingMark,
                    sizeGroupId: sizeGroupId,
                    sizeListString: sizeList,
                    totalUnit: totalUnit,
                    unitCmt: unitCmt,
                    totalPrice: totalPrice,
                    unitFob: unitFob,
                    totalAmount: totalAmount,
                    userId: userId
                  },
                  success: function (data) {
                    if (data.result == "Something Wrong") {
                      dangerAlert("Something went wrong");
                    } else if (data.result == "duplicate") {
                      dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                    } else {
                      drawItemTable(data.result);
                    }
                  }
                });
              } else {
                alert("Purchase Order Not Set... Please Select Purchase Order");
                $("#purchaseOrder").focus();
              }
            } else {
              alert("Size Group not selected ... Please Select Size group");
              $("#sizeGroup").focus();
            }
          } else {
            alert("Color Not Selected... Please Select Color");
            $("#color").focus();
          }
        } else {
          alert("Factory not selected... Please Select Factory Name");
          $("#factoryId").focus();
        }
      } else {
        alert("Item Type not selected... Please Select Item Type");
        $("#itemType").focus();
      }
    } else {
      alert("Style No not selected... Please Select Style No");
      $("#styleNo").focus();
    }
  } else {
    alert("Buyer Name not selected... Please Select Buyer Name");
    $("#buyerName").focus();
  }

}

function submitAction() {
  let buyerPoId = $("#buyerPOId").val();
  let buyerId = $("#buyerName").val();
  let paymentTerm = $("#paymentTerm").val();
  let currency = $("#currency").val();
  let totalRow = $("#tableList tr");
  let rowList = $("#tableList tr.dataRow");

  let totalUnit = 0;
  let unitCmt = 0;
  let totalPrice = 0;
  let unitFob = 0;
  let totalAmount = 0;

  console.log("rowList",rowList);
  rowList.each((index,row) =>{
    console.log(row);
    let autoId = row.getAttribute('data-auto-id');
    console.log("auto id=",autoId);
    totalUnit += Number($("#totalUnit" + autoId).text());
    unitCmt += Number($("#unitCmt" + autoId).val());
    totalPrice += Number($("#totalPrice" + autoId).text());
    unitFob += Number($("#unitFob" + autoId).val());
    totalAmount += Number($("#totalAmount" + autoId).text());
  });

  let note = $("#note").val();
  let remarks = $("#remarks").val();
  let userId = $("#userId").val();



  if (buyerId != 0) {
    if (totalRow.length != 0) {
      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './submitBuyerPO',
        data: {
          buyerPoId: buyerPoId,
          buyerId: buyerId,
          paymentTerm: paymentTerm,
          currency: currency,
          totalUnit: totalUnit,
          unitCmt: unitCmt,
          totalPrice: totalPrice,
          unitFob: unitFob,
          totalAmount: totalAmount,
          note: note,
          remarks: remarks,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Buyer Name..This Unit Name Already Exist")
          } else {
            successAlert("Buyer Purchase Order Save Successfully");
            refreshAction();
          }
        }
      });
    }
    else {
      alert("At first Add Size Wise Buyer Order Estimate");
    }


  } else {
    alert("Buyer Name not selected... Please Select Buyer Name");
    $("#buyerName").focus();
  }
}


function buyerPoEditAction() {

  let buyerPoId = $("#buyerPOId").val();
  let buyerId = $("#buyerName").val();
  let paymentTerm = $("#paymentTerm").val();
  let currency = $("#currency").val();
  let rowList = $("#tableList tr.dataRow");

  let totalUnit = 0;
  let unitCmt = 0;
  let totalPrice = 0;
  let unitFob = 0;
  let totalAmount = 0;

  rowList.each((index , row )=>{
    let autoId = row.getAttribute('data-auto-id');
    totalUnit += Number($("#totalUnit" + autoId).text());
    unitCmt += Number($("#unitCmt" + autoId).val());
    totalPrice += Number($("#totalPrice" + autoId).text());
    unitFob += Number($("#unitFob" + autoId).val());
    totalAmount += Number($("#totalAmount" + autoId).text());
  });

  let changedItems = {};
  changedItems['list'] = [];
  rowList = $("#tableList tr.changed");
  rowList.each((index , row )=>{
    let autoId = row.getAttribute('data-auto-id');
    let item = {
      autoId : autoId,
      unitCmt : Number($("#unitCmt" + autoId).val()),
      totalPrice : Number($("#totalPrice" + autoId).text()),
      unitFob : Number($("#unitFob" + autoId).val()),
      totalAmount : Number($("#totalAmount" + autoId).text())
    }
    changedItems.list.push(item);
  });

  let note = $("#note").val();
  let remarks = $("#remarks").val();
  let userId = $("#userId").val();

  console.log("Edit function call");
  if (buyerPoId != "0") {
    if (buyerId != 0) {

      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './editBuyerPO',
        data: {
          buyerPoId: buyerPoId,
          buyerId: buyerId,
          paymentTerm: paymentTerm,
          currency: currency,
          totalUnit: totalUnit,
          unitCmt: unitCmt,
          totalPrice: totalPrice,
          unitFob: unitFob,
          totalAmount: totalAmount,
          note: note,
          remarks: remarks,
          changedItemsList : JSON.stringify(changedItems),
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Buyer Name..This Unit Name Already Exist")
          } else {
            successAlert("Buyer Purchase Order Edit Successfully");
          }
        }
      });
    } else {
      alert("Buyer Name not selected... Please Select Buyer Name");
      $("#buyerName").focus();
    }
  } else {
    alert("Something Wrong... Buyer Purchase Order Id not found");
    $("#buyerName").focus();
  }

}

function searchBuyerPO(buyerPoNo) {

  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getBuyerPO',
    data: {
      buyerPoNo: buyerPoNo
    },
    success: function (data) {
      if (data.buyerPO == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.buyerPO == "duplicate") {
        dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
      } else {

        let buyerPo = data.buyerPO;
        console.log(buyerPo);
        $("#buyerPOId").val(buyerPo.buyerPoId);
        $("#buyerName").val(buyerPo.buyerId).change();
        $("#paymentTerm").val(buyerPo.paymentTerm).change();
        $("#currency").val(buyerPo.currency).change();
        $("#note").val(buyerPo.note);
        $("#remarks").val(buyerPo.remarks);

        drawItemTable(buyerPo.itemList);
        $('.modal').modal('hide')
        $("#btnPOSubmit").hide();
        $("#btnPOEdit").show();
        $("#btnPreview").prop("disabled", false);
      }
    }
  });
}

// $("#btnPreview").click(()=>{

//   const buyerPoNo = $("#buyerPOId").val();

//   $.ajax({
//     type: 'GET',
//     dataType: 'json',
//     url: './getBuyerPO',
//     data: {
//       buyerPoNo: buyerPoNo
//     },
//     success: function (data) {
//       if (data.buyerPO == "Something Wrong") {
//         dangerAlert("Something went wrong");
//       } else if (data.buyerPO == "duplicate") {
//         dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
//       } else {

//         let buyerPo = data.buyerPO;
//         console.log(buyerPo);
//         alert(buyerPoNo);
//       }
//     }
//   });
// });

function setBuyerPoItemDataForEdit(itemAutoId) {
 
	$.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getBuyerPOItem',
    data: {
      itemAutoId: itemAutoId
    },
    success: function (data) {
      if (data.result == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.result == "duplicate") {
        dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
      } else {

        let item = data.poItem;
        console.log(item);
        $("#itemAutoId").val(item.autoId);
        $("#customerOrder").val(item.customerOrder);
        $("#purchaseOrder").val(item.purchaseOrder);
        $("#shippingMark").val(item.shippingMark);
        $("#factory").val(item.factoryId).change();
        $("#color").val(item.colorId).change();

        sizeValueListForSet = item.sizeList;
        $("#sizeGroup").val(item.sizeGroupId).change();

        $("#itemAutoId").val(itemAutoId);
        $("#btnAdd").hide();
        $("#btnEdit").show();

        styleIdForSet = item.styleId;
        itemIdForSet = item.itemId;
        $("#buyerName").val(item.buyerId).change();


      }
    }
  });

}

function deleteBuyerPoItem(itemAutoId) {

  let buyerPoId = $("#buyerPOId").val();
  let userId = $("#userId").val();
  if (confirm("Are you sure to Delete this item")) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './deleteBuyerPoItem',
      data: {
        buyerPoNo: buyerPoId,
        itemAutoId: itemAutoId,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
        } else {
          drawItemTable(data.result);
          $('.modal').modal('hide');
          let buyerPoId = $("#buyerPOId").val();
          if(buyerPoId != "0"){
            $("#btnPOSubmit").hide();
            $("#btnPOEdit").show();
            $("#btnPreview").show();
          }
          
        }
      }
    });
  }

}

function reset() {
  let element = $(".alert");
  element.hide();
  $("#sizeGroup").val("0").change();
  $("#itemAutoId").val("0");
  $("#btnAdd").show();
  $("#btnEdit").hide();
}

function refreshAction() {
  location.reload();

}

function sizeLoadByGroup() {

  let groupId = $("#sizeGroup").val().trim();
  let child = "";
  let length = 0;
  if (groupId != "0") {
    length = sizesListByGroup['groupId' + groupId].length;
    for (let i = 0; i < length; i++) {
      //child += " <div class=\"list-group-item pt-0 pb-0 sizeNameList\"> <div class=\"form-group row mb-0\"><label for=\"sizeId" + sizesListByGroup['groupId' + groupId][i].sizeId + "\" class=\"col-md-6 col-form-label-sm pb-0 mb-0\" style=\"height:25px;\">" + sizesListByGroup['groupId' + groupId][i].sizeName + "</label><input type=\"number\" class=\"form-control-sm col-md-6\" id=\"sizeValue" + sizesListByGroup['groupId' + groupId][i].sizeId + "\" style=\"height:25px;\"></div></div>";
      child += `<div class="list-group-item pt-0 pb-0"> 
                  <div class="form-group row mb-0">
                    <label for="sizeValue${i}" class="col-md-6 col-form-label-sm pb-0 mb-0" style="height:25px;">${sizesListByGroup['groupId' + groupId][i].sizeName}</label>
                    <input type="number" class="form-control-sm col-md-6 sizeValue" id="sizeValue${i}" style="height:25px;">
                    <input type="hidden" id="sizeId${i}" value="${sizesListByGroup['groupId' + groupId][i].sizeId}">
                  </div>
                </div>`;
    }

  }
  $("#listGroup").html(child);

  $(".sizeValue").each((index,inputText) =>{
    inputText.addEventListener('keyup',(event)=>{
      if(event.keyCode == 13) {
        $("#sizeValue"+(index+1)).focus();
      }
    })
  });
  if (sizeValueListForSet.length > 0) {
    for (let i = 0; i < length; i++) {
      $("#sizeValue" + i).val(sizeValueListForSet[i].sizeQuantity);
    }
    sizeValueListForSet = [];
  }

}
function setData(unitId) {


  document.getElementById("unitId").value = unitId;
  document.getElementById("unitName").value = document.getElementById("unitName" + unitId).innerHTML;
  document.getElementById("unitValue").value = document.getElementById("unitValue" + unitId).innerHTML;
  $("#btnAdd").hide();
  $("#btnEdit").show();

}

function unitCmtFobTotalChange(autoId){

  let totalUnit = Number($("#totalUnit" + autoId).text());
  let unitCmt = Number($("#unitCmt" + autoId).val());
  let totalPrice = totalUnit * unitCmt;
  let unitFob = Number($("#unitFob" + autoId).val());
  let totalAmount = totalUnit * unitFob;


  $("#itemRow-"+autoId).addClass('changed');
  $("#totalPrice"+autoId).text(totalPrice.toFixed(2));
  $("#totalAmount"+autoId).text(totalAmount.toFixed(2))
}

function drawItemTable(dataList) {
  let length = dataList.length;
  sizeGroupId = "";
  let tables = "";
  let isClosingNeed = false;
  for (let i = 0; i < length; i++) {
    let item = dataList[i];
    console.log(item);
    if (sizeGroupId != item.sizeGroupId) {
      if (isClosingNeed) {
        tables += "</tbody></table> </div></div>";
      }
      sizeGroupId = item.sizeGroupId;
      tables += `<div class="row">
                        <div class="col-md-12 table-responsive" >
              <table class="table table-hover table-bordered table-sm mb-0 small-font">
              <thead class="no-wrap-text bg-light">
                <tr>
                  <th scope="col" class="min-width-150">Style</th>
                  <th scope="col" class="min-width-150">Item Name</th>
                  <th scope="col" class="min-width-150">Color</th>
                  <th scope="col">Customer Order</th>
                  <th scope="col">Purchase Order</th>
                  <th scope="col">Shipping Mark</th>
                  <th scope="col">Sizes Reg-Tall-N/A</th>`
      let sizeListLength = sizesListByGroup['groupId' + sizeGroupId].length;
      for (let j = 0; j < sizeListLength; j++) {
        tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + sizesListByGroup['groupId' + sizeGroupId][j].sizeName + "</th>";
      }
      tables += `<th scope="col">Total Units</th>
                  <th scope="col">Unit CMT</th>
                  <th scope="col">Total Price</th>
                  <th scope="col">Unit FOB</th>
                  <th scope="col">Total Price</th>
                  <th scope="col"><i class="fa fa-edit"></i></th>
                  <th scope="col"><i class="fa fa-trash"></i></th>
                </tr>
              </thead>
              <tbody id="dataList">`
      isClosingNeed = true;
    }
    tables += `<tr id='itemRow-${item.autoId}' class='dataRow notChanged' data-auto-id='${item.autoId}' data-purchase-order='${item.purchaseOrder}' data-style-id='${item.styleId}' data-item-id='${item.itemId}' data-customer-order='${item.customerOrder}' data-color-id='${item.colorId}' data-size-group-id='${item.sizeGroupId}'>
                  <td>${item.style}</td>
                  <td>${item.itemName}</td>
                  <td>${item.colorName}</td>
                  <td>${item.customerOrder}</td>
                  <td>${item.purchaseOrder}</td>
                  <td>${item.shippingMark}</td>
                  <td>${item.sizeReg}</td>`
    let sizeList = item.sizeList;
    let sizeListLength = sizeList.length;

    for (let j = 0; j < sizeListLength; j++) {

      tables += "<td>" + sizeList[j].sizeQuantity + "</td>"
    }
    tables += `<td class='totalUnit' id='totalUnit${item.autoId}'>${item.totalUnit}</td>
                <td class='unitCmt' ><input id='unitCmt${item.autoId}' class='form-control-sm min-width-60 max-width-100' type='number' value='${item.unitCmt}' onkeyup="unitCmtFobTotalChange('${item.autoId}')"></td>
                <td class='totalPrice' id='totalPrice${item.autoId}'>${item.totalPrice}</td>
                <td class='unitFob'><input id='unitFob${item.autoId}' class='form-control-sm min-width-60 max-width-100' value="${item.unitFob}" onkeyup="unitCmtFobTotalChange('${item.autoId}')"></td>
                <td class='totalAmount' id='totalAmount${item.autoId}'>${item.totalAmount}</td>
                <td><i class='fa fa-edit' onclick="setBuyerPoItemDataForEdit('${item.autoId}')" style='cursor : pointer;'> </i></td>
                <td><i class='fa fa-trash' onclick="deleteBuyerPoItem('${item.autoId}')" style='cursor : pointer;'> </i></td></tr>`;

  }
  tables += "</tbody></table> </div></div>";

  document.getElementById("tableList").innerHTML = tables;
}

function drawDataTable(data) {
  let rows = [];
  let length = data.length;

  for (let i = 0; i < length; i++) {
    rows.push(drawRowDataTable(data[i], i));
  }

  return rows;
}

function drawRowDataTable(rowData, c) {

  let row = $("<tr />")
  row.append($("<td>" + rowData.unitId + "</td>"));
  row.append($("<td id='unitName" + rowData.unitId + "'>" + rowData.unitName + "</td>"));
  row.append($("<td id='unitValue" + rowData.unitId + "'>" + rowData.unitValue + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.unitId + ")\"> </i></td>"));

  return row;
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

$(document).ready(function () {
  $("input:text").focus(function () { $(this).select(); });
});
$(document).ready(function () {
  $("input").focus(function () { $(this).select(); });
});
$(document).ready(function () {
  $("#search").on("keyup", function () {
    let value = $(this).val().toLowerCase();
    $("#poList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});



