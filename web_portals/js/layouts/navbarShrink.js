const navbarShrink = {
  navbarDom: document.querySelector(".navbar-container"),
  leftAsideDom: document.querySelector(".page-aside"),
  isnavbarShrink: !1,
  navbarHeight: 0,
  init() {
    this.navbarHeight = this.navbarDom.getBoundingClientRect().height, this.navbarShrink(), this.togglenavbarDrawerShow(), window.addEventListener("scroll", () => {
      this.navbarShrink()
    })
  },
  navbarShrink() {
    var a = document.documentElement.scrollTop || document.body.scrollTop;
    !this.isnavbarShrink && a > this.navbarHeight ? (this.isnavbarShrink = !0, document.body.classList.add("navbar-shrink")) : this.isnavbarShrink && a <= this.navbarHeight && (this.isnavbarShrink = !1, document.body.classList.remove("navbar-shrink"))
  },
  togglenavbarDrawerShow() {
    var a = [document.querySelector(".window-mask"), document.querySelector(".navbar-bar")],
      a = (document.querySelector(".navbar-drawer") && (a.push(...document.querySelectorAll(".navbar-drawer .drawer-navbar-list .drawer-navbar-item")), a.push(...document.querySelectorAll(".navbar-drawer .drawer-navbar-list .dropdown-item"))), a.forEach(a => {
        a.dataset.navbarInitialized || (a.dataset.navbarInitialized = 1, a.addEventListener("click", () => {
          document.body.classList.toggle("navbar-drawer-show")
        }))
      }), document.querySelector(".navbar-container .navbar-content .logo-title"));
    a && !a.dataset.navbarInitialized && (a.dataset.navbarInitialized = 1, a.addEventListener("click", () => {
      document.body.classList.remove("navbar-drawer-show")
    }))
  }
};
navbarShrink.init();