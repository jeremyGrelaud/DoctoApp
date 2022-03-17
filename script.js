var navbar = document.getElementById("navbar");
var menu = document.getElementById("menu");

//function that will run when we scroll the web page
window.onscroll = function(){
    if( window.pageYOffset >= menu.offsetTop ){
        navbar.classList.add("sticky");
    }
    else{
        navbar.classList.remove("sticky");
    }
}