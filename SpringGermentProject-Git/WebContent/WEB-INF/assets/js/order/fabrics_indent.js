
let styleIdForSet = 0;
let itemIdForSet = 0;
let itemColorIdForSet = 0;
let isFind = false;

let unitList = {};
window.onload = () => {
  document.title = "Fabrics Indent";

  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getUnitList',
    data: {},
    success: function (data) {
      unitList = {};

      $("#unit").empty();
      $("#unit").append("<option value='0'>Select Unit</option>");
      data.unitList.forEach(unit => {
        unitList[unit.unitId] = unit;
        $("#unit").append(`<option value='${unit.unitId}'>${unit.unitName}</option>`);
      });

      $('#unit').selectpicker('refresh');
      $('#unit').val('0').change();
    }
  });
}

$('#purchaseOrder').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {
  if ($("#purchaseOrder").val().length > 0) {
    let poList = '';
    $("#purchaseOrder").val().forEach(po => {
      poList += `'${po}',`;
    });
    poList = poList.slice(0, -1);
    let selectedStyleId = $("#styleNo").val();
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getStyleListByMultiplePurchaseOrder',
      data: {
        purchaseOrders: poList
      },
      success: function (data) {
        let options = "";

        let styleList = data.styleList;

        length = styleList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
        };
        $("#styleNo").html(options);
        $('#styleNo').selectpicker('refresh');
        $("#styleNo").selectpicker('val', selectedStyleId).change();

      }
    });
  }
});

$('#styleNo').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {
  if ($("#styleNo").val().length > 0) {
    let styleIdList = '';
    $("#styleNo").val().forEach(id => {
      styleIdList += `'${id}',`;
    });
    styleIdList = styleIdList.slice(0, -1);

    if ($("#purchaseOrder").val().length > 0) {

      let poList = '';
      $("#purchaseOrder").val().forEach(id => {
        poList += `'${id}',`;
      });
      poList = poList.slice(0, -1);
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getColorAndShippingListByMultipleStyleId',
        data: {
          purchaseOrders: poList,
          styleIdList: styleIdList
        },
        success: function (data) {
          let options = "";
          let colorList = data.colorList;
          length = colorList.length;
          for (let i = 0; i < length; i++) {
            options += "<option value='" + colorList[i].colorId + "'>" + colorList[i].colorName + "</option>";
          };
          $("#itemColor").html(options);
          $('#itemColor').selectpicker('refresh');

          /*
          options = "";
          let shippingMarkList = data.shippingMarkList;
          length = shippingMarkList.length;
          for (let i = 0; i < length; i++) {
            options += "<option value='" + shippingMarkList[i] + "'>" + shippingMarkList[i] + "</option>";
          };
          $("#shippingMark").html(options);
          $('#shippingMark').selectpicker('refresh');
          */
        }
      });
    } else {
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getPurchaseOrderByMultipleStyleId',
        data: {
          styleIdList: styleIdList
        },
        success: function (data) {
          let options = "";
          let buyerPoList = data.buyerPOList;
          let length = buyerPoList.length;
          for (let i = 0; i < length; i++) {
            options += "<option value='" + buyerPoList[i].name + "'>" + buyerPoList[i].name + "</option>";
          };

          $("#purchaseOrder").html(options);
          $('#purchaseOrder').selectpicker('refresh');

        }
      });
    }

    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getItemListByMultipleStyleId',
      data: {
        styleIdList: styleIdList
      },
      success: function (data) {
        let options = "";
        let itemList = data.itemList;
        let length = itemList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
        };

        $("#itemName").html(options);
        $("#itemName").selectpicker('refresh');
        $('#itemName').selectpicker('selectAll');

      }
    });
  }
});
/*
function styleItemWiseColorLoad() {
  let styleId = $("#styleNo").val();
  let itemId = $("#itemName").val();

  if (styleId != 0 && itemId != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getStyleItemWiseColor',
      data: {
        styleId: styleId,
        itemId: itemId
      },
      success: function (data) {

        let colorList = data.colorList;
        let options = "<option value='0' selected>Select Item Color</option>";
        let length = colorList.length;
        for (let i = 0; i < length; i++) {
          options += "<option value='" + colorList[i].colorId + "'>" + colorList[i].colorName + "</option>";
        };
        document.getElementById("itemColor").innerHTML = options;
        $('.selectpicker').selectpicker('refresh');
        $('#itemColor').val(itemColorIdForSet).change();
        itemColorIdForSet = 0;
      }
    });
  } else {
    let options = "<option value='0' selected>Select Item Color</option>";
    $("#itemColor").html(options);
    $('#itemColor').selectpicker('refresh');
    $('#itemColor').val(0).change();
    itemColorIdForSet = 0;
  }
}
*/
$('#itemColor').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {

  let poList = '';
  $("#purchaseOrder").val().forEach(id => {
    poList += `'${id}',`;
  });
  poList = poList.slice(0, -1);

  let styleIdList = '';
  $("#styleNo").val().forEach(id => {
    styleIdList += `'${id}',`;
  });
  styleIdList = styleIdList.slice(0, -1);

  let itemIdList = '';
  $("#itemName").val().forEach(id => {
    itemIdList += `'${id}',`;
  });
  itemIdList = itemIdList.slice(0, -1);

  let colorsId = '';
  $("#itemColor").val().forEach(id => {
    colorsId += `'${id}',`;
  });
  colorsId = colorsId.slice(0, -1);

  if (colorsId.length > 0) {

    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getOrderQtyByMultipleId',
      data: {
        purchaseOrder: poList,
        styleId: styleIdList,
        itemId: itemIdList,
        colorId: colorsId
      },
      success: function (data) {
        $("#quantity").val(data.orderQuantity);
        $("#dozen").val(data.dozenQuantity.toFixed(2));
        totalQuantityCalculate();
      }
    });
  } else {
    $("#quantity").val("0");
    $("#dozen").val("0");
    totalQuantityCalculate();
  }
});

