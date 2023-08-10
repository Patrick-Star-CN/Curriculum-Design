function initTocToggle() {
  Global.utils.TocToggle = {
    toggleBar: document.querySelector(".page-aside-toggle"),
    postPageContainerDom: document.querySelector(".post-page-container"),
    toggleBarIcon: document.querySelector(".page-aside-toggle i"),
    articleContentContainerDom: document.querySelector(".article-content-container"),
    mainContentDom: document.querySelector(".main-content"),
    isOpenPageAside: !1,
    initToggleBarButton() {
      this.toggleBar && this.toggleBar.addEventListener("click", () => {
        this.isOpenPageAside = !this.isOpenPageAside, Global.styleStatus.isOpenPageAside = this.isOpenPageAside, Global.setStyleStatus(), this.changePageLayoutWhenOpenToggle(this.isOpenPageAside)
      })
    },
    changePageLayoutWhenOpenToggle(e) {
      this.toggleBarIcon && (this.toggleBarIcon.className = e ? "fas fa-indent" : "fas fa-outdent"), this.postPageContainerDom.className = e ? "post-page-container show-toc" : "post-page-container", this.mainContentDom.className = e ? "main-content has-toc" : "main-content"
    },
    pageAsideHandleOfTOC(e) {
      this.toggleBar.style.display = "flex", this.isOpenPageAside = e, this.changePageLayoutWhenOpenToggle(e)
    }
  }, Global.utils.TocToggle.initToggleBarButton()
}

!0 === Global.theme_config.global.pjax && Global.utils ? initTocToggle() : window.addEventListener("DOMContentLoaded", initTocToggle);