let col9List = [];

function searchMasterLc(masterLCNo, buyerId, amendmentNo) {
  col9List = [];
  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './searchMasterLCForPassBook',
    data: {
      masterLCNo: masterLCNo
    },
    success: function (data) {
      console.log("Data", data);
      masterLCNo = data.masterLCNo;
      let date = data.date;
      let totalQty = data.totalQty;
      let totalValue = data.totalValue;
      let udInfo = data.udInfo;
      let udAmendmentList = data.udAmendmentList;
      let importLCList = data.importLCList;
      let importLCItemList = data.importLCItemList;
      let importBillEntryList = data.importBillEntryList;
      let exportInvoiceList = data.exportInvoiceList;
      let tempItem = {};
      let row = "";
      let index = 0;

      let col1summary = "";
      let col2summary = "";
      let col3summary = 0;
      let col4summary = `$${udInfo.udValue} <br/>QTY:${parseFloat(udInfo.udQuantity).toFixed(2)} PCS`;
      let col5summary = "";
      let col6summary = {};
      let col7summary = 0;
      let col8summary = "";
      let col9summary = {};
      let col10summary = "";
      let col11PcsSummary = 0;
      let col11CtnSummary = 0;
      let col12summary = 0;
      let col13summary = "";
      let col14summary = 0;
      let col15summary = 0;
      let col16summary = "";
      let col17summary = "";
      let col18summary = "";
      let col19summary = "";

      while (index < udAmendmentList.length || index < importLCList.length || index < importLCItemList.length || index < importBillEntryList.length || index < exportInvoiceList.length) {
        if (index == 0) {
          row += `<tr id=${index}>
                <td id="col1-${index}">${index + 1}</td>
                <td id="col2-${index}">${masterLCNo} <br/>DATE: ${date} <br/>FOB VALUE: $${parseFloat(totalValue).toFixed(2)} <br/>QTY: ${parseFloat(totalQty).toFixed(2)} PCS</td>`;
            col2summary = `VALUE: $${parseFloat(totalValue).toFixed(2)} QTY: ${parseFloat(totalQty).toFixed(2)} PCS`;
        } else {
          row += `<tr id=${index}>
                <td id="col1-${index}"></td>
                <td id="col2-${index}"></td>`;
        }

        if (importLCList[index]) {
          tempItem = importLCList[index];
          row += `<td id="col3-${index}">L/C NO: ${tempItem.invoiceNo} <br/>Date:${tempItem.invoiceDate} <br/>$${parseFloat(tempItem.totalValue).toFixed(2)}</td>`;
          col3summary += parseFloat(tempItem.totalValue);
        } else {
          row += ` <td id="col3-${index}"></td>`;
        }

        if (udAmendmentList[index]) {
          tempItem = udAmendmentList[index];
          if (index == 0) row += `<td id="col4-${index}">${tempItem.udNo} <br/>Date:${tempItem.udAmendmentDate}</td>`;
          else row += `<td id="col4-${index}">AMND# ${tempItem.udAmendmentNo} <br/>Date:${tempItem.udAmendmentDate}</td>`;
        } else {
          row += ` <td id="col4-${index}"></td>`;
        }

        if (importLCItemList[index]) {
          tempItem = importLCItemList[index];
          row += `<td id="col5-${index}">${tempItem.accessoriesItemName}</td>
                  <td id="col6-${index}">${parseFloat(tempItem.totalQty).toFixed(2)} ${tempItem.unitName}</td>`;
          if (col6summary[tempItem.unitName]) {
            col6summary[tempItem.unitName].totalQty += parseFloat(tempItem.totalQty)
          } else {
            col6summary[tempItem.unitName] = {
              totalQty : parseFloat(tempItem.totalQty)
            }
          }
        } else {
          row += ` <td id="col5-${index}"></td><td id="col6-${index}"></td>`;
        }

        if (importBillEntryList[index]) {
          tempItem = importBillEntryList[index];
          row += `<td id="col7-${index}">${tempItem.billEntryNo} <br/>Date:${tempItem.billEntryDate}</td>`;
          col7summary++;
        } else {
          row += `<td id="col7-${index}"></td>`;
        }

        row += `<td id="col8-${index}" contenteditable="true"></td>`;
        console.log(exportInvoiceList[index]);
        if (exportInvoiceList[index]) {
          let exportInvoice = exportInvoiceList[index];
          let styleList = exportInvoice.exportedStyleItemList;
          let col9 = "";
          let col10 = "";
          let k = 0;
          let fabricsObject = {};

          for (let i = 0; i < styleList.length; i++) {

            let style = styleList[i];
            let fabricsList = style.fabricsList;

            for (let j = 0; j < fabricsList.length; j++) {

              let fabrics = fabricsList[j];

              col9 += `${parseFloat(style.quantity).toFixed(2)} PCS X ${parseFloat(fabrics.consumption).toFixed(2)} <br/>=${(parseFloat(style.quantity) * parseFloat(fabrics.consumption)).toFixed(2)} ${fabrics.unitName} <br/>`;

              if (fabricsObject[fabrics.accessoriesItemId+fabrics.unitId]) {
                fabricsObject[fabrics.accessoriesItemId].totalQty += (parseFloat(style.quantity) * parseFloat(fabrics.consumption));
              } else {
                fabricsObject[fabrics.accessoriesItemId+fabrics.unitId] = {
                  fabricsId: fabrics.accessoriesItemId,
                  fabricsName: fabrics.itemName,
                  unitName: fabrics.unitName,
                  totalQty: (parseFloat(style.quantity) * parseFloat(fabrics.consumption)).toFixed(2)
                }
              }

              if (col9summary[fabrics.unitName]) {
                col9summary[fabrics.unitName].totalQty += (parseFloat(style.quantity) * parseFloat(fabrics.consumption))
              } else {
                col9summary[fabrics.unitName] = {
                  totalQty : (parseFloat(style.quantity) * parseFloat(fabrics.consumption))
                }
              }
            }

            col10 += style.itemDescription + ",<br/>";
          }
          col9 += "<br/>" + Object.keys(fabricsObject).map((key) => {
            k++;
            return (k + ")" + fabricsObject[key].fabricsName + "=" + fabricsObject[key].totalQty) +" "+fabricsObject[key].unitName+"<br/>";
          });
          col9List.push(col9);
          row += `<td id="col9-${index}">${col9}</td>
          <td id="col10-${index}">${col10}</td>
          <td id="col11-${index}">${parseFloat(exportInvoice.quantity).toFixed(2)} PCS <br/>${parseFloat(exportInvoice.cartonQty).toFixed(2)} CTN</td>
          <td id="col12-${index}">${exportInvoice.billEntryNo} <br/>${exportInvoice.billEntryDate}</td>
          <td id="col13-${index}" contenteditable="true"></td>
          <td id="col14-${index}">${exportInvoice.invoiceNo} <br/>$${parseFloat(exportInvoice.totalValue).toFixed(2)}</td>
          <td id="col15-${index}" contenteditable="true"></td>
          <td id="col16-${index}">${exportInvoice.expNo} <br/>${exportInvoice.expDate}</td>`;

          col11PcsSummary += parseFloat(exportInvoice.quantity);
          col11CtnSummary += parseFloat(exportInvoice.cartonQty);
          col12summary++;
          col14summary += parseFloat(exportInvoice.totalValue);
        } else {
          col9List.push('');
          row += `<td id="col9-${index}"></td>
          <td id="col10-${index}"></td>
          <td id="col11-${index}"></td>
          <td id="col12-${index}"></td>
          <td id="col13-${index}" contenteditable="true"></td>
          <td id="col14-${index}"></td>
          <td id="col15-${index}" contenteditable="true"></td>
          <td id="col16-${index}"></td>`
        }

        row += `<td id="col17-${index}" contenteditable="true"></td>
        <td id="col18-${index}" contenteditable="true"></td>
        <td id="col19-${index}" contenteditable="true"></td>
        </tr>`;

        index++;
      }

      $("#dataList").empty();
      $("#dataList").html(row);

      $("#masterLC").val(masterLCNo);
      $("#col2Summary").val(col2summary);
      $("#col3Summary").val(col3summary);
      $("#col4Summary").val(col4summary);
      $("#col6Summary").val(Object.keys(col6summary).map(key =>{
        return col6summary[key].totalQty +" "+key;
      }));
      $("#col7Summary").val(col7summary);
      $("#col9Summary").val(Object.keys(col9summary).map(key =>{
        return col9summary[key].totalQty +" "+key;
      }));
      $("#col11Summary").val(col11PcsSummary+" PCS, "+col11CtnSummary+" CTN")
    
      $("#col12Summary").val(col12summary);
      $("#col14Summary").val(col14summary);
      $("#loader").hide();
    }
  });

  $("#searchModal").modal('hide');

}



