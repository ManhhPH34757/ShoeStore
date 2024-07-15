function redirectToProductDetails(id) {
    window.location.href = `/products/details/${id}`;
}

$(document).ready(function () {
    $('#colorSelect, #sizeSelect').change(function () {
        updateProductDetails();
    });

    function updateProductDetails() {
        let selectColors = $('#colorSelect').val();
        let selectSizes = $('#sizeSelect').val();
        let productName = $('#productName').val();
        let idProduct = $('#idProduct').text();

        let tableHead = $('#headTable');
        tableHead.empty();
        let tableBody = $('#productDetailsTable');
        tableBody.empty();

        if (selectColors.length > 0 && selectSizes.length > 0) {
            let rowHead = '<tr>\n' +
                '                <th>#</th>\n' +
                '                <th>Product Name</th>\n' +
                '                <th>Quantity</th>\n' +
                '                <th>Price</th>\n' +
                '                <th hidden="hidden">ID Product</th>\n' +
                '                <th hidden="hidden">ID Color</th>\n' +
                '                <th hidden="hidden">ID Size</th>\n' +
                '                <th>Action</th>\n' +
                '            </tr>';
            tableHead.append(rowHead);
            selectColors.forEach(function (color) {
                let colorText = $('#colorSelect option[value="'+color+'"]').text();
                selectSizes.forEach(function (size) {
                    let sizeText = $('#sizeSelect option[value="'+size+'"]').text();

                    let row = '<tr>' +
                        '<td><input type="checkbox""></td>' +
                        '<td>' + productName + ' [Color ' + colorText + ' - Size ' + sizeText + ']' + '</td>' +
                        '<td><input class="form-control quantity" value="10" name="quantity" type="number" min="1"></td>' +
                        '<td><input class="form-control price" value="150000" name="price" type="number" min="1"></td>' +
                        '<td hidden="hidden">' + idProduct + '</td>' +
                        '<td hidden="hidden">' + color + '</td>' +
                        '<td hidden="hidden">' + size + '</td>' +
                        '<td><a href="#" class="btn btn-outline-danger delete-btn">Delete</a></td>' +
                        '</tr>';
                    tableBody.append(row);
                });
            });

            // Add event to btn delete
            $('.delete-btn').click(function (e) {
                e.preventDefault();
                // $(this).closest('tr').remove();
                $(this).parent().parent().remove();
                if ($('#productDetailsTable tr').length === 0) {
                    tableHead.empty();
                }
            });
        } else {
            tableHead.empty();
            tableBody.empty();
        }
    }

    $('#saveProductDetails').click(function (e) {
        let productDetails = [];

        $('#productDetailsTable tr').each(function () {
            let productDetail = {
                idProduct: $(this).find('td:eq(4)').text(),
                idColor: $(this).find('td:eq(5)').text(),
                idSize: $(this).find('td:eq(6)').text(),
                quantity: $(this).find('td:eq(2) input').val(),
                price: $(this).find('td:eq(3) input').val()
            };

            productDetails.push(productDetail);
        });
        console.log(productDetails);
        let idProduct = $('#idProduct').text();
        console.log(idProduct)
        $.ajax({
            url: '/products/create-details/' + idProduct,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(productDetails),
            success: function(response) {
                alert('Products saved successfully!');
                window.location.href = '/products/details/' + idProduct;  // Redirect to product details page
            },
            error: function(error) {
                alert('Giang tb: ' + error.responseText);
            }
        });
    });

});