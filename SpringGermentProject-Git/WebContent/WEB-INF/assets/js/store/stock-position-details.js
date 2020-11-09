

function loadStockItemPositionList(){
  const fromDate = $("#fromDate").val();
  const toDate = $("#toDate").val();
  const departmentId = $("#departmentId").val();

  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getStockItemPositionDetails',
    data: {
      fromDate: fromDate,
      toDate: toDate,
      departmentId : departmentId
    },
    success: function (data) {
      console.log(data.itemList);
      drawStockItemList(data.itemList)
    }
  });
  
}

function drawStockItemList(data) {
  const length = data.length;
  var tr_list="";
  $("#pendingInvoiceList").empty();
  
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    const id = i;
    tr_list=tr_list+"<tr id='row-" + id + "' >"
              +"<td id='purchaseOrder-"+id+"'>" + rowData.purchaseOrder + "</td>"
              +"<td id='styleNo-"+id+"'>" + rowData.styleNo + "</td>"
              +"<td id='itemName-"+id+"'>" + rowData.itemName + "</td>"
              +"<td id='itemColor-"+id+"'>" + rowData.itemColor + "</td>"
              +"<td id='stockItemName-"+id+"'>" + rowData.stockItemName + "</td>"
              +"<td id='stockItemColorName-"+id+"'>" + rowData.stockItemColorName + "</td>"
              +"<td id='rollSizeName-"+id+"'>" + rowData.sizeName + "</td>"
              +"<td id='unit-"+id+"'>" + rowData.unit + "</td>"
              +"<td id='openingBalance-"+id+"'>" + rowData.openingBalance + "</td>"
              +"<td id='receiveQty-"+id+"'>" + rowData.receiveQty + "</td>"
              +"<td id='qcPassedQty-"+id+"'>" + rowData.qcPassedQty + "</td>"
              +"<td id='returnQty-"+id+"'>" + rowData.returnQty + "</td>"
              +"<td id='issueQty-"+id+"'>" + rowData.issueQty + "</td>"
              +"<td id='issueReturnQty-"+id+"'>" + rowData.issueReturnQty + "</td>"
              +"<td id='transferInQty-"+id+"'>" + rowData.transferInQty + "</td>"
              +"<td id='transferOutQty-"+id+"'>" + rowData.transferOutQty + "</td>"
              +"<td id='closingBalance-"+id+"'>" + rowData.closingBalance + "</td>"
            +"</tr>";
  }
  $("#pendingInvoiceList").html(tr_list);
}

$(document).ready(function () {
    $("#purchaseOrderSearch , #styleNoSearch, #itemNameSearch,#fabricsItemSearch,#colorSearch,#rollSizeSearch").on("keyup", function () {
      const po = $("#purchaseOrderSearch").val().toLowerCase();
      const style = $("#styleNoSearch").val().toLowerCase();
      const item = $("#itemNameSearch").val().toLowerCase();
      const fabrics = $("#fabricsItemSearch").val().toLowerCase();
      const color = $("#colorSearch").val().toLowerCase();
      const rollId = $("#rollSizeSearch").val().toLowerCase();
  
      $("#pendingInvoiceList tr").filter(function () {
        const id = this.id.slice(4);
        
        if( ( ( !po.length || $("#purchaseOrder-"+id).text().toLowerCase().indexOf(po) > -1 ) && 
          ( !style.length || $("#styleNo-"+id).text().toLowerCase().indexOf(style) > -1 ) &&
          ( !item.length || $("#itemName-"+id).text().toLowerCase().indexOf(item) > -1 ) &&
          ( !fabrics.length || $("#stockItemName-"+id).text().toLowerCase().indexOf(fabrics) > -1 ) &&
          ( !color.length || $("#itemColor-"+id).text().toLowerCase().indexOf(color) > -1 || $("#stockItemColorName-"+id).text().toLowerCase().indexOf(color) > -1 )  &&
          ( !rollId.length || $("#rollSizeName-"+id).text().toLowerCase().indexOf(rollId) > -1 ) ) ){      
          $(this).show();
         }else{      
          $(this).hide();
         }
      });
    });
  });


  
var today = new Date();
document.getElementById("fromDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
document.getElementById("toDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);