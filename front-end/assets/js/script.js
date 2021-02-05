$(document).ready(function () {
    $('#table_id').DataTable();
    $('#table_search').DataTable();
});

// button active
var header = document.getElementById("kategori-search");
var btns = header.getElementsByClassName("btn");
for (var i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function () {
        var current = document.getElementsByClassName("active");
        // console.log(current[0].className);
        current[0].className = current[0].className.replace(" active", "");
        this.className += " active";
    });
}