function setOrderQtyByPOStyleItemColor() {
  let colorId = $("#itemColor").val();
  if (colorId != 0) {
    let purchaseOrder = $("#purchaseOrder option:selected").text();
    let styleId = $("#styleNo").val();
    let itemId = $("#itemName").val();

    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getOrderQtyByPOStyleItemAndColor',
      data: {
        purchaseOrder: purchaseOrder,
        styleId: styleId,
        itemId: itemId,
        colorId: colorId
      },
      success: function (data) {
        $("#quantity").val(data.orderQuantity);
        $("#dozen").val(data.dozenQuantity.toFixed(2));
        totalQuantityCalculate();
      }
    });
  } else {
    $("#quantity").val("0");
    $("#dozen").val("0");
  }
  totalQuantityCalculate();
}

function addAction() {

  let purchaseOrder = $("#purchaseOrder").val();
  let styleId = $("#styleNo").val();
  let styleNo = $("#styleNo option:selected").text();
  let itemId = $("#itemName").val();
  let itemColorId = $("#itemColor").val();
  let itemColors = $("#itemColor option:selected").text();

  let fabricsId = $("#fabricsItem").val();
  let fabricsName = $("#fabricsItem option:selected").text();
  let consumption = $("#consumption").val() == "" ? "0" : $("#consumption").val();
  let quantity = $("#quantity").val() == "" ? 0 : $("#quantity").val();
  let dozenQuantity = $("#dozen").val() == "" ? 0 : $("#dozen").val();
  let inPercent = $("#inPercent").val() == "" ? 0 : $("#inPercent").val();
  let percentQuantity = $("#percentQuantity").val() == "" ? 0 : $("#percentQuantity").val();
  let totalQuantity = $("#total").val() == "" ? 0 : $("#total").val();
  let unitId = $("#unit").val();
  let unit = $("#unit option:selected").text();
  let width = $("#width").val() == "" ? 0 : $("#width").val();
  let yard = $("#yard").val() == "" ? "0" : $("#yard").val();
  let gsm = $("#gsm").val() == "" ? "0" : $("#gsm").val();
  let grandQuantity = $("#grandQuantity").val() == "" ? "0" : $("#grandQuantity").val();
  let fabricsColorId = $("#fabricsColor").val();
  let fabricsColors = $("#fabricsColor option:selected").text();
  let brandId = $("#brand").val();




  if (styleId != 0) {
    if (itemId != 0) {
      if (itemColorId != 0) {
        if (fabricsId != 0) {
          if (quantity != 0) {
            if (dozen != 0) {
              if (consumption != 0) {
                if (unitId != 0) {
                  if (fabricsColorId != 0) {

                    let indentDataList = $("#dataList tr");
                    let length = indentDataList.length;
                    let listRowId = 0;
                    if (length > 0) listRowId = indentDataList[length - 1].id.slice(4);

                    let row = `<tr id='row-${++listRowId}' class='newIndentRow' data-item-type='newIndent' data-style-id='${styleId}' data-item-id='${itemId}' data-item-color-id='${itemColorId}' 
                    data-fabrics-id='${fabricsId}' data-quantity='${quantity}' data-dozen-qty='${dozenQuantity}' data-in-percent='${inPercent}' data-total-quantity='${totalQuantity}'
                    data-unit-id='${unitId}' data-width='${width}' data-yard='${yard}' data-gsm='${gsm}' data-fabrics-color-id='${fabricsColorId}' data-brand-id='${brandId}'>
                                  <td id='purchaseOrder-${listRowId}'>${purchaseOrder}</td>
                                  <td id='styleNo-${listRowId}'>${styleNo}</td>
                                  <td id='itemColor-${listRowId}'>${itemColors}</td>
                                  <td id='fabricName-${listRowId}'>${fabricsName}</td>
                                  <td id='fabricsColor-${listRowId}'>${fabricsColors}</td>
                                  <td id='dozenQty-${listRowId}'>${dozenQuantity}</td>
                                  <td id='consumption-${listRowId}'>${consumption}</td>
                                  <td id='percentQty-${listRowId}'>${percentQuantity}</td>
                                  <td id='unit-${listRowId}'>${unit}</td>
                                  <td id='totalQty-${listRowId}'>${grandQuantity}</td>
                                  <td><i class='fa fa-edit' onclick="viewFabricsIndent('${listRowId}','newIndent')" style='cursor:pointer;'> </i></td>
                                  <td><i class='fa fa-trash' onclick="deleteFabricsIndent('${listRowId}','newIndent')" style='cursor:pointer;'> </i></td>
                                </tr>`;

                    $("#dataList").append(row);

                  } else {
                    alert("Please Select Fabrics Color....");
                    $("#fabricsColor").focus();
                  }
                } else {
                  alert("Please Select Fabrics Unit....");
                  $("#unit").focus();
                }
              } else {
                alert("Consumption is empty ... Please Enter Consumption");
                $("#consumption").focus();
              }
            } else {
              alert("Dozen Quantity is empty ... Please Enter dozen quantity");
              $("#dozen").focus();
            }

          } else {
            alert("Quantity is empty ... Please Enter Quantity");
            $("#quantity").focus();
          }
        } else {
          alert("Fabrics item Not Selected... Please Select any Fabrics item");
          $("#fabricsItem").focus();
        }
      } else {
        alert("Color not selected... Please Select Color Name");
        $("#itemColor").focus();
      }
    } else {
      alert("Item Name not selected... Please Select Item Name");
      $("#itemName").focus();
    }
  } else {
    alert("Style No not selected... Please Select Style No");
    $("#styleNo").focus();
  }

}

