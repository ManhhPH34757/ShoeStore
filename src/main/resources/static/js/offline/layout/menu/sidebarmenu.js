/*
Template Name: Admin Template
Author: Wrappixel

File: js
*/
// ==============================================================
// Auto select left navbar
// ==============================================================
$(function () {
    "use strict";
    let url = window.location + "";
    let path = url.split('/')[3];

    let element = $("ul#sidebarnav a").filter(function () {
         return this.id === path; // || url.href.indexOf(this.href) === 0;
    });

    console.log(element);

    element.addClass("active");
    $("#sidebarnav a").on("click", function (e) {
        if (!$(this).hasClass("active")) {
            // hide any open menus and remove all other classes
            $("a", $(this).parents("ul:first")).removeClass("active");

            // open our new menu and add the open class
            $(this).addClass("active");
        } else if ($(this).hasClass("active")) {
            $(this).removeClass("active");
        }
    });
});