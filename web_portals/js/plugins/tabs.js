function setTabs() {
  var e = document.querySelectorAll(".tabs .nav-tabs");
  e && e.forEach(e => {
    e.querySelectorAll("a").forEach(e => {
      e.addEventListener("click", e => {
        e.preventDefault(), e.stopPropagation();
        var t = e.target.parentElement.parentElement.parentElement;
        return t.querySelector(".nav-tabs .active").classList.remove("active"), e.target.parentElement.classList.add("active"), t.querySelector(".tab-content .active").classList.remove("active"), t.querySelector(e.target.className).classList.add("active"), !1
      })
    })
  })
}

!0 === Global.theme_config.global.pjax && Global.utils ? setTabs() : window.addEventListener("DOMContentLoaded", setTabs);