function confirmAction() {


  let userId = $("#userId").val();
  let fabricsIndentId = $("#fabricsIndentId").val();

  let rowList = $("#dataList tr");
  let length = rowList.length;


  if (length > 0) {
    if (confirm("Are you Confirm to Save This Accessories Indent?")) {
      newIndentList = $("tr.newIndentRow");

      let fabricsItems = {};
      fabricsItems['list'] = [];

      newIndentList.each((index, indentRow) => {
        let id = indentRow.id.slice(4);

        const indent = {
          purchaseOrder: $("#purchaseOrder-" + id).text(),
          buyerOrderId: $("#purchaseOrder-" + id).text(),
          styleId: indentRow.getAttribute('data-style-id'),
          itemId: indentRow.getAttribute('data-item-id'),
          itemColorId: indentRow.getAttribute('data-item-color-id'),
          fabricsId: indentRow.getAttribute('data-fabrics-id'),
          fabricsColorId: indentRow.getAttribute('data-fabrics-color-id'),
          brandId: indentRow.getAttribute('data-brand-id'),
          orderQty: indentRow.getAttribute('data-quantity'),
          dozenQty: indentRow.getAttribute('data-dozen-qty'),
          consumption: $("#consumption-" + id).text(),
          inPercent: indentRow.getAttribute('data-in-percent'),
          percentQty: $("#percentQty-" + id).text(),
          totalQty: indentRow.getAttribute('data-total-quantity'),
          unitId: indentRow.getAttribute('data-unit-id'),
          width: indentRow.getAttribute('data-width'),
          yard: indentRow.getAttribute('data-yard'),
          gsm: indentRow.getAttribute('data-gsm'),
          grandQty: $("#totalQty-" + id).text(),
          userId: userId
        }

        fabricsItems.list.push(indent);
      })

      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './confirmFabricsIndent',
        data: {
          fabricsIndentId: fabricsIndentId,
          fabricsItems: JSON.stringify(fabricsItems),
        },
        success: function (data) {
          if (data.result != 'something wrong') {
            $("#fabricsIndentId").val(data.result);
            $("#indentId").text(data.result);
            alert("Accessories Save Successfully;")
          } else {
            alert("Incomplete...Something Wrong");
          }

        },
        error: function (jqXHR, textStatus, errorThrown) {
          //alert("Server Error");
          if (jqXHR.status === 0) {
            alert('Not connect.\n Verify Network.');
          } else if (jqXHR.status == 404) {
            alert('Requested page not found.');
          } else if (jqXHR.status == 500) {
            alert('Internal Server Error.');
          } else if (errorThrown === 'parsererror') {
            alert('Requested JSON parse failed');
          } else if (errorThrown === 'timeout') {
            alert('Time out error');
          } else if (errorThrown === 'abort') {
            alert('Ajax request aborted ');
          } else {
            alert('Uncaught Error.\n' + jqXHR.responseText);
          }

        }
      });

    }
  } else {
    warningAlert("You have not added any Fabrics... Please Add any Fabrics");
  }

}

