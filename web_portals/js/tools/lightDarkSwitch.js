Global.initModeToggle = () => {
  Global.utils.modeToggle = {
    modeToggleButton_dom: document.querySelector(".tool-dark-light-toggle"),
    iconDom: document.querySelector(".tool-dark-light-toggle i"),
    mermaidLightTheme: void 0 !== Global.theme_config.mermaid && void 0 !== Global.theme_config.mermaid.style && void 0 !== Global.theme_config.mermaid.style.light ? Global.theme_config.mermaid.style.light : "default",
    mermaidDarkTheme: void 0 !== Global.theme_config.mermaid && void 0 !== Global.theme_config.mermaid.style && void 0 !== Global.theme_config.mermaid.style.dark ? Global.theme_config.mermaid.style.dark : "dark",
    enableLightMode() {
      document.body.classList.remove("dark-mode"), document.body.classList.add("light-mode"), this.iconDom.className = "fa-regular fa-moon", Global.styleStatus.isDark = !1, Global.setStyleStatus(), this.mermaidLightInit()
    },
    enableDarkMode() {
      document.body.classList.add("dark-mode"), document.body.classList.remove("light-mode"), this.iconDom.className = "fa-regular fa-brightness", Global.styleStatus.isDark = !0, Global.setStyleStatus(), this.mermaidDarkInit()
    },
    mermaidLightInit() {
      window.mermaid && mermaid.initialize({theme: this.mermaidLightTheme})
    },
    mermaidDarkInit() {
      window.mermaid && mermaid.initialize({theme: this.mermaidDarkTheme})
    },
    isDarkPrefersColorScheme() {
      return window.matchMedia && window.matchMedia("(prefers-color-scheme: dark)")
    },
    initModeStatus() {
      var e = Global.getStyleStatus();
      e ? e.isDark ? this.enableDarkMode() : this.enableLightMode() : this.isDarkPrefersColorScheme().matches ? this.enableDarkMode() : this.enableLightMode()
    },
    initModeToggleButton() {
      this.modeToggleButton_dom.addEventListener("click", () => {
        document.body.classList.contains("dark-mode") ? this.enableLightMode() : this.enableDarkMode()
      })
    },
    initModeAutoTrigger() {
      this.isDarkPrefersColorScheme().addEventListener("change", e => {
        e.matches ? this.enableDarkMode() : this.enableLightMode()
      })
    }
  }, Global.utils.modeToggle.initModeStatus(), Global.utils.modeToggle.initModeToggleButton(), Global.utils.modeToggle.initModeAutoTrigger()
};