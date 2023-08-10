function initTOC() {
  Global.utils.navItems = document.querySelectorAll(".post-toc-wrap .post-toc li"), 0 < Global.utils.navItems.length ? (Global.utils = {
    ...Global.utils, updateActiveTOCLink() {
      if (Array.isArray(Global.utils.sections)) {
        let e = Global.utils.sections.findIndex(e => e && 0 < e.getBoundingClientRect().top - 200);
        -1 === e ? e = Global.utils.sections.length - 1 : 0 < e && e--, this.activateTOCLink(e)
      }
    }, registerTOCScroll() {
      Global.utils.sections = [...document.querySelectorAll(".post-toc li a.nav-link")].map(e => {
        const t = document.getElementById(decodeURI(e.getAttribute("href")).replace("#", ""));
        return e.addEventListener("click", e => {
          e.preventDefault();
          e = t.getBoundingClientRect().top + window.scrollY;
          window.anime({
            targets: document.scrollingElement,
            duration: 500,
            easing: "linear",
            scrollTop: e - 10,
            complete: function () {
              setTimeout(() => {
                Global.utils.pageTop_dom.classList.add("hide")
              }, 100)
            }
          })
        }), t
      })
    }, activateTOCLink(e) {
      var t, l, o, e = document.querySelectorAll(".post-toc li a.nav-link")[e];
      e && !e.classList.contains("active-current") && (document.querySelectorAll(".post-toc .active").forEach(e => {
        e.classList.remove("active", "active-current")
      }), e.classList.add("active", "active-current"), o = (t = document.querySelector(".toc-content-container")).getBoundingClientRect().top, l = t.offsetHeight > window.innerHeight ? (t.offsetHeight - window.innerHeight) / 2 : 0, o = e.getBoundingClientRect().top - o - Math.max(document.documentElement.clientHeight, window.innerHeight || 0) / 2 + e.offsetHeight / 2 - l, e = t.scrollTop + o, window.anime({
        targets: t,
        duration: 300,
        easing: "easeOutQuad",
        scrollTop: e
      }))
    }, showTOCAside() {
      var e = () => {
        var e = Global.getStyleStatus(), t = "isOpenPageAside";
        e && e.hasOwnProperty(t) ? Global.utils.TocToggle.pageAsideHandleOfTOC(e[t]) : Global.utils.TocToggle.pageAsideHandleOfTOC(!0)
      }, t = "init_open";
      !Global.theme_config.articles.toc.hasOwnProperty(t) || Global.theme_config.articles.toc[t] ? e() : Global.utils.TocToggle.pageAsideHandleOfTOC(!1)
    }
  }, Global.utils.showTOCAside(), Global.utils.registerTOCScroll()) : document.querySelectorAll(".toc-content-container, .toc-marker").forEach(e => {
    e.remove()
  })
}

!0 === Global.theme_config.global.pjax && Global.utils ? initTOC() : window.addEventListener("DOMContentLoaded", initTOC);