function saveAction() {

  let autoId = $("#fabricsIndentAutoId").val();

  let buyerOrderId = $("#purchaseOrder").val();

  let purchaseOrder = $("#purchaseOrder option:selected").text();
  let styleId = $("#styleNo").val();
  let itemId = $("#itemName").val();
  let itemColorId = $("#itemColor").val();

  let fabricsId = $("#fabricsItem").val();
  let consumption = $("#consumption").val() == "" ? "0" : $("#consumption").val();
  let quantity = $("#quantity").val() == "" ? 0 : $("#quantity").val();
  let dozenQuantity = $("#dozen").val() == "" ? 0 : $("#dozen").val();
  let inPercent = $("#inPercent").val() == "" ? 0 : $("#inPercent").val();
  let percentQuantity = $("#percentQuantity").val() == "" ? 0 : $("#percentQuantity").val();
  let totalQuantity = $("#total").val() == "" ? 0 : $("#total").val();
  let unitId = $("#unit").val();
  let width = $("#width").val() == "" ? 0 : $("#width").val();
  let yard = $("#yard").val() == "" ? "0" : $("#yard").val();
  let gsm = $("#gsm").val() == "" ? "0" : $("#gsm").val();
  let grandQuantity = $("#grandQuantity").val() == "" ? "0" : $("#grandQuantity").val();
  let fabricsColorId = $("#fabricsColor").val();
  let brandId = $("#brand").val();
  let userId = $("#userId").val();



  if (styleId != 0) {
    if (itemId != 0) {
      if (itemColorId != 0) {
        if (fabricsId != 0) {
          if (quantity != 0) {
            if (dozen != 0) {
              if (consumption != 0) {
                if (unitId != 0) {
                  if (fabricsColorId != 0) {
                    if (confirm("Are you Sure to Save this Fabrics Indent")) {
                      $.ajax({
                        type: 'POST',
                        dataType: 'json',
                        url: './saveFabricsIndent',
                        data: {
                          autoId: autoId,
                          purchaseOrder: purchaseOrder,
                          buyerOrderId: buyerOrderId,
                          styleId: styleId,
                          itemId: itemId,
                          itemColorId: itemColorId,
                          fabricsId: fabricsId,
                          qty: quantity,
                          dozenQty: dozenQuantity,
                          consumption: consumption,
                          inPercent: inPercent,
                          percentQty: percentQuantity,
                          totalQty: totalQuantity,
                          unitId: unitId,
                          width: width,
                          yard: yard,
                          gsm: gsm,
                          grandQty: grandQuantity,
                          fabricsColorId: fabricsColorId,
                          brandId: brandId,
                          userId: userId
                        },
                        success: function (data) {
                          if (data.result == "Something Wrong") {
                            dangerAlert("Something went wrong");
                          } else if (data.result == "duplicate") {
                            dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                          } else {

                            $("#dataList").empty();
                            $("#dataList").append(drawDataTable(data.result));
                            successAlert("Fabrics Indent Item Save Successfully");
                          }
                        }
                      });

                    }
                  } else {
                    alert("Please Select Fabrics Color....");
                    $("#fabricsColor").focus();
                  }
                } else {
                  alert("Please Select Fabrics Unit....");
                  $("#unit").focus();
                }
              } else {
                alert("Consumption is empty ... Please Enter Consumption");
                $("#consumption").focus();
              }
            } else {
              alert("Dozen Quantity is empty ... Please Enter dozen quantity");
              $("#dozen").focus();
            }

          } else {
            alert("Quantity is empty ... Please Enter Quantity");
            $("#quantity").focus();
          }
        } else {
          alert("Fabrics item Not Selected... Please Select any Fabrics item");
          $("#fabricsItem").focus();
        }
      } else {
        alert("Color not selected... Please Select Color Name");
        $("#itemColor").focus();
      }
    } else {
      alert("Item Name not selected... Please Select Item Name");
      $("#itemName").focus();
    }
  } else {
    alert("Style No not selected... Please Select Style No");
    $("#styleNo").focus();
  }

}