function previewAction() {
  let masterLc = $("#masterLC").val();
  let fromDate = $("#fromDate").val();
  let toDate = $("#toDate").val();
  let rowList = $("#dataList tr");
  let length = rowList.length;
  let userId = $("#userId").val();
  let col15summary = 0;

  let passBookDataList = [];
  for (let i = 0; i < length; i++) {
    const newRow = rowList[i];
    const id = newRow.id;
    col15summary += Number($("#col15-" + id).text());
      
    passBookDataList.push({
      col1: $("#col1-" + id).text(),
      col2: $("#col2-" + id).text(),
      col3: $("#col3-" + id).text(),
      col4: $("#col4-" + id).text(),
      col5: $("#col5-" + id).text(),
      col6: $("#col6-" + id).text(),
      col7: $("#col7-" + id).text(),
      col8: $("#col8-" + id).text(),
      col9: col9List[i],
      col10: $("#col10-" + id).text(),
      col11: $("#col11-" + id).text(),
      col12: $("#col12-" + id).text(),
      col13: $("#col13-" + id).text(),
      col14: $("#col14-" + id).text(),
      col15: $("#col15-" + id).text(),
      col16: $("#col16-" + id).text(),
      col17: $("#col17-" + id).text(),
      col18: $("#col18-" + id).text(),
      col19: $("#col19-" + id).text(),
    });
  }

  $("#loader").show();
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: './savePassBookData',
    data: {
      passBookData: JSON.stringify({
        masterLC: masterLc,
        fromDate: fromDate,
        toDate: toDate,
        passBookDataList: passBookDataList,
        col1summary: $("#col1Summary").val(),
        col2summary: $("#col2Summary").val(),
        col3summary: $("#col3Summary").val(),
        col4summary: $("#col4Summary").val(),
        col5summary: $("#col5Summary").val(),
        col6summary: $("#col6Summary").val(),
        col7summary: $("#col7Summary").val(),
        col8summary: $("#col8Summary").val(),
        col9summary: $("#col9Summary").val(),
        col10summary: $("#col10Summary").val(),
        col11summary: $("#col11Summary").val(),
        col12summary: $("#col12Summary").val(),
        col13summary: $("#col13Summary").val(),
        col14summary: $("#col14Summary").val(),
        col15summary: col15summary,
        col16summary: $("#col16Summary").val(),
        col17summary: $("#col17Summary").val(),
        col18summary: $("#col18Summary").val(),
        col19summary: $("#col19Summary").val(),
        userId: userId
      })
    },
    success: function (data) {
      $("#loader").hide();

      var url = "master_lc_pass_book_preview/" + masterLc
      window.open(url, '_blank');
    }
  });

}


function confirmAction(){
  let rowList = $("#dataList tr");
  let length = rowList.length;
  for (let i = 0; i < length; i++) {
    const newRow = rowList[i];
    const id = newRow.id;

    let col1 =  $("#col1-" + id).text();
    let col2 =  $("#col2-" + id).text();
    let col3 =  $("#col3-" + id).text();
    let col4 =  $("#col4-" + id).text();
    let col5 =  $("#col5-" + id).text();
    let col6 =  $("#col6-" + id).text();
    let col7 =  $("#col7-" + id).text();
    let col8 =  $("#col8-" + id).text();
    let col9 =  $("#col9-" + id).text().replace('\n',"#");
    let col10 =  $("#col10-" + id).text();
    let col11 =  $("#col11-" + id).text();
    let col12 =  $("#col12-" + id).text();
    let col13 =  $("#col13-" + id).text();
    let col14 =  $("#col14-" + id).text();
    let col15 =  $("#col15-" + id).text();
    let col16 =  $("#col16-" + id).text();
    let col17 =  $("#col17-" + id).text();
    let col18 =  $("#col18-" + id).text();
    let col19 =  $("#col19-" + id).text();

    console.log("column 9=",col9);
    
  }
}