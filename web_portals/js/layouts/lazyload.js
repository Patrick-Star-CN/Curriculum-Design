Global.initLazyLoad = () => {
  const t = document.querySelectorAll("img");
  let e = Date.now(), a = !0;

  function o(t) {
    e = Date.now(), a = Array.from(t).some(t => t.hasAttribute("lazyload"));
    const o = window.innerHeight, n = document.documentElement.scrollTop || document.body.scrollTop;
    t.forEach(e => {
      if (e.hasAttribute("lazyload") && !e.hasAttribute("loading") && o + n > e.offsetTop) {
        e.setAttribute("loading", !0);
        const a = setTimeout(() => {
          var t = new Image;
          const o = e.getAttribute("data-src");
          t.src = o, t.onload = () => {
            e.src = o, e.removeAttribute("lazyload"), e.removeAttribute("loading"), clearTimeout(a)
          }
        }, 500)
      }
    })
  }

  o(t), window.onscroll = () => {
    50 < Date.now() - e && a && o(t)
  }
};