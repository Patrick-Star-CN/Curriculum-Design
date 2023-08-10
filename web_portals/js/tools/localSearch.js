Global.initLocalSearch = () => {
  let e = Global.hexo_config.path;
  if (e) {
    let t = !1, n, o = !0;
    0 === e.length ? e = "search.xml" : e.endsWith("json") && (o = !1);
    const r = document.querySelector(".search-input"), l = document.getElementById("search-result"), v = (e, t, n) => {
        var o = e.length;
        if (0 === o) return [];
        let r = 0;
        var l, s = [];
        for (n || (t = t.toLowerCase(), e = e.toLowerCase()); -1 < (l = t.indexOf(e, r));) s.push({
          position: l,
          word: e
        }), r = l + o;
        return s
      }, m = (e, t, n, o) => {
        var r;
        let {position: l, word: s} = n[n.length - 1];
        var a = [];
        let i = 0;
        for (; l + s.length <= t && 0 !== n.length;) {
          s === o && i++, a.push({position: l, length: s.length});
          var c = l + s.length;
          for (n.pop(); 0 !== n.length && (r = n[n.length - 1], l = r.position, s = r.word, c > l);) n.pop()
        }
        return {hits: a, start: e, end: t, searchTextCount: i}
      }, x = (n, e) => {
        let o = "", r = e.start;
        return e.hits.forEach(e => {
          o += n.substring(r, e.position);
          var t = e.position + e.length;
          o += `<b class="search-keyword">${n.substring(e.position, t)}</b>`, r = t
        }), o += n.substring(r, e.end)
      }, s = () => {
        if (t) {
          let p = r.value.trim().toLowerCase(), g = p.split(/[-\s]+/), f = (1 < g.length && g.push(p), []);
          if (0 < p.length && n.forEach(({title: e, content: o, url: r}) => {
            let t = e.toLowerCase(), n = o.toLowerCase(), l = [], s = [], a = 0;
            if (g.forEach(e => {
              l = l.concat(v(e, t, !1)), s = s.concat(v(e, n, !1))
            }), 0 < l.length || 0 < s.length) {
              var i = l.length + s.length, c = ([l, s].forEach(e => {
                e.sort((e, t) => t.position !== e.position ? t.position - e.position : e.word.length - t.word.length)
              }), []);
              0 !== l.length && (d = m(0, e.length, l, p), a += d.searchTextCountInSlice, c.push(d));
              let n = [];
              for (; 0 !== s.length;) {
                var {position: h, word: u} = s[s.length - 1];
                let e = h - 20, t = h + 80;
                e < 0 && (e = 0), (t = t < h + u.length ? h + u.length : t) > o.length && (t = o.length);
                h = m(e, t, s, p);
                a += h.searchTextCountInSlice, n.push(h)
              }
              n.sort((e, t) => e.searchTextCount !== t.searchTextCount ? t.searchTextCount - e.searchTextCount : e.hits.length !== t.hits.length ? t.hits.length - e.hits.length : e.start - t.start);
              var d = parseInt(Global.theme_config.navbar.search.top_n_per_article || 1, 10);
              0 <= d && (n = n.slice(0, d));
              let t = "";
              t += 0 !== c.length ? `<li><a href="${r}" class="search-result-title">${x(e, c[0])}</a>` : `<li><a href="${r}" class="search-result-title">${e}</a>`, n.forEach(e => {
                t += `<a href="${r}"><p class="search-result">${x(o, e)}...</p></a>`
              }), t += "</li>", f.push({item: t, id: f.length, hitCount: i, searchTextCount: a})
            }
          }), 1 === g.length && "" === g[0]) l.innerHTML = '<div id="no-result"><i class="fa-solid fa-magnifying-glass fa-5x"></i></div>'; else if (0 === f.length) l.innerHTML = '<div id="no-result"><i class="fa-solid fa-box-open fa-5x"></i></div>'; else {
            f.sort((e, t) => e.searchTextCount !== t.searchTextCount ? t.searchTextCount - e.searchTextCount : e.hitCount !== t.hitCount ? t.hitCount - e.hitCount : t.id - e.id);
            let t = '<ul class="search-result-list">';
            f.forEach(e => {
              t += e.item
            }), t += "</ul>", l.innerHTML = t, window.pjax && window.pjax.refresh(l)
          }
        }
      }, a = () => {
        fetch(Global.hexo_config.root + e).then(e => e.text()).then(e => {
          t = !0, n = (n = o ? [...(new DOMParser).parseFromString(e, "text/xml").querySelectorAll("entry")].map(e => ({
            title: e.querySelector("title").textContent,
            content: e.querySelector("content").textContent,
            url: e.querySelector("url").textContent
          })) : JSON.parse(e)).filter(e => e.title).map(e => (e.title = e.title.trim(), e.content = e.content ? e.content.trim().replace(/<[^>]+>/g, "") : "", e.url = decodeURIComponent(e.url).replace(/\/{2,}/g, "/"), e));
          e = document.querySelector("#no-result");
          e && (e.innerHTML = '<i class="fa-solid fa-magnifying-glass fa-5x"></i>')
        })
      },
      i = (Global.theme_config.navbar.search.preload && a(), r && r.addEventListener("input", s), document.querySelectorAll(".search-popup-trigger").forEach(e => {
        e.addEventListener("click", () => {
          document.body.style.overflow = "hidden", document.querySelector(".search-pop-overlay").classList.add("active"), setTimeout(() => r.focus(), 500), t || a()
        })
      }), () => {
        document.body.style.overflow = "", document.querySelector(".search-pop-overlay").classList.remove("active")
      });
    document.querySelector(".search-pop-overlay").addEventListener("click", e => {
      e.target === document.querySelector(".search-pop-overlay") && i()
    }), document.querySelector(".search-input-field-pre").addEventListener("click", () => {
      r.value = "", r.focus(), s()
    }), document.querySelector(".popup-btn-close").addEventListener("click", i), window.addEventListener("pjax:success", i), window.addEventListener("keyup", e => {
      "Escape" === e.key && i()
    })
  } else console.warn("`hexo-generator-searchdb` plugin is not installed!")
};