function editAction() {

  let indentType = $("#indentType").val();

  let autoId = $("#fabricsIndentAutoId").val();
  let indentId = $("#fabricsIndentId").val();
  let purchaseOrder = $("#purchaseOrder").val().toString();
  let styleId = $("#styleNo").val().toString();
  let styleNo = $("#styleNo option:selected").text();
  let itemId = $("#itemName").val().toString();
  let itemColorId = $("#itemColor").val().toString();
  let itemColors = $("#itemColor option:selected").text();

  let fabricsId = $("#fabricsItem").val();
  let fabricsName = $("#fabricsItem option:selected").text();
  let consumption = $("#consumption").val() == "" ? "0" : $("#consumption").val();
  let quantity = $("#quantity").val() == "" ? 0 : $("#quantity").val();
  let dozenQuantity = $("#dozen").val() == "" ? 0 : $("#dozen").val();
  let inPercent = $("#inPercent").val() == "" ? 0 : $("#inPercent").val();
  let percentQuantity = $("#percentQuantity").val() == "" ? 0 : $("#percentQuantity").val();
  let totalQuantity = $("#total").val() == "" ? 0 : $("#total").val();
  let unitId = $("#unit").val();
  let unit = $("#unit option:selected").text();
  let width = $("#width").val() == "" ? 0 : $("#width").val();
  let yard = $("#yard").val() == "" ? "0" : $("#yard").val();
  let gsm = $("#gsm").val() == "" ? "0" : $("#gsm").val();
  let grandQuantity = $("#grandQuantity").val() == "" ? "0" : $("#grandQuantity").val();
  let fabricsColorId = $("#fabricsColor").val();
  let fabricsColors = $("#fabricsColor option:selected").text();
  let brandId = $("#brand").val();
  let userId = $("#userId").val();



  if (styleId != 0) {
    if (itemId != 0) {
      if (itemColorId != 0) {
        if (fabricsId != 0) {
          if (quantity != 0) {
            if (dozen != 0) {
              if (consumption != 0) {
                if (confirm("Are you Sure to Edit this Fabrics Indent")) {
                  if (indentType == 'newIndent') {
                    let row = $("#row-" + autoId);

                    row.attr('data-style-id', styleId);
                    row.attr('data-item-id', itemId);
                    row.attr('data-item-color-id', itemColorId);
                    row.attr('data-fabrics-id', fabricsId);
                    row.attr('data-quantity', quantity);
                    row.attr('data-dozen-qty', dozenQuantity);
                    row.attr('data-in-percent', inPercent);
                    row.attr('data-total-quantity', totalQuantity);
                    row.attr('data-unit-id', unitId);
                    row.attr('data-width', width);
                    row.attr('data-yard', yard);
                    row.attr('data-gsm', gsm);
                    row.attr('data-fabrics-color-id', fabricsColorId);
                    row.attr('data-brand-id', brandId);

                    $("#purchaseOrder-" + autoId).text(purchaseOrder);
                    $("#styleNo-" + autoId).text(styleNo);
                    $("#itemColor-" + autoId).text(itemColors);
                    $("#fabricsName-" + autoId).text(fabricsName);
                    $("#fabricsColor-" + autoId).text(fabricsColors);
                    $("#dozenQty-" + autoId).text(dozenQuantity);
                    $("#consumption-" + autoId).text(consumption);
                    $("#percentQty-" + autoId).text(percentQuantity);
                    $("#unit-" + autoId).text(unit);
                    $("#totalQty-" + autoId).text(grandQuantity);

                    successAlert("Indent Edit Successfully");
                    $("#btnAdd").show();
                    $("#btnEdit").hide();
                  } else {
                    console.log(autoId, indentId, itemId, itemColorId, fabricsId, quantity, consumption)
                    $.ajax({
                      type: 'POST',
                      dataType: 'json',
                      url: './editFabricsIndent',
                      data: {
                        autoId: autoId,
                        indentId: indentId,
                        purchaseOrder: purchaseOrder,
                        styleId: styleId,
                        itemId: itemId,
                        itemColorId: itemColorId,
                        fabricsId: fabricsId,
                        qty: quantity,
                        dozenQty: dozenQuantity,
                        consumption: consumption,
                        inPercent: inPercent,
                        percentQty: percentQuantity,
                        totalQty: totalQuantity,
                        unitId: unitId,
                        width: width,
                        yard: yard,
                        gsm: gsm,
                        grandQty: grandQuantity,
                        fabricsColorId: fabricsColorId,
                        brandId: brandId,
                        userId: userId
                      },
                      success: function (data) {
                        if (data.result == "Something Wrong") {
                          dangerAlert("Something went wrong");
                        } else if (data.result == "duplicate") {
                          dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                        } else {

                          if (data.result != 'Something Wrong') {

                            let row = $("#row-" + autoId);

                            row.attr('data-style-id', styleId);
                            row.attr('data-item-id', itemId);
                            row.attr('data-item-color-id', itemColorId);
                            row.attr('data-fabrics-id', fabricsId);
                            row.attr('data-quantity', quantity);
                            row.attr('data-dozen-qty', dozenQuantity);
                            row.attr('data-in-percent', inPercent);
                            row.attr('data-total-quantity', totalQuantity);
                            row.attr('data-unit-id', unitId);
                            row.attr('data-width', width);
                            row.attr('data-yard', yard);
                            row.attr('data-gsm', gsm);
                            row.attr('data-fabrics-color-id', fabricsColorId);
                            row.attr('data-brand-id', brandId);

                            $("#purchaseOrder-" + autoId).text(purchaseOrder);
                            $("#styleNo-" + autoId).text(styleNo);
                            $("#itemColor-" + autoId).text(itemColors);
                            $("#fabricsName-" + autoId).text(fabricsName);
                            $("#fabricsColor-" + autoId).text(fabricsColors);
                            $("#dozenQty-" + autoId).text(dozenQuantity);
                            $("#consumption-" + autoId).text(consumption);
                            $("#percentQty-" + autoId).text(percentQuantity);
                            $("#unit-" + autoId).text(unit);
                            $("#totalQty-" + autoId).text(grandQuantity);

                            successAlert("Indent Edit Successfully");
                            $("#btnAdd").show();
                            $("#btnEdit").hide();
                          } else {
                            warningAlert("Something Wrong");
                          }

                        }
                      }
                    });
                  }
                }
              } else {
                alert("Consumption is empty ... Please Enter Consumption");
                $("#consumption").focus();
              }
            } else {
              alert("Dozen Quantity is empty ... Please Enter dozen quantity");
              $("#dozen").focus();
            }

          } else {
            alert("Quantity is empty ... Please Enter Quantity");
            $("#quantity").focus();
          }
        } else {
          warningAlert("Fabrics item Not Selected... Please Select any Fabrics item");
          $("#fabricsItem").focus();
        }
      } else {
        alert("Color not selected... Please Select Color Name");
        $("#itemColor").focus();
      }
    } else {
      alert("Item Name not selected... Please Select Item Name");
      $("#itemName").focus();
    }
  } else {
    alert("Style No not selected... Please Select Style No");
    $("#styleNo").focus();
  }
}

