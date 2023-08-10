let Global = window.Global || {};
Global.hexo_config = {
  hostname: "s-chance.github.io",
  root: "/",
  language: "zh-CN",
  path: "search.xml"
}, Global.theme_config = {
  articles: {
    style: {
      font_size: "16px",
      line_height: 1.5,
      image_border_radius: "14px",
      image_alignment: "center",
      image_caption: !1,
      link_icon: !0
    },
    word_count: {enable: !0, count: !0, min2read: !0},
    author_label: {enable: !0, auto: !1, list: [""]},
    code_block: {copy: !0, style: "mac", font: {enable: !1, family: null, url: null}},
    toc: {enable: !0, max_depth: 3, number: !1, expand: !0, init_open: !0},
    copyright: !0,
    lazyload: !0,
    recommendation: {
      enable: !0,
      title: "推荐阅读",
      limit: 3,
      placeholder: "/images/wallhaven-wqery6-light.webp",
      skip_dirs: []
    }
  },
  colors: {primary: "#A31F34", secondary: null},
  global: {
    fonts: {chinese: {enable: !1, family: null, url: null}, english: {enable: !1, family: null, url: null}},
    content_max_width: "1000px",
    sidebar_width: "210px",
    hover: {shadow: !0, scale: !1},
    scroll_progress: {bar: !0, percentage: !1},
    busuanzi_counter: {enable: !0, site_pv: !0, site_uv: !0, post_pv: !0},
    pjax: !0,
    open_graph: !0,
    google_analytics: {enable: !1, id: null}
  },
  home_banner: {
    enable: !0,
    style: "static",
    image: {light: "/images/wallhaven-wqery6-light.webp", dark: "/images/wallhaven-wqery6-dark.webp"},
    title: "比奇堡资讯站",
    subtitle: {
      text: ["the truth of life"],
      hitokoto: {enable: !0, api: "https://v1.hitokoto.cn"},
      typing_speed: 100,
      backing_speed: 80,
      starting_delay: 500,
      backing_delay: 1500,
      loop: !0,
      smart_backspace: !0
    },
    text_color: {light: "#fff", dark: "#d1d1b6"},
    text_style: {title_size: "2.8rem", subtitle_size: "1.5rem", line_height: 1.2},
    custom_font: {enable: !1, family: null, url: null},
    social_links: {
      enable: !0,
      links: {
        github: "https://github.com/s-chance",
        instagram: null,
        zhihu: null,
        twitter: "https://twitter.com/patrick_star_tree",
        email: "mailto:patrick_star.tree@outlook.com",
        "stack-overflow": "https://stackoverflow.com/users/19329372/patrick_star"
      }
    }
  },
  plugins: {
    feed: {enable: !0},
    aplayer: {
      enable: !0,
      type: "fixed",
      audios: [{
        name: "千千阕歌",
        artist: "陈慧娴",
        url: "/music/陈慧娴 - 千千阕歌.mp3",
        cover: "/music/陈慧娴 - 千千阕歌.jpg.webp"
      }, {
        name: "被动",
        artist: "徐佳莹、伍佰",
        url: "/music/徐佳莹; 伍佰 - 被动.mp3",
        cover: "/music/徐佳莹; 伍佰 - 被动.jpg"
      }, {
        name: "月蚀",
        artist: "莫文蔚",
        url: "/music/莫文蔚 - 月蚀.mp3",
        cover: "/music/莫文蔚 - 月蚀.jpg"
      }, {
        name: "我记得",
        artist: "赵雷",
        url: "/music/赵雷 - 我记得.mp3",
        cover: "/music/赵雷 - 我记得.jpg"
      }, {
        name: "未单身",
        artist: "黄丽玲",
        url: "/music/黄丽玲 - 未单身.mp3",
        cover: "/music/黄丽玲 - 未单身.jpg"
      }]
    },
    mermaid: {enable: !1, version: "9.3.0"}
  },
  version: "2.1.3",
  navbar: {
    auto_hide: !0,
    color: {left: "#f78736", right: "#367df7", transparency: 35},
    links: {
      Home: {path: "/", icon: "fa-regular fa-house"},
      Archives: {path: "/archives", icon: "fa-regular fa-archive"},
      About: {
        icon: "fa-regular fa-user",
        submenus: {
          Me: "/about",
          Github: "https://github.com/s-chance",
          Blog: "https://www.patrick_star-tree.top",
          RSS: "atom.xml"
        }
      },
      Friends: {path: "/links", icon: "fa-solid fa-link"}
    },
    search: {enable: !0, preload: !0}
  },
  page_templates: {friends_column: 2, tags_style: "blur"},
  home: {
    sidebar: {
      enable: !0,
      position: "left",
      first_item: "menu",
      announcement: null,
      links: {
        Archives: {path: "/archives", icon: "fa-regular fa-archive"},
        Tags: {path: "/tags", icon: "fa-regular fa-tags"},
        Categories: {path: "/categories", icon: "fa-regular fa-folder"},
        RSS: {path: "atom.xml", icon: "fa-solid fa-rss"}
      }
    }, article_date_format: "auto", categories: {enable: !0, limit: 3}, tags: {enable: !0, limit: 3}
  }
}
Global.language_ago = {
  second: "%s 秒前",
  minute: "%s 分钟前",
  hour: "%s 小时前",
  day: "%s 天前",
  week: "%s 周前",
  month: "%s 个月前",
  year: "%s 年前"
}