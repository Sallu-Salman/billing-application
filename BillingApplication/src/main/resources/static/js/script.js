
function setItemForDelete(itemId){
    document.querySelector('#itemIdToDelete').value = itemId;
}

function setContactForDelete(contactId){
	document.querySelector("#contactIdToDelete").value = contactId;
}

function setInvoiceForDelete(invoiceId){
	document.querySelector("#invoiceIdToDelete").value = invoiceId;
}

function removeLineItem(button) {
	var row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
    updateIndexes();
    updateTotalAmount();
}

function addItem(){
	var table = document.querySelector("#lineItemTable tbody");
	var newRow = document.createElement("tr");

	var itemListData = itemList;

	console.log("BEGIN");
	newRow.innerHTML = `
		<td class="align-middle snum px-3 bg-light" th:text="${status.count}"></td>
        <td class="px-1 align-middle">
            <select class="form-select-sm border-0" name="lineItems[__index__].itemName" th:field="*{lineItems[__${status.index}__].itemName}" onchange="updateLineItem(this)" required>
				<option selected="selected" disabled="disabled"> </option>
				${itemListData.map(item => `<option value="${item.itemName}">${item.itemName}</option>`).join("")}
            </select>
        </td>
        <td class="px-3"><input class="form-control-plaintext" th:field="*{lineItems[__${status.index}__].itemQuantity}" type="number" name="lineItems[__index__].itemQuantity" onchange="updateLineAmount(this)" /></td>
        <td class="px-3"><input class="form-control-plaintext" th:field="*{lineItems[__${status.index}__].itemSellingPrice}" type="number" step="any" name="lineItems[__index__].itemSellingPrice" onchange="updateLineAmount(this)"/></td>
		<td class="px-3 bg-light"><input class="form-control-plaintext" th:field="*{lineItems[__${status.index}__].taxAmount}" type="number" step="any" name="lineItems[__index__].taxAmount" readonly/></td>
		<td class="px-3 bg-light"><input class="form-control-plaintext" th:field="*{lineItems[__${status.index}__].totalAmount}" type="number" step="any" name="lineItems[__index__].totalAmount" readonly/></td>
		<td class="align-middle px-3"><a onclick="removeLineItem(this)" th:onclick="removeLineItem(this)" ><img src="/images/delete.png" alt="delete" width="15px"></a></td>
    `;
	table.appendChild(newRow);
    updateIndexes();

	console.log("END");
}

function updateIndexes() {
    var rows = document.querySelectorAll('#lineItemTable tbody tr');
    rows.forEach(function(row, index) {
        var inputs = row.querySelectorAll('input');
        var selects = row.querySelector('select');
        selects.name = selects.name.replace(/__index__/g, index);
		row.querySelector('.snum').innerHTML = index+1;
        inputs.forEach(function(input) {
            input.name = input.name.replace(/__index__/g, index);
        });
    });
}

function updateLineItem(selectElement) {
	var selectedValue = selectElement.value;

	var selectedItem = itemList.find(function(item) {
		return item.itemName === selectedValue;
	});

	var row = selectElement.closest("tr");
	row.querySelector("[name^='lineItems'][name$='itemSellingPrice']").value = selectedItem.itemSellingPrice;
	row.querySelector("[name^='lineItems'][name$='itemQuantity']").value = 1;
	row.querySelector("[name^='lineItems'][name$='taxAmount']").value = itemTaxMap[selectedItem.itemId];
	updateLineAmount(selectElement);
}

function updateLineAmount(selectElement) {
	var row = selectElement.closest("tr");

	var price = row.querySelector("[name^='lineItems'][name$='itemSellingPrice']").value;
	var quantity = row.querySelector("[name^='lineItems'][name$='itemQuantity']").value;

	var total = (price * quantity);
	row.querySelector("[name^='lineItems'][name$='totalAmount']").value = (total).toFixed(2);

	updateTotalAmount();
}

function updateTotalAmount() {
	var rows = document.querySelectorAll('#lineItemTable tbody tr');
	var subTotal = 0;
	var taxAmount = 0;

	rows.forEach(function(row, index) {
		var rowTotal = parseFloat(row.querySelector("[name^='lineItems'][name$='totalAmount']").value)
        subTotal += rowTotal;
		var taxPercent = parseFloat(row.querySelector("[name^='lineItems'][name$='taxAmount']").value)
		taxAmount += (rowTotal * (taxPercent / 100));
    });

	var taxField = document.querySelector('#tax');
	var subTotalField = document.querySelector('#subTotal');
	var discountField = document.querySelector('#discount');
	var chargesField = document.querySelector('#charges');
	var totalCost = document.querySelector('#totalCost');

	taxField.value = taxAmount.toFixed(2);
	subTotalField.value = subTotal.toFixed(2)

	totalCost.value = (subTotal + taxAmount + parseFloat(chargesField.value) - parseFloat(discountField.value)).toFixed(2);
}

function downloadInvoice(){
	const divToDownload = document.getElementById('invoiceTemplate');

	console.log("hello");
	html2canvas(divToDownload).then(canvas => {
        const imgData = canvas.toDataURL('image/png');

        const aspectRatio = divToDownload.offsetWidth / divToDownload.offsetHeight;

        const pdf = new jsPDF({
          orientation: aspectRatio > 1 ? 'l' : 'p', 
          unit: 'px',
          format: [canvas.width, canvas.height],
        });

        pdf.addImage(imgData, 'PNG', 0, 0, pdf.internal.pageSize.getWidth(), pdf.internal.pageSize.getHeight());

        pdf.save('download.pdf');
      });
}

$(document).ready(function() {
	$('[data-toggle="tooltip"]').tooltip();
});

var xValues = ["Amount Received", "Amount Not Received"];
var yValues = [receivedAmount, notReceivedAmount];
var barColors = ["aqua", "lightseagreen"];

new Chart("receivableChart", {
  type: "pie",
  data: {
    labels: xValues,
    datasets: [{
      backgroundColor: barColors,
      data: yValues
    }]
  },
  options: {
    title: {
      display: true,
      text: "Amount Receivable"
    }
  }
});