function deleteFabricsIndent(autoId, indentType) {
  let indentId = $("#fabricsIndentId").val();

  if (confirm("Are You Sure To Delete this fabrics Indent?")) {
    if (indentType == 'newIndent') {
      $("#row-" + autoId).remove();
    } else {
      $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './deleteFabricsIndent',
        data: {
          autoId: autoId,
          indentId: indentId
        },
        success: function (data) {
          if (data.result) {
            successAlert("Delete Successfully... ")
            $("#row-" + autoId).remove();
          } else {
            warningAlert("Something Wrong..");
          }
        }
      });
    }
  }

}

function fieldRefresh() {
  $("#purchaseOrder").selectpicker('deselectAll');
  $("#styleNo").selectpicker('deselectAll');
  $("#itemName").selectpicker('deselectAll');
  $("#itemColor").selectpicker('deselectAll');
  $("#fabricsItem").val(0).change();
  $("#consumption").val(0);
  $("#dozen").val(0);
  $("#quantity").val(0);
  $("#inPercent").val(0);
  $("#total").val(0);
  $("#unit").val(0).change();
  $("#width").val(0);
  $("#yard").val(0);
  $("#gsm").val(0);
  $("#grandQuantity").val(0);
  $("#fabricsColor").val(0).change();
  $("#brand").val(0).change();
  $("#btnAdd").show();
  $("#btnEdit").hide();

}

