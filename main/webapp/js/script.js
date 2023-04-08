var btn = document.querySelector("button");
btn.addEventListener("click", function(){
    location.href = btn.getAttribute("href");
});
