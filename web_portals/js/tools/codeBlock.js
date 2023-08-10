Global.initCopyCode = () => {
  HTMLElement.prototype.wrap = function (e) {
    this.parentNode.insertBefore(e, this), this.parentNode.removeChild(this), e.appendChild(this)
  }, document.querySelectorAll("figure.highlight").forEach(e => {
    const t = document.createElement("div"),
      o = (e.wrap(t), t.classList.add("highlight-container"), t.insertAdjacentHTML("beforeend", '<div class="copy-button"><i class="fa-regular fa-copy"></i></div>'), t.insertAdjacentHTML("beforeend", '<div class="fold-button"><i class="fa-solid fa-chevron-down"></i></div>'), t.querySelector(".copy-button")),
      i = t.querySelector(".fold-button");
    o.addEventListener("click", () => {
      var e = [...t.querySelectorAll(".code .line")].map(e => e.innerText).join("\n");
      navigator.clipboard.writeText(e), o.querySelector("i").className = "fa-regular fa-check", setTimeout(() => {
        o.querySelector("i").className = "fa-regular fa-copy"
      }, 1e3)
    }), i.addEventListener("click", () => {
      t.classList.toggle("folded"), i.querySelector("i").className = t.classList.contains("folded") ? "fa-solid fa-chevron-up" : "fa-solid fa-chevron-down"
    })
  })
};