function refreshAction() {
  location.reload();
}



function fabricIndentReport() {
  let indentId = $("#fabricsIndentId").val();
  if(indentId != 'New'){
    let url = "fabricsIndentReportView/"+indentId;
    window.open(url, '_blank');
  } 
}

function searchFabricsIndent(indentId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchFabricsIndent',
    data: {
      indentId: indentId
    },
    success: function (data) {
      $("#dataList").empty();
      data.fabricsIndentList.forEach((indent) => {
        console.log(indent);
        let row = `<tr id='row-${indent.autoId}' class='oldIndentRow' data-item-type='oldIndent' data-style-id='${indent.styleId}' data-item-id='${indent.itemId}' data-item-color-id='${indent.itemColorId}' 
                    data-fabrics-id='${indent.fabricsId}' data-quantity='${indent.qty}' data-dozen-qty='${indent.dozenQty}' data-in-percent='${indent.inPercent}' data-total-quantity='${indent.totalQty}'
                    data-unit-id='${indent.unitId}' data-width='${indent.width}' data-yard='${indent.yard}' data-gsm='${indent.gsm}' data-fabrics-color-id='${indent.fabricsColorId}' data-brand-id='${indent.brandId}'>
                    <td id='purchaseOrder-${indent.autoId}'>${indent.purchaseOrder}</td>
                    <td id='styleNo-${indent.autoId}'>${indent.styleName}</td>
                    <td id='itemColor-${indent.autoId}'>${indent.itemColorName}</td>
                    <td id='fabricsName-${indent.autoId}'>${indent.fabricsName}</td>
                    <td id='fabricsColor-${indent.autoId}'>${indent.fabricsColor}</td>
                    <td id='dozenQty-${indent.autoId}'>${indent.dozenQty}</td>
                    <td id='consumption-${indent.autoId}'>${indent.consumption}</td>
                    <td id='percentQty-${indent.autoId}'>${indent.percentQty}</td>
                    <td id='unit-${indent.autoId}'>${indent.unit}</td>
                    <td id='totalQty-${indent.autoId}'>${indent.grandQty}</td>
                    <td><i class='fa fa-edit' onclick="viewFabricsIndent('${indent.autoId}','oldIndent')" style='cursor:pointer;'> </i></td>
                    <td><i class='fa fa-trash' onclick="deleteFabricsIndent('${indent.autoId}','oldIndent')" style='cursor:pointer;'> </i></td>
                  </tr>`;

        $("#dataList").append(row);
      });
      $("#indentId").text(data.fabricsIndentList[0].indentId);
      $("#fabricsIndentId").text(data.fabricsIndentList[0].indentId);
      $("#exampleModal").modal('hide');
    }
  });
}

function totalQuantityCalculate() {
  let dozenQuantity = $("#dozen").val() == "" ? 0 : Number($("#dozen").val());
  let consumption = $("#consumption").val() == "" ? 0 : Number($("#consumption").val());
  let inPercent = $("#inPercent").val() == "" ? 0 : Number($("#inPercent").val());

  let totalQuantity = dozenQuantity * consumption;
  let percentQuantity = (totalQuantity * inPercent) / 100;

  totalQuantity += percentQuantity;

  $("#percentQuantity").val(percentQuantity.toFixed(2));
  $("#total").val(totalQuantity.toFixed(2));

  let unit = $("#unit option:selected").text();
  let unitId = $("#unit").val();

  if (unit.toLowerCase() == "kg") {
    $("#gsm").val("0");
    $("#yard").val("0");
    $("#width").val("0");
    $("#width").prop("disabled", false);
    $("#yard").prop("disabled", false);
    $("#yard").val($("#consumption").val());
    //$("#gsm").prop("disabled", false);
    //$("#width").prop("disabled", false);
  } else {
    $("#gsm").val("0");
    $("#yard").val("0");
    $("#width").val("0");
    $("#width").prop("disabled", true);
    $("#yard").prop("disabled", true);
    $("#yard").val("0");
    //$("#gsm").prop("disabled", true);
    //$("#width").prop("disabled", true);
  }

  let unitValue = parseFloat(unitId == '0' ? "1" : unitList[unitId].unitValue);
  unitValue = unitValue == 0 ? 1 : unitValue;

  //let totalQuantity = $("#total").val() == "" ? 0 : $("#total").val();
  let grandQty = totalQuantity / unitValue;
  $("#grandQuantity").val(grandQty.toFixed(2));
}

