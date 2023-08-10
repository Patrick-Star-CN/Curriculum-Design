const usrTypeSpeed = Global.theme_config.home_banner.subtitle.typing_speed,
  usrBackSpeed = Global.theme_config.home_banner.subtitle.backing_speed,
  usrBackDelay = Global.theme_config.home_banner.subtitle.backing_delay,
  usrStartDelay = Global.theme_config.home_banner.subtitle.starting_delay,
  usrLoop = Global.theme_config.home_banner.subtitle.loop,
  usrSmartBackspace = Global.theme_config.home_banner.subtitle.smart_backspace,
  usrHitokotoAPI = Global.theme_config.home_banner.subtitle.hitokoto.api;
Global.theme_config.home_banner.subtitle.hitokoto.enable ? Global.initTyped = t => {
  fetch(usrHitokotoAPI).then(e => e.json()).then(e => {
    e = e.hitokoto, new Typed("#" + t, {
      strings: [e],
      typeSpeed: usrTypeSpeed || 100,
      smartBackspace: usrSmartBackspace || !1,
      backSpeed: usrBackSpeed || 80,
      backDelay: usrBackDelay || 1500,
      loop: usrLoop || !1,
      startDelay: usrStartDelay || 500
    })
  }).catch(console.error)
} : Global.initTyped = e => {
  var t = [];
  for (const a of Global.theme_config.home_banner.subtitle.text) t.push(a);
  document.getElementById(e) && new Typed("#" + e, {
    strings: t,
    typeSpeed: usrTypeSpeed || 100,
    smartBackspace: usrSmartBackspace || !1,
    backSpeed: usrBackSpeed || 80,
    backDelay: usrBackDelay || 1500,
    loop: usrLoop || !1,
    startDelay: usrStartDelay || 500
  })
};