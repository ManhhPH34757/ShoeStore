/* globals Chart:false, feather:false */

(function () {
    'use strict'

    feather.replace({'aria-hidden': 'true'})

    // Lấy tất cả các phần tử có class "nav-link"
    const dashBoard = document.getElementById('dashBoard');

            // Thêm class "active" cho phần tử được click
            dashBoard.classList.add('active');
})();