function gsmCalculation() {
  let width = $("#width").val() == "" ? 0 : $("#width").val();
  let yard = $("#yard").val() == "" ? 0 : $("#yard").val();
  let gsm = $("#gsm").val() == "" ? 0 : $("#gsm").val();
  let grandQty = (width * yard * gsm * 36) / 1550000;
  $("#grandQuantity").val(grandQty.toFixed(2));
}
function colorChangeAction() {
  $("#fabricsColor").val($("#itemColor").val()).change();
}


function viewFabricsIndent(autoId, indentType) {

  $("#indentType").val(indentType);
  let row = $("#row-" + autoId);

  $("#fabricsIndentAutoId").val(autoId);
  $("#fabricsItem").val(row.attr('data-fabrics-id')).change();
  $("#consumption").val($("#consumption-" + autoId).text());
  $("#quantity").val(row.attr('data-quantity'));
  $("#dozen").val(row.attr('data-dozen-qty'));
  $("#inPercent").val(row.attr('data-in-percent'));
  $("#percentQuantity").val($("#percentQty-" + autoId).text());
  $("#total").val(row.attr('data-total-quantity'));
  $("#unit").val(row.attr('data-unit-id')).change();
  $("#width").val(row.attr('data-width'));
  $("#yard").val(row.attr('data-yard'));
  $("#gsm").val(row.attr('data-gsm'));
  $("#grandQuantity").val($("#totalQty-" + autoId).text());
  $("#fabricsColor").val(row.attr('data-fabrics-color-id')).change();
  $("#brand").val(row.attr('data-brand-id')).change();
  $("#purchaseOrder").val($("#purchaseOrder-" + autoId).text()).change();
  console.log(autoId, row.attr('data-fabrics-id'), $("#consumption-" + autoId).text(), row.attr('data-quantity'), $("#dozen-" + autoId).text())
  console.log(row.attr('data-in-percent'), $("#percentQty-" + autoId).text(), row.attr('data-total-quantity'))

  $("#btnAdd").hide();
  $("#btnEdit").show();

  // $.ajax({
  //   type: 'GET',
  //   dataType: 'json',
  //   url: './getFabricsIndent',
  //   data: {
  //     autoId: autoId
  //   },
  //   success: function (data) {
  //     if (data.fabricsIndent == "Something Wrong") {
  //       dangerAlert("Something went wrong");
  //     } else if (data.fabricsIndent == "duplicate") {
  //       dangerAlert("Duplicate Item Name..This Item Name Already Exist")
  //     } else {

  //       isFind = true;
  //       let indent = data.fabricsIndent;
  //       styleIdForSet = indent.styleId;
  //       itemIdForSet = indent.itemId;
  //       itemColorIdForSet = indent.itemColorId;
  //       $("#fabricsIndentAutoId").val(indent.autoId);
  //       $("#fabricsItem").val(indent.fabricsId).change();
  //       $("#consumption").val(indent.consumption);
  //       $("#quantity").val(indent.qty);
  //       $("#dozen").val(indent.dozenQty);
  //       $("#inPercent").val(indent.inPercent);
  //       $("#percentQuantity").val(indent.percentQty);
  //       $("#total").val(indent.totalQty);
  //       $("#unit").val(indent.unitId).change();
  //       $("#width").val(indent.width);
  //       $("#yard").val(indent.yard);
  //       $("#gsm").val(indent.gsm);
  //       $("#grandQuantity").val(indent.grandQty.toFixed(2));
  //       $("#fabricsColor").val(indent.fabricsColorId).change();
  //       $("#brand").val(indent.brandId).change();
  //       $("#purchaseOrder").val(indent.purchaseOrder).change();
  //       $("#btnAdd").hide();
  //       $("#btnEdit").show();
  //     }
  //   }
  // });
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
  row.append($("<td>" + rowData.purchaseOrder + "</td>"));
  row.append($("<td>" + rowData.styleName + "</td>"));
  row.append($("<td>" + rowData.itemName + "</td>"));
  row.append($("<td>" + rowData.itemColorName + "</td>"));
  row.append($("<td>" + rowData.fabricsName + "</td>"));
  row.append($("<td>" + rowData.dozenQty + "</td>"));
  row.append($("<td>" + rowData.consumption + "</td>"));
  row.append($("<td>" + rowData.percentQty + "</td>"));
  row.append($("<td>" + rowData.totalQty + "</td>"));
  row.append($("<td>" + rowData.unit + "</td>"));
  row.append($("<td ><i class='fa fa-info-circle' onclick='viewFabricsIndent(" + rowData.autoId + ")' style='cursor:pointer;'> </i></td>"));


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
    $("#dataList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});



