/* globals Chart:false, feather:false */

(function () {
    'use strict'

    feather.replace({'aria-hidden': 'true'})

    // Lấy tất cả các phần tử có class "nav-link"
    const navLinks = document.querySelectorAll('.nav-link');

// Thêm sự kiện click cho mỗi phần tử "nav-link"
    navLinks.forEach(link => {
        link.addEventListener('click', function () {
            // Loại bỏ class "active" ở tất cả các "nav-link"
            navLinks.forEach(link => link.classList.remove('active'));

            // Thêm class "active" cho phần tử được click
            this.classList.add('active');
        });
    });
})();

